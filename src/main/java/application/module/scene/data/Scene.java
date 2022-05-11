package application.module.scene.data;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import application.client.Client.SendToClientJ;
import application.guid.IdUtils;
import application.module.common.CommonProtocolBuilder;
import application.module.common.CommonProtocols;
import application.module.fight.attribute.data.message.MonsterDead;
import application.module.fight.attribute.data.message.PlayerDead;
import application.module.fight.buff.data.message.AddBuff;
import application.module.fight.skill.FightSkillProtocolBuilder;
import application.module.fight.skill.base.context.TargetParameter;
import application.module.fight.skill.base.context.UseSkillDataTemp;
import application.module.fight.skill.data.message.SkillUseScene;
import application.module.fight.skill.operate.info.SkillUseInfo;
import application.module.fight.skill.util.SkillAimType;
import application.module.organism.*;
import application.module.player.data.domain.PlayerInfo;
import application.module.player.data.message.event.PlayerLogin;
import application.module.player.data.message.event.PlayerLogout;
import application.module.scene.SceneProtocolBuilder;
import application.module.scene.SceneProtocols;
import application.module.scene.data.domain.PositionInfo;
import application.module.scene.operate.*;
import application.module.scene.operate.event.CreateOrganismEntityAfter;
import application.module.scene.operate.event.CreatePlayerEntitiesAfter;
import application.module.scene.operate.event.PlayerReceiveAfter;
import application.module.scene.trigger.ScenePortalRefreshMonsterTrigger;
import application.util.ApplicationErrorCode;
import application.util.CommonOperateTypeInfo;
import application.util.RandomUtil;
import application.util.Vector;
import com.cala.orm.message.Publish;
import com.google.protobuf.GeneratedMessageV3;
import mobius.modular.client.Client;
import protocol.Skill;
import template.FightSkillTemplate;
import template.FightSkillTemplateHolder;
import template.SceneDataTemplate;
import template.SceneDataTemplateHolder;

import java.time.Duration;
import java.util.*;

/**
 * @author Luo Yong
 * @date 2022-3-3
 * @Source 1.0
 */
public class Scene extends UntypedAbstractActor {

    private final int sceneTemplateId;

    private final long sceneId;

    private ActorRef sceneData;

    private final SceneDataTemplate sceneDataTemplate;

    /**
     * key : 当前场景的玩家id
     * value : Client ActorRef
     */
    private final Map<Long, ActorRef> clientMap = new HashMap<>();

    private final Map<Long, PlayerInfo> playerInfoMap = new HashMap<>();

    private final Vector[] playerBirths;

    private long aiAgentPlayerId;

    public final static float DEFAULT_X = 0;
    public final static float DEFAULT_Y = 5;
    public final static int DEFAULT_FACE = OrganismFaceType.RIGHT;

    /**
     * 场景怪物最大数量 //TODO 后面需要读表
     */
    public final static int MAX_MONSTER_COUNT = 30;

    /**
     * key: FightOrganismId
     * value: FightOrganism场景坐标等数据
     */
    private final Map<Long, PositionInfo> positionInfoMap = new HashMap<>();

    private final Map<Long, NpcOrganism> npcOrganismMap = new HashMap<>();

    private final Map<Long, MonsterOrganism> monsterOrganismMap = new HashMap<>();

    private final Map<Long, ItemOrganism> itemOrganismMap = new HashMap<>();

    private final List<ActorRef> scenePortalRefreshMonsterTriggerList = new ArrayList<>();

    public Scene(int sceneTemplateId) {
        this.sceneId = IdUtils.fastSimpleUUIDLong();
        this.sceneTemplateId = sceneTemplateId;
        this.sceneDataTemplate = SceneDataTemplateHolder.getData(sceneTemplateId);
        float[] birthPoint = this.sceneDataTemplate.birthPoint();
        int length = birthPoint.length;
        int halfLength = length / 2;
        this.playerBirths = new Vector[halfLength];
        int index = 0;
        for (int i = 0; i < length; i += 2) {
            this.playerBirths[index++] = new Vector(birthPoint[i], birthPoint[i + 1]);
        }
        getContext().getSystem().scheduler().scheduleWithFixedDelay(Duration.ofMillis(1000), Duration.ofMillis(1000), this::selectClient, getContext().dispatcher());
    }

    public static Props create(int sceneId) {
        return Props.create(Scene.class, sceneId);
    }

    @Override
    public void onReceive(Object message) {
        switch (message) {
            case SceneMove sceneMove -> sceneMove(sceneMove);
            case SceneStop sceneStop -> sceneStop(sceneStop);
            case SceneJump sceneJump -> sceneJump(sceneJump);
            case SkillUseScene skillUseScene -> skillUseScene(skillUseScene);
            case AoiSendMessageToClient aoi -> sendToAllClient(aoi.protoId(), aoi.message());
            case ScenePlayerEntryWrap scenePlayerEntryWrap -> scenePlayerEntryWrap(scenePlayerEntryWrap);
            case ScenePlayerExit scenePlayerExit -> scenePlayerExit(scenePlayerExit);
            case SceneOrganismEntry sceneOrganismEntry -> sceneOrganismEntry(sceneOrganismEntry);
            case SceneOrganismExit sceneOrganismExit -> sceneOrganismExit(sceneOrganismExit);
            case PlayerLogin playerLogin -> playerLogin(playerLogin);
            case SceneInit sceneInit -> sceneInit(sceneInit);
            case PlayerLogout playerLogout -> playerLogout(playerLogout);
            case SceneFlash sceneFlash -> sceneFlash(sceneFlash);
            case GetPlayerDataToOtherScene getPlayerDataToOtherScene -> getPlayerDataToOtherScene(getPlayerDataToOtherScene);
            case PlayerDead playerDead -> playerDead(playerDead);
            case PlayerReceive playerReceive -> playerReceive(playerReceive);
            case HelpUseSkill helpUseSkill -> npcUseSkill(helpUseSkill);
            case PickUpItem pickUpItem -> pickUpItem(pickUpItem);
            case FuckNpc fuckNpc -> fuckNpc(fuckNpc);
            case MonsterDead monsterDead -> monsterDead(monsterDead);
            case SceneRush sceneRush -> sceneRush(sceneRush);
            default -> throw new IllegalStateException("Unexpected value: " + message.getClass().getName());
        }
    }

    private void sceneRush(SceneRush sceneRush) {
        Client.ReceivedFromClient r = sceneRush.r();
        protocol.Scene.CS10312 cs10312 = sceneRush.cs10312();
        long organismId = cs10312.getOrganismId();
        positionInfoMap.put(organismId, new PositionInfo(cs10312.getPositionX(), cs10312.getPositionY(),
                positionInfoMap.get(organismId).getFace()));
        //TODO 需要根据时间加速度，判断是否移动位置距离上一次同步合理
        sendToOtherClient(r.protoID(), SceneProtocolBuilder.getSc10312(organismId, cs10312), organismId);
    }

    private void monsterDead(MonsterDead monsterDead) {
        sceneOrganismExit(monsterDead.organismId(), monsterDead.organismType());
    }

    private void fuckNpc(FuckNpc fuckNpc) {
        var cs10311 = fuckNpc.cs10311();
        scenePortalRefreshMonsterTriggerList.forEach(trigger -> {
            trigger.tell(new CloseNpcDoor(cs10311.getOrganismNpcId(), 0), self());
        });

    }

    private void pickUpItem(PickUpItem pickUpItem) {
        var cs10030 = pickUpItem.cs10310();
        pickUpItem.buffData().tell(new AddBuff(pickUpItem.r(), 10013, cs10030.getOrganismId(),
                cs10030.getOrganismId(), self(), pickUpItem.attributeData(), null), self());
        long organismItemId = cs10030.getOrganismItemId();
        if (Objects.nonNull(removePositionInfo(organismItemId))) {
            npcOrganismMap.remove(organismItemId);
            sendToAllClient(SceneProtocols.SCENE_EXIT, SceneProtocolBuilder.getSc10301(organismItemId));
        }
    }

    private void npcUseSkill(HelpUseSkill helpUseSkill) {
        ActorRef actorRef = clientMap.get(aiAgentPlayerId);
        if (Objects.nonNull(actorRef)) {
            actorRef.tell(new application.client.Client.SendToClientJ(SceneProtocols.HELP_USE_SKILL,
                    SceneProtocolBuilder.getSc10308(helpUseSkill.organismId(), helpUseSkill.skillTemplateId())), self());
        }
    }

    private void playerReceive(PlayerReceive playerReceive) {
        Client.ReceivedFromClient r = playerReceive.r();
        long playerId = r.uID();
        this.sceneData.tell(new Publish(new PlayerReceiveAfter(Map.copyOf(clientMap), playerId, self())), self());
        clientMap.forEach((id, client) -> client.tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_FLASH,
                SceneProtocolBuilder.getSc10306(r.uID(), getPlayerBirth())), self()));
    }

    private void playerDead(PlayerDead playerDead) {

    }

    private void getPlayerDataToOtherScene(GetPlayerDataToOtherScene getPlayerDataToOtherScene) {
        long playerId = getPlayerDataToOtherScene.playerId();
        getPlayerDataToOtherScene.scene().tell(new ScenePlayerEntryWrap(getPlayerDataToOtherScene.client(), getPlayerInfo(playerId)), self());
        scenePlayerExit(playerId);
    }

    private void sceneFlash(SceneFlash sceneFlash) {
        Client.ReceivedFromClient r = sceneFlash.r();
        protocol.Scene.CS10306 cs10306 = sceneFlash.cs10306();
        long playerId = cs10306.getOrganismId();
        positionInfoMap.put(playerId, new PositionInfo(cs10306.getPositionX(), cs10306.getPositionY()));
        //TODO 需要校验玩家当前位置是否可以闪现
        clientMap.forEach((id, client) -> client.tell(new application.client.Client.SendToClientJ(r.protoID(),
                SceneProtocolBuilder.getSc10306(playerId, cs10306)), self()));
    }

    private void playerLogout(PlayerLogout playerLogout) {
        scenePlayerExit(playerLogout.r().uID());
    }

    private void sceneOrganismEntry(SceneOrganismEntry sceneOrganismEntry) {
        Organism organism = sceneOrganismEntry.organism();
        PositionInfo positionInfo = sceneOrganismEntry.positionInfo();
        if (organism instanceof NpcOrganism npcOrganism) {
            long id = npcOrganism.getId();
            putPositionInfo(id, positionInfo);
            npcOrganismMap.put(id, npcOrganism);
            sendToAllClient(SceneProtocols.SCENE_RETURN_ENTITY, SceneProtocolBuilder.getSc10304(id,
                    npcOrganism.getOrganismType(), positionInfo, npcOrganism.getOrganismTemplateId()));
        } else if (organism instanceof MonsterOrganism monsterOrganism) {
            if (monsterOrganismMap.size() >= MAX_MONSTER_COUNT) {
                return;
            }
            long id = monsterOrganism.getId();
            putPositionInfo(id, positionInfo);
            monsterOrganismMap.put(id, monsterOrganism);
            sendToAllClient(SceneProtocols.SCENE_RETURN_ENTITY, SceneProtocolBuilder.getSc10304(id,
                    monsterOrganism.getOrganismType(), positionInfo, monsterOrganism.getOrganismTemplateId()));
        } else if (organism instanceof ItemOrganism itemOrganism) {
            long id = itemOrganism.getId();
            putPositionInfo(id, positionInfo);
            itemOrganismMap.put(id, itemOrganism);
            sendToAllClient(SceneProtocols.SCENE_RETURN_ENTITY, SceneProtocolBuilder.getSc10304(id,
                    itemOrganism.getOrganismType(), positionInfo, itemOrganism.getOrganismTemplateId()));
        }

        this.sceneData.tell(new Publish(new CreateOrganismEntityAfter(clientMap.values(), organism)), self());
        if (sceneOrganismEntry.duration() > 0) {
            getContext().system().scheduler().scheduleOnce(Duration.ofMillis(sceneOrganismEntry.duration()), self(),
                    new SceneOrganismExit(organism.getId(), organism.getOrganismType()), getContext().dispatcher(), self());
        }
    }

    private void sceneOrganismExit(SceneOrganismExit sceneOrganismExit) {
        sceneOrganismExit(sceneOrganismExit.organismId(), sceneOrganismExit.organismType());
    }

    private void sceneOrganismExit(long organismId, byte organismType) {
        if (organismType == OrganismType.MONSTER) {
            if (Objects.nonNull(removePositionInfo(organismId))) {
                if (monsterOrganismMap.get(organismId).getOrganismTemplateId() == 10009) {
                    dealBossDead();
                }
                monsterOrganismMap.remove(organismId);
                sendToAllClient(SceneProtocols.SCENE_EXIT, SceneProtocolBuilder.getSc10301(organismId));
            }
        } else if (organismType == OrganismType.NPC) {
            if (Objects.nonNull(removePositionInfo(organismId))) {
                npcOrganismMap.remove(organismId);
                sendToAllClient(SceneProtocols.SCENE_EXIT, SceneProtocolBuilder.getSc10301(organismId));
            }
        } else if (organismType == OrganismType.ITEM) {
            if (Objects.nonNull(removePositionInfo(organismId))) {
                itemOrganismMap.remove(organismId);
                sendToAllClient(SceneProtocols.SCENE_EXIT, SceneProtocolBuilder.getSc10301(organismId));
            }
        }
    }

    // TODO: 2022-5-9 临时处理boss死亡
    private void dealBossDead() {
        //场景所有怪物消失
        Iterator<Long> iterator = monsterOrganismMap.keySet().iterator();
        while (iterator.hasNext()) {
            long organismId = iterator.next();
            monsterOrganismMap.remove(organismId);
            sendToAllClient(SceneProtocols.SCENE_EXIT, SceneProtocolBuilder.getSc10301(organismId));
        }

        //销毁场景触发器
        getContext().stop(scenePortalRefreshMonsterTriggerList.get(0));

        getContext().getSystem().scheduler().scheduleOnce(Duration.ofMillis(10000), this::sceneOut, getContext().dispatcher());

    }

    private void sceneOut() {
        Iterator<Map.Entry<Long, ActorRef>> iterator = clientMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, ActorRef> entry = iterator.next();
            this.sceneData.tell(new ReturnPerScene(entry.getKey(), entry.getValue()), self());
        }

        this.sceneData.tell(new SceneOut(sceneTemplateId), self());

    }

    private void sceneInit(SceneInit sceneInit) {
        this.sceneData = sender();
        if (this.sceneTemplateId == 20004) {
            ActorRef actorRef = getContext().actorOf(ScenePortalRefreshMonsterTrigger.create(1));
            actorRef.tell(sceneInit, self());
            scenePortalRefreshMonsterTriggerList.add(actorRef);
        }
    }

    private void sceneJump(SceneJump sceneJump) {
        Client.ReceivedFromClient r = sceneJump.r();
        protocol.Scene.CS10305 cs10305 = sceneJump.cs10305();
        protocol.Scene.JumpInfo jumpInfo = cs10305.getJumpInfo();
        long organismId = cs10305.getOrganismId();
        positionInfoMap.put(organismId, new PositionInfo(jumpInfo.getPositionX(), jumpInfo.getPositionY(), jumpInfo.getFace()));
        //TODO 需要根据时间加速度，判断是否移动位置距离上一次同步合理
        sendToOtherClient(r.protoID(), SceneProtocolBuilder.getSc10305(organismId, cs10305), organismId);
    }

    private void playerLogin(PlayerLogin playerLogin) {
        Client.ReceivedFromClient r = playerLogin.r();
        long playerId = playerLogin.playerInfo().id();
        clientMap.put(playerId, r.client());
        playerInfoMap.put(playerId, playerLogin.playerInfo());
        PositionInfo positionInfo = positionInfoMap.get(playerId);
        if (Objects.isNull(positionInfo)) {
            Vector vector = getPlayerBirth();
            positionInfo = new PositionInfo(vector.x(), vector.y(), DEFAULT_FACE);
            positionInfoMap.put(playerId, positionInfo);
        }
        r.client().tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_ENTER,
                SceneProtocolBuilder.getSc10300(sceneTemplateId, positionInfo)), self());
        //给场景的其他玩家发送我进来了
        PositionInfo finalPositionInfo = positionInfo;
        clientMap.forEach((id, client) -> {
            if (id != playerId) {
                client.tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_RETURN_ENTITY,
                        SceneProtocolBuilder.getSc10304(playerId, OrganismType.PLAYER, finalPositionInfo,
                                getPlayerInfo(playerId).profession())), self());
            }
        });
        //获取场景实体的数据
        getSceneAllOrganism(r.client(), playerId);
        this.sceneData.tell(new PlayerEntrySuccess(playerId, sceneTemplateId), self());
    }

    private void sceneStop(SceneStop sceneStop) {
        Client.ReceivedFromClient r = sceneStop.r();
        protocol.Scene.CS10303 cs10303 = sceneStop.cs10303();
        protocol.Scene.StopInfo stopInfo = cs10303.getStopInfo();
        long organismId = cs10303.getOrganismId();
        positionInfoMap.put(organismId, new PositionInfo(stopInfo.getPositionX(), stopInfo.getPositionY(), stopInfo.getFace()));
        //TODO 需要根据时间加速度，判断是否移动位置距离上一次同步合理
        sendToOtherClient(r.protoID(), SceneProtocolBuilder.getSc10303(organismId, cs10303), organismId);
    }

    private void scenePlayerExit(ScenePlayerExit scenePlayerExit) {
        scenePlayerExit(scenePlayerExit.r().uID());
    }

    private void scenePlayerExit(long playerId) {
        sendToOtherClient(SceneProtocols.SCENE_EXIT, SceneProtocolBuilder.getSc10301(playerId), playerId);
        PositionInfo positionInfo = positionInfoMap.get(playerId);
        positionInfoMap.remove(playerId);
        clientMap.remove(playerId);
        playerInfoMap.remove(playerId);

        //TODO 退出场景，玩家信息需要清空
        sender().tell(new ScenePlayerExitReturn(playerId, getSceneTemplateId(), positionInfo), self());
    }

    private void scenePlayerEntryWrap(ScenePlayerEntryWrap scenePlayerEntryWrap) {
        PlayerInfo playerInfo = scenePlayerEntryWrap.playerInfo();
        long playerId = playerInfo.id();
        ActorRef client = scenePlayerEntryWrap.client();
        this.clientMap.put(playerId, client);
        this.playerInfoMap.put(playerId, playerInfo);
        PositionInfo positionInfo = positionInfoMap.get(playerId);
        if (Objects.isNull(positionInfo)) {
            Vector vector = getPlayerBirth();
            positionInfo = new PositionInfo(vector.x(), vector.y(), DEFAULT_FACE);
            positionInfoMap.put(playerId, positionInfo);
        }
        client.tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_ENTER,
                SceneProtocolBuilder.getSc10300(sceneTemplateId, positionInfo)), self());
        //给场景的其他玩家发送我进来了
        PositionInfo finalPositionInfo = positionInfo;
        clientMap.forEach((id, client1) -> {
            if (id != playerId) {
                client1.tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_RETURN_ENTITY,
                        SceneProtocolBuilder.getSc10304(playerId, OrganismType.PLAYER, finalPositionInfo,
                                getPlayerInfo(playerId).profession())), self());
            }
        });

        //获取场景实体的数据
        getSceneAllOrganismOutSelf(client, playerId);

        this.sceneData.tell(new PlayerEntrySuccess(playerId, sceneTemplateId), self());
    }

    private void sceneMove(SceneMove sceneMove) {
        Client.ReceivedFromClient r = sceneMove.r();
        protocol.Scene.CS10302 cs10302 = sceneMove.cs10302();
        protocol.Scene.MoveInfo moveInfo = cs10302.getMoveInfo();
        long organismId = cs10302.getOrganismId();
        positionInfoMap.put(organismId, new PositionInfo(moveInfo.getPositionX(), moveInfo.getPositionY(), moveInfo.getFace()));
        //TODO 需要根据时间加速度，判断是否移动位置距离上一次同步合理
        sendToOtherClient(r.protoID(), SceneProtocolBuilder.getSc10302(organismId, cs10302), organismId);
    }

    private void skillUseScene(SkillUseScene skillUseScene) {
        CommonOperateTypeInfo commonOperateTypeInfo = (CommonOperateTypeInfo) skillUseScene.operateTypeInfo();
        if (skillUseScene.isSkillProcess()) {
            Client.ReceivedFromClient r = commonOperateTypeInfo.r();
            Skill.CS10055 cs10055 = (Skill.CS10055) commonOperateTypeInfo.message();
            long organismId = cs10055.getFightOrganismId();
            //TODO 判断当前场景是否可以释放技能，或者当前玩家位置是否可以放技能。
            if (true) {
                sender().tell(new SkillUseScene(new SkillUseInfo(commonOperateTypeInfo.r(), getTarget(commonOperateTypeInfo, true)), true), self());
                sendToOtherClient(r.protoID(), FightSkillProtocolBuilder.getSc10055(cs10055), r.uID());
            } else {
                commonOperateTypeInfo.r().client().tell(new SendToClientJ(CommonProtocols.APPLICATION_ERROR,
                        CommonProtocolBuilder.getSc10080(ApplicationErrorCode.USE_SKILL_SCENE)), self());
            }
            return;
        }
        Client.ReceivedFromClient r = commonOperateTypeInfo.r();
        Skill.CS10052 cs10052 = (Skill.CS10052) commonOperateTypeInfo.message();
        long organismId = cs10052.getFightOrganismId();
        //TODO 判断当前场景是否可以释放技能，或者当前玩家位置是否可以放技能。
        if (true) {
            sender().tell(new SkillUseScene(new SkillUseInfo(commonOperateTypeInfo.r(), getTarget(commonOperateTypeInfo, false)), false), self());
            sendToOtherClient(r.protoID(), FightSkillProtocolBuilder.getSc10052(cs10052), r.uID());
        } else {
            commonOperateTypeInfo.r().client().tell(new SendToClientJ(CommonProtocols.APPLICATION_ERROR,
                    CommonProtocolBuilder.getSc10080(ApplicationErrorCode.USE_SKILL_SCENE)), self());
        }
    }

    private UseSkillDataTemp getTarget(CommonOperateTypeInfo commonOperateTypeInfo, boolean isSkillProcess) {
        // TODO: 2022-4-29 后续需要删除
        if (isSkillProcess) {
            Skill.CS10055 cs10055 = (Skill.CS10055) commonOperateTypeInfo.message();
            //生成技能释放上下文
            UseSkillDataTemp useSkillDataTemp = UseSkillDataTemp.of(cs10055, self());
            useSkillDataTemp.setScene(self());
            useSkillDataTemp.setR(commonOperateTypeInfo.r());
            useSkillDataTemp.setSceneId(this.sceneId);
            PlayerInfo playerInfo1 = playerInfoMap.get(useSkillDataTemp.getAttackId());
            if (Objects.nonNull(playerInfo1)) {
                useSkillDataTemp.setProfession(playerInfo1.profession());
            }
            long fightOrganismId = cs10055.getFightOrganismId();
            if (Objects.isNull(playerInfoMap.get(fightOrganismId))) {
                NpcOrganism npcOrganism = npcOrganismMap.get(fightOrganismId);
                if (Objects.nonNull(npcOrganism)) {
                    useSkillDataTemp.setAttackType(OrganismType.NPC);
                    useSkillDataTemp.setAttackTemplateId(npcOrganism.getOrganismTemplateId());
                } else {
                    MonsterOrganism monsterOrganism = monsterOrganismMap.get(fightOrganismId);
                    if (Objects.nonNull(monsterOrganism)) {
                        useSkillDataTemp.setAttackType(OrganismType.MONSTER);
                        useSkillDataTemp.setAttackTemplateId(monsterOrganism.getOrganismTemplateId());
                    }
                }
            }

            //TODO 这里代码需要修改，写的什么垃圾
            useSkillDataTemp.getTargetId().forEach(id -> {
                PlayerInfo playerInfo = playerInfoMap.get(id);
                if (Objects.nonNull(playerInfo)) {
                    useSkillDataTemp.getTargetParameters().add(new TargetParameter(id,
                            playerInfo.level(), OrganismType.PLAYER, playerInfo.profession()));
                } else {
                    NpcOrganism npcOrganism = npcOrganismMap.get(id);
                    if (Objects.nonNull(npcOrganism)) {
                        useSkillDataTemp.getTargetParameters().add(new TargetParameter(id,
                                1, npcOrganism.getOrganismType(), npcOrganism.getOrganismTemplateId()));
                    } else {
                        MonsterOrganism monsterOrganism = monsterOrganismMap.get(id);
                        if (Objects.nonNull(monsterOrganism)) {
                            useSkillDataTemp.getTargetParameters().add(new TargetParameter(id,
                                    1, monsterOrganism.getOrganismType(), monsterOrganism.getOrganismTemplateId()));
                        }
                    }
                }
            });
            return useSkillDataTemp;
        }


        Skill.CS10052 cs10052 = (Skill.CS10052) commonOperateTypeInfo.message();
        FightSkillTemplate fightSkillTemplate = FightSkillTemplateHolder.getData(cs10052.getSkillId());
        //生成技能释放上下文
        UseSkillDataTemp useSkillDataTemp = UseSkillDataTemp.of(cs10052, self());
        useSkillDataTemp.setScene(self());
        useSkillDataTemp.setR(commonOperateTypeInfo.r());
        useSkillDataTemp.setSceneId(this.sceneId);
        PlayerInfo playerInfo1 = playerInfoMap.get(useSkillDataTemp.getAttackId());
        if (Objects.nonNull(playerInfo1)) {
            useSkillDataTemp.setProfession(playerInfo1.profession());
        }
        long fightOrganismId = cs10052.getFightOrganismId();
        if (Objects.isNull(playerInfoMap.get(fightOrganismId))) {
            NpcOrganism npcOrganism = npcOrganismMap.get(fightOrganismId);
            if (Objects.nonNull(npcOrganism)) {
                useSkillDataTemp.setAttackType(OrganismType.NPC);
                useSkillDataTemp.setAttackTemplateId(npcOrganism.getOrganismTemplateId());
            } else {
                MonsterOrganism monsterOrganism = monsterOrganismMap.get(fightOrganismId);
                if (Objects.nonNull(monsterOrganism)) {
                    useSkillDataTemp.setAttackType(OrganismType.MONSTER);
                    useSkillDataTemp.setAttackTemplateId(monsterOrganism.getOrganismTemplateId());
                }
            }
        }
        //TODO 获取目标
        if (SkillAimType.isOne(fightSkillTemplate)) {
        }
        //TODO 这里代码需要修改，写的什么垃圾
        useSkillDataTemp.getTargetId().forEach(id -> {
            PlayerInfo playerInfo = playerInfoMap.get(id);
            if (Objects.nonNull(playerInfo)) {
                useSkillDataTemp.getTargetParameters().add(new TargetParameter(id,
                        playerInfo.level(), OrganismType.PLAYER, playerInfo.profession()));
            } else {
                NpcOrganism npcOrganism = npcOrganismMap.get(id);
                if (Objects.nonNull(npcOrganism)) {
                    useSkillDataTemp.getTargetParameters().add(new TargetParameter(id,
                            1, npcOrganism.getOrganismType(), npcOrganism.getOrganismTemplateId()));
                } else {
                    MonsterOrganism monsterOrganism = monsterOrganismMap.get(id);
                    if (Objects.nonNull(monsterOrganism)) {
                        useSkillDataTemp.getTargetParameters().add(new TargetParameter(id,
                                1, monsterOrganism.getOrganismType(), monsterOrganism.getOrganismTemplateId()));
                    }
                }
            }
        });
        return useSkillDataTemp;
    }

    private void sendToAllClient(int protoId, GeneratedMessageV3 message) {
        clientMap.forEach((id, client) -> client.tell(new application.client.Client.SendToClientJ(protoId, message), self()));
    }

    private void sendToOtherClient(int protoId, GeneratedMessageV3 message, long organismId) {
        clientMap.forEach((id, client) -> {
            if (id != organismId) {
                client.tell(new application.client.Client.SendToClientJ(protoId,
                        message), self());
            }
        });
    }

    private void getSceneAllOrganism(ActorRef client, long organismId) {
        playerInfoMap.forEach((id, playerInfo) ->
                client.tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_RETURN_ENTITY,
                        SceneProtocolBuilder.getSc10304(id, OrganismType.PLAYER, getPositionInfo(id), playerInfo.profession())), self()));
        npcOrganismMap.values().forEach(npcOrganism ->
                client.tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_RETURN_ENTITY,
                        SceneProtocolBuilder.getSc10304(npcOrganism.getId(), npcOrganism.getOrganismType(),
                                getPositionInfo(npcOrganism.getId()), npcOrganism.getOrganismTemplateId())), self()));
        monsterOrganismMap.values().forEach(monsterOrganism ->
                client.tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_RETURN_ENTITY,
                        SceneProtocolBuilder.getSc10304(monsterOrganism.getId(), monsterOrganism.getOrganismType(),
                                getPositionInfo(monsterOrganism.getId()), monsterOrganism.getOrganismTemplateId())), self()));
        List<Organism> list = new ArrayList<>(npcOrganismMap.values());
        list.addAll(monsterOrganismMap.values());
        this.sceneData.tell(new Publish(new CreatePlayerEntitiesAfter(clientMap, organismId, list)), self());

    }

    private void getSceneAllOrganismOutSelf(ActorRef client, long organismId) {
        playerInfoMap.forEach((id, playerInfo) -> {
            if (id != organismId) {
                client.tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_RETURN_ENTITY,
                        SceneProtocolBuilder.getSc10304(id, OrganismType.PLAYER, getPositionInfo(id), playerInfo.profession())), self());
            }
        });
        npcOrganismMap.values().forEach(npcOrganism ->
                client.tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_RETURN_ENTITY,
                        SceneProtocolBuilder.getSc10304(npcOrganism.getId(), npcOrganism.getOrganismType(),
                                getPositionInfo(npcOrganism.getId()), npcOrganism.getOrganismTemplateId())), self()));
        monsterOrganismMap.values().forEach(monsterOrganism ->
                client.tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_RETURN_ENTITY,
                        SceneProtocolBuilder.getSc10304(monsterOrganism.getId(), monsterOrganism.getOrganismType(),
                                getPositionInfo(monsterOrganism.getId()), monsterOrganism.getOrganismTemplateId())), self()));
        List<Organism> list = new ArrayList<>(npcOrganismMap.values());
        list.addAll(monsterOrganismMap.values());
        this.sceneData.tell(new Publish(new CreatePlayerEntitiesAfter(clientMap, organismId, list)), self());
    }

    public int getSceneTemplateId() {
        return sceneTemplateId;
    }

    public long getSceneId() {
        return sceneId;
    }

    public void putPositionInfo(long id, PositionInfo positionInfo) {
        this.positionInfoMap.put(id, positionInfo);
    }

    public PositionInfo removePositionInfo(long id) {
        return this.positionInfoMap.remove(id);
    }

    public PositionInfo getPositionInfo(long id) {
        return this.positionInfoMap.get(id);
    }

    public Vector getPlayerBirth() {
        if (playerBirths.length == 1) {
            return playerBirths[0];
        }
        int r = RandomUtil.randomInt(playerBirths.length);
        return playerBirths[r];
    }

    public PlayerInfo getPlayerInfo(long id) {
        return playerInfoMap.get(id);
    }

    public void selectClient() {
        if (this.aiAgentPlayerId == 0) {
            for (Map.Entry<Long, ActorRef> entry : clientMap.entrySet()) {
                ActorRef client = entry.getValue();
                if (Objects.nonNull(client)) {
                    aiAgentPlayerId = entry.getKey();
                    client.tell(new SendToClientJ(SceneProtocols.AI, SceneProtocolBuilder.getSc10307()), self());
                    return;
                }
            }
        } else {
            ActorRef actorRef = clientMap.get(this.aiAgentPlayerId);
            if (Objects.isNull(actorRef)) {
                this.aiAgentPlayerId = 0;
            }
        }
    }
}

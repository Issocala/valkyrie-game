package application.module.scene.data;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import application.client.Client.SendToClientJ;
import application.guid.IdUtils;
import application.module.common.CommonProtocolBuilder;
import application.module.common.CommonProtocols;
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

    private final List<ScenePortalRefreshMonsterTrigger> scenePortalRefreshMonsterTriggerList = new ArrayList<>();

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
            case SceneOrganismEntry sceneOrganismEntry -> sceneMonsterEntry(sceneOrganismEntry);
            case SceneMonsterExit sceneMonsterExit -> sceneMonsterExit(sceneMonsterExit);
            case PlayerLogin playerLogin -> playerLogin(playerLogin);
            case SceneInit sceneInit -> sceneInit(sceneInit);
            case PlayerLogout playerLogout -> playerLogout(playerLogout);
            case CreateOrganismEntity createOrganismEntity -> createOrganismEntity(createOrganismEntity);
            case SceneFlash sceneFlash -> sceneFlash(sceneFlash);
            case GetPlayerDataToOtherScene getPlayerDataToOtherScene -> getPlayerDataToOtherScene(getPlayerDataToOtherScene);
            default -> throw new IllegalStateException("Unexpected value: " + message.getClass().getName());
        }
    }

    private void getPlayerDataToOtherScene(GetPlayerDataToOtherScene getPlayerDataToOtherScene) {
        ScenePlayerEntry scenePlayerEntry = getPlayerDataToOtherScene.scenePlayerEntry();
        Client.ReceivedFromClient r = scenePlayerEntry.r();
        getPlayerDataToOtherScene.scene().tell(new ScenePlayerEntryWrap(scenePlayerEntry, getPlayerInfo(r.uID())), self());
        scenePlayerExit(r);
    }

    private void sceneFlash(SceneFlash sceneFlash) {
        Client.ReceivedFromClient r = sceneFlash.r();
        protocol.Scene.CS10036 cs10036 = sceneFlash.cs10036();
        long playerId = r.uID();
        positionInfoMap.put(playerId, new PositionInfo(cs10036.getPositionX(), cs10036.getPositionY()));
        //TODO 需要校验玩家当前位置是否可以闪现
        clientMap.forEach((id, client) -> client.tell(new application.client.Client.SendToClientJ(r.protoID(),
                SceneProtocolBuilder.getSc10036(playerId, cs10036)), self()));
    }

    private void createOrganismEntity(CreateOrganismEntity createOrganismEntity) {

    }

    private void playerLogout(PlayerLogout playerLogout) {
        scenePlayerExit(playerLogout.r());
    }

    private void sceneMonsterEntry(SceneOrganismEntry sceneOrganismEntry) {
        Organism organism = sceneOrganismEntry.organism();
        PositionInfo positionInfo = sceneOrganismEntry.positionInfo();
        if (organism instanceof NpcOrganism npcOrganism) {
            if (npcOrganism.getOrganismTemplateId() == 10001) {
                putPositionInfo(npcOrganism.getId(), positionInfo);
            } else if (npcOrganism.getOrganismTemplateId() == 10002) {
                putPositionInfo(npcOrganism.getId(), positionInfo);
            } else if (npcOrganism.getOrganismTemplateId() == 10003) {
                putPositionInfo(npcOrganism.getId(), positionInfo);
            } else if (npcOrganism.getOrganismTemplateId() == 10004) {
                putPositionInfo(npcOrganism.getId(), positionInfo);
            }
            npcOrganismMap.put(npcOrganism.getId(), npcOrganism);
            sendToAllClient(SceneProtocols.SCENE_RETURN_ENTITY, SceneProtocolBuilder.getSc10034(npcOrganism.getId(),
                    npcOrganism.getOrganismType(), getPositionInfo(npcOrganism.getId()), npcOrganism.getOrganismTemplateId()));
        }

        this.sceneData.tell(new Publish(new CreateOrganismEntityAfter(clientMap.values(), organism)), self());
    }

    private void sceneMonsterExit(SceneMonsterExit sceneMonsterExit) {

    }

    private void sceneInit(SceneInit sceneInit) {
        this.sceneData = sender();
        if (this.sceneTemplateId == 20004) {
            ActorRef actorRef = getContext().actorOf(ScenePortalRefreshMonsterTrigger.create(1));
            actorRef.tell(sceneInit, self());
        }
    }

    private void sceneJump(SceneJump sceneJump) {
        Client.ReceivedFromClient r = sceneJump.r();
        protocol.Scene.CS10035 cs10035 = sceneJump.cs10035();
        protocol.Scene.JumpInfo jumpInfo = cs10035.getJumpInfo();
        positionInfoMap.put(r.uID(), new PositionInfo(jumpInfo.getPositionX(), jumpInfo.getPositionY(), jumpInfo.getFace()));
        //TODO 需要根据时间加速度，判断是否移动位置距离上一次同步合理
        sendToOtherClient(r.protoID(), SceneProtocolBuilder.getSc10035(r.uID(), cs10035), r.uID());
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
                SceneProtocolBuilder.getSc10030(sceneTemplateId, positionInfo)), self());
        //给场景的其他玩家发送我进来了
        List<Long> organismIds = List.of(playerId);
        PositionInfo finalPositionInfo = positionInfo;
        clientMap.forEach((id, client) -> {
            if (id != playerId) {
                client.tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_RETURN_ENTITY,
                        SceneProtocolBuilder.getSc10034(playerId, OrganismType.PLAYER, finalPositionInfo,
                                getPlayerInfo(id).profession())), self());
            }
        });
        //获取场景实体的数据
        getSceneAllOrganism(r, playerId);
    }

    private void sceneStop(SceneStop sceneStop) {
        Client.ReceivedFromClient r = sceneStop.r();
        protocol.Scene.CS10033 cs10033 = sceneStop.cs10033();
        protocol.Scene.StopInfo stopInfo = cs10033.getStopInfo();
        positionInfoMap.put(r.uID(), new PositionInfo(stopInfo.getPositionX(), stopInfo.getPositionY(), stopInfo.getFace()));
        //TODO 需要根据时间加速度，判断是否移动位置距离上一次同步合理
        sendToOtherClient(r.protoID(), SceneProtocolBuilder.getSc10033(r.uID(), cs10033), r.uID());
    }

    private void scenePlayerExit(ScenePlayerExit scenePlayerExit) {
        scenePlayerExit(scenePlayerExit.r());
    }

    private void scenePlayerExit(Client.ReceivedFromClient r) {
        long playerId = r.uID();
        sendToOtherClient(SceneProtocols.SCENE_EXIT, SceneProtocolBuilder.getSc10031(playerId), playerId);
        PositionInfo positionInfo = positionInfoMap.get(playerId);
        positionInfoMap.remove(playerId);
        clientMap.remove(r.uID());
        playerInfoMap.remove(r.uID());
        //TODO 退出场景，玩家信息需要清空
        sender().tell(new ScenePlayerExitReturn(playerId, getSceneTemplateId(), positionInfo), self());
    }

    private void scenePlayerEntryWrap(ScenePlayerEntryWrap scenePlayerEntryWrap) {
        ScenePlayerEntry scenePlayerEntry = scenePlayerEntryWrap.scenePlayerEntry();
        Client.ReceivedFromClient r = scenePlayerEntry.r();
        long playerId = r.uID();
        this.clientMap.put(playerId, r.client());
        this.playerInfoMap.put(playerId, scenePlayerEntryWrap.playerInfo());
        PositionInfo positionInfo = positionInfoMap.get(playerId);
        if (Objects.isNull(positionInfo)) {
            Vector vector = getPlayerBirth();
            positionInfo = new PositionInfo(vector.x(), vector.y(), DEFAULT_FACE);
            positionInfoMap.put(playerId, positionInfo);
        }
        r.client().tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_ENTER,
                SceneProtocolBuilder.getSc10030(sceneTemplateId, positionInfo)), self());
        //给场景的其他玩家发送我进来了
        PositionInfo finalPositionInfo = positionInfo;
        clientMap.forEach((id, client) -> {
            if (id != playerId) {
                client.tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_RETURN_ENTITY,
                        SceneProtocolBuilder.getSc10034(playerId, OrganismType.PLAYER, finalPositionInfo,
                                getPlayerInfo(id).profession())), self());
            }
        });

        //获取场景实体的数据
        getSceneAllOrganismOutSelf(r, playerId);
    }

    private void scenePlayerEntryWrap(Client.ReceivedFromClient r, long playerId) {

    }

    private void sceneMove(SceneMove sceneMove) {
        Client.ReceivedFromClient r = sceneMove.r();
        protocol.Scene.CS10032 cs10032 = sceneMove.cs10032();
        protocol.Scene.MoveInfo moveInfo = cs10032.getMoveInfo();
        positionInfoMap.put(r.uID(), new PositionInfo(moveInfo.getPositionX(), moveInfo.getPositionY(), moveInfo.getFace()));
        //TODO 需要根据时间加速度，判断是否移动位置距离上一次同步合理
        sendToOtherClient(r.protoID(), SceneProtocolBuilder.getSc10032(r.uID(), cs10032), r.uID());
    }

    private void skillUseScene(SkillUseScene skillUseScene) {
        CommonOperateTypeInfo commonOperateTypeInfo = (CommonOperateTypeInfo) skillUseScene.operateTypeInfo();
        Client.ReceivedFromClient r = commonOperateTypeInfo.r();
        Skill.CS10052 cs10052 = (Skill.CS10052) commonOperateTypeInfo.message();
        long organismId = cs10052.getFightOrganismId();
        //TODO 判断当前场景是否可以释放技能，或者当前玩家位置是否可以放技能。
        if (true) {
            sender().tell(new SkillUseScene(new SkillUseInfo(commonOperateTypeInfo.r(), getTarget(commonOperateTypeInfo))), self());
            sendToOtherClient(r.protoID(), FightSkillProtocolBuilder.getSc10052(cs10052), r.uID());
        } else {
            commonOperateTypeInfo.r().client().tell(new SendToClientJ(CommonProtocols.APPLICATION_ERROR,
                    CommonProtocolBuilder.getSc10080(ApplicationErrorCode.USE_SKILL_SCENE)), self());
        }
    }

    private UseSkillDataTemp getTarget(CommonOperateTypeInfo commonOperateTypeInfo) {
        Skill.CS10052 cs10052 = (Skill.CS10052) commonOperateTypeInfo.message();
        FightSkillTemplate fightSkillTemplate = FightSkillTemplateHolder.getData(cs10052.getSkillId());
        //生成技能释放上下文
        UseSkillDataTemp useSkillDataTemp = UseSkillDataTemp.of(cs10052, self());
        useSkillDataTemp.setScene(self());
        useSkillDataTemp.setR(commonOperateTypeInfo.r());
        useSkillDataTemp.setSceneId(this.sceneId);
        //TODO 获取目标
        if (SkillAimType.isOne(fightSkillTemplate)) {
        }
        //TODO 这里代码需要修改，写的什么垃圾
        useSkillDataTemp.getTargetId().forEach(id -> {
            PlayerInfo playerInfo = playerInfoMap.get(id);
            if (Objects.nonNull(playerInfo)) {
                useSkillDataTemp.getTargetParameters().add(new TargetParameter(id, playerInfo.level(), playerInfo.profession()));
            }else {
                NpcOrganism npcOrganism = npcOrganismMap.get(id);
                if (Objects.nonNull(npcOrganism)) {
                    useSkillDataTemp.getTargetParameters().add(new TargetParameter(id, 1, npcOrganism.getOrganismType()));
                }else {
                    MonsterOrganism monsterOrganism = monsterOrganismMap.get(id);
                    useSkillDataTemp.getTargetParameters().add(new TargetParameter(id, 1, monsterOrganism.getOrganismType()));
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

    private void getSceneAllOrganism(Client.ReceivedFromClient r, long organismId) {
        playerInfoMap.forEach((id, playerInfo) ->
                r.client().tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_RETURN_ENTITY,
                        SceneProtocolBuilder.getSc10034(id, OrganismType.PLAYER, getPositionInfo(id), playerInfo.profession())), self()));
        npcOrganismMap.values().forEach(npcOrganism ->
                r.client().tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_RETURN_ENTITY,
                        SceneProtocolBuilder.getSc10034(npcOrganism.getId(), npcOrganism.getOrganismType(),
                                getPositionInfo(npcOrganism.getId()), npcOrganism.getOrganismTemplateId())), self()));
        List<Organism> list = new ArrayList<>(npcOrganismMap.values());
        this.sceneData.tell(new Publish(new CreatePlayerEntitiesAfter(clientMap, organismId, list)), self());

    }

    private void getSceneAllOrganismOutSelf(Client.ReceivedFromClient r, long organismId) {
        playerInfoMap.forEach((id, playerInfo) -> {
            if (id != organismId) {
                r.client().tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_RETURN_ENTITY,
                        SceneProtocolBuilder.getSc10034(id, OrganismType.PLAYER, getPositionInfo(id), playerInfo.profession())), self());
            }
        });
        npcOrganismMap.values().forEach(npcOrganism ->
                r.client().tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_RETURN_ENTITY,
                        SceneProtocolBuilder.getSc10034(npcOrganism.getId(), npcOrganism.getOrganismType(),
                                getPositionInfo(npcOrganism.getId()), npcOrganism.getOrganismTemplateId())), self()));
        List<Organism> list = new ArrayList<>(npcOrganismMap.values());
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
}

package application.module.scene;

import akka.actor.ActorRef;
import akka.actor.Props;
import application.module.common.CommonProtocolBuilder;
import application.module.common.CommonProtocols;
import application.module.organism.*;
import application.module.organism.PlayerFight;
import application.module.player.data.entity.PlayerInfo;
import application.module.player.data.message.event.PlayerLogout;
import application.module.player.fight.attribute.AttributeProtocolBuilder;
import application.module.player.fight.attribute.AttributeProtocols;
import application.module.player.fight.attribute.data.message.MonsterDead;
import application.module.player.fight.attribute.data.message.PlayerDead;
import application.module.player.fight.skill.operate.SkillUseScene;
import application.module.player.operate.PlayerLoginOpt;
import application.module.player.util.PlayerOperateType;
import application.module.scene.data.entity.PositionInfo;
import application.module.scene.fight.buff.FightBuffProtocolBuilder;
import application.module.scene.fight.buff.FightBuffProtocols;
import application.module.scene.fight.skill.base.context.TargetParameter;
import application.module.scene.fight.skill.base.context.UseSkillDataTemp;
import application.module.scene.fight.skill.util.SkillAimType;
import application.module.scene.operate.*;
import application.module.scene.organism.PlayerSceneMgr;
import application.util.ApplicationErrorCode;
import application.util.CommonOperateTypeInfo;
import application.util.MessageUtil;
import application.util.Vector;
import mobius.core.java.api.AbstractLogActor;
import mobius.modular.client.Client;
import protocol.Skill;
import template.FightSkillTemplate;
import template.FightSkillTemplateHolder;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static application.module.scene.Scene.MAX_MONSTER_COUNT;

/**
 * @author Luo Yong
 * @date 2022-3-3
 * @Source 1.0
 */
public class SceneActor extends AbstractLogActor {

    private final Scene scene;

    public final static float DEFAULT_X = 0;
    public final static float DEFAULT_Y = 5;
    public final static int DEFAULT_FACE = OrganismFaceType.RIGHT;
    private Map<Integer, ActorRef> sceneId2SceneMap;

    private final List<ActorRef> scenePortalRefreshMonsterTriggerList = new ArrayList<>();

    public SceneActor(int sceneTemplateId) {
        this.scene = new Scene(self(), sceneTemplateId, sender());
        this.scene.init();
        getContext().getSystem().scheduler().scheduleWithFixedDelay(Duration.ofMillis(1000), Duration.ofMillis(1000), this::selectClient, getContext().dispatcher());
    }

    public static Props create(int sceneId) {
        return Props.create(SceneActor.class, sceneId);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SceneMove.class, this::sceneMove)
                .match(SceneStop.class, this::sceneStop)
                .match(SceneJump.class, this::sceneJump)
                .match(PlayerOperateType.class, this::playerOperateType)
                .match(ScenePlayerExit.class, this::scenePlayerExit)
                .match(ScenePlayerEntry.class, this::scenePlayerEntry)
                .match(SceneOrganismEntry.class, this::sceneOrganismEntry)
                .match(SceneOrganismExit.class, this::sceneOrganismExit)
                .match(PlayerLoginOpt.class, this::playerLoginOpt)
                .match(SceneInit.class, this::sceneInit)
                .match(PlayerLogout.class, this::playerLogout)
                .match(SceneFlash.class, this::sceneFlash)
                .match(PlayerDead.class, this::playerDead)
                .match(PlayerReceive.class, this::playerReceive)
                .match(HelpUseSkill.class, this::npcUseSkill)
                .match(PickUpItem.class, this::pickUpItem)
                .match(FuckNpc.class, this::fuckNpc)
                .match(MonsterDead.class, this::monsterDead)
                .match(SceneRush.class, this::sceneRush)
                .match(ScenePlayerEntryWrap.class, this::scenePlayerEntryWrap)
                .match(AllSceneInitFinally.class, this::allSceneInitFinally)
                .build();
    }

    private void allSceneInitFinally(AllSceneInitFinally allSceneInitFinally) {
        sceneId2SceneMap = allSceneInitFinally.sceneId2SceneMap();
    }

    private void scenePlayerEntry(ScenePlayerEntry scenePlayerEntry) {
        var cs10300 = scenePlayerEntry.cs10300();
        int newSceneId = (int) cs10300.getSceneId();
        if (!this.sceneId2SceneMap.containsKey(newSceneId)) {
            MessageUtil.sendClient(scenePlayerEntry.r().client(), CommonProtocols.APPLICATION_ERROR,
                    CommonProtocolBuilder.getSc10080(ApplicationErrorCode.SCENE_UNOPENED), self());
            return;
        }
        PlayerFight playerFight = this.scene.getPlayerFight(scenePlayerEntry.r().uID());
        if (Objects.isNull(playerFight)) {
            return;
        }
        scenePlayerExit(playerFight);
        ActorRef newScene = this.sceneId2SceneMap.get(newSceneId);
        newScene.tell(new ScenePlayerEntryWrap(playerFight), self());
    }

    private void playerOperateType(PlayerOperateType playerOperateType) {
        this.scene.getPlayerSceneMgr().receiver(this.scene, playerOperateType);
    }

    private void sceneRush(SceneRush sceneRush) {
        Client.ReceivedFromClient r = sceneRush.r();
        protocol.Scene.CS10312 cs10312 = sceneRush.cs10312();
        long organismId = cs10312.getOrganismId();
        setPositionInfo(organismId, new PositionInfo(cs10312.getPositionX(), cs10312.getPositionY(),
                getFightOrganism(organismId).getPositionInfo().getFace()));
        //TODO 需要根据时间加速度，判断是否移动位置距离上一次同步合理
        getPlayerSceneMgr().sendToOtherClient(this.scene, r.protoID(), SceneProtocolBuilder.getSc10312(organismId, cs10312), organismId);
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
        PlayerFight playerFight = this.scene.getPlayerFight(cs10030.getOrganismId());
        if (Objects.isNull(playerFight)) {
            return;
        }
        playerFight.getFightBuffMgr().addBuff(10013, playerFight, playerFight);
        long organismItemId = cs10030.getOrganismItemId();
        removeOrganism(organismItemId);
    }

    private void npcUseSkill(HelpUseSkill helpUseSkill) {
        ActorRef actorRef = getPlayerSceneMgr().getClient(getPlayerSceneMgr().getAiAgentPlayerId());
        if (Objects.nonNull(actorRef)) {
            actorRef.tell(new application.client.Client.SendToClientJ(SceneProtocols.HELP_USE_SKILL,
                    SceneProtocolBuilder.getSc10308(helpUseSkill.organismId(), helpUseSkill.skillTemplateId())), self());
        }
    }

    private void playerReceive(PlayerReceive playerReceive) {
        Client.ReceivedFromClient r = playerReceive.r();
        long playerId = r.uID();
        // TODO: 2022-5-17 xxx
//        this.sceneData.tell(new Publish(new PlayerReceiveAfter(Map.copyOf(clientMap), playerId, self())), self());
        getPlayerSceneMgr().sendToAllClient(this.scene, SceneProtocols.SCENE_FLASH, SceneProtocolBuilder.getSc10306(r.uID(), getPlayerBirth()));
    }

    private void playerDead(PlayerDead playerDead) {

    }

    private void sceneFlash(SceneFlash sceneFlash) {
        Client.ReceivedFromClient r = sceneFlash.r();
        protocol.Scene.CS10306 cs10306 = sceneFlash.cs10306();
        long playerId = cs10306.getOrganismId();
        setPositionInfo(playerId, new PositionInfo(cs10306.getPositionX(), cs10306.getPositionY()));
        //TODO 需要校验玩家当前位置是否可以闪现
        getPlayerSceneMgr().sendToAllClient(this.scene, r.protoID(), SceneProtocolBuilder.getSc10306(playerId, cs10306));
    }

    private void playerLogout(PlayerLogout playerLogout) {
        scenePlayerExit(playerLogout.r().uID());
    }

    private void sceneOrganismEntry(SceneOrganismEntry sceneOrganismEntry) {
        Organism organism = sceneOrganismEntry.organism();
        switch (organism) {
            case NpcOrganism npcOrganism -> {
                this.scene.getNpcSceneMgr().addNpcOrganism(npcOrganism);
                getPlayerSceneMgr().getPlayerFightMap().values().forEach(playerFight -> {
                    npcOrganism.sendSelfData(playerFight.getClient());
                });
            }
            case MonsterOrganism monsterOrganism -> {
                if (this.scene.getMonsterSceneMgr().getMonsterMap().size() >= MAX_MONSTER_COUNT) {
                    return;
                }
                this.scene.getMonsterSceneMgr().addMonsterOrganism(monsterOrganism);
                getPlayerSceneMgr().getPlayerFightMap().values().forEach(playerFight -> {
                    monsterOrganism.sendSelfData(playerFight.getClient());
                });
            }
            case ItemOrganism itemOrganism -> {
                this.scene.getItemSceneMgr().addItemOrganism(itemOrganism);
                getPlayerSceneMgr().getPlayerFightMap().values().forEach(playerFight -> {
                    itemOrganism.sendSelfData(playerFight.getClient());
                });
            }
            default -> throw new IllegalStateException("Unexpected value: " + organism.getClass().getName());
        }

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
            if (this.scene.getMonsterSceneMgr().containsMonsterOrganism(organismId)) {
                if (this.scene.getMonsterSceneMgr().getMonsterOrganism(organismId).getOrganismTemplateId() == 10009) {
                    dealBossDead();
                }
            }
            this.scene.getMonsterSceneMgr().removeMonsterOrganism(organismId);
            getPlayerSceneMgr().sendToAllClient(this.scene, SceneProtocols.SCENE_EXIT, SceneProtocolBuilder.getSc10301(organismId));
        } else if (organismType == OrganismType.NPC) {
            this.scene.getNpcSceneMgr().removeNpcOrganism(organismId);
            getPlayerSceneMgr().sendToAllClient(this.scene, SceneProtocols.SCENE_EXIT, SceneProtocolBuilder.getSc10301(organismId));
        } else if (organismType == OrganismType.ITEM) {
            this.scene.getItemSceneMgr().removeItemOrganism(organismId);
            getPlayerSceneMgr().sendToAllClient(this.scene, SceneProtocols.SCENE_EXIT, SceneProtocolBuilder.getSc10301(organismId));
        }
    }

    // TODO: 2022-5-9 临时处理boss死亡
    private void dealBossDead() {
        //场景所有怪物消失
        this.scene.getMonsterSceneMgr().getMonsterMap().keySet().removeIf(organismId -> {
            getPlayerSceneMgr().sendToAllClient(this.scene, SceneProtocols.SCENE_EXIT, SceneProtocolBuilder.getSc10301(organismId));
            return true;
        });
        //销毁场景触发器
        getContext().stop(scenePortalRefreshMonsterTriggerList.get(0));

        getContext().getSystem().scheduler().scheduleOnce(Duration.ofMillis(10000), this::sceneOut, getContext().dispatcher());

    }

    private void sceneOut() {
//        clientMap.entrySet().removeIf(entry -> {
//            this.sceneData.tell(new ReturnPerScene(entry.getKey(), entry.getValue()), self());
//            return true;
//        });
//        this.sceneData.tell(new SceneOut(this.scene.getSceneTemplateId()), self());
    }

    private void sceneInit(SceneInit sceneInit) {
        if (this.scene.getSceneTemplateId() == 20004) {
//            ActorRef actorRef = getContext().actorOf(ScenePortalRefreshMonsterTrigger.create(1));
//            actorRef.tell(sceneInit, self());
//            scenePortalRefreshMonsterTriggerList.add(actorRef);
        }
    }

    private void sceneJump(SceneJump sceneJump) {
        Client.ReceivedFromClient r = sceneJump.r();
        protocol.Scene.CS10305 cs10305 = sceneJump.cs10305();
        protocol.Scene.JumpInfo jumpInfo = cs10305.getJumpInfo();
        long organismId = cs10305.getOrganismId();
        setPositionInfo(organismId, new PositionInfo(jumpInfo.getFinalPosX(), jumpInfo.getFinalPosY(), jumpInfo.getFace()));
        //TODO 需要根据时间加速度，判断是否移动位置距离上一次同步合理
        getPlayerSceneMgr().sendToOtherClient(this.scene, r.protoID(), SceneProtocolBuilder.getSc10305(organismId, cs10305), organismId);
    }

    private void playerLoginOpt(PlayerLoginOpt playerLogin) {
        PlayerFight playerFight = playerLogin.playerFight();
        long playerId = playerFight.getId();
        ActorRef client = playerFight.getClient();
        PositionInfo positionInfo = playerFight.getPositionInfo();
        if (Objects.isNull(positionInfo)) {
            Vector vector = getPlayerBirth();
            positionInfo = new PositionInfo(vector.x(), vector.y(), DEFAULT_FACE);
            playerFight.setPositionInfo(positionInfo);
        }
        playerFight.setScene(this.scene);
        this.scene.getPlayerSceneMgr().addPlayerFight(playerFight);
        client.tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_ENTER,
                SceneProtocolBuilder.getSc10300(this.scene.getSceneTemplateId(), positionInfo)), self());
        //给场景的其他玩家发送我进来了
        sendToOtherClientData(playerId, playerFight, positionInfo);

        //获取场景实体的数据
        getSceneAllOrganism(client);
    }

    private void sendToOtherClientData(long playerId, PlayerFight playerFight, PositionInfo positionInfo) {
        this.scene.getPlayerSceneMgr().sendToOtherClient(this.scene, SceneProtocols.SCENE_RETURN_ENTITY,
                SceneProtocolBuilder.getSc10304(playerId, OrganismType.PLAYER, positionInfo,
                        getPlayerInfo(playerId).profession()), playerId);

        this.scene.getPlayerSceneMgr().sendToOtherClient(this.scene, AttributeProtocols.FIGHT_ATTRIBUTE_GET,
                AttributeProtocolBuilder.get10040(playerId, playerFight.getFightMap()), playerId);

        this.scene.getPlayerSceneMgr().sendToOtherClient(this.scene, FightBuffProtocols.GET,
                FightBuffProtocolBuilder.getSc10070(playerId, playerFight.getFightBuffMgr().getFightOrganismBuffs()), playerId);

    }

    private void sceneStop(SceneStop sceneStop) {
        Client.ReceivedFromClient r = sceneStop.r();
        protocol.Scene.CS10303 cs10303 = sceneStop.cs10303();
        protocol.Scene.StopInfo stopInfo = cs10303.getStopInfo();
        long organismId = cs10303.getOrganismId();
        setPositionInfo(organismId, new PositionInfo(stopInfo.getPositionX(), stopInfo.getPositionY(), stopInfo.getFace()));
        //TODO 需要根据时间加速度，判断是否移动位置距离上一次同步合理
        getPlayerSceneMgr().sendToOtherClient(this.scene, r.protoID(), SceneProtocolBuilder.getSc10303(organismId, cs10303), organismId);
    }

    private void setPositionInfo(long organismId, PositionInfo positionInfo) {
        Organism organism = getFightOrganism(organismId);
        organism.setPositionInfo(positionInfo);
    }

    private FightOrganism getFightOrganism(long organismId) {
        FightOrganism fightOrganism = null;
        if (getPlayerSceneMgr().containsPlayerFight(organismId)) {
            fightOrganism = this.scene.getPlayerFight(organismId);
        } else if (this.scene.getMonsterSceneMgr().containsMonsterOrganism(organismId)) {
            fightOrganism = this.scene.getMonsterSceneMgr().getMonsterOrganism(organismId);
        } else if (this.scene.getNpcSceneMgr().containsNpcOrganism(organismId)) {
            fightOrganism = this.scene.getNpcSceneMgr().getNpcOrganism(organismId);
        }
        return fightOrganism;
    }

    private void scenePlayerExit(ScenePlayerExit scenePlayerExit) {
        scenePlayerExit(scenePlayerExit.r().uID());
    }

    private void scenePlayerExit(long playerId) {

        PositionInfo positionInfo = this.scene.getPlayerFight(playerId).getPositionInfo();
        scene.getPlayerSceneMgr().removePlayerFight(playerId);

        //TODO 退出场景，玩家信息需要清空
        getPlayerSceneMgr().sendToAllClient(this.scene, SceneProtocols.SCENE_EXIT, SceneProtocolBuilder.getSc10301(playerId));
    }

    private void scenePlayerExit(PlayerFight playerFight) {
        long playerId = playerFight.getId();
        playerFight.switchScene();
        scene.getPlayerSceneMgr().removePlayerFight(playerId);

        //TODO 退出场景，玩家信息需要清空
        getPlayerSceneMgr().sendToAllClient(this.scene, SceneProtocols.SCENE_EXIT, SceneProtocolBuilder.getSc10301(playerId));
    }

    private void scenePlayerEntryWrap(ScenePlayerEntryWrap scenePlayerEntryWrap) {
        PlayerFight playerFight = scenePlayerEntryWrap.playerFight();
        PlayerInfo playerInfo = playerFight.getPlayerInfo();
        long playerId = playerInfo.id();
        ActorRef client = playerFight.getClient();
        Vector vector = getPlayerBirth();
        PositionInfo positionInfo = new PositionInfo(vector.x(), vector.y(), DEFAULT_FACE);
        playerFight.setPositionInfo(positionInfo);
        // TODO: 2022-5-17 战斗相关的处理切换场景 FightAttributeMgr等
        getPlayerSceneMgr().addPlayerFight(playerFight);

        playerFight.getPlayerActor().tell(new PlayerEntrySuccessOpt(playerId, this.scene.getSceneTemplateId(), self()), self());
        client.tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_ENTER,
                SceneProtocolBuilder.getSc10300(this.scene.getSceneTemplateId(), positionInfo)), self());
        //给场景的其他玩家发送我进来了
        getPlayerSceneMgr().sendToOtherClient(this.scene, SceneProtocols.SCENE_RETURN_ENTITY, SceneProtocolBuilder.getSc10304(
                playerId, OrganismType.PLAYER, positionInfo, playerFight.getPlayerInfo().profession()), playerId);

        //获取场景实体的数据
        getSceneAllOrganism(client);
    }

    private void sceneMove(SceneMove sceneMove) {
        Client.ReceivedFromClient r = sceneMove.r();
        protocol.Scene.CS10302 cs10302 = sceneMove.cs10302();
        protocol.Scene.MoveInfo moveInfo = cs10302.getMoveInfo();
        long organismId = cs10302.getOrganismId();
        setPositionInfo(organismId, new PositionInfo(moveInfo.getPositionX(), moveInfo.getPositionY(), moveInfo.getFace()));
        //TODO 需要根据时间加速度，判断是否移动位置距离上一次同步合理
        getPlayerSceneMgr().sendToOtherClient(this.scene, r.protoID(), SceneProtocolBuilder.getSc10302(organismId, cs10302), organismId);
    }

    private void skillUseScene(SkillUseScene skillUseScene) {
        // TODO: 2022-5-21 这里需要判断当前场景是否可以释放技能

        long playerId = skillUseScene.cs10052().getFightOrganismId();
        PlayerFight playerFight = this.scene.getPlayerFight(playerId);
        if (Objects.nonNull(playerFight)) {
            playerFight.getFightSkillMgr().activeUseSkill(this.scene, skillUseScene.cs10052());
        }
//        CommonOperateTypeInfo commonOperateTypeInfo = (CommonOperateTypeInfo) skillUseScene.operateTypeInfo();
//        if (skillUseScene.isSkillProcess()) {
//            Client.ReceivedFromClient r = commonOperateTypeInfo.r();
//            Skill.CS10055 cs10055 = (Skill.CS10055) commonOperateTypeInfo.message();
//            long organismId = cs10055.getFightOrganismId();
//            //TODO 判断当前场景是否可以释放技能，或者当前玩家位置是否可以放技能。
//            if (true) {
//                sender().tell(new SkillUseScene(new SkillUseInfo(commonOperateTypeInfo.r(), getTarget(commonOperateTypeInfo, true)), true), self());
//                getPlayerSceneMgr().sendToOtherClient(this.scene, r.protoID(), FightSkillProtocolBuilder.getSc10055(cs10055), r.uID());
//            } else {
//                commonOperateTypeInfo.r().client().tell(new SendToClientJ(CommonProtocols.APPLICATION_ERROR,
//                        CommonProtocolBuilder.getSc10080(ApplicationErrorCode.USE_SKILL_SCENE)), self());
//            }
//            return;
//        }
//        Client.ReceivedFromClient r = commonOperateTypeInfo.r();
//        Skill.CS10052 cs10052 = (Skill.CS10052) commonOperateTypeInfo.message();
//        long organismId = cs10052.getFightOrganismId();
//        //TODO 判断当前场景是否可以释放技能，或者当前玩家位置是否可以放技能。
//        if (true) {
//            sender().tell(new SkillUseScene(new SkillUseInfo(commonOperateTypeInfo.r(), getTarget(commonOperateTypeInfo, false)), false), self());
//            getPlayerSceneMgr().sendToOtherClient(this.scene, r.protoID(), FightSkillProtocolBuilder.getSc10052(cs10052), r.uID());
//        } else {
//            commonOperateTypeInfo.r().client().tell(new SendToClientJ(CommonProtocols.APPLICATION_ERROR,
//                    CommonProtocolBuilder.getSc10080(ApplicationErrorCode.USE_SKILL_SCENE)), self());
//        }
    }

    private UseSkillDataTemp getTarget(CommonOperateTypeInfo commonOperateTypeInfo, boolean isSkillProcess) {
        // TODO: 2022-4-29 后续需要删除
        if (isSkillProcess) {
            Skill.CS10055 cs10055 = (Skill.CS10055) commonOperateTypeInfo.message();
            //生成技能释放上下文
            UseSkillDataTemp useSkillDataTemp = UseSkillDataTemp.of(cs10055, this.scene);
            useSkillDataTemp.setR(commonOperateTypeInfo.r());
            useSkillDataTemp.setSceneId(this.scene.getSceneId());
            long fightOrganismId = cs10055.getFightOrganismId();
            FightOrganism fightOrganism = getFightOrganism(fightOrganismId);
            if (Objects.nonNull(fightOrganism) && fightOrganism.getOrganismType() != OrganismType.PLAYER) {
                useSkillDataTemp.setAttackType(fightOrganism.getOrganismType());
                if (fightOrganism instanceof MonsterOrganism monsterOrganism) {
                    useSkillDataTemp.setAttackTemplateId(monsterOrganism.getOrganismTemplateId());
                } else if (fightOrganism instanceof NpcOrganism npcOrganism) {
                    useSkillDataTemp.setAttackTemplateId(npcOrganism.getOrganismTemplateId());
                }
            }

            //TODO 这里代码需要修改，写的什么垃圾
            useSkillDataTemp.getTargetId().forEach(id -> {
                PlayerFight playerFight = getPlayerSceneMgr().getPlayerFight(useSkillDataTemp.getAttackId());
                if (Objects.nonNull(playerFight)) {
                    useSkillDataTemp.getTargetParameters().add(new TargetParameter(playerFight));
                } else {
                    useSkillDataTemp.getTargetParameters().add(new TargetParameter(getFightOrganism(id)));
                }
            });
            return useSkillDataTemp;
        }

        Skill.CS10052 cs10052 = (Skill.CS10052) commonOperateTypeInfo.message();
        FightSkillTemplate fightSkillTemplate = FightSkillTemplateHolder.getData(cs10052.getSkillId());
        //生成技能释放上下文
        UseSkillDataTemp useSkillDataTemp = UseSkillDataTemp.of(cs10052, this.scene);
        useSkillDataTemp.setR(commonOperateTypeInfo.r());
        useSkillDataTemp.setSceneId(this.scene.getSceneTemplateId());
        long fightOrganismId = cs10052.getFightOrganismId();
        FightOrganism fightOrganism = getFightOrganism(fightOrganismId);
        if (Objects.nonNull(fightOrganism) && fightOrganism.getOrganismType() != OrganismType.PLAYER) {
            useSkillDataTemp.setAttackType(fightOrganism.getOrganismType());
            if (fightOrganism instanceof MonsterOrganism monsterOrganism) {
                useSkillDataTemp.setAttackTemplateId(monsterOrganism.getOrganismTemplateId());
            } else if (fightOrganism instanceof NpcOrganism npcOrganism) {
                useSkillDataTemp.setAttackTemplateId(npcOrganism.getOrganismTemplateId());
            }
        }
        //TODO 获取目标
        if (SkillAimType.isOne(fightSkillTemplate)) {
        }
        //TODO 这里代码需要修改，写的什么垃圾
        useSkillDataTemp.getTargetId().forEach(id -> {
            PlayerFight playerFight = getPlayerSceneMgr().getPlayerFight(useSkillDataTemp.getAttackId());
            if (Objects.nonNull(playerFight)) {
                useSkillDataTemp.getTargetParameters().add(new TargetParameter(playerFight));
            } else {
                useSkillDataTemp.getTargetParameters().add(new TargetParameter(getFightOrganism(id)));
            }
        });
        return useSkillDataTemp;
    }


    private void getSceneAllOrganism(ActorRef client) {
        scene.getPlayerSceneMgr().getPlayerFightMap().values().forEach(playerFight -> playerFight.sendSelfData(client));
        scene.getNpcSceneMgr().getNpcMap().values().forEach(npcOrganism -> npcOrganism.sendSelfData(client));
        scene.getMonsterSceneMgr().getMonsterMap().values().forEach(monsterOrganism -> monsterOrganism.sendSelfData(client));
        scene.getItemSceneMgr().getItemMap().values().forEach(itemOrganism -> itemOrganism.sendSelfData(client));
    }

    private void getSceneAllOrganismOutSelf(ActorRef client, long organismId) {
        scene.getPlayerSceneMgr().getPlayerFightMap().values().forEach(playerFight -> {
            long id = playerFight.getId();
            if (id != organismId) {
                playerFight.sendSelfData(client);
            }
        });
        scene.getPlayerSceneMgr().getPlayerFightMap().values().forEach(playerFight -> playerFight.sendSelfData(client));
        scene.getNpcSceneMgr().getNpcMap().values().forEach(npcOrganism -> npcOrganism.sendSelfData(client));
        scene.getMonsterSceneMgr().getMonsterMap().values().forEach(monsterOrganism -> monsterOrganism.sendSelfData(client));
        scene.getItemSceneMgr().getItemMap().values().forEach(itemOrganism -> itemOrganism.sendSelfData(client));
    }

    public PositionInfo getPositionInfo(long id) {
        return getFightOrganism(id).getPositionInfo();
    }

    public Vector getPlayerBirth() {
        return this.scene.getPlayerBirth();
    }

    public PlayerInfo getPlayerInfo(long id) {
        return getPlayerSceneMgr().getPlayerFight(id).getPlayerInfo();
    }

    public void selectClient() {
        getPlayerSceneMgr().selectClient(this.scene);
    }

    public PlayerSceneMgr getPlayerSceneMgr() {
        return this.scene.getPlayerSceneMgr();
    }

    public ActorRef getClient(long playerId) {
        return getPlayerSceneMgr().getPlayerFight(playerId).getClient();
    }

    private void removeOrganism(long organismId) {
        if (getPlayerSceneMgr().containsPlayerFight(organismId)) {
            this.scene.getPlayerSceneMgr().removePlayerFight(organismId);
        } else if (this.scene.getMonsterSceneMgr().containsMonsterOrganism(organismId)) {
            this.scene.getMonsterSceneMgr().removeMonsterOrganism(organismId);
        } else if (this.scene.getNpcSceneMgr().containsNpcOrganism(organismId)) {
            this.scene.getNpcSceneMgr().removeNpcOrganism(organismId);
        }
        getPlayerSceneMgr().sendToAllClient(this.scene, SceneProtocols.SCENE_EXIT, SceneProtocolBuilder.getSc10301(organismId));
    }


}

package application.module.scene;

import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import application.module.common.CommonProtocolBuilder;
import application.module.common.CommonProtocols;
import application.module.organism.FightOrganism;
import application.module.organism.OrganismFaceType;
import application.module.organism.OrganismType;
import application.module.organism.PlayerFight;
import application.module.player.data.entity.PlayerInfo;
import application.module.player.data.message.event.PlayerLogout;
import application.module.player.fight.attribute.AttributeProtocolBuilder;
import application.module.player.fight.attribute.AttributeProtocols;
import application.module.player.fight.attribute.data.message.MonsterDead;
import application.module.player.fight.attribute.data.message.PlayerDead;
import application.module.player.operate.PlayerLoginOpt;
import application.module.player.util.PlayerOperateType;
import application.module.scene.fight.buff.FightBuffProtocolBuilder;
import application.module.scene.fight.buff.FightBuffProtocols;
import application.module.scene.fight.state.base.StateType;
import application.module.scene.operate.*;
import application.module.scene.organism.PlayerSceneMgr;
import application.module.scene.trigger.ScenePortalRefreshMonsterTrigger;
import application.util.ApplicationErrorCode;
import application.util.MessageUtil;
import application.util.Vector3;
import mobius.core.java.api.AbstractLogActor;
import mobius.modular.client.Client;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

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
    Cancellable cancellable;


    public SceneActor(int sceneTemplateId) {
        this.scene = new Scene(self(), sceneTemplateId);
        this.scene.init(getContext());
        cancellable = getContext().getSystem().scheduler().scheduleWithFixedDelay(Duration.ofMillis(1000),
                Duration.ofMillis(1000), self(), new SceneTick(), getContext().dispatcher(), self());
    }

    public static Props create(int sceneId) {
        return Props.create(SceneActor.class, sceneId);
    }


    @Override
    public void postStop() throws Exception, Exception {
        super.postStop();
        cancellable.cancel();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SceneMove.class, this::sceneMove)
                .match(SceneStop.class, this::sceneStop)
                .match(SceneJump.class, this::sceneJump)
                .match(SceneTick.class, this::sceneTick)
                .match(PlayerOperateType.class, this::playerOperateType)
                .match(ScenePlayerExit.class, this::scenePlayerExit)
                .match(ScenePlayerEntry.class, this::scenePlayerEntry)
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
                .match(SceneOut.class, this::sceneOut)
                .build();
    }

    private void allSceneInitFinally(AllSceneInitFinally allSceneInitFinally) {
        this.scene.setSceneId2SceneMap(allSceneInitFinally.sceneId2SceneMap());
    }

    private void scenePlayerEntry(ScenePlayerEntry scenePlayerEntry) {
        var cs10300 = scenePlayerEntry.cs10300();
        int newSceneId = (int) cs10300.getSceneId();
        PlayerFight playerFight = getPlayerSceneMgr().getPlayerFight(scenePlayerEntry.r().uID());
        if(Objects.isNull(playerFight)) {
            return;
        }
        scenePlayerEntry(newSceneId, playerFight);
    }

    private void scenePlayerEntry(int newSceneId, PlayerFight playerFight) {
        if (!containsSceneActor(newSceneId)) {
            MessageUtil.sendClient(playerFight.getClient(), CommonProtocols.APPLICATION_ERROR,
                    CommonProtocolBuilder.getSc10080(ApplicationErrorCode.SCENE_UNOPENED), self());
            playerFight.getPlayerActor().tell(new PlayerEntryErrorOpt(playerFight.getId(), newSceneId), self());
            return;
        }
        // TODO: 2022-6-7 需要根据要进入的场景id进行条件校验
        scenePlayerExit(playerFight);
        ActorRef newScene = getSceneActor(newSceneId);
        newScene.tell(new ScenePlayerEntryWrap(playerFight), self());
    }

    private void playerOperateType(PlayerOperateType playerOperateType) {
        this.scene.getPlayerSceneMgr().receiver(this.scene, playerOperateType);
    }

    private void sceneRush(SceneRush sceneRush) {
        Client.ReceivedFromClient r = sceneRush.r();
        protocol.Scene.CS10312 cs10312 = sceneRush.cs10312();
        long organismId = cs10312.getOrganismId();
        FightOrganism organism = getFightOrganism(organismId);
        if (Objects.nonNull(organism) && !organism.getFightStateMgr().isMoveUse()) {
            return;
        }
        setPoint(organism, new Vector3(cs10312.getPositionX(), cs10312.getPositionY(),
                getFightOrganism(organismId).getPoint().z()));
        //TODO 需要根据时间加速度，判断是否移动位置距离上一次同步合理
        getPlayerSceneMgr().sendToOtherClient(this.scene, r.protoID(), SceneProtocolBuilder.getSc10312(organismId, cs10312), organismId);
    }

    private void monsterDead(MonsterDead monsterDead) {
        sceneOrganismExit(monsterDead.organismId(), monsterDead.organismType());
    }

    private void fuckNpc(FuckNpc fuckNpc) {
        var cs10311 = fuckNpc.cs10311();
        if (Objects.isNull(this.scene.getTrigger())) {
            return;
        }
        this.scene.getTrigger().closeNpcDoor(new CloseNpcDoor(cs10311.getOrganismNpcId(), 0, cs10311.getOrganismId()));
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
        PlayerFight playerFight = getPlayerSceneMgr().getPlayerFight(playerId);
        if (Objects.nonNull(playerFight)) {
            playerFight.getFightStateMgr().changeState(StateType.ActionType.IDLE_STATE, scene);
            playerFight.getFightAttributeMgr().receive();
        }
        getPlayerSceneMgr().sendToAllClient(this.scene, SceneProtocols.SCENE_FLASH, SceneProtocolBuilder.getSc10306(r.uID(), getPlayerBirth()));
    }

    private void playerDead(PlayerDead playerDead) {

    }

    private void sceneFlash(SceneFlash sceneFlash) {
        Client.ReceivedFromClient r = sceneFlash.r();
        protocol.Scene.CS10306 cs10306 = sceneFlash.cs10306();
        long organismId = cs10306.getOrganismId();

        FightOrganism organism = getFightOrganism(organismId);
        if (Objects.nonNull(organism) && !organism.getFightStateMgr().isMoveUse()) {
            return;
        }
        setPoint(organism, Vector3.ofXY(cs10306.getPositionX(), cs10306.getPositionY()));
        //TODO 需要校验玩家当前位置是否可以闪现
        getPlayerSceneMgr().sendToAllClient(this.scene, r.protoID(), SceneProtocolBuilder.getSc10306(organismId, cs10306));
    }

    private void playerLogout(PlayerLogout playerLogout) {
        scenePlayerExit(playerLogout.playerId());
    }


    private void sceneOrganismExit(SceneOrganismExit sceneOrganismExit) {
        sceneOrganismExit(sceneOrganismExit.organismId(), sceneOrganismExit.organismType());
    }

    private void sceneOrganismExit(long organismId, byte organismType) {
        if (organismType == OrganismType.MONSTER) {
            if (this.scene.getMonsterSceneMgr().containsMonsterOrganism(organismId)) {
                if (this.scene.getMonsterSceneMgr().getMonsterOrganism(organismId).getOrganismTemplateId() == 10009) {
                }
            }
            this.scene.getMonsterSceneMgr().removeMonsterOrganism(organismId);
        } else if (organismType == OrganismType.NPC) {
            this.scene.getNpcSceneMgr().removeNpcOrganism(organismId);
        } else if (organismType == OrganismType.ITEM) {
            this.scene.getItemSceneMgr().removeItemOrganism(organismId);
        }
    }


    private void sceneOut(SceneOut sceneOut) {
        Iterator<PlayerFight> iterator = getPlayerSceneMgr().getPlayerFightMap().values().iterator();
        while (iterator.hasNext()) {
            PlayerFight playerFight = iterator.next();
            scenePlayerEntry(playerFight.getPerSceneId(), playerFight);
        }

        this.scene.getSceneModule().tell(sceneOut, self());
    }

    private void sceneInit(SceneInit sceneInit) {
        this.scene.setSceneModule(sender());
        if (this.scene.getSceneTemplateId() == 20004) {
            this.scene.setTrigger(new ScenePortalRefreshMonsterTrigger(20004));
            this.scene.getTrigger().init(this.scene);
        }
    }

    private void sceneJump(SceneJump sceneJump) {
        Client.ReceivedFromClient r = sceneJump.r();
        protocol.Scene.CS10305 cs10305 = sceneJump.cs10305();
        protocol.Scene.JumpInfo jumpInfo = cs10305.getJumpInfo();
        long organismId = cs10305.getOrganismId();
        FightOrganism organism = getFightOrganism(organismId);
        if (Objects.nonNull(organism) && !organism.getFightStateMgr().isMoveUse()) {
            return;
        }
        setPoint(organism, new Vector3(jumpInfo.getFinalPosX(), jumpInfo.getFinalPosY(), jumpInfo.getFace()));
        //TODO 需要根据时间加速度，判断是否移动位置距离上一次同步合理
        getPlayerSceneMgr().sendToOtherClient(this.scene, r.protoID(), SceneProtocolBuilder.getSc10305(organismId, cs10305), organismId);
    }

    private void playerLoginOpt(PlayerLoginOpt playerLogin) {
        PlayerFight playerFight = playerLogin.playerFight();
        long playerId = playerFight.getId();
        ActorRef client = playerFight.getClient();
        Vector3 vector3 = playerFight.getPoint();
        if (Objects.isNull(vector3)) {
            Vector3 vector3D = getPlayerBirth();
            vector3 = new Vector3(vector3D.x(), vector3D.y(), DEFAULT_FACE);
            playerFight.setPoint(vector3);
        }
        playerFight.setScene(this.scene);
        this.scene.getPlayerSceneMgr().addPlayerFight(playerFight);
        client.tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_ENTER,
                SceneProtocolBuilder.getSc10300(this.scene.getSceneTemplateId(), vector3)), self());
        //给场景的其他玩家发送我进来了
        playerFight.sendSelfDataToSceneOtherClient(scene);

        //获取场景实体的数据
        getSceneAllOrganism(client);
        playerFight.getPlayerActor().tell(new PlayerEntrySuccessOpt(playerId, this.scene.getSceneTemplateId(),
                self(), getPlayerSceneMgr().getPlayerFightMap().values().stream().filter(playerFight1 -> playerFight1.getId() != playerId)
                .collect(ArrayList::new, (list, playerFight1) -> list.add(playerFight1.getId()), ArrayList::addAll)), self());
    }

    private void sendToOtherClientData(long playerId, PlayerFight playerFight, Vector3 vector3) {
        this.scene.getPlayerSceneMgr().sendToOtherClient(this.scene, SceneProtocols.SCENE_RETURN_ENTITY,
                SceneProtocolBuilder.getSc10304(playerId, OrganismType.PLAYER, vector3,
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
        FightOrganism organism = getFightOrganism(organismId);
        if (Objects.nonNull(organism) && !organism.getFightStateMgr().isMoveUse()) {
            return;
        }
        setPoint(organism, new Vector3(stopInfo.getPositionX(), stopInfo.getPositionY(), stopInfo.getFace()));
        //TODO 需要根据时间加速度，判断是否移动位置距离上一次同步合理
        getPlayerSceneMgr().sendToOtherClient(this.scene, r.protoID(), SceneProtocolBuilder.getSc10303(organismId, cs10303), organismId);
    }

    private void setPoint(FightOrganism organism, Vector3 vector3) {
        if (Objects.isNull(organism)) {
            return;
        }
        organism.setPoint(vector3);
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
        scene.getPlayerSceneMgr().removePlayerFight(playerId);

        //TODO 退出场景，玩家信息需要清空
        getPlayerSceneMgr().sendToAllClient(this.scene, SceneProtocols.SCENE_EXIT, SceneProtocolBuilder.getSc10301(playerId));
    }

    private void scenePlayerExit(PlayerFight playerFight) {
        long playerId = playerFight.getId();
        playerFight.switchScene();
        scene.getPlayerSceneMgr().removePlayerFight(playerId);
        playerFight.setPerSceneId(this.scene.getSceneTemplateId());

        //TODO 退出场景，玩家信息需要清空
        getPlayerSceneMgr().sendToAllClient(this.scene, SceneProtocols.SCENE_EXIT, SceneProtocolBuilder.getSc10301(playerId));
    }

    private void scenePlayerEntryWrap(ScenePlayerEntryWrap scenePlayerEntryWrap) {
        PlayerFight playerFight = scenePlayerEntryWrap.playerFight();
        PlayerInfo playerInfo = playerFight.getPlayerInfo();
        long playerId = playerInfo.id();
        ActorRef client = playerFight.getClient();
        Vector3 vector3D = getPlayerBirth();
        Vector3 vector3 = new Vector3(vector3D.x(), vector3D.y(), DEFAULT_FACE);
        playerFight.setPoint(vector3);
        playerFight.setScene(this.scene);
        getPlayerSceneMgr().addPlayerFight(playerFight);

        client.tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_ENTER,
                SceneProtocolBuilder.getSc10300(this.scene.getSceneTemplateId(), vector3)), self());
        //给场景的其他玩家发送我进来了
        playerFight.sendSelfDataToSceneOtherClient(scene);

        //获取场景实体的数据
        getSceneAllOrganismOutSelf(client, playerId);
        playerFight.getPlayerActor().tell(new PlayerEntrySuccessOpt(playerId, this.scene.getSceneTemplateId(),
                self(), getPlayerSceneMgr().getPlayerFightMap().values().stream().filter(playerFight1 -> playerFight1.getId() != playerId)
                .collect(ArrayList::new, (list, playerFight1) -> list.add(playerFight1.getId()), ArrayList::addAll)), self());
    }

    private void sceneMove(SceneMove sceneMove) {
        Client.ReceivedFromClient r = sceneMove.r();
        protocol.Scene.CS10302 cs10302 = sceneMove.cs10302();
        protocol.Scene.MoveInfo moveInfo = cs10302.getMoveInfo();
        long organismId = cs10302.getOrganismId();
        FightOrganism organism = getFightOrganism(organismId);
        if (Objects.nonNull(organism) && !organism.getFightStateMgr().isMoveUse()) {
            return;
        }
        setPoint(organism, new Vector3(moveInfo.getPositionX(), moveInfo.getPositionY(), moveInfo.getFace()));
        //TODO 需要根据时间加速度，判断是否移动位置距离上一次同步合理
        getPlayerSceneMgr().sendToOtherClient(this.scene, r.protoID(), SceneProtocolBuilder.getSc10302(organismId, cs10302), organismId);
    }

    private void getSceneAllOrganism(ActorRef client) {
        scene.getPlayerSceneMgr().getPlayerFightMap().values().forEach(playerFight -> playerFight.sendSelfData(client));
        scene.getNpcSceneMgr().getNpcMap().values().forEach(npcOrganism -> npcOrganism.sendSelfData(client));
        scene.getMonsterSceneMgr().getMonsterMap().values().forEach(monsterOrganism -> monsterOrganism.sendSelfData(client));
        scene.getItemSceneMgr().getItemMap().values().forEach(itemOrganism -> itemOrganism.sendSelfData(client));
        scene.getEffectSceneMgr().getEffectMap().values().forEach(effectOrganism -> effectOrganism.sendSelfData(client));
    }

    private void getSceneAllOrganismOutSelf(ActorRef client, long organismId) {
        scene.getPlayerSceneMgr().getPlayerFightMap().values().forEach(playerFight -> {
            long id = playerFight.getId();
            if (id != organismId) {
                playerFight.sendSelfData(client);
            }
        });
        scene.getNpcSceneMgr().getNpcMap().values().forEach(npcOrganism -> npcOrganism.sendSelfData(client));
        scene.getMonsterSceneMgr().getMonsterMap().values().forEach(monsterOrganism -> monsterOrganism.sendSelfData(client));
        scene.getItemSceneMgr().getItemMap().values().forEach(itemOrganism -> itemOrganism.sendSelfData(client));
        scene.getEffectSceneMgr().getEffectMap().values().forEach(effectOrganism -> effectOrganism.sendSelfData(client));
    }

    public Vector3 getPlayerBirth() {
        return this.scene.getPlayerBirth();
    }

    public PlayerInfo getPlayerInfo(long id) {
        return getPlayerSceneMgr().getPlayerFight(id).getPlayerInfo();
    }

    public void sceneTick(SceneTick sceneTick) {
        getPlayerSceneMgr().selectClient(this.scene);
        if (Objects.nonNull(this.scene.getTrigger())) {
            this.scene.getTrigger().tick();
        }
        scene.tick(sceneTick);
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
        } else if (this.scene.getEffectSceneMgr().containsEffectOrganism(organismId)) {
            this.scene.getEffectSceneMgr().removeEffectOrganism(organismId);
        }
    }

    public ActorRef getSceneActor(int sceneId) {
        return this.scene.getSceneId2SceneMap().get(sceneId);
    }

    public boolean containsSceneActor(int sceneId) {
        return this.scene.getSceneId2SceneMap().containsKey(sceneId);
    }

}

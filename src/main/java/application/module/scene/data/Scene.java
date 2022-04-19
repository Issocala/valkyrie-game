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
import application.module.organism.MonsterOrganism;
import application.module.organism.OrganismFaceType;
import application.module.organism.OrganismType;
import application.module.player.data.message.event.PlayerLogin;
import application.module.player.data.message.event.PlayerLogout;
import application.module.scene.SceneProtocolBuilder;
import application.module.scene.SceneProtocols;
import application.module.scene.data.domain.PositionInfo;
import application.module.scene.operate.*;
import application.module.scene.operate.event.CreateOrganismEntities;
import application.module.scene.trigger.SceneRefreshTrigger;
import application.util.ApplicationErrorCode;
import application.util.CommonOperateTypeInfo;
import com.cala.orm.message.Publish;
import com.google.protobuf.GeneratedMessageV3;
import mobius.modular.client.Client;
import protocol.Skill;
import template.FightSkillTemplate;
import template.FightSkillTemplateHolder;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-3-3
 * @Source 1.0
 */
public class Scene extends UntypedAbstractActor {

    private final int sceneTemplateId;

    private final long sceneId;

    private ActorRef sceneData;

    /**
     * key : 当前场景的玩家id
     * value : Client ActorRef
     */
    private final Map<Long, ActorRef> clientMap = new HashMap<>();

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

    private final Map<Long, MonsterOrganism> monsterOrganismMap = new HashMap<>();

    private final List<SceneRefreshTrigger> sceneRefreshTriggerList = new ArrayList<>();

    public Scene(int sceneTemplateId) {
        this.sceneId = IdUtils.fastSimpleUUIDLong();
        this.sceneTemplateId = sceneTemplateId;
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
            case AoiSendMessageToClient aoi -> sendToOtherClientIncludeSelf(aoi.protoId(), aoi.message(), aoi.organismId());
            case ScenePlayerEntry scenePlayerEntry -> scenePlayerEntry(scenePlayerEntry);
            case ScenePlayerExit scenePlayerExit -> scenePlayerExit(scenePlayerExit);
            case SceneMonsterEntry sceneMonsterEntry -> sceneMonsterEntry(sceneMonsterEntry);
            case SceneMonsterExit sceneMonsterExit -> sceneMonsterExit(sceneMonsterExit);
            case PlayerLogin playerLogin -> playerLogin(playerLogin);
            case SceneInit sceneInit -> sceneInit(sceneInit);
            case PlayerLogout playerLogout -> playerLogout(playerLogout);
            default -> throw new IllegalStateException("Unexpected value: " + message);
        }
    }

    private void playerLogout(PlayerLogout playerLogout) {
        scenePlayerExit(playerLogout.r());
    }

    private void sceneMonsterEntry(SceneMonsterEntry sceneMonsterEntry) {

    }

    private void sceneMonsterExit(SceneMonsterExit sceneMonsterExit) {

    }

    private void sceneInit(SceneInit sceneInit) {
        this.sceneData = sender();
        if (this.sceneTemplateId == 2004) {
            this.getContext().system().scheduler().scheduleOnce(Duration.ofSeconds(1), () -> {

            }, this.getContext().dispatcher());
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
        scenePlayerEntry(playerLogin.r(), playerLogin.playerInfo().id());
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
        clientMap.remove(r.uID(), r.client());
        //TODO 退出场景，玩家信息需要清空
        sender().tell(new ScenePlayerExitReturn(playerId, getSceneTemplateId(), positionInfo), self());
    }

    private void scenePlayerEntry(ScenePlayerEntry scenePlayerEntry) {
        scenePlayerEntry(scenePlayerEntry.r(), scenePlayerEntry.r().uID());
    }

    private void scenePlayerEntry(Client.ReceivedFromClient r, long playerId) {
        clientMap.put(playerId, r.client());
        if (!positionInfoMap.containsKey(playerId)) {
            positionInfoMap.put(playerId, new PositionInfo(DEFAULT_X, DEFAULT_Y, DEFAULT_FACE));
        }
        r.client().tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_ENTER,
                SceneProtocolBuilder.getSc10030(sceneTemplateId)), self());
        //给场景的其他玩家发送我进来了
        PositionInfo positionInfo = positionInfoMap.get(playerId);
        List<Long> organismIds = List.of(playerId);
        clientMap.forEach((id, client) -> {
            if (id != playerId) {
                client.tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_RETURN_ENTITY,
                        SceneProtocolBuilder.getSc10034(playerId, OrganismType.PLAYER, positionInfo)), self());
            }
            this.sceneData.tell(new Publish(new CreateOrganismEntities(client, organismIds, OrganismType.PLAYER)), self());
        });

        //获取场景实体的数据
        getSceneAllOrganism(r);
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
        //TODO 获取目标
        if (SkillAimType.isOne(fightSkillTemplate)) {
        }
        useSkillDataTemp.getTargetId().forEach(id -> useSkillDataTemp.getTargetParameters().add(new TargetParameter(id)));
        return useSkillDataTemp;
    }

    private void sendToOtherClientIncludeSelf(int protoId, GeneratedMessageV3 message, long organismId) {
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

    private void getSceneAllOrganism(Client.ReceivedFromClient r) {
        positionInfoMap.forEach((id, positionInfo) -> r.client().tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_RETURN_ENTITY,
                SceneProtocolBuilder.getSc10034(id, OrganismType.PLAYER, positionInfo)), self()));
        this.sceneData.tell(new Publish(new CreateOrganismEntities(r.client(), positionInfoMap.keySet().stream().toList(), (byte) 0)), self());
    }

    public int getSceneTemplateId() {
        return sceneTemplateId;
    }

    public long getSceneId() {
        return sceneId;
    }
}

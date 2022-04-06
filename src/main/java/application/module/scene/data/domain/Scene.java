package application.module.scene.data.domain;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import application.client.Client.SendToClientJ;
import application.module.common.CommonProtocolBuilder;
import application.module.common.CommonProtocols;
import application.module.fight.base.context.UseSkillDataTemp;
import application.module.fight.skill.data.message.SkillUseScene;
import application.module.fight.skill.data.message.SkillUseState;
import application.module.fight.skill.operate.info.SkillUseInfo;
import application.module.fight.skill.util.SkillAimType;
import application.module.organism.OrganismFaceType;
import application.module.organism.OrganismType;
import application.module.player.data.message.PlayerLogin;
import application.module.scene.SceneProtocolBuilder;
import application.module.scene.SceneProtocols;
import application.module.scene.operate.*;
import application.util.ApplicationErrorCode;
import application.util.CommonOperateTypeInfo;
import mobius.modular.client.Client;
import protocol.Skill;
import template.FightSkillTemplate;
import template.FightSkillTemplateHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-3-3
 * @Source 1.0
 */
public class Scene extends UntypedAbstractActor {

    private final long sceneId;

    /**
     * key : 当前场景的玩家id
     * value : Client ActorRef
     */
    private final Map<Long, ActorRef> clientMap = new HashMap<>();

    public final static float DEFAULT_X = 0;
    public final static float DEFAULT_Y = 5;
    public final static int DEFAULT_FACE = OrganismFaceType.RIGHT;

    /**
     * key: FightOrganismId
     * value: FightOrganism场景坐标等数据
     */
    private final Map<Long, PositionInfo> positionInfoMap = new HashMap<>();

    public Scene(long sceneId) {
        this.sceneId = sceneId;
    }

    public static Props create(long sceneId) {
        return Props.create(Scene.class, sceneId);
    }

    @Override
    public void onReceive(Object message) {
        switch (message) {
            case SceneMove sceneMove -> sceneMove(sceneMove);
            case SceneStop sceneStop -> sceneStop(sceneStop);
            case SceneJump sceneJump -> sceneJump(sceneJump);
            case SkillUseState skillUseScene -> skillUseScene(skillUseScene);
            case ScenePlayerEntry scenePlayerEntry -> scenePlayerEntry(scenePlayerEntry);
            case ScenePlayerExit scenePlayerExit -> scenePlayerExit(scenePlayerExit);
            case PlayerLogin playerLogin -> playerLogin(playerLogin);
            default -> throw new IllegalStateException("Unexpected value: " + message);
        }
    }

    private void sceneJump(SceneJump sceneJump) {
        Client.ReceivedFromClient r = sceneJump.r();
        protocol.Scene.CS10035 cs10035 = sceneJump.cs10035();
        protocol.Scene.JumpInfo jumpInfo = cs10035.getJumpInfo();
        positionInfoMap.put(r.uID(), new PositionInfo(jumpInfo.getPositionX(), jumpInfo.getPositionY(), jumpInfo.getFace()));
        //TODO 需要根据时间加速度，判断是否移动位置距离上一次同步合理
        clientMap.forEach((id, client) -> {
            if (id != r.uID()) {
                client.tell(new application.client.Client.SendToClientJ(r.protoID(),
                        SceneProtocolBuilder.getSc10035(r.uID(), cs10035)), self());
            }
        });
    }

    private void playerLogin(PlayerLogin playerLogin) {
        scenePlayerEntry(playerLogin.r(), playerLogin.playerId());
    }

    private void sceneStop(SceneStop sceneStop) {
        Client.ReceivedFromClient r = sceneStop.r();
        protocol.Scene.CS10033 cs10033 = sceneStop.cs10033();
        protocol.Scene.StopInfo stopInfo = cs10033.getStopInfo();
        positionInfoMap.put(r.uID(), new PositionInfo(stopInfo.getPositionX(), stopInfo.getPositionY(), stopInfo.getFace()));
        //TODO 需要根据时间加速度，判断是否移动位置距离上一次同步合理
        clientMap.forEach((id, client) -> {
            if (id != r.uID()) {
                client.tell(new application.client.Client.SendToClientJ(r.protoID(),
                        SceneProtocolBuilder.getSc10033(r.uID(), cs10033)), self());
            }
        });
    }

    private void scenePlayerExit(ScenePlayerExit scenePlayerExit) {
        Client.ReceivedFromClient r = scenePlayerExit.r();
        long playerId = r.uID();
        clientMap.forEach((id, client) -> {
            if (id != playerId) {
                client.tell(new application.client.Client.SendToClientJ(r.protoID(),
                        SceneProtocolBuilder.getSc10031(playerId)), self());
            }
        });
        positionInfoMap.remove(playerId);
        clientMap.remove(r.uID(), r.client());
        //TODO 退出场景，玩家信息需要清空
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
                SceneProtocolBuilder.getSc10030(sceneId)), self());
        //给场景的其他玩家发送我进来了
        clientMap.forEach((id, client) -> {
            if (playerId != id) {
                PositionInfo positionInfo = positionInfoMap.get(playerId);
                client.tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_RETURN_ENTITY,
                        SceneProtocolBuilder.getSc10034(playerId, OrganismType.PLAYER, positionInfo)), self());
            }
        });
        //获取场景实体的数据
        positionInfoMap.forEach((id, positionInfo) -> r.client().tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_RETURN_ENTITY,
                SceneProtocolBuilder.getSc10034(id, OrganismType.PLAYER, positionInfo)), self()));
    }

    private void sceneMove(SceneMove sceneMove) {
        Client.ReceivedFromClient r = sceneMove.r();
        protocol.Scene.CS10032 cs10032 = sceneMove.cs10032();
        protocol.Scene.MoveInfo moveInfo = cs10032.getMoveInfo();
        positionInfoMap.put(r.uID(), new PositionInfo(moveInfo.getPositionX(), moveInfo.getPositionY(), moveInfo.getFace()));
        //TODO 需要根据时间加速度，判断是否移动位置距离上一次同步合理
        clientMap.forEach((id, client) -> {
            if (id != r.uID()) {
                client.tell(new application.client.Client.SendToClientJ(r.protoID(),
                        SceneProtocolBuilder.getSc10032(r.uID(), cs10032)), self());
            }
        });
    }


    private void skillUseScene(SkillUseState skillUseScene) {
        CommonOperateTypeInfo commonOperateTypeInfo = (CommonOperateTypeInfo) skillUseScene.operateTypeInfo();
        Skill.CS10052 cs10052 = (Skill.CS10052) commonOperateTypeInfo.message();
        long organismId = cs10052.getFightOrganismId();
        //TODO 判断当前场景是否可以释放技能，或者当前玩家位置是否可以放技能。
        if (true) {
            sender().tell(new SkillUseScene(new SkillUseInfo(commonOperateTypeInfo.r(), getTarget(commonOperateTypeInfo))), self());
        }else {
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
        return useSkillDataTemp;
    }

    public long getSceneId() {
        return sceneId;
    }
}

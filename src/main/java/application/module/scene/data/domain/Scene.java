package application.module.scene.data.domain;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import application.module.fight.attribute.data.message.AttributeUpdateFightAttribute;
import application.module.fight.base.context.UseSkillDataTemp;
import application.module.fight.skill.data.message.SkillUse;
import application.module.fight.skill.util.SkillAimType;
import application.module.organism.OrganismFaceType;
import application.module.organism.OrganismType;
import application.module.player.data.message.PlayerLogin;
import application.module.scene.SceneProtocolBuilder;
import application.module.scene.SceneProtocols;
import application.module.scene.operate.SceneMove;
import application.module.scene.operate.ScenePlayerEntry;
import application.module.scene.operate.ScenePlayerExit;
import application.module.scene.operate.SceneStop;
import application.module.scene.operate.info.SkillUseInfo;
import application.util.CommonOperateTypeInfo;
import mobius.modular.client.Client;
import protocol.Skill;
import template.FightSkillTemplate;
import template.FightSkillTemplateHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Luo Yong
 * @date 2022-3-3
 * @Source 1.0
 */
public class Scene extends UntypedAbstractActor {

    private final long sceneId;

    /**
     * key: FightOrganismId
     */
    private final Map<Long, ActorRef> fightSkillRuntimeContextMap = new HashMap<>();

    /**
     * key : 当前场景的玩家id
     * value : Client ActorRef
     */
    private final Map<Long, ActorRef> clientMap = new HashMap<>();

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
            case SkillUse skillUse -> skillUse(skillUse);
            case ScenePlayerEntry scenePlayerEntry -> scenePlayerEntry(scenePlayerEntry);
            case ScenePlayerExit scenePlayerExit -> scenePlayerExit(scenePlayerExit);
            case AttributeUpdateFightAttribute attributeUpdateFightAttribute -> attributeUpdateFightAttribute(attributeUpdateFightAttribute);
            case PlayerLogin playerLogin -> playerLogin(playerLogin);
            default -> throw new IllegalStateException("Unexpected value: " + message);
        }
    }

    private void playerLogin(PlayerLogin playerLogin) {
        long playerId = playerLogin.playerId();
        ActorRef clientRef = playerLogin.r().client();
        clientMap.put(playerId, clientRef);
        if (!positionInfoMap.containsKey(playerId)) {
            positionInfoMap.put(playerId, new PositionInfo(5.0f, 0, OrganismFaceType.right));
        }
        //给场景的其他玩家发送我进来了
        clientMap.forEach((id, client) -> {
            if (playerId != id) {
                PositionInfo positionInfo = positionInfoMap.get(playerId);
                client.tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_RETURN_ENTITY,
                        SceneProtocolBuilder.getSc10034(playerId, OrganismType.PLAYER, positionInfo)), self());
            }
        });

        //获取场景实体的数据
        positionInfoMap.forEach((id, positionInfo) ->
                clientRef.tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_RETURN_ENTITY,
                        SceneProtocolBuilder.getSc10034(playerId, OrganismType.PLAYER, positionInfo)), self()));
    }

    private void sceneStop(SceneStop sceneStop) {
        Client.ReceivedFromClient r = sceneStop.r();
        protocol.Scene.CS10033 cs10033 = sceneStop.cs10033();
        protocol.Scene.StopInfo stopInfo = cs10033.getStopInfo();
        positionInfoMap.put(r.uID(), new PositionInfo(stopInfo.getPositionX(), stopInfo.getPositionY(), stopInfo.getFace()));
        //TODO 需要根据时间加速度，判断是否移动位置距离上一次同步合理
        clientMap.values().forEach(client -> client.tell(new application.client.Client.SendToClientJ(r.protoID(),
                SceneProtocolBuilder.getSc10033(r.uID(), cs10033)), self()));
    }

    private void scenePlayerExit(ScenePlayerExit scenePlayerExit) {
        Client.ReceivedFromClient r = scenePlayerExit.r();
        long playerId = r.uID();
        clientMap.values().forEach(client -> client.tell(new application.client.Client.SendToClientJ(r.protoID(),
                SceneProtocolBuilder.getSc10031(playerId)), self()));
        clientMap.remove(r.uID(), r.client());
        //TODO 退出场景，玩家位置是否需要清空
    }

    private void scenePlayerEntry(ScenePlayerEntry scenePlayerEntry) {
        Client.ReceivedFromClient r = scenePlayerEntry.r();
        long playerId = r.uID();
        r.client().tell(new application.client.Client.SendToClientJ(r.protoID(),
                SceneProtocolBuilder.getSc10030(sceneId)), self());
        clientMap.put(playerId, r.client());
        if (!positionInfoMap.containsKey(playerId)) {
            positionInfoMap.put(playerId, new PositionInfo(5.0f, 0, OrganismFaceType.right));
        }
        //TODO 将自己的进入场景的初始数据发送给其他玩家
        clientMap.forEach((id, client) -> {
            if (playerId != id) {
                PositionInfo positionInfo = positionInfoMap.get(playerId);
                client.tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_RETURN_ENTITY,
                        SceneProtocolBuilder.getSc10034(playerId, OrganismType.PLAYER, positionInfo)), self());
            }
        });

        //获取场景实体的数据
        positionInfoMap.forEach((id, positionInfo) -> r.client().tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_RETURN_ENTITY,
                SceneProtocolBuilder.getSc10034(playerId, OrganismType.PLAYER, positionInfo)), self()));

    }

    private void sceneMove(SceneMove sceneMove) {
        Client.ReceivedFromClient r = sceneMove.r();
        protocol.Scene.CS10032 cs10032 = sceneMove.cs10032();
        protocol.Scene.MoveInfo moveInfo = cs10032.getMoveInfo();
        positionInfoMap.put(r.uID(), new PositionInfo(moveInfo.getPositionX(), moveInfo.getPositionY(), moveInfo.getFace()));
        //TODO 需要根据时间加速度，判断是否移动位置距离上一次同步合理
        clientMap.values().forEach(client -> client.tell(new application.client.Client.SendToClientJ(r.protoID(),
                SceneProtocolBuilder.getSc10032(r.uID(), cs10032)), self()));
    }

    private void attributeUpdateFightAttribute(AttributeUpdateFightAttribute attributeUpdateFightAttribute) {
        long playerId = attributeUpdateFightAttribute.playerId();
        if (fightSkillRuntimeContextMap.containsKey(playerId)) {
            ActorRef fightSkillRuntimeContext = fightSkillRuntimeContextMap.get(playerId);
            fightSkillRuntimeContext.tell(attributeUpdateFightAttribute, self());
        }
    }

    private void skillUse(SkillUse skillUse) {
        CommonOperateTypeInfo commonOperateTypeInfo = (CommonOperateTypeInfo) skillUse.operateTypeInfo();
        Skill.CS10052 cs10052 = (Skill.CS10052) commonOperateTypeInfo.message();
        ActorRef actorRef = this.fightSkillRuntimeContextMap.get(cs10052.getFightOrganismId());
        if (Objects.isNull(actorRef)) {
            return;
        }
        actorRef.tell(new SkillUse(new SkillUseInfo(self(), getTarget(cs10052), commonOperateTypeInfo.r())), self());
    }

    private UseSkillDataTemp getTarget(Skill.CS10052 cs10052) {
        FightSkillTemplate fightSkillTemplate = FightSkillTemplateHolder.getData(cs10052.getSkillId());
        UseSkillDataTemp useSkillDataTemp = UseSkillDataTemp.of(cs10052);
        useSkillDataTemp.setScene(self());
        //TODO 获取目标
        if (SkillAimType.isOne(fightSkillTemplate)) {
        }
        return useSkillDataTemp;
    }

    public long getSceneId() {
        return sceneId;
    }
}

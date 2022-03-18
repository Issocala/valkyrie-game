package application.module.scene.data.domain;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import application.module.fight.attribute.data.message.AttributeUpdateFightAttribute;
import application.module.fight.base.context.UseSkillDataTemp;
import application.module.fight.skill.data.message.SkillUse;
import application.module.fight.skill.util.SkillAimType;
import application.module.scene.SceneProtocolBuilder;
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
            default -> throw new IllegalStateException("Unexpected value: " + message);
        }
    }

    private void sceneStop(SceneStop sceneStop) {
        Client.ReceivedFromClient r = sceneStop.r();
        //TODO 需要根据时间加速度，判断是否移动位置距离上一次同步合理
        clientMap.values().forEach(client -> client.tell(new application.client.Client.SendToClientJ(r.protoID(),
                SceneProtocolBuilder.getSc10033(r.uID(), sceneStop.cs10033())), self()));
    }

    private void scenePlayerExit(ScenePlayerExit scenePlayerExit) {
        Client.ReceivedFromClient r = scenePlayerExit.r();
        long playerId = r.uID();
        clientMap.values().forEach(client -> {
            client.tell(new application.client.Client.SendToClientJ(r.protoID(), SceneProtocolBuilder.getSc10031(playerId)), self());
        });
        clientMap.remove(r.uID(), r.client());
    }

    private void scenePlayerEntry(ScenePlayerEntry scenePlayerEntry) {
        Client.ReceivedFromClient r = scenePlayerEntry.r();
        clientMap.put(r.uID(), r.client());

        //TODO 从配置表里面获取当前场景初始坐标

        PositionInfo positionInfo = new PositionInfo(5.0f, 0, 0);
        positionInfoMap.put(r.uID(), positionInfo);
        clientMap.values().forEach(client -> client.tell(new application.client.Client.SendToClientJ(r.protoID(), SceneProtocolBuilder.getSc10030(getSceneId(), r.uID(), positionInfo)), self()));

        //TODO 获取视野内其他玩家的初始数据发送给自己

        //TODO 将自己的进入场景需要的初始数据发送给其他玩家

    }

    private void sceneMove(SceneMove sceneMove) {
        Client.ReceivedFromClient r = sceneMove.r();
        //TODO 需要根据时间加速度，判断是否移动位置距离上一次同步合理
        clientMap.values().forEach(client -> client.tell(new application.client.Client.SendToClientJ(r.protoID(),
                SceneProtocolBuilder.getSc10032(r.uID(), sceneMove.cs10032())), self()));
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

package application.module.fight.skill;

import akka.actor.ActorRef;
import application.module.common.data.domain.DataMessage;
import application.module.fight.skill.data.SkillData;
import application.module.fight.skill.data.message.SkillIsLearn;
import application.module.fight.skill.data.message.SkillUse;
import application.module.fight.skill.operate.SkillIsLearnType;
import application.module.fight.skill.util.SkillType;
import application.module.scene.data.SceneData;
import application.module.state.data.StateData;
import application.util.CommonOperateTypeInfo;
import application.util.DataMessageAndReply;
import com.cala.orm.message.DataReturnMessage;
import com.cala.orm.util.RuntimeResult;
import com.google.protobuf.InvalidProtocolBufferException;
import mobius.modular.client.Client;
import mobius.modular.module.api.AbstractModule;
import protocol.Skill;
import template.FightSkillTemplate;
import template.FightSkillTemplateHolder;

import java.util.Objects;

/**
 * @author Luo Yong
 * @date 2022-2-18
 * @Source 1.0
 */
public class FightSkillModule extends AbstractModule {

    private ActorRef skillData;
    private ActorRef sceneData;
    private ActorRef stateData;

    @Override
    public void initData() {
        this.dataAgent().tell(new DataMessage.RequestData(SkillData.class), self());
        this.dataAgent().tell(new DataMessage.RequestData(StateData.class), self());
        this.dataAgent().tell(new DataMessage.RequestData(SceneData.class), self());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Client.ReceivedFromClient.class, this::receivedFromClient)
                .match(DataMessage.DataResult.class, this::dataResult)
                .match(DataReturnMessage.class, this::dataResultMessage)
                .build();
    }

    private void dataResultMessage(DataReturnMessage dataReturnMessage) {
        RuntimeResult result = dataReturnMessage.result();
        if (result.isOK()) {
            switch (dataReturnMessage.operateType()) {
                case SkillIsLearnType skillIsLearnType -> stateData.tell(new SkillUse(skillIsLearnType.operateTypeInfo()), self());
                case SkillUse skillUse -> sceneData.tell(skillUse, self());
                default -> throw new IllegalStateException("Unexpected value: " + dataReturnMessage.operateType());
            }
        }
    }

    private void dataResult(DataMessage.DataResult dataResult) {
        Class<?> clazz = dataResult.clazz();
        if (clazz == SkillData.class) {
            skillData = dataResult.actorRef();
        } else if (clazz == SceneData.class) {
            sceneData = dataResult.actorRef();
        } else if (clazz == StateData.class) {
            stateData = dataResult.actorRef();
        }
    }

    private void receivedFromClient(Client.ReceivedFromClient r) {
        int pId = r.protoID();
        switch (pId) {
            case FightSkillProtocols.USE_SKILL -> useSkill(r);
        }
    }

    private void useSkill(Client.ReceivedFromClient r) {
        try {
            var skill = Skill.CS10052.parseFrom(r.message());
            FightSkillTemplate fightSkillTemplate = FightSkillTemplateHolder.getData(skill.getSkillId());
            if (Objects.nonNull(fightSkillTemplate) && SkillType.isActive(fightSkillTemplate)) {
                skillData.tell(new SkillIsLearn(new DataMessageAndReply(self(),
                        new SkillIsLearnType(new CommonOperateTypeInfo(r, skill)))), self());
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}

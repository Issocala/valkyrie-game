package application.module.fight.skill;

import akka.actor.ActorRef;
import application.module.common.data.domain.DataMessage;
import application.module.fight.attribute.AttributeTemplateId;
import application.module.fight.attribute.data.AttributeData;
import application.module.fight.attribute.data.message.AddMp;
import application.module.fight.attribute.data.message.UpdateFightAttribute;
import application.module.fight.attribute.fight.FightAttributeMgr;
import application.module.fight.buff.data.FightBuffData;
import application.module.fight.skill.base.context.FightRuntimeContext;
import application.module.fight.skill.base.context.PassiveSkillDataTemp;
import application.module.fight.skill.base.context.UseSkillDataTemp;
import application.module.fight.skill.base.function.FightSkillFunctionContainer;
import application.module.fight.skill.base.function.active.FightSkillActiveFunction;
import application.module.fight.skill.base.function.passive.FightPassiveSkillFunction;
import application.module.fight.skill.base.skill.FightPassiveSkillWrap;
import application.module.fight.skill.base.skill.FightSkillWrap;
import application.module.fight.skill.base.skill.FightSkillWrapContainer;
import application.module.fight.skill.data.SkillData;
import application.module.fight.skill.data.message.*;
import application.module.fight.skill.operate.SkillIsLearnType;
import application.module.fight.skill.operate.info.SkillUseInfo;
import application.module.fight.skill.util.PassiveTriggerType;
import application.module.fight.skill.util.SkillType;
import application.module.player.data.PlayerEntityData;
import application.module.player.data.message.event.PlayerLogin;
import application.module.scene.data.SceneData;
import application.module.state.data.StateData;
import application.util.CommonOperateTypeInfo;
import application.util.DataMessageAndReply;
import application.util.RandomUtil;
import com.cala.orm.message.DataReturnMessage;
import com.cala.orm.message.OperateType;
import com.cala.orm.message.SubscribeEvent;
import com.cala.orm.util.RuntimeResult;
import com.google.protobuf.InvalidProtocolBufferException;
import mobius.modular.client.Client;
import mobius.modular.module.api.AbstractModule;
import protocol.Skill;
import template.*;

import java.util.HashMap;
import java.util.Map;
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
    private ActorRef attributeData;
    private ActorRef playerEntityData;
    private ActorRef buffData;

    /**
     * key: FightOrganismId
     */
    private final Map<Long, FightRuntimeContext> fightRuntimeContextMap = new HashMap<>();

    @Override
    public void initData() {
        FightSkillFunctionContainer.registerPassive(getContext());
        this.dataAgent().tell(new DataMessage.RequestData(SkillData.class), self());
        this.dataAgent().tell(new DataMessage.RequestData(StateData.class), self());
        this.dataAgent().tell(new DataMessage.RequestData(SceneData.class), self());
        this.dataAgent().tell(new DataMessage.RequestData(AttributeData.class), self());
        this.dataAgent().tell(new DataMessage.RequestData(PlayerEntityData.class), self());
        this.dataAgent().tell(new DataMessage.RequestData(FightBuffData.class), self());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Client.ReceivedFromClient.class, this::receivedFromClient)
                .match(DataReturnMessage.class, this::dataResultMessage)
                .match(OperateType.class, this::operateType)
                .match(DataMessage.DataResult.class, this::dataResult)
                .match(PlayerLogin.class, this::playerLogin)
                .build();
    }

    private void playerLogin(PlayerLogin playerLogin) {
        this.skillData.tell(playerLogin, self());
    }

    private void operateType(OperateType operateType) {
        switch (operateType) {
            case SkillUseState skillUseState -> sceneData.tell(new SkillUseScene(skillUseState.operateTypeInfo(), false), self());
            case SkillUseScene skillUseScene -> skillUseScene(skillUseScene);
            case SkillGetValidAttribute skillGetValidAttribute -> skillGetValidAttribute(skillGetValidAttribute);
            case SkillGetAllAttribute skillGetAllAttribute -> skillGetAllAttribute(skillGetAllAttribute);
            default -> throw new IllegalStateException("Unexpected value: " + operateType);
        }
    }

    private void skillGetAllAttribute(SkillGetAllAttribute skillGetAllAttribute) {
        UseSkillDataTemp useSkillDataTemp = skillGetAllAttribute.useSkillDataTemp();
        long fightOrganismId = useSkillDataTemp.getAttackId();
        FightRuntimeContext fightRuntimeContext = getFightRuntimeContext(fightOrganismId);
        if (skillGetAllAttribute.isSkillProcess()) {
            activeUseSkill(FightSkillProcessTemplateHolder.getData(useSkillDataTemp.getSkillId()), useSkillDataTemp, fightRuntimeContext);
            return;
        }
        FightSkillWrap fightSkillWrap = FightSkillWrapContainer.getFightSkillWrap(useSkillDataTemp.getSkillId());
        FightSkillTemplate fightSkillTemplate = fightSkillWrap.getFightSkillTemplate();
        if (FightAttributeMgr.getValue(useSkillDataTemp.getAttackAttributeMap(), AttributeTemplateId.VAR_HP) <= fightSkillTemplate.costHp()
                || FightAttributeMgr.getValue(useSkillDataTemp.getAttackAttributeMap(), AttributeTemplateId.VAR_MP) < fightSkillTemplate.costMp()) {
            //TODO 考虑是否需要返回客户端错误信息
//            return;
        }
        fightRuntimeContext.startCD(fightSkillTemplate);

        activeUseSkill(fightSkillWrap, useSkillDataTemp, fightRuntimeContext);
    }

    private void skillGetValidAttribute(SkillGetValidAttribute skillGetValidAttribute) {

    }

    public void activeUseSkill(FightSkillProcessTemplate fightSkillProcessTemplate, UseSkillDataTemp useSkillDataTemp, FightRuntimeContext fightRuntimeContext) {
        useSkillDataTemp.setSkillModule(self());
        useSkillDataTemp.setBuffData(this.buffData);
        FightSkillActiveFunction fightSkillActiveFunction = FightSkillFunctionContainer.getFunction(fightSkillProcessTemplate.function());
        if (Objects.nonNull(fightSkillActiveFunction)) {
            fightSkillActiveFunction.castSkill(null, fightSkillProcessTemplate, useSkillDataTemp);
        }
    }

    /**
     * 主动技能执行
     */
    public void activeUseSkill(FightSkillWrap fightSkillWrap, UseSkillDataTemp useSkillDataTemp, FightRuntimeContext fightRuntimeContext) {
        FightSkillTemplate fightSkillTemplate = fightSkillWrap.getFightSkillTemplate();
        if (fightSkillTemplate.skillType() != SkillType.ACTIVE) {
            return;
        }
        useSkillDataTemp.setSkillModule(self());
        useSkillDataTemp.setBuffData(this.buffData);

        useSkillPre(useSkillDataTemp, fightRuntimeContext);

        fightSkillWrap.getList().forEach(fightSkillProcessTemplate -> {
            if (fightSkillProcessTemplate.delayTime() == 0) {
                if (useSkillDataTemp.getTargetParameters().size() == 0) {
                    // TODO: 2022-4-27 不支持空放这里 
                }
                FightSkillActiveFunction fightSkillActiveFunction = FightSkillFunctionContainer.getFunction(fightSkillProcessTemplate.function());
                if (Objects.nonNull(fightSkillActiveFunction)) {
                    fightSkillActiveFunction.castSkill(fightSkillWrap, fightSkillProcessTemplate, useSkillDataTemp);
                }
            }
        });

        useSkillAfter();
        if (useSkillDataTemp.getChangeAttributeMap().size() > 0) {
            attributeData.tell(new UpdateFightAttribute(useSkillDataTemp.getChangeAttributeMap(),
                    useSkillDataTemp.getAttackId(), useSkillDataTemp.getScene(), useSkillDataTemp.getStateData()), self());
        }
        useSkillDataTemp.getTargetParameters().forEach(targetParameter -> {
            if (targetParameter.getChangeAttributeMap().size() > 0) {
                attributeData.tell(new UpdateFightAttribute(targetParameter.getChangeAttributeMap(),
                        targetParameter.getTargetId(), useSkillDataTemp.getScene(), useSkillDataTemp.getStateData()), self());
            }
        });
        if (fightSkillTemplate.costMp() != 0) {
            attributeData.tell(new AddMp(useSkillDataTemp.getAttackId(), useSkillDataTemp.getAttackType(),
                    useSkillDataTemp.getR(), -fightSkillTemplate.costMp(), useSkillDataTemp.getScene()), self());
        }
    }

    private void useSkillPre(UseSkillDataTemp useSkillDataTemp, FightRuntimeContext fightRuntimeContext) {
        OrganismDataTemplate organismDataTemplate = OrganismDataTemplateHolder.getData(useSkillDataTemp.getProfession());
        if (Objects.isNull(organismDataTemplate)) {
            return;
        }
        for (int skillId : organismDataTemplate.skills()) {
            FightPassiveSkillWrap fightPassiveSkillWrap = FightSkillWrapContainer.getFightPassiveSkillWrap(skillId);
            if (Objects.isNull(fightPassiveSkillWrap)) {
                continue;
            }
            FightPassiveSkillTemplate template = fightPassiveSkillWrap.getFightPassiveSkillTemplate();
            if (fightRuntimeContext.inCDTime(template)) {
                continue;
            }
            if (RandomUtil.randomInt10000() >= template.weight()) {
//                continue;
            }
            if (template.skillTriggerType() == PassiveTriggerType.USE_SKILL) {
                fightPassiveSkillWrap.getList().forEach(processTemplate -> {
                    FightPassiveSkillFunction function = FightSkillFunctionContainer.getPassiveFunction(processTemplate.function());
                    if (Objects.nonNull(function) && useSkillDataTemp.getTargetParameters().size() > 0) {
                        function.castSkill(fightPassiveSkillWrap, processTemplate, new PassiveSkillDataTemp(useSkillDataTemp.getAttackId(), useSkillDataTemp));
                    }
                });
            } else if (template.skillTriggerType() == PassiveTriggerType.USE_SKILL_TARGET) {
                fightPassiveSkillWrap.getList().forEach(processTemplate -> {
                    FightPassiveSkillFunction function = FightSkillFunctionContainer.getPassiveFunction(processTemplate.function());
                    if (Objects.nonNull(function) && useSkillDataTemp.getTargetParameters().size() > 0) {
                        function.castSkill(fightPassiveSkillWrap, processTemplate, new PassiveSkillDataTemp(useSkillDataTemp));
                    }
                });
            }
            fightRuntimeContext.startCD(template);
        }
    }

    private void useSkillAfter() {


    }

    private void dataResultMessage(DataReturnMessage dataReturnMessage) {
        RuntimeResult result = dataReturnMessage.result();
        if (result.isOK()) {
            switch (dataReturnMessage.operateType()) {
                case SkillIsLearnType skillIsLearnType -> stateData.tell(new SkillUseState(skillIsLearnType.operateTypeInfo()), self());
                default -> throw new IllegalStateException("Unexpected value: " + dataReturnMessage.operateType());
            }
        }
    }

    private void skillUseScene(SkillUseScene skillUseScene) {
        SkillUseInfo skillUseInfo = (SkillUseInfo) skillUseScene.operateTypeInfo();
        UseSkillDataTemp useSkillDataTemp = skillUseInfo.useSkillDataTemp();
        useSkillDataTemp.setAttributeData(attributeData);
        useSkillDataTemp.setStateData(stateData);
        long fightOrganismId = useSkillDataTemp.getAttackId();
        FightRuntimeContext fightRuntimeContext = getFightRuntimeContext(fightOrganismId);
        if (Objects.isNull(fightRuntimeContext)) {
            fightRuntimeContext = new FightRuntimeContext();
            addFightRuntimeContext(fightOrganismId, fightRuntimeContext);
        }
        if (!skillUseScene.isSkillProcess()) {
            FightSkillWrap fightSkillWrap = FightSkillWrapContainer.getFightSkillWrap(useSkillDataTemp.getSkillId());
            if (fightRuntimeContext.inCDTime(fightSkillWrap)) {
                return;
            }
            useSkillDataTemp.getAttributeData().tell(new SkillGetAllAttribute(useSkillDataTemp, false), self());
        } else {
            useSkillDataTemp.getAttributeData().tell(new SkillGetAllAttribute(useSkillDataTemp, true), self());
        }
    }

    private void dataResult(DataMessage.DataResult dataResult) {
        Class<?> clazz = dataResult.clazz();
        if (clazz == SkillData.class) {
            this.skillData = dataResult.actorRef();
        } else if (clazz == SceneData.class) {
            this.sceneData = dataResult.actorRef();
        } else if (clazz == StateData.class) {
            this.stateData = dataResult.actorRef();
        } else if (clazz == AttributeData.class) {
            this.attributeData = dataResult.actorRef();
        } else if (clazz == PlayerEntityData.class) {
            this.playerEntityData = dataResult.actorRef();
            this.playerEntityData.tell(new SubscribeEvent(PlayerLogin.class, self()), self());
        } else if (clazz == FightBuffData.class) {
            this.buffData = dataResult.actorRef();
        }
    }

    private void receivedFromClient(Client.ReceivedFromClient r) {
        int pId = r.protoID();
        switch (pId) {
            case FightSkillProtocols.USE -> useSkill(r);
            case FightSkillProtocols.LEARN -> learn(r);
            case FightSkillProtocols.USE_PROCESS -> useProcess(r);
        }
    }

    private void useProcess(Client.ReceivedFromClient r) {
        try {
            var skillProcess = Skill.CS10055.parseFrom(r.message());

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private void learn(Client.ReceivedFromClient r) {

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

    private FightRuntimeContext getFightRuntimeContext(long fightOrganismId) {
        return this.fightRuntimeContextMap.get(fightOrganismId);
    }

    private void addFightRuntimeContext(long fightOrganismId, FightRuntimeContext fightRuntimeContext) {
        this.fightRuntimeContextMap.put(fightOrganismId, fightRuntimeContext);
    }
}

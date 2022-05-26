package application.module.scene.fight.skill;

import akka.actor.ActorRef;
import application.condition.Condition;
import application.condition.ConditionContainer;
import application.condition.ConditionContext;
import application.module.common.CommonProtocolBuilder;
import application.module.common.CommonProtocols;
import application.module.organism.FightOrganism;
import application.module.player.fight.attribute.AttributeTemplateId;
import application.module.scene.Scene;
import application.module.scene.fight.attribute.FightAttributeMgr;
import application.module.scene.fight.skill.base.context.FightRuntimeContext;
import application.module.scene.fight.skill.base.context.PassiveSkillDataTemp;
import application.module.scene.fight.skill.base.context.TargetParameter;
import application.module.scene.fight.skill.base.context.UseSkillDataTemp;
import application.module.scene.fight.skill.base.function.FightSkillFunctionContainer;
import application.module.scene.fight.skill.base.function.active.FightSkillActiveFunction;
import application.module.scene.fight.skill.base.function.passive.FightPassiveSkillFunction;
import application.module.scene.fight.skill.base.skill.FightPassiveSkillWrap;
import application.module.scene.fight.skill.base.skill.FightSkillWrap;
import application.module.scene.fight.skill.base.skill.FightSkillWrapContainer;
import application.module.scene.fight.skill.util.PassiveTriggerType;
import application.module.scene.fight.skill.util.SkillType;
import application.util.ApplicationErrorCode;
import application.util.RandomUtil;
import application.util.StringUtils;
import protocol.Skill;
import template.FightPassiveSkillTemplate;
import template.FightSkillTemplate;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Luo Yong
 * @date 2022-5-16
 * @Source 1.0
 */
public class FightSkillMgr {

    private FightOrganism owner;

    private Set<Integer> enableSkillSet;

    private final FightRuntimeContext fightRuntimeContext = new FightRuntimeContext();

    public void activeUseSkill(Scene scene, Skill.CS10052 cs10052) {

        if (!owner.getFightStateMgr().isSkillUse()) {
            return;
        }

        UseSkillDataTemp useSkillDataTemp = UseSkillDataTemp.of(cs10052, scene);
        FightSkillWrap fightSkillWrap = FightSkillWrapContainer.getFightSkillWrap(useSkillDataTemp.getSkillId());
        if (Objects.isNull(fightSkillWrap)) {
            return;
        }

        if (fightRuntimeContext.inCDTime(fightSkillWrap)) {
            return;
        }
        FightSkillTemplate fightSkillTemplate = fightSkillWrap.getFightSkillTemplate();
        if (FightAttributeMgr.getValue(owner.getFightAttributeMgr().getFightMap(), AttributeTemplateId.VAR_MP) < fightSkillTemplate.costMp()
                || FightAttributeMgr.getValue(getAttackAttributeMap(), AttributeTemplateId.VAR_HP) <= fightSkillTemplate.costHp()) {
            useSkillDataTemp.getR().client().tell(new application.client.Client.SendToClientJ(CommonProtocols.APPLICATION_ERROR,
                    CommonProtocolBuilder.getSc10080(ApplicationErrorCode.USE_SKILL_HP_MP)), ActorRef.noSender());
            return;
        }

        fightRuntimeContext.startCD(fightSkillTemplate);
        activeUseSkill(fightSkillWrap, useSkillDataTemp);
    }

    private void activeUseSkill(FightSkillWrap fightSkillWrap, UseSkillDataTemp useSkillDataTemp) {
        FightSkillTemplate fightSkillTemplate = fightSkillWrap.getFightSkillTemplate();
        if (fightSkillTemplate.skillType() != SkillType.ACTIVE) {
            return;
        }

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

        useSkillAfter(useSkillDataTemp, fightRuntimeContext);

        owner.getFightAttributeMgr().addFightMap(useSkillDataTemp.getChangeAttributeMap());

        useSkillDataTemp.getTargetParameters().forEach(targetParameter ->
                targetParameter.getTargetOrganism().getFightAttributeMgr().addFightMap(targetParameter.getChangeAttributeMap()));
        owner.addMp(useSkillDataTemp.getAttack(), -fightSkillTemplate.costMp());
    }

    private void useSkillPre(UseSkillDataTemp useSkillDataTemp, FightRuntimeContext fightRuntimeContext) {
        doPassive(useSkillDataTemp, fightRuntimeContext, PassiveTriggerType.USE_SKILL);
    }

    private void useSkillAfter(UseSkillDataTemp useSkillDataTemp, FightRuntimeContext fightRuntimeContext) {
        doPassive(useSkillDataTemp, fightRuntimeContext, PassiveTriggerType.DAMAGE_AFTER);
    }

    public Map<Short, Long> getAttackAttributeMap() {
        return owner.getFightAttributeMgr().getFightMap();

    }

    private void doPassive(UseSkillDataTemp useSkillDataTemp, FightRuntimeContext fightRuntimeContext, short passiveTriggerType) {
        if (useSkillDataTemp.getTargetParameters().size() <= 0) {
            return;
        }
        FightOrganism fightOrganism = useSkillDataTemp.getAttack();
        doPassive1(useSkillDataTemp, fightOrganism, fightRuntimeContext, passiveTriggerType);
        short bePassiveTriggerType = passiveTriggerType == PassiveTriggerType.USE_SKILL ? PassiveTriggerType.BE_USE_SKILL :
                passiveTriggerType == PassiveTriggerType.DAMAGE_BEFORE ? PassiveTriggerType.BE_DAMAGE_BEFORE :
                        passiveTriggerType == PassiveTriggerType.DAMAGE_AFTER ? PassiveTriggerType.BE_DAMAGE_AFTER : passiveTriggerType;

        for (TargetParameter targetParameter : useSkillDataTemp.getTargetParameters()) {
            doPassive1(useSkillDataTemp, targetParameter.getTargetOrganism(), fightRuntimeContext, bePassiveTriggerType);
        }
    }

    private void doPassive1(UseSkillDataTemp useSkillDataTemp, FightOrganism fightOrganism, FightRuntimeContext fightRuntimeContext, short passiveTriggerType) {
        for (int skillId : fightOrganism.getFightSkillMgr().getEnableSkillSet()) {
            FightPassiveSkillWrap fightPassiveSkillWrap = FightSkillWrapContainer.getFightPassiveSkillWrap(skillId);
            if (Objects.isNull(fightPassiveSkillWrap)) {
                continue;
            }
            FightPassiveSkillTemplate template = fightPassiveSkillWrap.getFightPassiveSkillTemplate();
            if (passiveTriggerType == template.skillTriggerType()) {
                if (fightRuntimeContext.inCDTime(template)) {
                    continue;
                }
                if (RandomUtil.randomInt10000() >= template.weight()) {
                    continue;
                }
                if (!StringUtils.isEmpty(template.condition())) {
                    String[] ss = StringUtils.toStringArray(template.condition());
                    short id = Short.parseShort(ss[0]);
                    Condition condition = ConditionContainer.parseCondition(id, ss);
                    ConditionContext conditionContext = new ConditionContext();
                    conditionContext.put(UseSkillDataTemp.class.getSimpleName(), useSkillDataTemp);
                    if (!condition.doValid(conditionContext)) {
                        continue;
                    }
                }
                if (template.skillTriggerTraget() == 1) {
                    fightPassiveSkillWrap.getList().forEach(processTemplate -> {
                        FightPassiveSkillFunction function = FightSkillFunctionContainer.getPassiveFunction(processTemplate.function());
                        if (Objects.nonNull(function)) {
                            function.castSkill(fightPassiveSkillWrap, processTemplate, new PassiveSkillDataTemp(useSkillDataTemp.getAttack(), useSkillDataTemp));
                        }
                    });
                } else if (template.skillTriggerTraget() == 2) {
                    fightPassiveSkillWrap.getList().forEach(processTemplate -> {
                        FightPassiveSkillFunction function = FightSkillFunctionContainer.getPassiveFunction(processTemplate.function());
                        if (Objects.nonNull(function)) {
                            function.castSkill(fightPassiveSkillWrap, processTemplate, new PassiveSkillDataTemp(useSkillDataTemp));
                        }
                    });
                }
                fightRuntimeContext.startCD(template);
            }
        }
    }

    //get and set

    public Set<Integer> getEnableSkillSet() {
        return enableSkillSet;
    }

    public void setEnableSkillSet(Set<Integer> enableSkillSet) {
        this.enableSkillSet = enableSkillSet;
    }

    public FightOrganism getOwner() {
        return owner;
    }

    public void setOwner(FightOrganism owner) {
        this.owner = owner;
    }
}

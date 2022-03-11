package application.module.fight.base.skill.invoker;

import application.module.fight.base.context.UseSkillDataTemp;
import application.module.fight.base.function.FightSkillActiveFunction;
import application.module.fight.base.function.FightSkillFunctionContainer;
import application.module.fight.base.skill.FightSkillWrap;
import application.module.fight.skill.util.SkillType;

/**
 * @author Luo Yong
 * @date 2022-2-28
 * @Source 1.0
 */
public class FightSkillInvoker {

    public static final FightSkillInvoker INSTANCE = new FightSkillInvoker();

    /**
     * 主动技能执行
     */
    public void invokeActive(FightSkillWrap fightSkillWrap, UseSkillDataTemp useSkillDataTemp) {
        if (fightSkillWrap.getFightSkillTemplate().skillTargetType() != SkillType.ACTIVE) {
            return;
        }
        preCastSkill();

        fightSkillWrap.getList().forEach(fightSkillProcessTemplate -> {
            FightSkillActiveFunction fightSkillActiveFunction = (FightSkillActiveFunction) FightSkillFunctionContainer.getFunction(fightSkillProcessTemplate.function());

            fightSkillActiveFunction.castSkill(fightSkillWrap, fightSkillProcessTemplate, useSkillDataTemp);

        });

        afterCastSkill();
    }

    /**
     * 释放技能前
     */
    public void preCastSkill() {

    }

    /**
     * 释放技能后
     */
    public void afterCastSkill() {
    }
}

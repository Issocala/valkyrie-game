package application.condition.impl;

import application.condition.Condition;
import application.condition.ConditionContext;
import application.condition.ConditionType;
import application.condition.annotation.ConditionId;
import application.module.scene.fight.skill.base.context.UseSkillDataTemp;
import template.FightSkillTemplate;
import template.FightSkillTemplateHolder;

/**
 * @author Luo Yong
 * @date 2022-5-6
 * @Source 1.0
 */
@ConditionId(id = ConditionType.NORMAL_ATTACK)
public class NormalAttackCondition implements Condition {
    @Override
    public boolean doValid(ConditionContext conditionContext) {
        UseSkillDataTemp useSkillDataTemp = (UseSkillDataTemp) conditionContext.get(UseSkillDataTemp.class.getSimpleName());
        FightSkillTemplate template = FightSkillTemplateHolder.getData(useSkillDataTemp.getSkillId());

        return template.activeSkillType() == 1;
    }

    @Override
    public void parse(String[] ss) {

    }
}

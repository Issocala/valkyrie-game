package application.condition.impl;

import application.condition.Condition;
import application.condition.ConditionItem;
import application.condition.core.ConditionContext;
import application.condition.core.ConditionItemBase;
import application.module.scene.fight.skill.base.context.UseSkillDataTemp;
import application.util.ApplicationCode;
import application.util.ApplicationErrorCode;
import template.FightSkillTemplate;
import template.FightSkillTemplateHolder;

/**
 * @author Luo Yong
 * @date 2022-5-6
 * @Source 1.0
 */
public class NormalAttackCondition extends ConditionItemBase {

    @Override
    public int eligibleTo(ConditionContext conditionContext) {
        UseSkillDataTemp useSkillDataTemp = (UseSkillDataTemp) conditionContext.get(UseSkillDataTemp.class.getSimpleName());
        FightSkillTemplate template = FightSkillTemplateHolder.getData(useSkillDataTemp.getSkillId());

        return template.activeSkillType() == 1 ? ApplicationCode.CODE_SUCCESS : ApplicationErrorCode.CODE_ERROR;
    }

    @Override
    public void parser(String s) {

    }
}

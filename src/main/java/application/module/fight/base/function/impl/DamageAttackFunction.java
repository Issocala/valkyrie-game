package application.module.fight.base.function.impl;

import application.module.fight.base.context.UseSkillDataTemp;
import application.module.fight.base.function.FightSkillActiveFunction;
import application.module.fight.base.skill.FightSkillWrap;
import application.util.StringUtils;
import template.FightSkillProcessTemplate;

/**
 * @author Luo Yong
 * @date 2022-2-28
 * @Source 1.0
 */
public class DamageAttackFunction extends FightSkillActiveFunction {

    @Override
    public void castSkill(FightSkillWrap fightSkillWrap, FightSkillProcessTemplate fightSkillProcessTemplate, UseSkillDataTemp useSkillDataTemp) {
        int[] attributeParameter = StringUtils.toIntArray(fightSkillProcessTemplate.attributeParameter());
        //技能固定伤害
        int damage = attributeParameter[0];
        //技能伤害系数
        int damageRate = attributeParameter[1];
//        basicAttack(fightSkillProcessTemplate, useSkillDataTemp, damage, damageRate);
    }

    private void basicAttack(FightSkillProcessTemplate fightSkillProcessTemplate, UseSkillDataTemp useSkillDataTemp, final int damage, final int damageRate) {

    }

    @Override
    public void onReceive(Object message) throws Throwable, Throwable {

    }
}

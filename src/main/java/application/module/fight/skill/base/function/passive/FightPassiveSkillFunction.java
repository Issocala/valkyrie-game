package application.module.fight.skill.base.function.passive;

import application.module.fight.skill.base.context.PassiveSkillDataTemp;
import application.module.fight.skill.base.function.FightSkillFunction;
import application.module.fight.skill.base.skill.FightPassiveSkillWrap;
import application.module.fight.skill.base.skill.FightSkillWrap;
import template.FightPassiveSkillProcessTemplate;
import template.FightPassiveSkillTemplate;
import template.FightSkillProcessTemplate;

/**
 * @author Luo Yong
 * @date 2022-4-15
 * @Source 1.0
 */
public abstract class FightPassiveSkillFunction implements FightSkillFunction {

    public abstract void castSkill(FightPassiveSkillWrap fightPassiveSkillWrap, FightPassiveSkillProcessTemplate processTemplate, PassiveSkillDataTemp passiveSkillDataTemp);

    public void learnProcess(FightPassiveSkillWrap fightPassiveSkillWrap, FightPassiveSkillProcessTemplate processTemplate, PassiveSkillDataTemp passiveSkillDataTemp) {
    }

    public void removeProcess(FightPassiveSkillWrap fightPassiveSkillWrap, FightPassiveSkillProcessTemplate processTemplate, PassiveSkillDataTemp passiveSkillDataTemp) {
    }

}

package application.module.fight.skill.base.function.passive;

import application.module.fight.skill.base.context.PassiveSkillDataTemp;
import application.module.fight.skill.base.function.FightSkillFunction;
import application.module.fight.skill.base.skill.FightPassiveSkillWrap;
import template.FightPassiveSkillProcessTemplate;

/**
 * @author Luo Yong
 * @date 2022-4-15
 * @Source 1.0
 */
public interface FightPassiveSkillFunction extends FightSkillFunction {

    void castSkill(FightPassiveSkillWrap fightPassiveSkillWrap, FightPassiveSkillProcessTemplate processTemplate, PassiveSkillDataTemp passiveSkillDataTemp);

    default void learnProcess(FightPassiveSkillWrap fightPassiveSkillWrap, FightPassiveSkillProcessTemplate processTemplate, PassiveSkillDataTemp passiveSkillDataTemp) {
    }

    default void removeProcess(FightPassiveSkillWrap fightPassiveSkillWrap, FightPassiveSkillProcessTemplate processTemplate, PassiveSkillDataTemp passiveSkillDataTemp) {
    }

}

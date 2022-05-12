package application.module.fight.skill.base.function.active;

import application.module.fight.skill.base.context.UseSkillDataTemp;
import application.module.fight.skill.base.function.FightSkillFunction;
import application.module.fight.skill.base.skill.FightSkillWrap;
import template.FightSkillProcessTemplate;

/**
 * @author Luo Yong
 * @date 2022-2-28
 * @Source 1.0
 */
public interface FightSkillActiveFunction extends FightSkillFunction {


    void castSkill(FightSkillWrap fightSkillWrap, FightSkillProcessTemplate fightSkillProcessTemplate, UseSkillDataTemp useSkillDataTemp);

}

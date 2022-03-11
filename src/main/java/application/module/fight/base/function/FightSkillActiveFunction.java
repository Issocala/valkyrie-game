package application.module.fight.base.function;

import application.module.fight.base.context.UseSkillDataTemp;
import application.module.fight.base.skill.FightSkillWrap;
import template.FightSkillProcessTemplate;

/**
 * @author Luo Yong
 * @date 2022-2-28
 * @Source 1.0
 */
public abstract class FightSkillActiveFunction {

    public abstract void castSkill(FightSkillWrap fightSkillWrap, FightSkillProcessTemplate fightSkillProcessTemplate, UseSkillDataTemp useSkillDataTemp);

}

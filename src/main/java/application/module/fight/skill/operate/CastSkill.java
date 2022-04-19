package application.module.fight.skill.operate;

import application.module.fight.skill.base.context.UseSkillDataTemp;
import application.module.fight.skill.base.skill.FightSkillWrap;
import template.FightSkillProcessTemplate;

/**
 * @author Luo Yong
 * @date 2022-3-29
 * @Source 1.0
 */
public record CastSkill(FightSkillWrap fightSkillWrap, FightSkillProcessTemplate fightSkillProcessTemplate, UseSkillDataTemp useSkillDataTemp) {
}

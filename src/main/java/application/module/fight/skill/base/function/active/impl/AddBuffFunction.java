package application.module.fight.skill.base.function.active.impl;

import akka.actor.Props;
import application.module.fight.skill.base.context.UseSkillDataTemp;
import application.module.fight.skill.base.function.active.FightSkillActiveFunction;
import application.module.fight.skill.base.skill.FightSkillWrap;
import template.FightSkillProcessTemplate;

/**
 * @author Luo Yong
 * @date 2022-4-11
 * @Source 1.0
 */
public class AddBuffFunction extends FightSkillActiveFunction {

    public static Props create() {
        return Props.create(AddBuffFunction.class);
    }

    @Override
    public void castSkill(FightSkillWrap fightSkillWrap, FightSkillProcessTemplate fightSkillProcessTemplate, UseSkillDataTemp useSkillDataTemp) {
    }
}

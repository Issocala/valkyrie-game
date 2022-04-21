package application.module.fight.skill.base.function.active.impl;

import akka.actor.Props;
import application.module.fight.skill.base.context.UseSkillDataTemp;
import application.module.fight.skill.base.function.active.FightSkillActiveFunction;
import application.module.fight.skill.base.skill.FightSkillWrap;
import template.FightSkillProcessTemplate;

/**
 * @author Luo Yong
 * @date 2022-4-20
 * @Source 1.0
 */
public class CreateOrganismEntity extends FightSkillActiveFunction {

    public static Props create() {
        return Props.create(CreateOrganismEntity.class);
    }

    @Override
    public void castSkill(FightSkillWrap fightSkillWrap, FightSkillProcessTemplate fightSkillProcessTemplate, UseSkillDataTemp useSkillDataTemp) {

    }
}

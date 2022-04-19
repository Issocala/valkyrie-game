package application.module.fight.skill.base.function.active;

import akka.actor.UntypedAbstractActor;
import application.module.fight.skill.base.context.UseSkillDataTemp;
import application.module.fight.skill.base.function.FightSkillFunction;
import application.module.fight.skill.base.skill.FightSkillWrap;
import application.module.fight.skill.operate.CastSkill;
import template.FightSkillProcessTemplate;

/**
 * @author Luo Yong
 * @date 2022-2-28
 * @Source 1.0
 */
public abstract class FightSkillActiveFunction extends UntypedAbstractActor implements FightSkillFunction {

    @Override
    public void onReceive(Object message) {
        switch (message) {
            case CastSkill castSkill -> castSkill(castSkill.fightSkillWrap(), castSkill.fightSkillProcessTemplate(), castSkill.useSkillDataTemp());
            default -> receive(message);
        }
    }

    public abstract void castSkill(FightSkillWrap fightSkillWrap, FightSkillProcessTemplate fightSkillProcessTemplate, UseSkillDataTemp useSkillDataTemp);

    public void receive(Object message) {
    }

}

package application.module.fight.skill.base.function.active.impl;

import akka.actor.ActorRef;
import application.module.fight.buff.data.message.AddBuff;
import application.module.fight.skill.base.context.UseSkillDataTemp;
import application.module.fight.skill.base.function.active.FightSkillActiveFunction;
import application.module.fight.skill.base.skill.FightSkillWrap;
import application.util.RandomUtil;
import template.FightSkillProcessTemplate;

/**
 * @author Luo Yong
 * @date 2022-4-29
 * @Source 1.0
 */
public class IceMagic4Function implements FightSkillActiveFunction {
    @Override
    public void castSkill(FightSkillWrap fightSkillWrap, FightSkillProcessTemplate fightSkillProcessTemplate, UseSkillDataTemp useSkillDataTemp) {
        useSkillDataTemp.getTargetParameters().forEach(targetParameter -> {
            int num = RandomUtil.randomInt(3);
            for (int i = 0; i <= num; i++) {
                useSkillDataTemp.getBuffData().tell(new AddBuff(useSkillDataTemp.getR(), 10003, useSkillDataTemp.getAttackId(),
                        targetParameter.getTargetId(), useSkillDataTemp.getScene(), useSkillDataTemp.getAttributeData(), useSkillDataTemp.getStateData()), ActorRef.noSender());
            }
        });
    }
}

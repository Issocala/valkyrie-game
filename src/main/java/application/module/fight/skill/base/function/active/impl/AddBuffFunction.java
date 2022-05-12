package application.module.fight.skill.base.function.active.impl;

import akka.actor.ActorRef;
import akka.actor.Props;
import application.module.fight.buff.data.message.AddBuff;
import application.module.fight.skill.base.context.UseSkillDataTemp;
import application.module.fight.skill.base.function.active.FightSkillActiveFunction;
import application.module.fight.skill.base.skill.FightSkillWrap;
import application.util.StringUtils;
import template.FightSkillProcessTemplate;

/**
 * @author Luo Yong
 * @date 2022-4-11
 * @Source 1.0
 */
public class AddBuffFunction implements FightSkillActiveFunction {

    public static Props create() {
        return Props.create(AddBuffFunction.class);
    }

    @Override
    public void castSkill(FightSkillWrap fightSkillWrap, FightSkillProcessTemplate fightSkillProcessTemplate, UseSkillDataTemp useSkillDataTemp) {
        int[] attributeParameter = StringUtils.toIntArray(fightSkillProcessTemplate.attributeParameter());
        int buffTemplateId = attributeParameter[0];
        long duration = attributeParameter[1];
        useSkillDataTemp.getTargetParameters().forEach(targetParameter ->
                useSkillDataTemp.getBuffData().tell(new AddBuff(useSkillDataTemp.getR(), buffTemplateId, useSkillDataTemp.getAttackId(),
                        targetParameter.getTargetId(), duration, useSkillDataTemp.getScene(), useSkillDataTemp.getAttributeData(), useSkillDataTemp.getStateData(), null), ActorRef.noSender()));
    }
}

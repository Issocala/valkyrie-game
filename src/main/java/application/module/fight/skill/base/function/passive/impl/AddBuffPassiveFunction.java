package application.module.fight.skill.base.function.passive.impl;

import akka.actor.ActorRef;
import application.module.fight.buff.data.message.AddBuff;
import application.module.fight.skill.base.context.PassiveSkillDataTemp;
import application.module.fight.skill.base.function.passive.FightPassiveSkillFunction;
import application.module.fight.skill.base.skill.FightPassiveSkillWrap;
import application.util.StringUtils;
import template.FightPassiveSkillProcessTemplate;

/**
 * @author Luo Yong
 * @date 2022-4-15
 * @Source 1.0
 */
public class AddBuffPassiveFunction extends FightPassiveSkillFunction {

    @Override
    public void castSkill(FightPassiveSkillWrap fightPassiveSkillWrap, FightPassiveSkillProcessTemplate processTemplate, PassiveSkillDataTemp dataTemp) {
        int[] attributeParameter = StringUtils.toIntArray(processTemplate.attributeParameter());
        int buffTemplateId = attributeParameter[0];
        dataTemp.getTargetParameters().forEach(targetParameter ->
                dataTemp.getBuffData().tell(new AddBuff(dataTemp.getR(), buffTemplateId, dataTemp.getAttackId(), targetParameter.getTargetId(),
                        dataTemp.getScene(), dataTemp.getAttributeData(), dataTemp.getStateData()), ActorRef.noSender())
        );
    }
}

package application.module.scene.fight.skill.base.function.passive.impl;

import application.module.scene.fight.skill.base.context.PassiveSkillDataTemp;
import application.module.scene.fight.skill.base.function.passive.FightPassiveSkillFunction;
import application.module.scene.fight.skill.base.skill.FightPassiveSkillWrap;
import application.util.StringUtils;
import template.FightPassiveSkillProcessTemplate;

/**
 * @author Luo Yong
 * @date 2022-4-15
 * @Source 1.0
 */
public class AddBuffPassiveFunction implements FightPassiveSkillFunction {

    @Override
    public void castSkill(FightPassiveSkillWrap fightPassiveSkillWrap, FightPassiveSkillProcessTemplate processTemplate, PassiveSkillDataTemp dataTemp) {
        int[] attributeParameter = StringUtils.toIntArray(processTemplate.attributeParameter());
        int buffTemplateId = attributeParameter[0];
        dataTemp.getTargetParameters().forEach(targetParameter ->
                dataTemp.addBuff(buffTemplateId, dataTemp.getAttack(), targetParameter.getTargetOrganism())
        );
    }
}

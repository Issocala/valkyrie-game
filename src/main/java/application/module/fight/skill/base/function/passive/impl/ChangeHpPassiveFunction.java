package application.module.fight.skill.base.function.passive.impl;

import application.module.fight.attribute.AttributeTemplateId;
import application.module.fight.attribute.data.message.AddHp;
import application.module.fight.attribute.fight.FightAttributeMgr;
import application.module.fight.skill.base.context.PassiveSkillDataTemp;
import application.module.fight.skill.base.function.passive.FightPassiveSkillFunction;
import application.module.fight.skill.base.skill.FightPassiveSkillWrap;
import template.FightPassiveSkillProcessTemplate;

/**
 * @author Luo Yong
 * @date 2022-5-6
 * @Source 1.0
 */
public class ChangeHpPassiveFunction extends FightPassiveSkillFunction {
    @Override
    public void castSkill(FightPassiveSkillWrap fightPassiveSkillWrap, FightPassiveSkillProcessTemplate processTemplate, PassiveSkillDataTemp passiveSkillDataTemp) {
        long finalHp = Long.parseLong(processTemplate.attributeParameter());
        passiveSkillDataTemp.getTargetParameters().forEach(targetParameter -> {
            long hp = FightAttributeMgr.getValue(targetParameter.getAttributeMap(), AttributeTemplateId.VAR_HP);
            passiveSkillDataTemp.getAttributeData().tell(new AddHp(targetParameter.getTargetId(), targetParameter.getOrganismType(),
                    passiveSkillDataTemp.getR(), finalHp - hp, passiveSkillDataTemp.getAttackId(), passiveSkillDataTemp.getOrganismType(), passiveSkillDataTemp.getScene(), passiveSkillDataTemp.getStateData()), null);
        });
    }
}

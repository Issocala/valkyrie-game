package application.module.scene.fight.skill.base.function.passive.impl;

import application.module.player.fight.attribute.AttributeTemplateId;
import application.module.scene.fight.attribute.FightAttributeMgr;
import application.module.scene.fight.skill.base.context.PassiveSkillDataTemp;
import application.module.scene.fight.skill.base.function.passive.FightPassiveSkillFunction;
import application.module.scene.fight.skill.base.skill.FightPassiveSkillWrap;
import template.FightPassiveSkillProcessTemplate;

/**
 * @author Luo Yong
 * @date 2022-5-6
 * @Source 1.0
 */
public class ChangeHpPassiveFunction implements FightPassiveSkillFunction {
    @Override
    public void castSkill(FightPassiveSkillWrap fightPassiveSkillWrap, FightPassiveSkillProcessTemplate processTemplate, PassiveSkillDataTemp passiveSkillDataTemp) {
        long finalHp = Long.parseLong(processTemplate.attributeParameter());
        passiveSkillDataTemp.getTargetParameters().forEach(targetParameter -> {
            long hp = FightAttributeMgr.getValue(targetParameter.getFightMap(), AttributeTemplateId.VAR_HP);
            targetParameter.addHp(passiveSkillDataTemp.getAttack(), finalHp - hp);
        });
    }
}

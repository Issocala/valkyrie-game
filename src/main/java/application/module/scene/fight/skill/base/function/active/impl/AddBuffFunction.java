package application.module.scene.fight.skill.base.function.active.impl;

import akka.actor.Props;
import application.module.scene.fight.buff.FightBuffMgr;
import application.module.scene.fight.buff.FightOrganismBuff;
import application.module.scene.fight.skill.base.context.UseSkillDataTemp;
import application.module.scene.fight.skill.base.function.active.FightSkillActiveFunction;
import application.module.scene.fight.skill.base.skill.FightSkillWrap;
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
        useSkillDataTemp.getTargetParameters().forEach(targetParameter -> {
            targetParameter.addBuff(buffTemplateId, useSkillDataTemp.getAttack(), targetParameter.getTargetOrganism(), duration);
        });
    }
}

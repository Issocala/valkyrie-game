package application.module.scene.fight.skill.base.function.active.impl;

import application.module.scene.fight.buff.FightBuffMgr;
import application.module.scene.fight.buff.FightOrganismBuff;
import application.module.scene.fight.skill.base.context.UseSkillDataTemp;
import application.module.scene.fight.skill.base.function.active.FightSkillActiveFunction;
import application.module.scene.fight.skill.base.skill.FightSkillWrap;
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
                targetParameter.addBuff(10003, useSkillDataTemp.getAttack(), targetParameter.getTargetOrganism());
            }
        });
    }
}

package application.module.scene.fight.skill.base.function.active.impl;

import akka.actor.Props;
import application.module.player.fight.attribute.AttributeTemplateId;
import application.module.scene.fight.buff.FightBuffMgr;
import application.module.scene.fight.buff.FightOrganismBuff;
import application.module.scene.fight.skill.base.context.UseSkillDataTemp;
import application.module.scene.fight.skill.base.function.active.FightSkillActiveFunction;
import application.module.scene.fight.skill.base.skill.FightSkillWrap;
import template.FightSkillProcessTemplate;

import java.util.Map;


/**
 * @author Luo Yong
 * @date 2022-2-28
 * @Source 1.0
 */
public class MadWarrior3Function implements FightSkillActiveFunction {

    public static Props create() {
        return Props.create(MadWarrior3Function.class);
    }

    @Override
    public void castSkill(FightSkillWrap fightSkillWrap, FightSkillProcessTemplate fightSkillProcessTemplate, UseSkillDataTemp useSkillDataTemp) {
        Map<Short, Long> fightMap = useSkillDataTemp.getFightMap();
        long douQi = fightMap.getOrDefault(AttributeTemplateId.DOU_QI, 0L);
        if (douQi > 0) {
            useSkillDataTemp.addBuff(10002, useSkillDataTemp.getAttack(), useSkillDataTemp.getAttack());
            // TODO: 2022-4-29 击退效果
        }
    }
}

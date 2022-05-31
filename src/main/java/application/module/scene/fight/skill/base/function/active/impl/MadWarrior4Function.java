package application.module.scene.fight.skill.base.function.active.impl;

import akka.actor.Props;
import application.module.player.fight.attribute.AttributeTemplateId;
import application.module.scene.fight.buff.FightOrganismBuff;
import application.module.scene.fight.skill.base.context.UseSkillDataTemp;
import application.module.scene.fight.skill.base.function.active.FightSkillActiveFunction;
import application.module.scene.fight.skill.base.skill.FightSkillWrap;
import application.util.StringUtils;
import template.FightSkillProcessTemplate;

import java.util.HashMap;
import java.util.Map;

import static application.module.scene.fight.buff.FightBuffMgr.createBuff;


/**
 * @author Luo Yong
 * @date 2022-2-28
 * @Source 1.0
 */
public class MadWarrior4Function implements FightSkillActiveFunction {

    public static Props create() {
        return Props.create(MadWarrior4Function.class);
    }

    @Override
    public void castSkill(FightSkillWrap fightSkillWrap, FightSkillProcessTemplate fightSkillProcessTemplate, UseSkillDataTemp useSkillDataTemp) {
        int[] attributeParameter = StringUtils.toIntArray(fightSkillProcessTemplate.attributeParameter());
        int buffId = attributeParameter[0];
        long duration = attributeParameter[1];


        Map<Short, Long> fightMap = useSkillDataTemp.getFightMap();
        final long douQi = fightMap.getOrDefault(AttributeTemplateId.DOU_QI, 0L);
        if (douQi > 0) {
            Map<Short, Long> extFightMap = new HashMap<>();
            extFightMap.put(AttributeTemplateId.CRIT_RATIO, 200L * douQi + 1);
            extFightMap.put(AttributeTemplateId.CRIT_ADD_DAMAGE_RATIO, 500L * douQi + 1);
            for (int i = 0; i < douQi; i++) {
                useSkillDataTemp.removeBuff(10002, useSkillDataTemp.getAttack());
            }
            FightOrganismBuff buff = createBuff(buffId, useSkillDataTemp.getAttack(), useSkillDataTemp.getTarget(), duration);
            buff.setAttributeMap(extFightMap);
            useSkillDataTemp.getAttack().getFightBuffMgr().addBuff(buff);
        }
    }
}

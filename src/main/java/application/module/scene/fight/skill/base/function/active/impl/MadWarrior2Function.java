package application.module.scene.fight.skill.base.function.active.impl;

import akka.actor.Props;
import application.module.player.fight.attribute.AttributeTemplateId;
import application.module.scene.fight.buff.FightBuffMgr;
import application.module.scene.fight.buff.FightOrganismBuff;
import application.module.scene.fight.skill.base.context.UseSkillDataTemp;
import application.module.scene.fight.skill.base.function.active.FightSkillActiveFunction;
import application.module.scene.fight.skill.base.skill.FightSkillWrap;
import application.util.StringUtils;
import template.FightSkillProcessTemplate;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Luo Yong
 * @date 2022-2-28
 * @Source 1.0
 */
public class MadWarrior2Function implements FightSkillActiveFunction {

    public static Props create() {
        return Props.create(MadWarrior2Function.class);
    }

    @Override
    public void castSkill(FightSkillWrap fightSkillWrap, FightSkillProcessTemplate fightSkillProcessTemplate, UseSkillDataTemp useSkillDataTemp) {
        int[] attributeParameter = StringUtils.toIntArray(fightSkillProcessTemplate.attributeParameter());
        int buffId = attributeParameter[0];
        long duration = attributeParameter[1];
        short attributeTemplateId = (short) attributeParameter[2];
        long value = attributeParameter[3];
        Map<Short, Long> map = new HashMap<>();
        map.put(attributeTemplateId, value);
        Map<Short, Long> fightMap = useSkillDataTemp.getFightMap();
        long douQi = fightMap.getOrDefault(AttributeTemplateId.DOU_QI, 0L);
        if (douQi > 0) {
            useSkillDataTemp.removeBuff(10002, useSkillDataTemp.getAttack());
            FightOrganismBuff buff = FightBuffMgr.createBuff(buffId, useSkillDataTemp.getAttack(), useSkillDataTemp.getAttack(), duration);
            buff.setAttributeMap(map);
            useSkillDataTemp.getAttack().getFightBuffMgr().addBuff(buff);
        }
    }
}

package application.module.scene.fight.skill.base.function.active.impl;

import akka.actor.Props;
import application.module.player.fight.attribute.AttributeTemplateId;
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
            extFightMap.put(AttributeTemplateId.CRIT_RATIO, 200L * douQi);
            extFightMap.put(AttributeTemplateId.CRIT_ADD_DAMAGE_RATIO, 500L * douQi);
            for (int i = 0; i < douQi; i++) {
                useSkillDataTemp.removeBuff(10002);
            }
            useSkillDataTemp.getTargetParameters().forEach(targetParameter -> {
                useSkillDataTemp.addBuff(10002, useSkillDataTemp.getAttack(),
                        targetParameter.getTargetOrganism(), duration + douQi * 1000);
            });
        }
    }
}

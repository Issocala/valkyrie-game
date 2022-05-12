package application.module.fight.skill.base.function.active.impl;

import akka.actor.ActorRef;
import akka.actor.Props;
import application.module.fight.attribute.AttributeTemplateId;
import application.module.fight.buff.data.message.RemoveBuff;
import application.module.fight.skill.base.context.UseSkillDataTemp;
import application.module.fight.skill.base.function.active.FightSkillActiveFunction;
import application.module.fight.skill.base.skill.FightSkillWrap;
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
//        int[] attributeParameter = StringUtils.toIntArray(fightSkillProcessTemplate.attributeParameter());
//        int num = attributeParameter[0];
        Map<Short, Long> fightMap = useSkillDataTemp.getAttackAttributeMap();
        long douQi = fightMap.getOrDefault(AttributeTemplateId.DOU_QI, 0L);
        if (douQi > 0) {
            useSkillDataTemp.getBuffData().tell(new RemoveBuff(useSkillDataTemp.getR(), 10002,
                    useSkillDataTemp.getAttackId(), useSkillDataTemp.getAttackId()), ActorRef.noSender());
            // TODO: 2022-4-29 击退效果
        }
    }
}

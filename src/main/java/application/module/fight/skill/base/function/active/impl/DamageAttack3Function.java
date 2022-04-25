package application.module.fight.skill.base.function.active.impl;

import akka.actor.Props;
import application.module.fight.attribute.AttributeTemplateId;
import application.module.fight.skill.FightSkillProtocolBuilder;
import application.module.fight.skill.FightSkillProtocols;
import application.module.fight.skill.base.context.UseSkillDataTemp;
import application.module.fight.skill.base.function.active.FightSkillActiveFunction;
import application.module.fight.skill.base.skill.FightSkillWrap;
import application.module.scene.operate.AoiSendMessageToClient;
import application.util.StringUtils;
import protocol.Skill;
import template.FightSkillProcessTemplate;

import java.util.List;

import static application.module.fight.attribute.fight.FightAttributeMgr.basicAttack;


/**
 * @author Luo Yong
 * @date 2022-2-28
 * @Source 1.0
 */
public class DamageAttack3Function extends FightSkillActiveFunction {

    public static Props create() {
        return Props.create(DamageAttack3Function.class);
    }

    @Override
    public void castSkill(FightSkillWrap fightSkillWrap, FightSkillProcessTemplate fightSkillProcessTemplate, UseSkillDataTemp useSkillDataTemp) {
        int[] attributeParameter = StringUtils.toIntArray(fightSkillProcessTemplate.attributeParameter());
        //技能固定伤害
        int damage = attributeParameter[0];
        //技能伤害系数
        int damageRate = attributeParameter[1];

        int whileTime = attributeParameter[2];
        long douQi = useSkillDataTemp.getAttackAttributeMap().getOrDefault(AttributeTemplateId.DOU_QI, 0L);
        if (douQi > 1) {
            if (douQi == 5) {

            }
            useSkillDataTemp.getAttackAttributeMap().put(AttributeTemplateId.DOU_QI, douQi - 1);
            whileTime++;
        }

        damageRate = damageRate == 0 ? 1 : damageRate;
        for (int i = 0; i < whileTime; i++) {
            List<Skill.DamageData> damageDataList = basicAttack(useSkillDataTemp, 0, 1);
            useSkillDataTemp.getScene().tell(new AoiSendMessageToClient(FightSkillProtocols.USE_RESULT,
                    FightSkillProtocolBuilder.getSc10053(useSkillDataTemp.getAttackId(), fightSkillProcessTemplate.id(),
                            useSkillDataTemp.getSkillId(), damageDataList), useSkillDataTemp.getAttackId()), self());
        }
    }
}

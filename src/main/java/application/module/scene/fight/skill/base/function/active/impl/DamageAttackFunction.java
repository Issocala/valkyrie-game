package application.module.scene.fight.skill.base.function.active.impl;

import akka.actor.Props;
import application.module.player.fight.skill.FightSkillProtocols;
import application.module.scene.fight.skill.FightSkillProtocolBuilder;
import application.module.scene.fight.skill.base.context.UseSkillDataTemp;
import application.module.scene.fight.skill.base.function.active.FightSkillActiveFunction;
import application.module.scene.fight.skill.base.skill.FightSkillWrap;
import application.util.StringUtils;
import protocol.Skill;
import template.FightSkillProcessTemplate;

import java.util.List;

import static application.module.scene.fight.attribute.FightAttributeMgr.basicAttack;


/**
 * @author Luo Yong
 * @date 2022-2-28
 * @Source 1.0
 */
public class DamageAttackFunction implements FightSkillActiveFunction {

    public static Props create() {
        return Props.create(DamageAttackFunction.class);
    }

    @Override
    public void castSkill(FightSkillWrap fightSkillWrap, FightSkillProcessTemplate fightSkillProcessTemplate, UseSkillDataTemp useSkillDataTemp) {
        int damage;
        double damageRate;
        if (StringUtils.isEmpty(fightSkillProcessTemplate.attributeParameter())) {
            damage = 0;
            damageRate = 1;
        } else {
            String[] attributeParameter = StringUtils.toStringArray(fightSkillProcessTemplate.attributeParameter());
            //技能固定伤害
            damage = Integer.parseInt(attributeParameter[0]);
            //技能伤害系数
            damageRate = Double.parseDouble(attributeParameter[1]);
        }
        int whileTime = fightSkillProcessTemplate.triggerNum();

        if (useSkillDataTemp.getTargetParameters().size() == 0) {
            return;
        }
        for (int i = 0; i < whileTime; i++) {
            List<Skill.DamageData> damageDataList = basicAttack(useSkillDataTemp, damage, damageRate);
            useSkillDataTemp.sendToAllClient(FightSkillProtocols.USE_RESULT, FightSkillProtocolBuilder.getSc10053(
                    useSkillDataTemp.getAttackId(), fightSkillProcessTemplate.id(), useSkillDataTemp.getSkillId(),
                    damageDataList, useSkillDataTemp.getSkillOrganismId()));
        }
    }
}

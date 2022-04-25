package application.module.fight.skill.base.function.active.impl;

import akka.actor.Props;
import application.module.fight.buff.data.message.AddBuff;
import application.module.fight.skill.FightSkillProtocolBuilder;
import application.module.fight.skill.FightSkillProtocols;
import application.module.fight.skill.base.context.UseSkillDataTemp;
import application.module.fight.skill.base.function.active.FightSkillActiveFunction;
import application.module.fight.skill.base.skill.FightSkillWrap;
import application.module.scene.operate.AoiSendMessageToClient;
import application.util.RandomUtil;
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
public class DamageAttack2Function extends FightSkillActiveFunction {

    public static Props create() {
        return Props.create(DamageAttack2Function.class);
    }

    @Override
    public void castSkill(FightSkillWrap fightSkillWrap, FightSkillProcessTemplate fightSkillProcessTemplate, UseSkillDataTemp useSkillDataTemp) {
        int[] attributeParameter = StringUtils.toIntArray(fightSkillProcessTemplate.attributeParameter());
        //技能固定伤害
        int damage = attributeParameter[0];
        //技能伤害系数
        int damageRate = attributeParameter[1];

        int whileTime = attributeParameter[2];

        damageRate = damageRate == 0 ? 1 : damageRate;
        if (useSkillDataTemp.getSkillId() == 1) {
            if (RandomUtil.randomInt(10000) < 1000) {
                useSkillDataTemp.getBuffData().tell(new AddBuff(useSkillDataTemp.getR(), 1000, useSkillDataTemp.getAttackId(), useSkillDataTemp.getAttackId(),
                        3000, useSkillDataTemp.getScene(), useSkillDataTemp.getAttributeData(), useSkillDataTemp.getStateData()), self());
            }
        }
        for (int i = 0; i < whileTime; i++) {
            List<Skill.DamageData> damageDataList = basicAttack(useSkillDataTemp, 0, 1);
            useSkillDataTemp.getScene().tell(new AoiSendMessageToClient(FightSkillProtocols.USE_RESULT,
                    FightSkillProtocolBuilder.getSc10053(useSkillDataTemp.getAttackId(), fightSkillProcessTemplate.id(),
                            useSkillDataTemp.getSkillId(), damageDataList), useSkillDataTemp.getAttackId()), self());
        }
    }
}

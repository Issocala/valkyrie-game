package application.module.fight.skill.base.function.active.impl;

import akka.actor.ActorRef;
import akka.actor.Props;
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
public class DamageAttackFunction extends FightSkillActiveFunction {

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
        }else {
            String[] attributeParameter = StringUtils.toStringArray(fightSkillProcessTemplate.attributeParameter());
            //技能固定伤害
            damage = Integer.parseInt(attributeParameter[0]);
            //技能伤害系数
            damageRate = Double.parseDouble(attributeParameter[1]);
        }
        int whileTime = fightSkillProcessTemplate.triggerNum();

        for (int i = 0; i < whileTime; i++) {
            List<Skill.DamageData> damageDataList = basicAttack(useSkillDataTemp, damage, damageRate);
            useSkillDataTemp.getScene().tell(new AoiSendMessageToClient(FightSkillProtocols.USE_RESULT,
                    FightSkillProtocolBuilder.getSc10053(useSkillDataTemp.getAttackId(), fightSkillProcessTemplate.id(),
                            useSkillDataTemp.getSkillId(), damageDataList), useSkillDataTemp.getAttackId()), ActorRef.noSender());
        }
    }
}

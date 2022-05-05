package application.module.fight.skill.base.function.active.impl;

import akka.actor.ActorRef;
import application.module.fight.attribute.data.message.AddHp;
import application.module.fight.attribute.fight.FightAttributeMgr;
import application.module.fight.skill.FightSkillProtocolBuilder;
import application.module.fight.skill.FightSkillProtocols;
import application.module.fight.skill.base.context.UseSkillDataTemp;
import application.module.fight.skill.base.function.active.FightSkillActiveFunction;
import application.module.fight.skill.base.skill.FightSkillWrap;
import application.module.scene.operate.AoiSendMessageToClient;
import application.util.MathConstant;
import application.util.StringUtils;
import protocol.Skill;
import template.FightSkillProcessTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static application.module.fight.attribute.AttributeTemplateId.*;
import static application.module.fight.attribute.AttributeTemplateId.BOSS_ADD_DAMAGE;

/**
 * @author Luo Yong
 * @date 2022-5-2
 * @Source 1.0
 */
public class AttributeRatioDamageFunction extends FightSkillActiveFunction {
    @Override
    public void castSkill(FightSkillWrap fightSkillWrap, FightSkillProcessTemplate fightSkillProcessTemplate, UseSkillDataTemp useSkillDataTemp) {
        String[] parameter = StringUtils.toStringArray(fightSkillProcessTemplate.attributeParameter());
        short attributeTemplateId = Short.parseShort(parameter[0]);
        double ratio = Double.parseDouble(parameter[1]);
        List<Skill.DamageData> damageDataList = new ArrayList<>();

        useSkillDataTemp.getTargetParameters().forEach(targetParameter -> {
            Map<Short, Long> targetAttributeMap = targetParameter.getAttributeMap();
            Skill.DamageData.Builder builder = Skill.DamageData.newBuilder();
            long finalDamage = (long) (FightAttributeMgr.getValue(targetAttributeMap, attributeTemplateId) * ratio);

            // TODO: 2022-4-29 这里后续放到被动伤害结算前,做成被动通用逻辑
            if (targetAttributeMap.containsKey(BOSS_ADD_DAMAGE)) {
                finalDamage = targetAttributeMap.get(BOSS_ADD_DAMAGE) * finalDamage / MathConstant.TEN_THOUSAND;
            }
            if (targetAttributeMap.containsKey(ICE_MAGIC_SHIELD)) {
                long curMp = targetAttributeMap.get(VAR_MP);
                if (curMp > 500) {
                    long reduceDamage = (long) (finalDamage * 0.35);
                    long finalReduceDamage = curMp - 500 - reduceDamage;
                    if (finalReduceDamage < 0) {
                        reduceDamage += finalReduceDamage;
                    }
                    targetAttributeMap.put(VAR_MP, curMp - reduceDamage);
                    targetParameter.getChangeAttributeMap().put(VAR_MP, -reduceDamage);
                    builder.setReduceMP(reduceDamage);
                    finalDamage -= reduceDamage;
                }
            }
            Skill.DamageData damageData = builder.setDamage(-finalDamage).setDamageType(0).setTargetId(targetParameter.getTargetId()).build();
            damageDataList.add(damageData);
            useSkillDataTemp.getAttributeData().tell(new AddHp(targetParameter.getTargetId(), targetParameter.getOrganismType(),
                    useSkillDataTemp.getR(), -finalDamage, useSkillDataTemp.getAttackId(), useSkillDataTemp.getAttackType(), useSkillDataTemp.getScene(), useSkillDataTemp.getStateData()), null);
        });

        useSkillDataTemp.getScene().tell(new AoiSendMessageToClient(FightSkillProtocols.USE_RESULT,
                FightSkillProtocolBuilder.getSc10053(useSkillDataTemp.getAttackId(), fightSkillProcessTemplate.id(),
                        useSkillDataTemp.getSkillId(), damageDataList), useSkillDataTemp.getAttackId()), ActorRef.noSender());
    }
}

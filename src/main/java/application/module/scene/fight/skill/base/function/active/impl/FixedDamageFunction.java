package application.module.scene.fight.skill.base.function.active.impl;

import application.module.player.fight.skill.FightSkillProtocols;
import application.module.scene.fight.skill.FightSkillProtocolBuilder;
import application.module.scene.fight.skill.base.context.UseSkillDataTemp;
import application.module.scene.fight.skill.base.function.active.FightSkillActiveFunction;
import application.module.scene.fight.skill.base.skill.FightSkillWrap;
import application.util.MathConstant;
import application.util.StringUtils;
import protocol.Skill;
import template.FightSkillProcessTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static application.module.player.fight.attribute.AttributeTemplateId.*;

/**
 * @author Luo Yong
 * @date 2022-4-29
 * @Source 1.0
 */
public class FixedDamageFunction implements FightSkillActiveFunction {
    @Override
    public void castSkill(FightSkillWrap fightSkillWrap, FightSkillProcessTemplate fightSkillProcessTemplate, UseSkillDataTemp useSkillDataTemp) {
        int[] parameter = StringUtils.toIntArray(fightSkillProcessTemplate.attributeParameter());
        int fixedDamage = parameter[0];

        List<Skill.DamageData> damageDataList = new ArrayList<>();

        useSkillDataTemp.getTargetParameters().forEach(targetParameter -> {
            Map<Short, Long> targetAttributeMap = targetParameter.getFightMap();
            Skill.DamageData.Builder builder = Skill.DamageData.newBuilder();
            long finalDamage = fixedDamage;

            // TODO: 2022-4-29 这里后续放到被动伤害结算前,做成被动通用逻辑

            if (targetAttributeMap.containsKey(BOSS_ADD_DAMAGE)) {
                finalDamage += targetAttributeMap.get(BOSS_ADD_DAMAGE) * finalDamage / MathConstant.TEN_THOUSAND;
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

            Skill.DamageData damageData = builder.setDamage(-finalDamage).setDamageType(0)
                    .setTargetId(targetParameter.getId()).build();
            damageDataList.add(damageData);
            targetParameter.addHp(useSkillDataTemp.getAttack(), -finalDamage);
        });

        useSkillDataTemp.sendToAllClient(FightSkillProtocols.USE_RESULT, FightSkillProtocolBuilder.getSc10053(
                useSkillDataTemp.getAttackId(), fightSkillProcessTemplate.id(), useSkillDataTemp.getSkillId(), damageDataList));
    }
}

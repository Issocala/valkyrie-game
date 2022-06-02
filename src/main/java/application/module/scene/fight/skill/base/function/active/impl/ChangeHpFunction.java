package application.module.scene.fight.skill.base.function.active.impl;

import application.module.player.fight.skill.FightSkillProtocols;
import application.module.scene.fight.skill.FightSkillProtocolBuilder;
import application.module.scene.fight.skill.base.context.UseSkillDataTemp;
import application.module.scene.fight.skill.base.function.active.FightSkillActiveFunction;
import application.module.scene.fight.skill.base.skill.FightSkillWrap;
import application.util.StringUtils;
import protocol.Skill;
import template.FightSkillProcessTemplate;

import java.util.ArrayList;
import java.util.List;

import static application.module.player.fight.attribute.AttributeTemplateId.VAR_HP;

/**
 * @author Luo Yong
 * @date 2022-5-6
 * @Source 1.0
 */
public class ChangeHpFunction implements FightSkillActiveFunction {
    @Override
    public void castSkill(FightSkillWrap fightSkillWrap, FightSkillProcessTemplate fightSkillProcessTemplate, UseSkillDataTemp useSkillDataTemp) {
        int[] parameter = StringUtils.toIntArray(fightSkillProcessTemplate.attributeParameter());
        int targetHp = parameter[0];

        List<Skill.DamageData> damageDataList = new ArrayList<>();

        if (useSkillDataTemp.getTargetParameters().size() == 0) {
            return;
        }

        useSkillDataTemp.getTargetParameters().forEach(targetParameter -> {
            Skill.DamageData.Builder builder = Skill.DamageData.newBuilder();
            long finalDamage = useSkillDataTemp.getAttack().getFightMap().get(VAR_HP) - targetHp;
            Skill.DamageData damageData = builder.setDamage(-finalDamage).setDamageType(0).setTargetId(targetParameter.getId()).build();
            damageDataList.add(damageData);
            targetParameter.addHp(useSkillDataTemp.getAttack(), -finalDamage);
        });
        useSkillDataTemp.sendToAllClient(FightSkillProtocols.USE_RESULT, FightSkillProtocolBuilder.getSc10053(useSkillDataTemp.getAttackId(),
                fightSkillProcessTemplate.id(), useSkillDataTemp.getSkillId(), damageDataList, useSkillDataTemp.getSkillOrganismId()));
    }
}

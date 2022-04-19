package application.module.fight.skill;

import protocol.Skill;

import java.util.List;
import java.util.Set;

/**
 * @author Luo Yong
 * @date 2022-4-6
 * @Source 1.0
 */
public class FightSkillProtocolBuilder {

    public static Skill.SC10052 getSc10052(Skill.CS10052 cs10052) {
        return Skill.SC10052.newBuilder()
                .setSkillId(cs10052.getSkillId())
                .setAttackId(cs10052.getFightOrganismId())
                .addAllTargetId(cs10052.getTargetIdList())
                .setDirection(cs10052.getDirection())
                .setSkillPositionX(cs10052.getSkillPositionX())
                .setSkillPositionY(cs10052.getSkillPositionY())
                .setTimestamp(cs10052.getTimestamp())
                .setSkillOrganismId(cs10052.getSkillOrganismId())
                .build();
    }

    public static Skill.SC10050 getSc10050(long playerId, Set<Integer> skillSet) {
        return Skill.SC10050.newBuilder().addAllSkillId(skillSet).setOrganismId((int) playerId).build();
    }

    public static Skill.SC10053 getSc10053(long attackId, int skillId, int processId, List<Skill.DamageData> damageDataList) {
        return Skill.SC10053.newBuilder()
                .setAttackId(attackId)
                .setSkillId(skillId)
                .setProcessId(processId)
                .addAllDamageData(damageDataList)
                .build();
    }

}

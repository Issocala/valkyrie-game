package application.module.fight.attribute.fight;

import application.module.fight.attribute.AttributeTemplateIdContainer;
import application.module.fight.base.context.UseSkillDataTemp;
import application.module.organism.OrganismType;
import application.module.player.PlayerConfig;
import application.util.AttributeMapUtil;
import application.util.RandomUtil;
import template.FightSkillProcessTemplate;

import java.util.HashMap;
import java.util.Map;

import static application.module.fight.attribute.AttributeTemplateId.*;

/**
 * 战斗相关临时属性
 *
 * @author Luo Yong
 * @date 2022-2-24
 * @Source 1.0
 */
public class FightAttributeMgr {

    /**
     * 基础伤害计算系数
     */
    private final static double PARAMETER_A = 0.35;
    private final static double PARAMETER_B = 2;
    private final static double PARAMETER_C = 2;

    private final static double TEN_THOUSAND_RATIO = 10000;

    public FightAttributeMgr() {
    }

    /**
     * 当前的战斗属性数据快照
     */
    private Map<Short, Long> fightAttributeMap = new HashMap<>();

    public Long getValue(short id) {
        return getValue(fightAttributeMap, id);
    }

    public Long getValue(Map<Short, Long> map, short id) {
        long extValue = 0;
        if (AttributeTemplateIdContainer.VALUE_EXTRA.containsKey(id)) {
            short ext = AttributeTemplateIdContainer.VALUE_EXTRA.get(id);
            extValue = map.getOrDefault(ext, 0L);
        }
        long value = map.getOrDefault(id, 0L);
        return value + extValue;
    }

    public Map<Short, Long> getCurrAttribute() {
        Map<Short, Long> map = new HashMap<>();
        AttributeMapUtil.add(map, fightAttributeMap);
        return map;
    }

    public void addTemplateAttributeMap(Map<Short, Long> fightAttributeMap) {
        long preHp = this.fightAttributeMap.get(MAX_HP);
        long preMp = this.fightAttributeMap.get(MAX_MP);

        this.fightAttributeMap = fightAttributeMap;

        long addHp = this.fightAttributeMap.get(MAX_HP) - preHp;
        long addMp = this.fightAttributeMap.get(MAX_MP) - preMp;
        addHp(addHp);
        addMp(addMp);
    }

    public void addHp(long hp) {
        long currHp = fightAttributeMap.getOrDefault(VAR_HP, 0L) + hp;
        if (currHp < 0) {
            currHp = 0;
        }
        currHp = Math.min(fightAttributeMap.get(MAX_HP), currHp);
        fightAttributeMap.put(VAR_HP, currHp);
    }

    public void addMp(long mp) {
        long currMp = fightAttributeMap.getOrDefault(VAR_MP, 0L) + mp;
        if (currMp < 0) {
            currMp = 0;
        }
        currMp = Math.min(fightAttributeMap.get(MAX_MP), currMp);
        fightAttributeMap.put(VAR_MP, currMp);
    }

    private void basicAttack(FightSkillProcessTemplate fightSkillProcessTemplate, UseSkillDataTemp useSkillDataTemp,
                             final int skillFixedDamage, final int skillFixedDamageRate) {

        useSkillDataTemp.getTargetParameters().forEach(targetParameter -> {
            Map<Short, Long> targetAttributeMap = targetParameter.attributeMap();
            long attack = 0;
            long pierce = 0;
            long targetDefence = 0;
            if (PlayerConfig.Profession.isAttackDamage(useSkillDataTemp.getProfession())) {
                attack = getValue(ATTACK);
                pierce = getValue(ATTACK_PIERCE);
                targetDefence = getValue(targetAttributeMap, ATTACK_DEFENCE);
            } else {
                attack = getValue(MAGIC);
                pierce = getValue(MAGIC_PIERCE);
                targetDefence = getValue(targetAttributeMap, MAGIC_DEFENCE);
            }
            long roleAttack = getValue(ROLE_ATTACK);
            long rolePierce = getValue(ROLE_PIERCE);
            long roleTargetDefence = getValue(targetAttributeMap, ROLE_DEFENCE);
            long finalAttack = attack + roleAttack;
            long finalTargetDefence = targetDefence + roleTargetDefence;
            long finalPierce = pierce + rolePierce;
            long hit = getValue(HIT);
            long hitRatio = getValue(HIT_RATIO);
            long ignoreDefenceRatio = getValue(IGNORE_DEFENCE_RATIO);
            long crit = getValue(CRIT);
            long critRatio = getValue(CRIT_RATIO);
            long reduceBlockRatio = getValue(REDUCE_BLOCK_RATIO);
            long critAddDamageRatio = getValue(CRIT_ADD_DAMAGE_RATIO);
            long blockReduceDamageRatio = getValue(BLOCK_REDUCE_DAMAGE_RATIO);
            long finalDamageRatio = getValue(FINAL_DAMAGE_RATIO);
            long playerDamageRatio = getValue(PLAYER_DAMAGE_RATIO);
            long bossDamageRatio = getValue(BOSS_DAMAGE_RATIO);
            long skillDamageRatio = getValue(SKILL_DAMAGE_RATIO);
            long trueDamage = getValue(TRUE_DAMAGE);

            long targetMiss = getValue(targetAttributeMap, MISS);
            long targetMissRatio = getValue(targetAttributeMap, MISS_RATIO);
            long targetReduceIgnoreDefenceRatio = getValue(targetAttributeMap, REDUCE_IGNORE_DEFENCE_RATIO);
            long targetReduceCrit = getValue(targetAttributeMap, REDUCE_CRIT);
            long targetReduceCritRatio = getValue(targetAttributeMap, REDUCE_CRIT_RATIO);
            long targetBlockRatio = getValue(targetAttributeMap, BLOCK_RATIO);
            long targetCritReduceDamageRatio = getValue(targetAttributeMap, CRIT_REDUCE_DAMAGE_RATIO);
            long targetBlockAddDamageRatio = getValue(targetAttributeMap, BLOCK_ADD_DAMAGE_RATIO);
            long targetReduceFinalDamageRatio = getValue(targetAttributeMap, REDUCE_FINAL_DAMAGE_RATIO);
            long targetReducePlayerDamageRatio = getValue(targetAttributeMap, REDUCE_PLAYER_DAMAGE_RATIO);
            long targetReduceBossDamageRatio = getValue(targetAttributeMap, REDUCE_BOSS_DAMAGE_RATIO);
            long targetReduceSkillDamageRatio = getValue(targetAttributeMap, REDUCE_SKILL_DAMAGE_RATIO);
            long targetReduceTrueDamage = getValue(targetAttributeMap, REDUCE_TRUE_DAMAGE);

            double missRate = Math.min(Math.max((targetMiss - hit) / (double) ((targetMiss - hit) * 3
                    + targetParameter.targetLevel() * 100L) + (targetMissRatio - hitRatio) / TEN_THOUSAND_RATIO, 0), 0.8);
            double ignoreDefenceRate = Math.min(Math.max(ignoreDefenceRatio - targetReduceIgnoreDefenceRatio, 0), 0.8);
            double critRate = Math.min(Math.max((crit - targetReduceCrit) / (double) ((crit + targetReduceCrit) * 3
                    + useSkillDataTemp.getAttackLevel() * 100L) + (critRatio - targetReduceCritRatio) / TEN_THOUSAND_RATIO, 0), 0.8);
            double blockRate = Math.min(Math.max((targetBlockRatio - reduceBlockRatio) / TEN_THOUSAND_RATIO, 0), 1);

            if (isMissRate(missRate)) {
                useSkillDataTemp.getR().client().tell(null, null);
            }

            if (isIgnoreDefence(ignoreDefenceRate)) {
                finalTargetDefence = 0;
            }

            double baseDamage = finalAttack * (1 - (finalTargetDefence + (pierce - finalTargetDefence) * PARAMETER_A)
                    / (finalTargetDefence * PARAMETER_B + finalPierce * PARAMETER_C));

            double critDamage = 0;
            if (isCrit(critRate)) {
                critDamage = Math.max(Math.min((critAddDamageRatio - targetCritReduceDamageRatio) / TEN_THOUSAND_RATIO + 0.5, 3), 0.1);
            }

            double blockDamage = 0;
            if (isBlock(blockRate)) {
                blockDamage = Math.max(Math.min(targetBlockAddDamageRatio - blockReduceDamageRatio / TEN_THOUSAND_RATIO + 0.5, 3), 0.1);
            }

            double specialDamage = Math.max(Math.min(critDamage - blockDamage, 4), -0.7);
            double extDamageExt = 0;
            if (targetParameter.OrganismType() == OrganismType.PLAYER) {
                extDamageExt = (playerDamageRatio - targetReducePlayerDamageRatio) / TEN_THOUSAND_RATIO;
            } else if (targetParameter.OrganismType() == OrganismType.BOSS) {
                extDamageExt = (bossDamageRatio - targetReduceBossDamageRatio) / TEN_THOUSAND_RATIO;
            }
            double extDamage = (finalDamageRatio - targetReduceFinalDamageRatio) / TEN_THOUSAND_RATIO + extDamageExt;

            double skillDamage = baseDamage * skillFixedDamageRate * (1 + (skillDamageRatio - targetReduceSkillDamageRatio)
                    / TEN_THOUSAND_RATIO + skillFixedDamage + trueDamage - targetReduceTrueDamage);

            long finalDamage = (long) (skillDamage * (1 + specialDamage) * (1 + extDamage));
        });
    }

    private boolean isMissRate(double missRate) {
        return missRate > RandomUtil.randomDouble1();
    }

    private boolean isIgnoreDefence(double ignoreDefenceRate) {
        return ignoreDefenceRate > RandomUtil.randomDouble1();
    }

    private boolean isCrit(double critRate) {
        return critRate > RandomUtil.randomDouble(1);
    }

    private boolean isBlock(double blockRate) {
        return blockRate > RandomUtil.randomDouble1();
    }


}

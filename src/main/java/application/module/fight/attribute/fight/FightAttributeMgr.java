package application.module.fight.attribute.fight;

import application.module.fight.attribute.AttributeTemplateIdContainer;
import application.module.fight.attribute.data.message.AddHp;
import application.module.fight.skill.base.context.UseSkillDataTemp;
import application.module.organism.OrganismType;
import application.module.player.PlayerConfig;
import application.util.MathConstant;
import application.util.RandomUtil;
import protocol.Skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private final static double PARAMETER_B = 1;
    private final static double PARAMETER_C = 3;

    public final static double TEN_THOUSAND_RATIO = MathConstant.TEN_THOUSAND;

    public FightAttributeMgr() {
    }

    /**
     * 当前的战斗属性数据快照
     */
    private Map<Short, Long> fightAttributeMap = new HashMap<>();

    public static Long getValue(Map<Short, Long> map, short id) {
        if (AttributeTemplateIdContainer.VALUE_EXTRA.containsKey(id)) {
            short extId = AttributeTemplateIdContainer.VALUE_EXTRA.get(id);
            return map.getOrDefault(id, 0L) + map.getOrDefault(extId, 0L);
        }
        return map.getOrDefault(id, 0L);
    }

//    public Map<Short, Long> getCurrAttribute() {
//        Map<Short, Long> map = new HashMap<>();
//        AttributeMapUtil.add(map, fightAttributeMap);
//        return map;
//    }
//
//    public void addTemplateAttributeMap(Map<Short, Long> fightAttributeMap) {
//        long preHp = this.fightAttributeMap.get(MAX_HP);
//        long preMp = this.fightAttributeMap.get(MAX_MP);
//
//        this.fightAttributeMap = fightAttributeMap;
//
//        long addHp = this.fightAttributeMap.get(MAX_HP) - preHp;
//        long addMp = this.fightAttributeMap.get(MAX_MP) - preMp;
//        addHp(addHp);
//        addMp(addMp);
//    }
//
//    public void addHp(long hp) {
//        if (hp == 0) {
//            return;
//        }
//        long currHp = fightAttributeMap.getOrDefault(VAR_HP, 0L) + hp;
//        if (currHp < 0) {
//            currHp = 0;
//        }
//        currHp = Math.min(fightAttributeMap.get(MAX_HP), currHp);
//        fightAttributeMap.put(VAR_HP, currHp);
//    }
//
//    public void addMp(long mp) {
//        if (mp == 0) {
//            return;
//        }
//        long currMp = fightAttributeMap.getOrDefault(VAR_MP, 0L) + mp;
//        if (currMp < 0) {
//            currMp = 0;
//        }
//        currMp = Math.min(fightAttributeMap.get(MAX_MP), currMp);
//        fightAttributeMap.put(VAR_MP, currMp);
//    }

    public static List<Skill.DamageData> basicAttack(UseSkillDataTemp useSkillDataTemp,
                                                     final int skillFixedDamage, final double skillDamageRate) {

        List<Skill.DamageData> damageDataList = new ArrayList<>();

        useSkillDataTemp.getTargetParameters().forEach(targetParameter -> {
            Skill.DamageData.Builder builder = Skill.DamageData.newBuilder();
            builder.setDamageType(0);
            Map<Short, Long> targetAttributeMap = targetParameter.getAttributeMap();
            long attack;
            long pierce;
            long targetDefence;
            if (PlayerConfig.Profession.isAttackDamage(useSkillDataTemp.getProfession())) {
                attack = getValue(useSkillDataTemp.getAttackAttributeMap(), ATTACK);
                pierce = getValue(useSkillDataTemp.getAttackAttributeMap(), ATTACK_PIERCE);
                targetDefence = getValue(targetAttributeMap, ATTACK_DEFENCE);
            } else {
                attack = getValue(useSkillDataTemp.getAttackAttributeMap(), MAGIC);
                pierce = getValue(useSkillDataTemp.getAttackAttributeMap(), MAGIC_PIERCE);
                targetDefence = getValue(targetAttributeMap, MAGIC_DEFENCE);
            }
            long roleAttack = getValue(useSkillDataTemp.getAttackAttributeMap(), ROLE_ATTACK);
            long rolePierce = getValue(useSkillDataTemp.getAttackAttributeMap(), ROLE_PIERCE);
            long roleTargetDefence = getValue(targetAttributeMap, ROLE_DEFENCE);
            long finalAttack = attack + roleAttack;
            long finalTargetDefence = targetDefence + roleTargetDefence;
            long finalPierce = pierce + rolePierce;
            long hit = getValue(useSkillDataTemp.getAttackAttributeMap(), HIT);
            long hitRatio = getValue(useSkillDataTemp.getAttackAttributeMap(), HIT_RATIO);
            long ignoreDefenceRatio = getValue(useSkillDataTemp.getAttackAttributeMap(), IGNORE_DEFENCE_RATIO);
            long crit = getValue(useSkillDataTemp.getAttackAttributeMap(), CRIT);
            long critRatio = getValue(useSkillDataTemp.getAttackAttributeMap(), CRIT_RATIO);
            long reduceBlockRatio = getValue(useSkillDataTemp.getAttackAttributeMap(), REDUCE_BLOCK_RATIO);
            long critAddDamageRatio = getValue(useSkillDataTemp.getAttackAttributeMap(), CRIT_ADD_DAMAGE_RATIO);
            long blockReduceDamageRatio = getValue(useSkillDataTemp.getAttackAttributeMap(), BLOCK_REDUCE_DAMAGE_RATIO);
            long finalDamageRatio = getValue(useSkillDataTemp.getAttackAttributeMap(), FINAL_DAMAGE_RATIO);
            long playerDamageRatio = getValue(useSkillDataTemp.getAttackAttributeMap(), PLAYER_DAMAGE_RATIO);
            long bossDamageRatio = getValue(useSkillDataTemp.getAttackAttributeMap(), BOSS_DAMAGE_RATIO);
            long skillDamageRatio = getValue(useSkillDataTemp.getAttackAttributeMap(), SKILL_DAMAGE_RATIO);
            long trueDamage = getValue(useSkillDataTemp.getAttackAttributeMap(), TRUE_DAMAGE);

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

            //闪避几率=	min(max( min( B闪避[id:201] / ( B闪避[id:201] + A命中[id:200]  * 9 ) , 0.5 ) + ( B闪避几率[id:300] - A命中几率[id:301])/10000 , 0 ) ,0.9 )
            double missRate = Math.min(Math.max(Math.min((targetMiss / (double) (targetMiss + hit * 9)), 0.5)
                    + (targetMissRatio - hitRatio) / TEN_THOUSAND_RATIO, 0), 0.9);

            boolean miss = isMissRate(missRate, builder);

            if (miss) {
                useSkillDataTemp.getR().client().tell(null, null);
                return;
            }
            //  无视防御率  防御 = 防御 * ( 1 - min(max((A无视防御率[id:310] - B防御强化率[id:311]) / 10000 ,0), 1 ) )
            double ignoreDefenceRate = Math.min(Math.max(ignoreDefenceRatio - targetReduceIgnoreDefenceRatio, 0), 1);
            finalTargetDefence = (long) (finalTargetDefence * (1 - ignoreDefenceRate));

            //	暴击几率 = min(max( min( A暴击[id:202] / ( A暴击[id:202] + B抗暴[id:203]  * 9 ）, 0.5 ) + ( A暴击几率[id:302] - B抗暴几率[id:303])/10000 , 0 ) ,1.2 )
            double critRate = Math.min(Math.max(Math.min((crit / (double) (crit + targetReduceCrit * 9)), 0.5)
                    + (critRatio - targetReduceCritRatio) / TEN_THOUSAND_RATIO, 0), 0.8);
            double blockRate = Math.min(Math.max((targetBlockRatio - reduceBlockRatio) / TEN_THOUSAND_RATIO, 0), 1);

            //  基础伤害 =	 A攻击 * ( 1 -  MIN( B防御  / (B防御 * 系数B + A穿透 * 系数C ) , 最大免伤率 ) )
            double var_ = finalTargetDefence * PARAMETER_B + finalPierce * PARAMETER_C;
            double baseDamage;
            if (var_ == 0) {
                baseDamage = finalAttack;
            } else {
                baseDamage = finalAttack * (1 - Math.min((finalTargetDefence / var_), 0.75));
            }

            double critDamage = 0;
            if (isCrit(critRate, builder)) {
                critDamage = Math.max(Math.min((critAddDamageRatio - targetCritReduceDamageRatio) / TEN_THOUSAND_RATIO + 0.5, 3), 0.1);
            }

            double blockDamage = 0;
            if (isBlock(blockRate, builder)) {
                blockDamage = Math.max(Math.min(targetBlockAddDamageRatio - blockReduceDamageRatio / TEN_THOUSAND_RATIO + 0.5, 3), 0.1);
            }

            double specialDamage = Math.max(Math.min(critDamage - blockDamage, 4), -0.7);
            double extDamageExt = 0;
            if (targetParameter.getOrganismType() == OrganismType.PLAYER) {
                extDamageExt = (playerDamageRatio - targetReducePlayerDamageRatio) / TEN_THOUSAND_RATIO;
            } else if (targetParameter.getOrganismType() == OrganismType.BOSS) {
                extDamageExt = (bossDamageRatio - targetReduceBossDamageRatio) / TEN_THOUSAND_RATIO;
            }
            double extDamage = (finalDamageRatio - targetReduceFinalDamageRatio) / TEN_THOUSAND_RATIO + extDamageExt;

            double skillDamage = baseDamage * skillDamageRate * (1 + (skillDamageRatio - targetReduceSkillDamageRatio)
                    / TEN_THOUSAND_RATIO + skillFixedDamage + trueDamage - targetReduceTrueDamage);
            long finalDamage = (long) (skillDamage * (1 + specialDamage) * (1 + extDamage));
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
            Skill.DamageData damageData = builder.setDamage(-finalDamage).setTargetId(targetParameter.getTargetId()).build();
            damageDataList.add(damageData);
            useSkillDataTemp.getAttributeData().tell(new AddHp(targetParameter.getTargetId(), targetParameter.getOrganismType(),
                    useSkillDataTemp.getR(), -finalDamage, useSkillDataTemp.getAttackId(), useSkillDataTemp.getAttackType(), useSkillDataTemp.getScene(), useSkillDataTemp.getStateData()), null);
        });
        return damageDataList;
    }

    private static boolean isMissRate(double missRate, Skill.DamageData.Builder builder) {
        boolean miss = missRate > RandomUtil.randomDouble1();
        if (miss) {
            builder.setDamageType(1);
        }
        return miss;
    }

    private static boolean isIgnoreDefence(double ignoreDefenceRate, Skill.DamageData.Builder builder) {
        boolean ignore = ignoreDefenceRate > RandomUtil.randomDouble1();
        if (ignore) {
            int temp = builder.getDamageType();
            temp |= (1 << 4);
            builder.setDamageType(temp);
        }
        return ignore;
    }

    private static boolean isCrit(double critRate, Skill.DamageData.Builder builder) {
        boolean crit = critRate > RandomUtil.randomDouble1();
        if (crit) {
            int temp = builder.getDamageType();
            temp |= (1 << 2);
            builder.setDamageType(temp);
        }
        return crit;
    }

    private static boolean isBlock(double blockRate, Skill.DamageData.Builder builder) {
        boolean block = blockRate > RandomUtil.randomDouble1();
        if (block) {
            int temp = builder.getDamageType();
            temp |= (1 << 3);
            builder.setDamageType(temp);
        }
        return block;
    }


}

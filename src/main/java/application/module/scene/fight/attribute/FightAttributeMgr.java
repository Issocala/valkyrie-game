package application.module.scene.fight.attribute;

import application.module.organism.FightOrganism;
import application.module.organism.MonsterOrganism;
import application.module.player.PlayerConfig;
import application.module.organism.PlayerFight;
import application.module.player.fight.attribute.AttributeProtocolBuilder;
import application.module.player.fight.attribute.AttributeProtocols;
import application.module.player.fight.attribute.AttributeTemplateIdContainer;
import application.module.scene.Scene;
import application.module.scene.fight.skill.base.context.UseSkillDataTemp;
import application.util.AttributeMapUtil;
import application.util.MathConstant;
import application.util.RandomUtil;
import protocol.Skill;

import java.util.*;

import static application.module.player.fight.attribute.AttributeTemplateId.*;

/**
 * @author Luo Yong
 * @date 2022-5-16
 * @Source 1.0
 */
public class FightAttributeMgr {

    private FightOrganism owner;

    private Map<Short, Long> fightMap = new HashMap<>();

    private Set<Short> dirtyFightSet = new HashSet<>();

    public void addFightMap(Map<Short, Long> fightMap) {
        doAddAttribute(this.fightMap, fightMap);
        sendToAllClient();
    }

    public void subFightMap(Map<Short, Long> fightMap) {
        doSubAttribute(this.fightMap, fightMap);
        sendToAllClient();
    }

    public Map<Short, Long> getFightMap() {
        return fightMap;
    }

    public void setFightMap(Map<Short, Long> fightMap) {
        this.fightMap = fightMap;
    }

    public Set<Short> getDirtyFightSet() {
        return dirtyFightSet;
    }

    public void setDirtyFightSet(Set<Short> dirtyFightSet) {
        this.dirtyFightSet = dirtyFightSet;
    }

    /**
     * 基础伤害计算系数
     */
    private final static double PARAMETER_A = 0.35;
    private final static double PARAMETER_B = 1;
    private final static double PARAMETER_C = 3;

    public final static double TEN_THOUSAND_RATIO = MathConstant.TEN_THOUSAND;

    public FightAttributeMgr() {
    }


    public static long getValue(Map<Short, Long> map, short id) {
        if (AttributeTemplateIdContainer.VALUE_EXTRA.containsKey(id)) {
            short extId = AttributeTemplateIdContainer.VALUE_EXTRA.get(id);
            return map.getOrDefault(id, 0L) + map.getOrDefault(extId, 0L);
        }
        return map.getOrDefault(id, 0L);
    }

    public static long getRoleAttack(Map<Short, Long> map) {
        return map.getOrDefault(ATTACK, 0L) + map.getOrDefault(ATTACK_EXTRA, 0L) +
                map.getOrDefault(MAGIC, 0L) + map.getOrDefault(MAGIC_EXTRA, 0L) +
                map.getOrDefault(ROLE_ATTACK, 0L) + map.getOrDefault(ROLE_ATTACK_EXTRA, 0L);
    }

    public static long getRoleAttackDefence(Map<Short, Long> map) {
        return map.getOrDefault(ATTACK_DEFENCE, 0L) + map.getOrDefault(ATTACK_DEFENCE_EXTRA, 0L) +
                map.getOrDefault(ROLE_DEFENCE, 0L) + map.getOrDefault(ROLE_DEFENCE_EXTRA, 0L);
    }

    public static long getRoleMagicDefence(Map<Short, Long> map) {
        return map.getOrDefault(MAGIC_DEFENCE, 0L) + map.getOrDefault(MAGIC_DEFENCE_EXTRA, 0L) +
                map.getOrDefault(ROLE_DEFENCE, 0L) + map.getOrDefault(ROLE_DEFENCE_EXTRA, 0L);
    }

    public static long getRolePierce(Map<Short, Long> map) {
        return map.getOrDefault(ATTACK_PIERCE, 0L) + map.getOrDefault(ATTACK_PIERCE_EXTRA, 0L) +
                map.getOrDefault(MAGIC_PIERCE, 0L) + map.getOrDefault(MAGIC_PIERCE_EXTRA, 0L) +
                map.getOrDefault(ROLE_PIERCE, 0L) + map.getOrDefault(ROLE_PIERCE_EXTRA, 0L);
    }

    public static List<Skill.DamageData> basicAttack(UseSkillDataTemp useSkillDataTemp,
                                                     final int skillFixedDamage, final double skillDamageRate) {

        List<Skill.DamageData> damageDataList = new ArrayList<>();
        Map<Short, Long> attackMap = useSkillDataTemp.getFightMap();

        useSkillDataTemp.getTargetParameters().forEach(targetParameter -> {
            Skill.DamageData.Builder builder = Skill.DamageData.newBuilder();
            builder.setDamageType(0);
            Map<Short, Long> targetAttributeMap = targetParameter.getFightMap();
            long attack = 0;
            long pierce = 0;
            long targetDefence = 0;
            if (useSkillDataTemp.getAttack() instanceof PlayerFight playerFight) {
                if (PlayerConfig.Profession.isAttackDamage(playerFight.getPlayerInfo().profession())) {
                    attack = getValue(attackMap, ATTACK);
                    pierce = getValue(attackMap, ATTACK_PIERCE);
                    targetDefence = getValue(targetAttributeMap, ATTACK_DEFENCE);
                } else {
                    attack = getValue(attackMap, MAGIC);
                    pierce = getValue(attackMap, MAGIC_PIERCE);
                    targetDefence = getValue(targetAttributeMap, MAGIC_DEFENCE);
                }
            }

            long roleAttack = getValue(attackMap, ROLE_ATTACK);
            long rolePierce = getValue(attackMap, ROLE_PIERCE);
            long roleTargetDefence = getValue(targetAttributeMap, ROLE_DEFENCE);
            long finalAttack = attack + roleAttack;
            long finalTargetDefence = targetDefence + roleTargetDefence;
            long finalPierce = pierce + rolePierce;
            long hit = getValue(attackMap, HIT);
            long hitRatio = getValue(attackMap, HIT_RATIO);
            long ignoreDefenceRatio = getValue(attackMap, IGNORE_DEFENCE_RATIO);
            long crit = getValue(attackMap, CRIT);
            long critRatio = getValue(attackMap, CRIT_RATIO);
            long reduceBlockRatio = getValue(attackMap, REDUCE_BLOCK_RATIO);
            long critAddDamageRatio = getValue(attackMap, CRIT_ADD_DAMAGE_RATIO);
            long blockReduceDamageRatio = getValue(attackMap, BLOCK_REDUCE_DAMAGE_RATIO);
            long finalDamageRatio = getValue(attackMap, FINAL_DAMAGE_RATIO);
            long playerDamageRatio = getValue(attackMap, PLAYER_DAMAGE_RATIO);
            long bossDamageRatio = getValue(attackMap, BOSS_DAMAGE_RATIO);
            long skillDamageRatio = getValue(attackMap, SKILL_DAMAGE_RATIO);
            long trueDamage = getValue(attackMap, TRUE_DAMAGE);

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
            double val_1 = targetMiss + hit * 9;
            double missRate;
            if (val_1 == 0) {
                missRate = Math.min(Math.max((targetMissRatio - hitRatio) / TEN_THOUSAND_RATIO, 0), 0.9);
            } else {
                missRate = Math.min(Math.max(Math.min((targetMiss / val_1), 0.5) + (targetMissRatio - hitRatio) / TEN_THOUSAND_RATIO, 0), 0.9);
            }

            boolean miss = isMissRate(missRate, builder);
            if (miss) {
                return;
            }

            //  无视防御率  防御 = 防御 * ( 1 - min(max((A无视防御率[id:310] - B防御强化率[id:311]) / 10000 ,0), 1 ) )
            double ignoreDefenceRate = Math.min(Math.max(ignoreDefenceRatio - targetReduceIgnoreDefenceRatio, 0), 1);
            finalTargetDefence = (long) (finalTargetDefence * (1 - ignoreDefenceRate));

            //	暴击几率 = min(max( min( A暴击[id:202] / ( A暴击[id:202] + B抗暴[id:203]  * 9 ）, 0.5 ) + ( A暴击几率[id:302] - B抗暴几率[id:303])/10000 , 0 ) ,1.2 )
            double val_2 = crit + targetReduceCrit * 9;
            double critRate;
            if (val_2 == 0) {
                critRate = Math.min(Math.max((critRatio - targetReduceCritRatio) / TEN_THOUSAND_RATIO, 0), 1.2);
            } else {
                critRate = Math.min(Math.max(Math.min((crit / val_2), 0.5) + (critRatio - targetReduceCritRatio) / TEN_THOUSAND_RATIO, 0), 1.2);
            }
            double blockRate = Math.min(Math.max((targetBlockRatio - reduceBlockRatio) / TEN_THOUSAND_RATIO, 0), 1);

            //  基础伤害 =	 A攻击 * ( 1 -  MIN( B防御  / (B防御 * 系数B + A穿透 * 系数C ) , 最大免伤率 ) )
            double var_3 = finalTargetDefence * PARAMETER_B + finalPierce * PARAMETER_C;
            double baseDamage;
            if (var_3 == 0) {
                baseDamage = finalAttack;
            } else {
                baseDamage = finalAttack * (1 - Math.min((finalTargetDefence / var_3), 0.75));
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
            if (targetParameter.getTargetOrganism() instanceof PlayerFight) {
                extDamageExt = (playerDamageRatio - targetReducePlayerDamageRatio) / TEN_THOUSAND_RATIO;
            } else if (targetParameter.getTargetOrganism() instanceof MonsterOrganism) {
                extDamageExt = (bossDamageRatio - targetReduceBossDamageRatio) / TEN_THOUSAND_RATIO;
            }
            double extDamage = (finalDamageRatio - targetReduceFinalDamageRatio) / TEN_THOUSAND_RATIO + extDamageExt;

            double skillDamage = baseDamage * skillDamageRate * (1 + (skillDamageRatio - targetReduceSkillDamageRatio)
                    / TEN_THOUSAND_RATIO + skillFixedDamage + trueDamage - targetReduceTrueDamage);
            long finalDamage = (long) (skillDamage * (1 + specialDamage) * (1 + extDamage));
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
            Skill.DamageData damageData = builder.setDamage(-finalDamage).setTargetId(targetParameter.getId()).build();
            damageDataList.add(damageData);
            targetParameter.addHp(useSkillDataTemp.getAttack(), -finalDamage);
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

    /**
     * 处理全局模块的百分比加成
     *
     * @param allFightAttributeMap 被添加的集合目标
     * @param id2FightAttributeMap 需要添加的集合
     */
    public static void doAddAttribute(Map<Short, Long> allFightAttributeMap, Map<Short, Long> id2FightAttributeMap) {
        if (AttributeMapUtil.isEmpty(id2FightAttributeMap)) {
            return;
        }
        AttributeTemplateIdContainer.reducePublicExt(allFightAttributeMap, id2FightAttributeMap.keySet());
        AttributeMapUtil.add(allFightAttributeMap, id2FightAttributeMap);
        AttributeTemplateIdContainer.finalFatherResult(allFightAttributeMap, id2FightAttributeMap.keySet());
    }

    /**
     * 处理全局模块的百分比加成
     *
     * @param allFightAttributeMap 被减少的集合目标
     * @param id2FightAttributeMap 需要减少的集合
     */
    public static void doSubAttribute(Map<Short, Long> allFightAttributeMap, Map<Short, Long> id2FightAttributeMap) {
        if (AttributeMapUtil.isEmpty(id2FightAttributeMap)) {
            return;
        }
        AttributeTemplateIdContainer.reducePublicExt(allFightAttributeMap, id2FightAttributeMap.keySet());
        AttributeMapUtil.sub(allFightAttributeMap, id2FightAttributeMap);
        AttributeTemplateIdContainer.finalFatherResult(allFightAttributeMap, id2FightAttributeMap.keySet());
    }

    public void receive() {
        this.fightMap.put(VAR_HP, getValue(this.fightMap, MAX_HP));
        this.fightMap.put(VAR_MP, getValue(this.fightMap, MAX_MP));
        sendToAllClient();
    }

    public void sendToAllClient() {
        Scene scene = this.owner.getScene();
        scene.getPlayerSceneMgr().sendToAllClient(scene, AttributeProtocols.FIGHT_ATTRIBUTE_GET,
                AttributeProtocolBuilder.get10040(owner.getId(), Map.copyOf(fightMap)));
    }

    //get and set

    public FightOrganism getOwner() {
        return owner;
    }

    public void setOwner(FightOrganism owner) {
        this.owner = owner;
    }


}

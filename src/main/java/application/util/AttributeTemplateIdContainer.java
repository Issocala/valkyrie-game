package application.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static application.util.AttributeTemplateId.*;

/**
 * @author Luo Yong
 * @date 2022-3-8
 * @Source 1.0
 */
public class AttributeTemplateIdContainer {

    /**
     * 全部属性ID集合
     */
    private final static Set<Short> ALL = new HashSet<>();
    /**
     * 值类型属性集合
     */
    private final static Set<Short> VALUE = new HashSet<>();
    /**
     * 比例类型属性集合
     */
    private final static Set<Short> RATIO = new HashSet<>();

    /**
     * 比例类型  -> 属性类型
     */
    private final static Map<Short, Short> RATIO_VALUE = new HashMap<>();

    /**
     * 属性类型 -> 比例类型
     */
    private final static Map<Short, Short> VALUE_RATIO = new HashMap<>();

    /**
     * 子属性 -> 全局属性
     */
    private final static Map<Short, Short> VALUE_PUBLIC_RATIO = new HashMap<>();

    /**
     * 基础属性->基础属性的子属性集合
     */
    private final static Map<Short, Set<Short>> BASE = new HashMap<>();

    /**
     * 值属性 -> 能够改变值属性的比例类型
     */
    private final static Map<Short, Set<Short>> VALUE_RATIOS = new HashMap<>();

    static {
        VALUE.add(ATTACK);
        VALUE.add(MAGIC);
        VALUE.add(ATTACK_DEFENCE);
        VALUE.add(MAGIC_DEFENCE);
        VALUE.add(ATTACK_PIERCE);
        VALUE.add(MAGIC_PIERCE);
        VALUE.add(ROLE_ATTACK);
        VALUE.add(ROLE_DEFENCE);
        VALUE.add(ROLE_PIERCE);
        VALUE.add(HIT);
        VALUE.add(MISS);
        VALUE.add(CRIT);
        VALUE.add(REDUCE_CRIT);
        VALUE.add(FIXED_DAMAGE);
        VALUE.add(REDUCE_FIXED_DAMAGE);
        VALUE.add(RECOVER_HP);
        VALUE.add(RECOVER_MP);
        VALUE.add(LEVEL_HP);
        VALUE.add(LEVEL_MP);
        VALUE.add(EQUIP_HP);
        VALUE.add(EQUIP_MP);


        RATIO.add(HIT_RATIO);
        RATIO.add(MISS_RATIO);
        RATIO.add(CRIT_RATIO);
        RATIO.add(REDUCE_CRIT_RATIO);
        RATIO.add(HIT_FIXED_DAMAGE_RATIO);
        RATIO.add(REDUCE_HIT_FIXED_DAMAGE_RATIO);
        RATIO.add(DEAD_RATIO);
        RATIO.add(REDUCE_DEAD_RATIO);
        RATIO.add(DEAD_FIXED_DAMAGE_RATIO);
        RATIO.add(REDUCE_DEAD_FIXED_DAMAGE_RATIO);
        RATIO.add(PARRY_RATIO);
        RATIO.add(REDUCE_PARRY_RATIO);
        RATIO.add(PARRY_FIXED_DAMAGE_RATIO);
        RATIO.add(REDUCE_PARRY_FIXED_DAMAGE_RATIO);
        RATIO.add(IGNORE_DEFENCE_RATIO);
        RATIO.add(REDUCE_IGNORE_DEFENCE_RATIO);
        RATIO.add(IGNORE_DEFENCE_FIXED_DAMAGE_RATIO);
        RATIO.add(SKILL_DAMAGE_RATIO);
        RATIO.add(REDUCE_SKILL_DAMAGE_RATIO);
        RATIO.add(BOSS_DAMAGE_RATIO);
        RATIO.add(REDUCE_BOSS_DAMAGE_RATIO);
        RATIO.add(PLAYER_DAMAGE_RATIO);
        RATIO.add(REDUCE_PLAYER_DAMAGE_RATIO);
        RATIO.add(FINAL_DAMAGE_RATIO);
        RATIO.add(REDUCE_FINAL_DAMAGE_RATIO);
        RATIO.add(ATTACK_SPEED_RATIO);
        RATIO.add(SPEED_RATIO);
        RATIO.add(JUMP_RATIO);
        RATIO.add(BUFF_TIME_RATIO);
        RATIO.add(REDUCE_ABNORMAL_STATE_TIME_RATIO);
        RATIO.add(REDUCE_CONTROL_TIME_RATIO);
        RATIO.add(REDUCE_REPEL_RATIO);
        RATIO.add(HP_PER_RATIO);
        RATIO.add(MP_PER_RATIO);
        RATIO.add(ROLE_ATTACK_PER_RATIO);
        RATIO.add(ROLE_DEFENCE_PER_RATIO);
        RATIO.add(ROLE_PIERCE_PER_RATIO);
        RATIO.add(RECOVER_HP_PER_RATIO);
        RATIO.add(RECOVER_MP_PER_RATIO);
        RATIO.add(EXP_RATIO);
        RATIO.add(ITEM_DROP_RATIO);
        RATIO.add(LEVEL_HP_PER_RATIO);
        RATIO.add(LEVEL_MP_PER_RATIO);
        RATIO.add(EQUIP_HP_PER_RATIO);
        RATIO.add(EQUIP_MP_PER_RATIO);


        ALL.addAll(VALUE);
        ALL.addAll(RATIO);

        addRatioAndValue(HP_PER_RATIO, HP);
        addRatioAndValue(MP_PER_RATIO, MP);
        addRatioAndValue(ROLE_ATTACK_PER_RATIO, ROLE_ATTACK);
        addRatioAndValue(ROLE_DEFENCE_PER_RATIO, ROLE_DEFENCE);
        addRatioAndValue(ROLE_PIERCE_PER_RATIO, ROLE_PIERCE);
        addRatioAndValue(RECOVER_HP_PER_RATIO, RECOVER_HP);
        addRatioAndValue(RECOVER_MP_PER_RATIO, RECOVER_MP);
        addRatioAndValue(LEVEL_HP_PER_RATIO, LEVEL_HP);
        addRatioAndValue(LEVEL_MP_PER_RATIO, LEVEL_MP);
        addRatioAndValue(EQUIP_HP_PER_RATIO, EQUIP_HP);
        addRatioAndValue(EQUIP_MP_PER_RATIO, EQUIP_MP);

        /*
         * 总属性对应的分模块属性的集合
         */
        Set<Short> hp = new HashSet<>();
        hp.add(LEVEL_HP);
        hp.add(EQUIP_HP);
        BASE.put(HP, hp);

        Set<Short> mp = new HashSet<>();
        mp.add(LEVEL_MP);
        mp.add(EQUIP_MP);
        BASE.put(MP, mp);


        /*
          这里放置除了一对一提升ratio的属性之外的映射
          若后续一个属性会被更多的全局数据影响，值可以改为Set集合
         */
        VALUE_PUBLIC_RATIO.put(LEVEL_HP, HP_PER_RATIO);
        VALUE_PUBLIC_RATIO.put(LEVEL_MP, MP_PER_RATIO);
        VALUE_PUBLIC_RATIO.put(EQUIP_HP, HP_PER_RATIO);
        VALUE_PUBLIC_RATIO.put(EQUIP_MP, MP_PER_RATIO);

    }

    /**
     * 更优的做法或许是值对应的万分比ratio加成会有多个，用set集合存储更为合理
     */
    public static void addRatioAndValue(short ratio, short value) {
        RATIO_VALUE.put(ratio, value);
        VALUE_RATIO.put(value, ratio);
    }

    /**
     * 处理百分比属性转换为值属性
     * 这是一个简单粗略版本，后续可以根据需求放特定地方，进行修改，比如投放一些根据不同等级，增加不同比例的属性（动态属性）
     */
    public static Map<Short, Long> finalResult(Map<Short, Long> attributes) {
        Map<Short, Long> attributeCopy = new HashMap<>(attributes);
        attributeCopy.keySet().stream()
                .filter(VALUE_RATIO::containsKey)
                .forEach(id -> {
                            short ratio = VALUE_RATIO.get(id);
                            long valuePer = 0;
                            if (attributeCopy.containsKey(ratio)) {
                                valuePer += attributeCopy.get(ratio);
                            }
                            if (VALUE_PUBLIC_RATIO.containsKey(id)) {
                                short ratio1 = VALUE_PUBLIC_RATIO.get(id);
                                if (attributeCopy.containsKey(ratio1)) {
                                    valuePer += attributeCopy.get(ratio1);
                                }
                            }
                            long value = attributeCopy.get(id);
                            value += value * valuePer / MathConstant.TEN_THOUSAND;
                            attributeCopy.put(id, value);
                        }
                );
        //处理汇总基础属性，比如hp,mp
        BASE.forEach((id, set) -> {
            long countValue = set.stream().filter(attributeCopy::containsKey).mapToLong(attributeCopy::get).sum();
            attributeCopy.put(id, countValue);
        });

        return attributeCopy;
    }

}

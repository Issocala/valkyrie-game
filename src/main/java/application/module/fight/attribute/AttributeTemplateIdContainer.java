package application.module.fight.attribute;

import java.util.*;

import static application.module.fight.attribute.AttributeTemplateId.*;

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
     * 子属性 -> 全局属性
     */
    private final static Map<Short, Short> VALUE_PUBLIC = new HashMap<>();

    /**
     * 全局百分比加成 -> 受加成的值集合
     */
    private final static Map<Short, Set<Short>> PUBLIC_BASE = new HashMap<>();

    /**
     * 子系统模块百分比加成 -> 受加仓的值集合
     */
    private final static Map<Short, Set<Short>> PART_RATIO_VALUE = new HashMap<>();

    public final static Map<Short, Short> VALUE_EXTRA = new HashMap<>();

    static {
        addValue(MAX_HP);
        addValue(MAX_MP);
        addValue(ATTACK);
        addValue(MAGIC);
        addValue(ATTACK_DEFENCE);
        addValue(MAGIC_DEFENCE);
        addValue(ATTACK_PIERCE);
        addValue(MAGIC_PIERCE);
        addValue(ROLE_ATTACK);
        addValue(ROLE_DEFENCE);
        addValue(ROLE_PIERCE);
        addValue(HIT);
        addValue(MISS);
        addValue(CRIT);
        addValue(REDUCE_CRIT);
        addValue(TRUE_DAMAGE);
        addValue(REDUCE_TRUE_DAMAGE);
        addValue(RECOVER_HP);
        addValue(RECOVER_MP);


        addRatio(HIT_RATIO);
        addRatio(MISS_RATIO);
        addRatio(CRIT_RATIO);
        addRatio(REDUCE_CRIT_RATIO);
        addRatio(CRIT_ADD_DAMAGE_RATIO);
        addRatio(CRIT_REDUCE_DAMAGE_RATIO);
        addRatio(DEAD_RATIO);
        addRatio(REDUCE_DEAD_RATIO);
        addRatio(DEAD_FIXED_DAMAGE_RATIO);
        addRatio(REDUCE_DEAD_FIXED_DAMAGE_RATIO);
        addRatio(BLOCK_RATIO);
        addRatio(REDUCE_BLOCK_RATIO);
        addRatio(BLOCK_ADD_DAMAGE_RATIO);
        addRatio(BLOCK_REDUCE_DAMAGE_RATIO);
        addRatio(IGNORE_DEFENCE_RATIO);
        addRatio(REDUCE_IGNORE_DEFENCE_RATIO);
        addRatio(SKILL_DAMAGE_RATIO);
        addRatio(REDUCE_SKILL_DAMAGE_RATIO);
        addRatio(BOSS_DAMAGE_RATIO);
        addRatio(REDUCE_BOSS_DAMAGE_RATIO);
        addRatio(PLAYER_DAMAGE_RATIO);
        addRatio(REDUCE_PLAYER_DAMAGE_RATIO);
        addRatio(FINAL_DAMAGE_RATIO);
        addRatio(REDUCE_FINAL_DAMAGE_RATIO);
        addRatio(ATTACK_SPEED_RATIO);
        addRatio(SPEED_RATIO);
        addRatio(JUMP_RATIO);
        addRatio(BUFF_TIME_RATIO);
        addRatio(REDUCE_ABNORMAL_STATE_TIME_RATIO);
        addRatio(REDUCE_CONTROL_TIME_RATIO);
        addRatio(REDUCE_REPEL_RATIO);
        addRatio(HP_PER_RATIO);
        addRatio(MP_PER_RATIO);
        addRatio(ROLE_ATTACK_PER_RATIO);
        addRatio(ROLE_DEFENCE_PER_RATIO);
        addRatio(ROLE_PIERCE_PER_RATIO);
        addRatio(RECOVER_HP_PER_RATIO);
        addRatio(RECOVER_MP_PER_RATIO);
        addRatio(EXP_RATIO);
        addRatio(ITEM_DROP_RATIO);
        addRatio(COIN_RATIO);
        addRatio(EQUIP_HP_PER_RATIO);
        addRatio(EQUIP_ATTACK_PER_RATIO);
        addRatio(EQUIP_DEFENCE_PER_RATIO);
        addRatio(EQUIP_PIERCE_PER_RATIO);
        addRatio(EQUIP_ALL_PER_RATIO);

        /*
        添加全局
         */
        addPublicBase(HP_PER_RATIO, MAX_HP);
        addPublicBase(MP_PER_RATIO, MAX_MP);
        addPublicBase(ROLE_ATTACK_PER_RATIO, ROLE_ATTACK);
        addPublicBase(ROLE_ATTACK_PER_RATIO, ATTACK);
        addPublicBase(ROLE_ATTACK_PER_RATIO, MAGIC);
        addPublicBase(ROLE_DEFENCE_PER_RATIO, ROLE_DEFENCE);
        addPublicBase(ROLE_DEFENCE_PER_RATIO, ATTACK_DEFENCE);
        addPublicBase(ROLE_DEFENCE_PER_RATIO, MAGIC_DEFENCE);
        addPublicBase(ROLE_PIERCE_PER_RATIO, ROLE_PIERCE);
        addPublicBase(ROLE_PIERCE_PER_RATIO, ATTACK_PIERCE);
        addPublicBase(ROLE_PIERCE_PER_RATIO, MAGIC_PIERCE);
        addPublicBase(RECOVER_HP_PER_RATIO, RECOVER_HP);
        addPublicBase(RECOVER_MP_PER_RATIO, RECOVER_MP);

        /*
        添加子系统
         */
        addPartBase(EQUIP_HP_PER_RATIO, MAX_HP);


        addValueExtra(MAX_HP, HP_EXTRA);
        addValueExtra(MAX_MP, MP_EXTRA);
        addValueExtra(ATTACK, ATTACK_EXTRA);
        addValueExtra(MAGIC, MAGIC_EXTRA);
        addValueExtra(ATTACK_DEFENCE, ATTACK_DEFENCE_EXTRA);
        addValueExtra(MAGIC_DEFENCE, MAGIC_DEFENCE_EXTRA);
        addValueExtra(ATTACK_PIERCE, ATTACK_PIERCE_EXTRA);
        addValueExtra(MAGIC_PIERCE, MAGIC_PIERCE_EXTRA);

    }

    /**
     * 添加值属性
     *
     * @param value 值属性
     */
    public static void addValue(short value) {
        VALUE.add(value);
        ALL.add(value);
    }

    /**
     * 添加比例属性
     *
     * @param ratio 比例属性
     */
    public static void addRatio(short ratio) {
        RATIO.add(ratio);
        ALL.add(ratio);
    }

    /**
     * 添加全局加成百分比以及对应值属性
     *
     * @param ratio 全局比例
     * @param value 值属性
     */
    public static void addPublicBase(short ratio, short value) {
        Set<Short> values = PUBLIC_BASE.get(ratio);
        if (Objects.isNull(values)) {
            values = new HashSet<>();
            PUBLIC_BASE.put(ratio, values);
        }
        values.add(value);
    }

    /**
     * 添加局部子系统加成百分比以及对应值属性
     *
     * @param ratio 子系统比例
     * @param value 值属性
     */
    public static void addPartBase(short ratio, short value) {
        Set<Short> values = PART_RATIO_VALUE.get(ratio);
        if (Objects.isNull(values)) {
            values = new HashSet<>();
            PART_RATIO_VALUE.put(ratio, values);
        }
        values.add(value);
    }

    public static void addValueExtra(short value, short extra) {
        VALUE_EXTRA.put(value, extra);
    }

    public static void reducePublicExt(Map<Short, Long> attributes, Set<Short> set) {
        set.stream().filter(PUBLIC_BASE::containsKey).forEach(aShort -> PUBLIC_BASE.get(aShort).forEach(sonShort -> {
            long ext = attributes.getOrDefault(sonShort, 0L) * attributes.getOrDefault(aShort, 0L) / 10000;
            short extId = VALUE_EXTRA.get(aShort);
            attributes.put(extId, attributes.getOrDefault(extId, 0L) - ext);
        }));
    }

    public static Map<Short, Long> reducePartExt(Map<Short, Long> attributes, Set<Short> set) {
        Map<Short, Long> reduces = new HashMap<>();
        set.stream().filter(PART_RATIO_VALUE::containsKey).forEach(aShort -> PART_RATIO_VALUE.get(aShort).forEach(sonShort -> {
            long ext = attributes.getOrDefault(sonShort, 0L) * attributes.getOrDefault(aShort, 0L) / 10000;
            short extId = VALUE_EXTRA.get(aShort);
            attributes.put(extId, attributes.getOrDefault(extId, 0L) - ext);
            reduces.put(extId, ext);
        }));
        return reduces;
    }

    /**
     * 处理父节点百分比属性转换为值属性
     */
    public static void finalFatherResult(Map<Short, Long> attributes) {
        PUBLIC_BASE.forEach((e, set) -> {
            if (attributes.containsKey(e)) {
                set.stream().filter(attributes::containsKey).forEach(aShort -> {
                    long ext = attributes.get(aShort) * attributes.getOrDefault(e, 0L) / 10000;
                    short extId = VALUE_EXTRA.get(aShort);
                    attributes.put(extId, attributes.getOrDefault(extId, 0L) + ext);
                });
            }
        });
    }

    /**
     * 处理子节点
     */
    public static Map<Short, Long> finalPartResult(Map<Short, Long> attributes, Set<Short> isUpdateSet) {
        Map<Short, Long> addExtMap = new HashMap<>();
        isUpdateSet.forEach((id) -> {
            Set<Short> set = PART_RATIO_VALUE.get(id);
            if (Objects.nonNull(set)) {
                set.stream().filter(attributes::containsKey).forEach(aShort -> {
                    long ext = attributes.get(aShort) * attributes.getOrDefault(id, 0L) / 10000;
                    short extId = VALUE_EXTRA.get(aShort);
                    long add = ext - attributes.getOrDefault(extId, 0L);
                    addExtMap.put(extId, add);
                    attributes.put(extId, ext);
                });
            }
        });
        return addExtMap;
    }
}

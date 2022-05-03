package application.module.fight.skill.util;

/**
 * @author Luo Yong
 * @date 2022-4-15
 * @Source 1.0
 */
public interface PassiveTriggerType {

    /**
     * 使用技能触发被动，效果作用于自身
     */
    byte USE_SKILL = 1;

    /**
     * 受到技能结算之后
     */
    byte BE_USE_SKILL = 2;

    /**
     * 伤害计算前
     */
    byte DAMAGE_BEFORE = 3;

    /**
     * 受到伤害计算前
     */
    byte BE_DAMAGE_BEFORE = 4;

    /**
     * 伤害计算后
     */
    byte DAMAGE_AFTER = 5;

    /**
     * 受到伤害计算后
     */
    byte BE_DAMAGE_AFTER = 6;

    /**
     * 添加buff
     */
    byte Add_Buff = 7;

    /**
     * 使用技能触发被动，效果作用于目标
     */
    byte USE_SKILL_TARGET = 8;

}

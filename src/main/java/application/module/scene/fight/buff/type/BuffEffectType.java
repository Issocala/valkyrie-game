package application.module.scene.fight.buff.type;

/**
 * buff效果类型
 *
 * @author Luo Yong
 * @date 2022-4-1
 * @Source 1.0
 */
public interface BuffEffectType {
    /**
     * 嘲讽
     */
    byte TAUNT = 1;

    /**
     * 冰冻
     */
    byte FREEZE = 2;

    /**
     * 中毒
     */
    byte POISONING = 3;

    /**
     * 霸体
     */
    byte SUPER_ARMOR = 4;

    /**
     * 无敌
     */
    byte INVINCIBLE = 5;

    /**
     * 金身
     */
    byte GOLDEN_BODY = 6;

    /**
     * 眩晕
     */
    byte DIZZINESS = 7;

    /**
     * 击飞
     */
    byte STRIKE_TO_FLY = 8;

    /**
     * 麻痹
     */
    byte PARALYSIS = 9;

    /**
     * 免疫增益buff
     */
    byte IMMUNE_POSITIVE_BUFF = 10;

    /**
     * 免疫减益buff
     */
    byte IMMUNE_PASSIVE_BUFF = 11;

    /**
     * 免疫所有buff
     */
    byte IMMUNE_ALL_BUFF = 12;

}

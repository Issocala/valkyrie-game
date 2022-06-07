package application.condition.constant;

/**
 * @author Luo Yong
 * @date 2022-5-6
 * @Source 1.0
 */
public interface ConditionItemType {

    /**
     * 攻击是普攻
     */
    short NORMAL_ATTACK = 1;

    short OR = Short.MAX_VALUE - 1;
    short AND = Short.MAX_VALUE;

}

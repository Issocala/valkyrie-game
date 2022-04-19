package application.module.fight.buff.type;

/**
 * @author Luo Yong
 * @date 2022-4-1
 * @Source 1.0
 */
public interface BuffRepeatRuleType {
    /**
     * 不可覆盖buff
     **/
    byte NO_REPLACE_BUFF = 0;

    /**
     * 时间覆盖的buff
     */
    byte REPLACE_TIME_BUFF = 1;

    /**
     * 时间叠加的buff
     **/
    byte REPEAT_TIME_BUFF = 2;

    /**
     * 效果叠加的buff
     **/
    byte REPEAT_EFFECT_BUFF = 3;

}

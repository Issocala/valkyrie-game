package application.module.scene.fight.buff.type;

/**
 * @author Luo Yong
 * @date 2022-4-1
 * @Source 1.0
 */
public interface BuffCoverRuleType {
    /**
     * 不可覆盖buff
     **/
    byte NO_REPLACE_BUFF = 0;

    /**
     * 时间替换的buff
     */
    byte REPLACE_TIME_BUFF = 1;

    /**
     * 时间叠加的buff
     **/
    byte REPEAT_TIME_BUFF = 2;

    /**
     * 效果叠加,时间替换
     **/
    byte REPEAT_EFFECT_AND_REPLACE_TIME = 3;

    /**
     * 效果叠加,时间叠加
     **/
    byte REPEAT_EFFECT_AND_REPEAT_TIME = 4;

    /**
     * 层数属性叠加,时间替换(不叠加额外效果属性,只叠加配置表层数属性)
     */
    byte REPEAT_COVER_AND_REPLACE_TIME = 5;

    /**
     * 层数属性叠加,时间叠加(不叠加额外效果属性,只叠加配置表层数属性)
     */
    byte REPEAT_COVER_AND_REPEAT_TIME = 6;

    /**
     * 层数属性叠加
     */
    byte REPEAT_COVER = 7;

}

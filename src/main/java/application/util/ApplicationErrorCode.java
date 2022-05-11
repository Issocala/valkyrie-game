package application.util;

/**
 * @author Luo Yong
 * @date 2022-3-23
 * @Source 1.0
 */
public interface ApplicationErrorCode {
    //蓝量或血量不足
    int USE_SKILL_HP_MP = 10001;
    //当前状态不能使用技能
    int USE_SKILL_STATE = 10002;
    //当前场景不能使用技能
    int USE_SKILL_SCENE = 10003;

}

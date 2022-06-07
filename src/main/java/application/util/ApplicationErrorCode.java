package application.util;

/**
 * @author Luo Yong
 * @date 2022-3-23
 * @Source 1.0
 */
public interface ApplicationErrorCode {
    int CODE_ERROR = -1;
    //蓝量或血量不足
    int USE_SKILL_HP_MP = 10001;
    //当前状态不能使用技能
    int USE_SKILL_STATE = 10002;
    //当前场景不能使用技能
    int USE_SKILL_SCENE = 10003;
    //BOSS已死亡，请等待20秒后复活
    int SCENE_UNOPENED = 10004;
    //客户端断开连接
    int CLIENT_DISCONNECT = 10005;

}

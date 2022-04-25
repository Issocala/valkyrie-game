package application.module.player;

import application.module.GameProtocols;

/**
 * @author Luo Yong
 * @date 2022-1-17
 * @Source 1.0
 */
public interface PlayerProtocols {
    /**
     * 获取全部角色
     */
    int GETAll = GameProtocols.PLAYER;

    /**
     * 创建角色
     */
    int CREATE = GameProtocols.PLAYER + 1;

    /**
     * 角色登入
     */
    int LOGIN = GameProtocols.PLAYER + 2;

    /**
     * 角色删除
     */
    int DELETE = GameProtocols.PLAYER + 3;

    /**
     * 玩家下线
     */
    int LOGOUT = GameProtocols.PLAYER + 4;

    /**
     * 返回玩家实体信息
     */
    int EntityInfo = GameProtocols.PLAYER + 5;

}

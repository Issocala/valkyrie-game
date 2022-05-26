package application.module.scene.fight.state;

import application.module.GameProtocols;

/**
 * 状态
 *
 * @author Luo Yong
 * @date 2022-2-28
 * @Source 1.0
 */
public interface StateProtocols {

    int ALL = GameProtocols.STATE;

    int ADD_STATE = GameProtocols.STATE + 1;

    int REMOVE_STATE = GameProtocols.STATE + 2;

}

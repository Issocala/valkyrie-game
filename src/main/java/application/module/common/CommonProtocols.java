package application.module.common;

import application.module.GameProtocols;

/**
 * @author Luo Yong
 * @date 2022-3-28
 * @Source 1.0
 */
public interface CommonProtocols {

    int APPLICATION_ERROR = GameProtocols.COMMON;

    int SERVER_TIME = GameProtocols.COMMON + 1;

}

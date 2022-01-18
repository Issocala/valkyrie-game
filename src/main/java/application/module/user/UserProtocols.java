package application.module.user;

import application.module.GameProtocols;

/**
 * @author Luo Yong
 * @date 2022-1-17
 * @Source 1.0
 */
public interface UserProtocols {

    int REGISTER = GameProtocols.USER;


    int LOGIN = GameProtocols.USER + 1;

}

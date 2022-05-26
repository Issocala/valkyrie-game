package application.module.scene.fight.buff;

import application.module.GameProtocols;

/**
 * @author Luo Yong
 * @date 2022-3-2
 * @Source 1.0
 */
public interface FightBuffProtocols {

    int GET = GameProtocols.BUFF;

    int ADD = GameProtocols.BUFF + 1;

    int REMOVE = GameProtocols.BUFF + 2;

}

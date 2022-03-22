package application.module.scene;


import application.module.GameProtocols;

/**
 * @author Luo Yong
 * @date 2022-2-8
 * @Source 1.0
 */
public interface SceneProtocols {

    int SCENE_ENTER = GameProtocols.SCENE;

    int SCENE_EXIT = GameProtocols.SCENE + 1;

    int SCENE_MOVE = GameProtocols.SCENE + 2;

    int SCENE_STOP = GameProtocols.SCENE + 3;

    int SCENE_RETURN_ENTITY = GameProtocols.SCENE + 4;

}

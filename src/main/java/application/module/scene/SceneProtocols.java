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

    int SCENE_JUMP = GameProtocols.SCENE + 5;

    int SCENE_FLASH = GameProtocols.SCENE + 6;

    int AI = GameProtocols.SCENE + 7;

    int HELP_USE_SKILL = GameProtocols.SCENE + 8;

    int RECEIVE = GameProtocols.SCENE + 9;

    int PICK_UP_ITEM = GameProtocols.SCENE + 10;

    int FUCK_NPC = GameProtocols.SCENE + 11;

    int SCENE_RUSH = GameProtocols.SCENE + 12;

}

package application.module.scene.gameinstance;

import akka.actor.ActorRef;
import application.module.scene.Scene;

/**
 * @author Luo Yong
 * @date 2022-6-7
 * @Source 1.0
 */
public class GameInstance extends Scene {

    Scene scene;

    private int currLayer;

    private int gameInstanceTemplateId;


    public GameInstance(ActorRef sceneActor, int sceneTemplateId) {
        super(sceneActor, sceneTemplateId);
    }


}

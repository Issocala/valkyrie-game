package application.module.scene;

import akka.actor.ActorRef;
import application.module.base.scene.GameScene;
import com.google.common.base.Preconditions;
import mobius.modular.module.api.AbstractModule;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2021-12-31
 * @Source 1.0
 */
public final class GameArea extends AbstractModule {

    private final Map<Long, GamePlane> sceneRefId2GamePlaneMap = new HashMap<>();

    private final Map<Long, GameScene> sceneId2GameSceneMap = new HashMap<>();

    @Override
    public void initData() {
        loadAllPreGameScene();
    }

    @Override
    public Receive createReceive() {
        return null;
    }

    /**
     * 加载需要
     */
    private void loadAllPreGameScene() {

    }

    public ActorRef getGameSceneRef(long sceneId) {
        GameScene gameScene = sceneId2GameSceneMap.get(sceneId);
        Preconditions.checkNotNull(gameScene, "obtain gameScene by sceneId error, nonentity this sceneId : " + sceneId);
        return gameScene.self();
    }

    public ActorRef getGameSceneRefBySceneIdRef(long sceneIdRef) {
        GamePlane gamePlane = sceneRefId2GamePlaneMap.get(sceneIdRef);
        GameScene gameScene = null;
        Preconditions.checkNotNull(gameScene, "obtain gameScene by sceneId error, nonentity this sceneIdRef : " + sceneIdRef);
        return gameScene.self();
    }
}

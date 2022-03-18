package application.module.scene;

import application.module.scene.data.domain.PositionInfo;
import protocol.Scene;

/**
 * @author Luo Yong
 * @date 2022-3-18
 * @Source 1.0
 */
public class SceneProtocolBuilder {

    public static Scene.SC10030 getSc10030(long sceneId, long playerId, PositionInfo positionInfo) {
        return Scene.SC10030.newBuilder()
                .setSceneId(sceneId)
                .setOrganismId(playerId)
                .setPositionX(positionInfo.positionX())
                .setPositionY(positionInfo.positionY())
                .setFace(positionInfo.face())
                .build();
    }

    public static Scene.SC10031 getSc10031(long playerId) {
        return Scene.SC10031.newBuilder()
                .setPlayerId(playerId)
                .build();
    }

    public static Scene.SC10032 getSc10032(long playerId, Scene.CS10032 cs10032) {
        return Scene.SC10032.newBuilder()
                .setPlayerId(playerId)
                .setMoveInfo(cs10032.getMoveInfo())
                .build();
    }

    public static Scene.SC10033 getSc10033(long playerId, Scene.CS10033 cs10033) {
        return Scene.SC10033.newBuilder()
                .setPlayerId(playerId)
                .setStopInfo(cs10033.getStopInfo())
                .build();
    }
}

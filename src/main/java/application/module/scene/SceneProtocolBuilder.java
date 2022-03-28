package application.module.scene;

import application.module.scene.data.domain.PositionInfo;
import protocol.Scene;

/**
 * @author Luo Yong
 * @date 2022-3-18
 * @Source 1.0
 */
public class SceneProtocolBuilder {

    public static Scene.SC10030 getSc10030(long sceneId) {
        return Scene.SC10030.newBuilder()
                .setSceneId(sceneId)
                .build();
    }

    public static Scene.SC10031 getSc10031(long playerId) {
        return Scene.SC10031.newBuilder()
                .setPlayerId(playerId)
                .build();
    }

    public static Scene.SC10032 getSc10032(long playerId, Scene.CS10032 cs10032) {
        return Scene.SC10032.newBuilder()
                .setOrganismId(playerId)
                .setMoveInfo(cs10032.getMoveInfo())
                .build();
    }

    public static Scene.SC10033 getSc10033(long playerId, Scene.CS10033 cs10033) {
        return Scene.SC10033.newBuilder()
                .setOrganismId(playerId)
                .setStopInfo(cs10033.getStopInfo())
                .build();
    }

    public static Scene.SC10034 getSc10034(long organismId, byte organismType, PositionInfo positionInfo) {
        return Scene.SC10034.newBuilder()
                .setOrganismId(organismId)
                .setOrganismType(organismType)
                .setPositionX(positionInfo.positionX())
                .setPositionY(positionInfo.positionY())
                .setFace(positionInfo.face())
                .build();
    }
}

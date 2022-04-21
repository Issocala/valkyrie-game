package application.module.scene;

import application.module.scene.data.domain.PositionInfo;
import protocol.Scene;

/**
 * @author Luo Yong
 * @date 2022-3-18
 * @Source 1.0
 */
public class SceneProtocolBuilder {

    public static Scene.SC10030 getSc10030(long sceneId, PositionInfo positionInfo) {
        return Scene.SC10030.newBuilder()
                .setSceneId(sceneId)
                .setPositionX(positionInfo.getPositionX())
                .setPositionY(positionInfo.getPositionY())
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
                .setPositionX(positionInfo.getPositionX())
                .setPositionY(positionInfo.getPositionY())
                .setFace(positionInfo.getFace())
                .build();
    }

    public static Scene.SC10035 getSc10035(long organismId, Scene.CS10035 cs10035) {
        return Scene.SC10035.newBuilder()
                .setOrganismId(organismId)
                .setJumpInfo(cs10035.getJumpInfo())
                .build();
    }

    public static Scene.SC10036 getSc10036(long organismId, Scene.CS10036 cs10036) {
        return Scene.SC10036.newBuilder()
                .setOrganismId(organismId)
                .setPositionX(cs10036.getPositionX())
                .setPositionY(cs10036.getPositionY())
                .build();
    }
}

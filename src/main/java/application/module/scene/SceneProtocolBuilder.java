package application.module.scene;

import application.module.scene.data.domain.PositionInfo;
import application.util.Vector;
import protocol.Scene;

/**
 * @author Luo Yong
 * @date 2022-3-18
 * @Source 1.0
 */
public class SceneProtocolBuilder {

    public static Scene.SC10300 getSc10300(long sceneId, PositionInfo positionInfo) {
        return Scene.SC10300.newBuilder()
                .setSceneId(sceneId)
                .setPositionX(positionInfo.getPositionX())
                .setPositionY(positionInfo.getPositionY())
                .build();
    }

    public static Scene.SC10301 getSc10301(long playerId) {
        return Scene.SC10301.newBuilder()
                .setPlayerId(playerId)
                .build();
    }

    public static Scene.SC10302 getSc10302(long playerId, Scene.CS10302 cs10302) {
        return Scene.SC10302.newBuilder()
                .setOrganismId(playerId)
                .setMoveInfo(cs10302.getMoveInfo())
                .build();
    }

    public static Scene.SC10303 getSc10303(long playerId, Scene.CS10303 cs10303) {
        return Scene.SC10303.newBuilder()
                .setOrganismId(playerId)
                .setStopInfo(cs10303.getStopInfo())
                .build();
    }

    public static Scene.SC10304 getSc10304(long organismId, byte organismType, PositionInfo positionInfo, int organismIdTemplate) {
        return Scene.SC10304.newBuilder()
                .setOrganismId(organismId)
                .setOrganismType(organismType)
                .setPositionX(positionInfo.getPositionX())
                .setPositionY(positionInfo.getPositionY())
                .setFace(positionInfo.getFace())
                .setOrganismTemplateId(organismIdTemplate)
                .build();
    }

    public static Scene.SC10305 getSc10305(long organismId, Scene.CS10305 cs10305) {
        return Scene.SC10305.newBuilder()
                .setOrganismId(organismId)
                .setJumpInfo(cs10305.getJumpInfo())
                .build();
    }

    public static Scene.SC10306 getSc10306(long organismId, Scene.CS10306 cs10306) {
        return Scene.SC10306.newBuilder()
                .setOrganismId(organismId)
                .setPositionX(cs10306.getPositionX())
                .setPositionY(cs10306.getPositionY())
                .build();
    }

    public static Scene.SC10306 getSc10306(long organismId, Vector vector) {
        return Scene.SC10306.newBuilder()
                .setOrganismId(organismId)
                .setPositionX(vector.x())
                .setPositionY(vector.y())
                .build();
    }

    public static Scene.SC10307 getSc10307() {
        return Scene.SC10307.newBuilder()
                .build();
    }

    public static Scene.SC10308 getSc10308(long organismId, int skillTemplateId) {
        return Scene.SC10308.newBuilder()
                .setOrganismId(organismId)
                .setSkillTemplateId(skillTemplateId)
                .build();
    }

    public static Scene.SC10312 getSc10312(long organismId, Scene.CS10312 cs10312) {
        return Scene.SC10312.newBuilder()
                .setOrganismId(organismId)
                .setPositionX(cs10312.getPositionX())
                .setPositionY(cs10312.getPositionY())
                .setTime(cs10312.getTime())
                .build();
    }

}

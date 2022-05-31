package application.module.scene;

import application.util.Vector3;
import protocol.Scene;

/**
 * @author Luo Yong
 * @date 2022-3-18
 * @Source 1.0
 */
public class SceneProtocolBuilder {

    public static Scene.SC10300 getSc10300(long sceneId, Vector3 vector3) {
        return Scene.SC10300.newBuilder()
                .setSceneId(sceneId)
                .setPositionX(vector3.x())
                .setPositionY(vector3.y())
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

    public static Scene.SC10304 getSc10304(long organismId, byte organismType, Vector3 vector3, int organismIdTemplate) {
        return Scene.SC10304.newBuilder()
                .setOrganismId(organismId)
                .setOrganismType(organismType)
                .setPositionX(vector3.x())
                .setPositionY(vector3.y())
                .setFace((int) vector3.z())
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

    public static Scene.SC10306 getSc10306(long organismId, Vector3 v) {
        return Scene.SC10306.newBuilder()
                .setOrganismId(organismId)
                .setPositionX(v.x())
                .setPositionY(v.y())
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

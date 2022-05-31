package application.module.organism;

import application.guid.IdUtils;
import application.module.scene.Scene;
import application.util.Vector3;
import application.util.LongId;

/**
 * 生物
 *
 * @author Luo Yong
 * @date 2022-2-25
 * @Source 1.0
 */
public abstract class Organism extends LongId {

    private final byte organismType;

    private Vector3 point;

    private Vector3 direction;

    private final int organismTemplateId;

    private Scene scene;

    public Organism(byte organismType, int organismTemplateId) {
        this(IdUtils.fastSimpleUUIDLong(), organismType, null, organismTemplateId);
    }

    public Organism(byte organismType, Vector3 point, int organismTemplateId) {
        this(IdUtils.fastSimpleUUIDLong(), organismType, point, organismTemplateId);
    }

    public Organism(long id, byte organismType, int organismTemplateId) {
        this(id, organismType, Vector3.ZERO, organismTemplateId);
    }

    public Organism(long id, byte organismType, Vector3 point, int organismTemplateId) {
        super(id);
        this.organismType = organismType;
        this.point = point;
        this.organismTemplateId = organismTemplateId;
    }

    public byte getOrganismType() {
        return organismType;
    }

    public Vector3 getPoint() {
        return point;
    }

    public void setPoint(Vector3 point) {
        this.point = point;
    }

    public Vector3 getDirection() {
        return direction;
    }

    public void setDirection(Vector3 direction) {
        this.direction = direction;
    }

    public int getOrganismTemplateId() {
        return organismTemplateId;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}

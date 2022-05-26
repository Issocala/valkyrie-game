package application.module.organism;

import application.guid.IdUtils;
import application.module.scene.Scene;
import application.module.scene.data.entity.PositionInfo;
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

    private PositionInfo positionInfo;

    private final int organismTemplateId;

    private Scene scene;

    public Organism(byte organismType, int organismTemplateId) {
        this(IdUtils.fastSimpleUUIDLong(), organismType, null, organismTemplateId);
    }

    public Organism(byte organismType, PositionInfo positionInfo, int organismTemplateId) {
        this(IdUtils.fastSimpleUUIDLong(), organismType, positionInfo, organismTemplateId);
    }

    public Organism(long id, byte organismType, int organismTemplateId) {
        this(id, organismType, null, organismTemplateId);
    }

    public Organism(long id, byte organismType, PositionInfo positionInfo, int organismTemplateId) {
        super(id);
        this.organismType = organismType;
        this.positionInfo = positionInfo;
        this.organismTemplateId = organismTemplateId;
    }

    public byte getOrganismType() {
        return organismType;
    }

    public PositionInfo getPositionInfo() {
        return positionInfo;
    }

    public void setPositionInfo(PositionInfo positionInfo) {
        this.positionInfo = positionInfo;
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

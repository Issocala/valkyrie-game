package application.module.organism;

import application.guid.IdUtils;
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

    private final int organismTemplateId;

    public Organism(int organismTemplateId, byte organismType) {
        this(IdUtils.fastSimpleUUIDLong(), organismTemplateId, organismType);
    }

    public Organism(long id, int organismTemplateId, byte organismType) {
        super(id);
        this.organismType = organismType;
        this.organismTemplateId = organismTemplateId;
    }

    public byte getOrganismType() {
        return organismType;
    }

    public int getOrganismTemplateId() {
        return organismTemplateId;
    }
}

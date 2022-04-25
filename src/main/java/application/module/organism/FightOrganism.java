package application.module.organism;


import application.guid.IdUtils;

/**
 * 有战斗能力的生物
 *
 * @author Luo Yong
 * @date 2022-2-25
 * @Source 1.0
 */
public abstract class FightOrganism extends Organism {
    private final int organismTemplateId;

    public FightOrganism(int organismTemplateId, byte organismType) {
        super(IdUtils.fastSimpleUUIDLong(), organismType);
        this.organismTemplateId = organismTemplateId;
    }

    public FightOrganism(long id, int organismTemplateId, byte organismType) {
        super(id, organismType);
        this.organismTemplateId = organismTemplateId;
    }

    public int getOrganismTemplateId() {
        return organismTemplateId;
    }
}

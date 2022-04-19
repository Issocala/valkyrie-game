package application.module.organism;

import application.guid.IdUtils;

/**
 * @author Luo Yong
 * @date 2022-4-18
 * @Source 1.0
 */
public class MonsterOrganism extends FightOrganism {

    private final int organismTemplateId;

    public MonsterOrganism(int organismTemplateId, byte organismType) {
        super(IdUtils.fastSimpleUUIDLong(), organismType);
        this.organismTemplateId = organismTemplateId;
    }

    public MonsterOrganism(int organismTemplateId) {
        super(IdUtils.fastSimpleUUIDLong(), OrganismType.MONSTER);
        this.organismTemplateId = organismTemplateId;
    }

    public int getOrganismTemplateId() {
        return organismTemplateId;
    }
}

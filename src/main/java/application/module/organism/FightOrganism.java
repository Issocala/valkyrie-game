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


    public FightOrganism(int organismTemplateId, byte organismType) {
        this(IdUtils.fastSimpleUUIDLong(), organismTemplateId, organismType);
    }

    public FightOrganism(long id, int organismTemplateId, byte organismType) {
        super(id, organismTemplateId, organismType);
    }

}

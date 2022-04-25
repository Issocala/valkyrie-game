package application.module.organism;

/**
 * @author Luo Yong
 * @date 2022-4-18
 * @Source 1.0
 */
public class MonsterOrganism extends FightOrganism {
    public MonsterOrganism(int organismTemplateId) {
        super(organismTemplateId, OrganismType.MONSTER);
    }
}

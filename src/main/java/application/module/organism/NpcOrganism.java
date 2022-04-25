package application.module.organism;

/**
 * @author Luo Yong
 * @date 2022-4-22
 * @Source 1.0
 */
public class NpcOrganism extends FightOrganism{

    public NpcOrganism(int organismTemplateId) {
        super(organismTemplateId, OrganismType.NPC);
    }
}

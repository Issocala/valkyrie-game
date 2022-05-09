package application.module.organism;

/**
 * @author Luo Yong
 * @date 2022-5-9
 * @Source 1.0
 */
public class ItemOrganism extends Organism {
    public ItemOrganism(int organismTemplateId) {
        super(organismTemplateId, OrganismType.ITEM);
    }
}

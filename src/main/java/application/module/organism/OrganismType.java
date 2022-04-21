package application.module.organism;

/**
 * @author Luo Yong
 * @date 2022-3-16
 * @Source 1.0
 */
public interface OrganismType {

    byte PLAYER = 1;

    byte MONSTER = 2;

    byte BOSS = 3;

    static boolean isFightOrganism(Organism organism) {

        return isFightOrganism(organism.getOrganismType());
    }

    static boolean isFightOrganism(byte organismType) {
        return organismType == PLAYER ||  organismType == MONSTER || organismType == BOSS;
    }
}

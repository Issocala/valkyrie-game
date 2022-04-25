package application.module.organism;

/**
 * @author Luo Yong
 * @date 2022-3-1
 * @Source 1.0
 */
public class Player extends FightOrganism {
    public Player(byte profession) {
        super(profession, OrganismType.PLAYER);
    }

    public Player(long id, byte profession) {
        super(id, profession, OrganismType.PLAYER);
    }
}

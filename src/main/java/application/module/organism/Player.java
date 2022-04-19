package application.module.organism;

import application.guid.IdUtils;

/**
 * @author Luo Yong
 * @date 2022-3-1
 * @Source 1.0
 */
public class Player extends FightOrganism {
    public Player() {
        super(IdUtils.fastSimpleUUIDLong(), OrganismType.PLAYER);
    }

    public Player(long id) {
        super(id, OrganismType.PLAYER);
    }
}

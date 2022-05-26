package application.module.scene.fight.buff.function;

import application.module.organism.FightOrganism;
import application.module.scene.fight.buff.FightOrganismBuff;

/**
 * @author Luo Yong
 * @date 2022-4-6
 * @Source 1.0
 */
public abstract class FightOrganismBuffFunction {

    public boolean tickBuffFunction(FightOrganism from, FightOrganism to, FightOrganismBuff buff) {
        return true;
    }

    public boolean addBuffFunction(FightOrganism from, FightOrganism to, FightOrganismBuff buff) {
        return true;
    }

    public boolean removeBuffFunction(FightOrganism from, FightOrganism to, FightOrganismBuff buff) {
        return true;
    }


}

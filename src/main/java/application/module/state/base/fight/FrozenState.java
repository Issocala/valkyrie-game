package application.module.state.base.fight;

import application.module.state.base.FightOrganismState;

/**
 * @author Luo Yong
 * @date 2022-4-18
 * @Source 1.0
 */
public class FrozenState extends FightState {
    public FrozenState(short id) {
        super(id);
    }

    @Override
    public void enter(FightOrganismState fightOrganismState) {

    }

    @Override
    public void exit(FightOrganismState fightOrganismState) {

    }

}

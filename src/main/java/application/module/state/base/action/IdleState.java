package application.module.state.base.action;

import application.module.state.base.FightOrganismState;
import application.module.state.base.StateType;

/**
 * 活着
 *
 * @author Luo Yong
 * @date 2022-3-1
 * @Source 1.0
 */
public class IdleState extends ActionState {

    public IdleState(short id) {
        super(id);
        addTransition(StateType.ActionType.DEAD_STATE);
    }

    @Override
    public void enter(FightOrganismState fightOrganismState) {

    }

    @Override
    public void exit(FightOrganismState fightOrganismState) {

    }

    @Override
    public boolean isBroadcast() {
        return true;
    }
}

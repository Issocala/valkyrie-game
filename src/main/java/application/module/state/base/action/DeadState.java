package application.module.state.base.action;

import application.module.state.base.FightOrganismState;
import application.module.state.base.StateType;

/**
 * 死亡
 *
 * @author Luo Yong
 * @date 2022-3-1
 * @Source 1.0
 */
public class DeadState extends ActionState {


    public DeadState(short id) {
        super(id);
        addTransition(StateType.ActionType.IDLE_STATE);
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

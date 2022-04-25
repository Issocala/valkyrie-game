package application.module.state.base.movement;

import application.module.state.base.FightOrganismState;
import application.module.state.base.StateType;

/**
 * 停止
 *
 * @author Luo Yong
 * @date 2022-3-1
 * @Source 1.0
 */
public class StopState extends MovementState {


    public StopState(short id) {
        super(id);
        addTransition(StateType.MovementType.MOVE_ID);
    }

    @Override
    public void enter(FightOrganismState fightOrganismState) {

    }

    @Override
    public void exit(FightOrganismState fightOrganismState) {

    }
}

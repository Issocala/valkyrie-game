package application.module.scene.fight.state.base.movement;

import application.module.scene.fight.state.FightStateMgr;
import application.module.scene.fight.state.base.StateType;

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
    public void enter(FightStateMgr fightStateMgr) {

    }

    @Override
    public void exit(FightStateMgr fightStateMgr) {

    }


}

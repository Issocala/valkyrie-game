package application.module.scene.fight.state.base.action;

import application.module.scene.fight.state.FightStateMgr;
import application.module.scene.fight.state.base.StateType;

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
    public void enter(FightStateMgr fightStateMgr) {

    }

    @Override
    public void exit(FightStateMgr fightStateMgr) {

    }


    @Override
    public boolean isBroadcast() {
        return true;
    }
}

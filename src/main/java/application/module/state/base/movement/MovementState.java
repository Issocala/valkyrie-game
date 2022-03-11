package application.module.state.base.movement;

import application.module.state.base.FSMStateBase;

/**
 * @author Luo Yong
 * @date 2022-3-1
 * @Source 1.0
 */
public abstract class MovementState extends FSMStateBase<Long> {

    public MovementState(short id) {
        super(id);
    }

    @Override
    public boolean isBroadcast() {
        return true;
    }
}

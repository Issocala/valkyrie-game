package application.module.scene.fight.state.base.movement;

import application.module.scene.fight.state.base.FSMStateBase;

/**
 * @author Luo Yong
 * @date 2022-3-1
 * @Source 1.0
 */
public abstract class MovementState extends FSMStateBase {

    public MovementState(short id) {
        super(id);
    }

    @Override
    public boolean isBroadcast() {
        return true;
    }
}

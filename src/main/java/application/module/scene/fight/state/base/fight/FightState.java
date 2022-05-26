package application.module.scene.fight.state.base.fight;

import application.module.scene.fight.state.base.FSMStateBase;

/**
 * @author Luo Yong
 * @date 2022-3-1
 * @Source 1.0
 */
public abstract class FightState extends FSMStateBase {

    public FightState(short id) {
        super(id);
    }

    @Override
    public boolean isBroadcast() {
        return true;
    }
}

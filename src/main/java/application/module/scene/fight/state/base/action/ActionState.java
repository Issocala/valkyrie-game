package application.module.scene.fight.state.base.action;

import application.module.scene.fight.state.base.FSMStateBase;

/**
 * @author Luo Yong
 * @date 2022-3-1
 * @Source 1.0
 */
public abstract class ActionState extends FSMStateBase {
    public ActionState(short id) {
        super(id);
    }
}

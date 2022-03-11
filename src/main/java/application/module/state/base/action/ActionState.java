package application.module.state.base.action;

import application.module.state.base.FSMStateBase;

/**
 * @author Luo Yong
 * @date 2022-3-1
 * @Source 1.0
 */
public abstract class ActionState extends FSMStateBase<Long> {
    public ActionState(short id) {
        super(id);
    }
}

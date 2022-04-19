package application.module.state.base.buff;

import application.module.state.base.FSMStateBase;

/**
 * @author Luo Yong
 * @date 2022-3-1
 * @Source 1.0
 */
public abstract class BuffState extends FSMStateBase {

    public BuffState(short id) {
        super(id);
    }

    @Override
    public boolean isBroadcast() {
        return true;
    }
}

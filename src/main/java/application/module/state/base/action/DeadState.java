package application.module.state.base.action;

/**
 * 死亡
 *
 * @author Luo Yong
 * @date 2022-3-1
 * @Source 1.0
 */
public class DeadState extends ActionState {

    public final static short DEAD_STATE = 302;

    public DeadState(short id) {
        super(id);
    }

    @Override
    public void enter(Long owner) {

    }

    @Override
    public void exit(Long owner) {

    }

    @Override
    public boolean isBroadcast() {
        return true;
    }
}

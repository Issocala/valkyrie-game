package application.module.state.base.action;

/**
 * 活着
 *
 * @author Luo Yong
 * @date 2022-3-1
 * @Source 1.0
 */
public class IdleState extends ActionState {

    public final static short IDLE_STATE = 201;

    public IdleState(short id) {
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

package application.module.state.base.movement;

/**
 * 停止
 *
 * @author Luo Yong
 * @date 2022-3-1
 * @Source 1.0
 */
public class StopState extends MovementState {

    public final static short STOP_ID = 102;

    public StopState(short id) {
        super(id);
        addTransition(MoveState.MOVE_ID);
    }

    @Override
    public void enter(Long owner) {

    }

    @Override
    public void exit(Long owner) {

    }
}

package application.module.state.base.movement;

/**
 * 移动
 *
 * @author Luo Yong
 * @date 2022-3-1
 * @Source 1.0
 */
public class MoveState extends MovementState {

    public final static short MOVE_ID = 101;

    public MoveState(short id) {
        super(id);
        addTransition(StopState.STOP_ID);
    }

    @Override
    public void enter(Long owner) {

    }

    @Override
    public void exit(Long owner) {

    }
}

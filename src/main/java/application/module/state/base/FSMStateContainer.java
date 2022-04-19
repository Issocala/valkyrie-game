package application.module.state.base;

import application.module.state.base.action.DeadState;
import application.module.state.base.action.IdleState;
import application.module.state.base.movement.MoveState;
import application.module.state.base.movement.StopState;
import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-2-28
 * @Source 1.0
 */
public class FSMStateContainer {

    private final static Map<Short, FSMStateBase> stateId2StateClassMap = new HashMap<>();

    static {
        // MovementState
        add(StateType.Movement.MOVE_ID, new MoveState(StateType.Movement.MOVE_ID));
        add(StateType.Movement.STOP_ID, new StopState(StateType.Movement.STOP_ID));

        // ActionState
        add(StateType.ActionType.IDLE_STATE, new IdleState(StateType.ActionType.IDLE_STATE));
        add(StateType.ActionType.DEAD_STATE, new DeadState(StateType.ActionType.DEAD_STATE));
    }

    private static void add(short id, FSMStateBase fsmStateBase) {
        stateId2StateClassMap.put(id, fsmStateBase);
    }

    public static FSMStateBase getState(final short id) {
        FSMStateBase fsmStateBase = stateId2StateClassMap.get(id);
        Preconditions.checkNotNull(fsmStateBase, "StateId并不存在！！！");
        return fsmStateBase;
    }
}

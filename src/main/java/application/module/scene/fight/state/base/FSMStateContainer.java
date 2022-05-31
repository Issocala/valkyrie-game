package application.module.scene.fight.state.base;

import application.module.scene.fight.state.base.action.DeadState;
import application.module.scene.fight.state.base.action.IdleState;
import application.module.scene.fight.state.base.fight.*;
import application.module.scene.fight.state.base.movement.MoveState;
import application.module.scene.fight.state.base.movement.StopState;
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
        add(StateType.MovementType.MOVE_ID, new MoveState(StateType.MovementType.MOVE_ID));
        add(StateType.MovementType.STOP_ID, new StopState(StateType.MovementType.STOP_ID));

        // ActionState
        add(StateType.ActionType.IDLE_STATE, new IdleState(StateType.ActionType.IDLE_STATE));
        add(StateType.ActionType.DEAD_STATE, new DeadState(StateType.ActionType.DEAD_STATE));

        //BuffState
        add(StateType.FightType.FROZEN_STATE, new FrozenState(StateType.FightType.FROZEN_STATE));
        add(StateType.FightType.SUPER_ARMOR_STATE, new SuperArmorState(StateType.FightType.SUPER_ARMOR_STATE));
        add(StateType.FightType.IMPRISON_STATE, new ImprisonState(StateType.FightType.IMPRISON_STATE));
        add(StateType.FightType.SPEED_DOWN_STATE, new SpeedDownState(StateType.FightType.SPEED_DOWN_STATE));
        add(StateType.FightType.DIZZINESS_STATE, new DizzinessState(StateType.FightType.DIZZINESS_STATE));
        add(StateType.FightType.SPEED_UP_STATE, new SpeedUpState(StateType.FightType.SPEED_UP_STATE));
        add(StateType.FightType.FRENZY_STATE, new FrenzyState(StateType.FightType.FRENZY_STATE));
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

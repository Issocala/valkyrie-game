package application.module.state.base;

import application.module.fight.skill.data.message.SkillUseState;
import application.module.state.base.action.ActionState;
import application.module.state.base.movement.MovementState;
import application.util.LongId;

import java.util.Objects;

/**
 * 有战斗能力生物的状态管理器
 *
 * @author Luo Yong
 * @date 2022-3-1
 * @Source 1.0
 */
public class FightOrganismState extends LongId {

    public final static short DEFAULT_MOVEMENT_STATE = StateType.Movement.STOP_ID;
    private MovementState currMovementState;

    public final static short DEFAULT_ACTION_STATE = StateType.ActionType.IDLE_STATE;
    private ActionState currActionState;

    public FightOrganismState(long id) {
        super(id);
        setMovementStateDefault();
        setActionStateDefault();
    }

    public boolean changeState(short id) {
        return changeState(FSMStateContainer.getState(id));
    }

    public boolean changeState(final FSMStateBase state) {
        return switch (state) {
            case null -> false;
            case MovementState movementState -> toMovementState(movementState);
            case ActionState actionState -> toActionState(actionState);
            default -> throw new IllegalStateException("Unexpected value: " + state);
        };
    }

    public boolean cancelState(short id) {
        return cancelState(FSMStateContainer.getState(id));
    }

    public boolean cancelState(final FSMStateBase state) {
        if (Objects.isNull(state)) {
            return false;
        }
        switch (state) {
            case MovementState ignored -> cancelMovementState();
            case ActionState ignored -> cancelActionState();
            default -> throw new IllegalStateException("Unexpected value: " + state);
        }
        return true;
    }

    private void cancelActionState() {
        toActionState((ActionState) FSMStateContainer.getState(DEFAULT_ACTION_STATE));
    }

    private void cancelMovementState() {
        toMovementState((MovementState) FSMStateContainer.getState(DEFAULT_MOVEMENT_STATE));
    }

    private boolean toMovementState(MovementState movementState) {
        if (this.currMovementState == movementState || !this.currMovementState.isTransition(movementState.getId())) {
            return false;
        }
        this.currMovementState.exit(this);
        movementState.enter(this);
        this.currMovementState = movementState;
        return true;
    }

    private boolean toActionState(ActionState actionState) {
        if (this.currActionState == actionState || !this.currActionState.isTransition(actionState.getId())) {
            return false;
        }
        this.currActionState.exit(this);
        currActionState.enter(this);
        this.currActionState = actionState;
        return true;
    }

    public void setMovementStateDefault() {
        this.currMovementState = (MovementState) FSMStateContainer.getState(DEFAULT_MOVEMENT_STATE);
    }

    public void setActionStateDefault() {
        this.currActionState = (ActionState) FSMStateContainer.getState(DEFAULT_ACTION_STATE);
    }

    public MovementState getCurrMovementState() {
        return currMovementState;
    }

    public ActionState getCurrActionState() {
        return currActionState;
    }

    //TODO 校验玩家当前状态是否可以释放技能
    public boolean isSkillUse(SkillUseState skillUseState) {
        return true;
    }

}

package application.module.scene.fight.state;

import application.module.organism.FightOrganism;
import application.module.scene.Scene;
import application.module.scene.fight.state.base.FSMStateBase;
import application.module.scene.fight.state.base.FSMStateContainer;
import application.module.scene.fight.state.base.StateType;
import application.module.scene.fight.state.base.action.ActionState;
import application.module.scene.fight.state.base.fight.FightState;
import application.module.scene.fight.state.base.movement.MovementState;
import com.google.protobuf.GeneratedMessageV3;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Luo Yong
 * @date 2022-5-16
 * @Source 1.0
 */
public class FightStateMgr {

    private FightOrganism owner;

    public final static short DEFAULT_MOVEMENT_STATE = StateType.MovementType.STOP_ID;
    private MovementState currMovementState;

    public final static short DEFAULT_ACTION_STATE = StateType.ActionType.IDLE_STATE;
    private ActionState currActionState;

    private final Set<FightState> fightStateSet = new HashSet<>();


    public FightStateMgr() {
        setMovementStateDefault();
        setActionStateDefault();
    }

    public boolean changeState(short id, Scene scene) {
        return changeState(FSMStateContainer.getState(id), scene);
    }

    public boolean changeState(final FSMStateBase state, Scene scene) {
        if (Objects.isNull(state)) {
            return false;
        }
        if (state instanceof MovementState movementState) {
            return toMovementState(movementState);
        } else if (state instanceof ActionState actionState) {
            return toActionState(actionState, scene);
        } else if (state instanceof FightState fightState) {
            return toFightState(fightState, scene);
        }
        return false;
    }

    public boolean isState(short id) {
        return isState(FSMStateContainer.getState(id));
    }

    public boolean isState(final FSMStateBase state) {
        if (Objects.isNull(state)) {
            return false;
        }
        if (state instanceof MovementState movementState) {
            return movementState == this.currMovementState;
        } else if (state instanceof ActionState actionState) {
            return actionState == this.currActionState;
        } else if (state instanceof FightState fightState) {
            return this.getFightStateSet().contains(fightState);
        }
        return false;
    }


    public boolean cancelState(short id, Scene scene) {
        return cancelState(FSMStateContainer.getState(id), scene);
    }

    public boolean cancelState(final FSMStateBase state, Scene scene) {
        if (Objects.isNull(state)) {
            return false;
        }
        if (state instanceof MovementState) {
            cancelMovementState();
        } else if (state instanceof ActionState) {
            cancelActionState(scene);
        } else if (state instanceof FightState fightState) {
            cancelFightState(fightState, scene);
        }
        return true;
    }

    private void cancelFightState(FightState fightState, Scene scene) {
        if (this.fightStateSet.remove(fightState)) {
            fightState.exit(this);
            sendToAllClient(scene, StateProtocols.REMOVE_STATE, StateProtocolBuilder.getSc10062(getId(), fightState.getId()));
        }
    }

    private void cancelActionState(Scene scene) {
        toActionState((ActionState) FSMStateContainer.getState(DEFAULT_ACTION_STATE), scene);
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

    private boolean toActionState(ActionState actionState, Scene scene) {
        if (this.currActionState == actionState || !this.currActionState.isTransition(actionState.getId())) {
            return false;
        }
        this.currActionState.exit(this);
        currActionState.enter(this);
        this.currActionState = actionState;
        sendToAllClient(scene, StateProtocols.ADD_STATE, StateProtocolBuilder.getSc10061(getId(), actionState.getId()));
        return true;
    }

    private boolean toFightState(FightState fightState, Scene scene) {
        if (!this.fightStateSet.contains(fightState)) {
            fightState.enter(this);
            this.fightStateSet.add(fightState);
            sendToAllClient(scene, StateProtocols.ADD_STATE, StateProtocolBuilder.getSc10061(getId(), fightState.getId()));
        }
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

    public Set<FightState> getFightStateSet() {
        return fightStateSet;
    }

    //TODO 校验玩家当前状态是否可以释放技能
    public boolean isSkillUse() {
        if (StateType.ActionType.validNoUseSkill(this.currActionState)) {
            return false;
        }
        return !StateType.FightType.validNoUseSkill(this.fightStateSet);
    }

    //TODO 校验玩家当前状态是否可以移动
    public boolean isMoveUse() {
        if (StateType.ActionType.validNoUseSkill(this.currActionState)) {
            return false;
        }
        return !StateType.FightType.validNoUseSkill(this.fightStateSet);
    }

    public FightOrganism getOwner() {
        return owner;
    }

    public void setOwner(FightOrganism owner) {
        this.owner = owner;
    }

    private void sendToAllClient(Scene scene, int protoId, GeneratedMessageV3 message) {
        scene.getPlayerSceneMgr().sendToAllClient(scene, protoId, message);
    }

    private long getId() {
        return owner.getId();
    }

    private void resetState() {
        this.setActionStateDefault();
        this.setMovementStateDefault();
    }

}

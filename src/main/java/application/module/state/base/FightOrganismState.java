package application.module.state.base;

import akka.actor.ActorRef;
import application.module.fight.skill.data.message.SkillUseState;
import application.module.scene.operate.AoiSendMessageToClient;
import application.module.state.StateProtocolBuilder;
import application.module.state.StateProtocols;
import application.module.state.base.action.ActionState;
import application.module.state.base.fight.FightState;
import application.module.state.base.movement.MovementState;
import application.util.LongId;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 有战斗能力生物的状态管理器
 *
 * @author Luo Yong
 * @date 2022-3-1
 * @Source 1.0
 */
public class FightOrganismState extends LongId {

    public final static short DEFAULT_MOVEMENT_STATE = StateType.MovementType.STOP_ID;
    private MovementState currMovementState;

    public final static short DEFAULT_ACTION_STATE = StateType.ActionType.IDLE_STATE;
    private ActionState currActionState;

    private final Set<FightState> fightStateSet = new HashSet<>();

    public FightOrganismState(long id) {
        super(id);
        setMovementStateDefault();
        setActionStateDefault();
    }

    public boolean changeState(short id, ActorRef scene) {
        return changeState(FSMStateContainer.getState(id), scene);
    }

    public boolean changeState(final FSMStateBase state, ActorRef scene) {
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


    public boolean cancelState(short id, ActorRef scene) {
        return cancelState(FSMStateContainer.getState(id), scene);
    }

    public boolean cancelState(final FSMStateBase state, ActorRef scene) {
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

    private void cancelFightState(FightState fightState, ActorRef scene) {
        if (this.fightStateSet.remove(fightState)) {
            fightState.exit(this);
            scene.tell(new AoiSendMessageToClient(StateProtocols.REMOVE_STATE,
                    StateProtocolBuilder.getSc10062(getId(), fightState.getId()), getId()), ActorRef.noSender());
        }
    }

    private void cancelActionState(ActorRef scene) {
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

    private boolean toActionState(ActionState actionState, ActorRef scene) {
        if (this.currActionState == actionState || !this.currActionState.isTransition(actionState.getId())) {
            return false;
        }
        this.currActionState.exit(this);
        currActionState.enter(this);
        this.currActionState = actionState;
        scene.tell(new AoiSendMessageToClient(StateProtocols.ADD_STATE,
                StateProtocolBuilder.getSc10061(getId(), actionState.getId()), getId()), ActorRef.noSender());
        return true;
    }

    private boolean toFightState(FightState fightState, ActorRef scene) {
        if (!this.fightStateSet.contains(fightState)) {
            fightState.enter(this);
            this.fightStateSet.add(fightState);
            scene.tell(new AoiSendMessageToClient(StateProtocols.ADD_STATE,
                    StateProtocolBuilder.getSc10061(getId(), fightState.getId()), getId()), ActorRef.noSender());
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
    public boolean isSkillUse(SkillUseState skillUseState) {
        if (StateType.ActionType.validNoUseSkill(this.currActionState)) {
            return false;
        }
        return !StateType.FightType.validNoUseSkill(this.fightStateSet);
    }
}

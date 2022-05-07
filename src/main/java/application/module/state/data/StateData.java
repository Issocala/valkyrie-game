package application.module.state.data;

import akka.actor.ActorRef;
import akka.actor.Props;
import application.client.Client.SendToClientJ;
import application.module.common.CommonProtocolBuilder;
import application.module.common.CommonProtocols;
import application.module.fight.skill.data.message.SkillUseState;
import application.module.player.data.message.event.PlayerLogin;
import application.module.player.operate.PlayerLoginDbReturn;
import application.module.scene.operate.event.CreateOrganismEntityAfter;
import application.module.scene.operate.event.CreatePlayerEntitiesAfter;
import application.module.scene.operate.event.PlayerReceiveAfter;
import application.module.state.StateProtocolBuilder;
import application.module.state.StateProtocols;
import application.module.state.base.FightOrganismState;
import application.module.state.data.domain.StateEntity;
import application.module.state.operate.OrganismCancelState;
import application.module.state.operate.OrganismChangeState;
import application.util.ApplicationErrorCode;
import application.util.CommonOperateTypeInfo;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.cache.DataInit;
import com.cala.orm.message.DBReturnMessage;
import com.cala.orm.message.DataBase;
import protocol.Skill;

import java.util.*;

/**
 * @author Luo Yong
 * @date 2022-3-7
 * @Source 1.0
 */
public class StateData extends AbstractDataCacheManager<StateEntity> {

    public static Props create() {
        return Props.create(StateData.class, StateData::new).withDispatcher(DATA_AND_DB_DISPATCHER);
    }

    private final Map<Long, FightOrganismState> fightOrganismStateMap = new HashMap<>();

    private StateData() {
    }

    @Override
    protected void dataInit(DataInit dataInit) {

    }

    @Override
    public void receive(DataBase dataBase) {
        switch (dataBase) {
            case SkillUseState skillUseState -> skillUseState(skillUseState);
            case OrganismChangeState organismChangeState -> organismChangeState(organismChangeState);
            case OrganismCancelState organismCancelState -> organismCancelState(organismCancelState);
            case PlayerLogin playerLogin -> playerLogin(playerLogin);
            case CreatePlayerEntitiesAfter createPlayerEntitiesAfter -> createPlayerEntitiesAfter(createPlayerEntitiesAfter);
            case CreateOrganismEntityAfter createOrganismEntityAfter -> createOrganismEntityAfter(createOrganismEntityAfter);
            case PlayerReceiveAfter playerReceiveAfter -> playerReceiveAfter(playerReceiveAfter);
            default -> throw new IllegalStateException("Unexpected value: " + dataBase);
        }
    }

    private void playerReceiveAfter(PlayerReceiveAfter playerReceiveAfter) {
        long playerId = playerReceiveAfter.playerId();
        FightOrganismState fightOrganismState = fightOrganismStateMap.get(playerId);
        resetState(fightOrganismState);
        playerReceiveAfter.clientMap().forEach((id, client) -> {
            if (Objects.nonNull(client)) {
                client.tell(new SendToClientJ(StateProtocols.ADD_STATE,
                        StateProtocolBuilder.getSc10061(fightOrganismState.getId(), fightOrganismState.getCurrActionState().getId())), self());
            }
        });
    }

    private void resetState(FightOrganismState fightOrganismState) {
        fightOrganismState.setActionStateDefault();
        fightOrganismState.setMovementStateDefault();
    }

    private void createOrganismEntityAfter(CreateOrganismEntityAfter createOrganismEntityAfter) {
        long organismId = createOrganismEntityAfter.organism().getId();
        FightOrganismState fightOrganismState = fightOrganismStateMap.get(organismId);
        if (Objects.isNull(fightOrganismState)) {
            fightOrganismState = new FightOrganismState(organismId);
            fightOrganismStateMap.put(organismId, fightOrganismState);
        }

        FightOrganismState finalFightOrganismState = fightOrganismState;
        createOrganismEntityAfter.clients().forEach(client -> {
            if (Objects.nonNull(client)) {
                client.tell(new SendToClientJ(StateProtocols.ADD_STATE,
                        StateProtocolBuilder.getSc10061(finalFightOrganismState.getId(), finalFightOrganismState.getCurrActionState().getId())), self());
            }
        });

    }

    private void createPlayerEntitiesAfter(CreatePlayerEntitiesAfter createPlayerEntitiesAfter) {
        long playerId = createPlayerEntitiesAfter.playerId();
        FightOrganismState fightOrganismState = fightOrganismStateMap.get(playerId);
        if (Objects.isNull(fightOrganismState)) {
            fightOrganismState = new FightOrganismState(playerId);
            fightOrganismStateMap.put(playerId, fightOrganismState);
        }

        FightOrganismState finalFightOrganismState = fightOrganismState;
        List<FightOrganismState> fightOrganismStates = new ArrayList<>();
        createPlayerEntitiesAfter.clientMap().forEach((id, client) -> {
            if (id != playerId) {
                client.tell(new SendToClientJ(StateProtocols.ADD_STATE,
                        StateProtocolBuilder.getSc10061(finalFightOrganismState.getId(), finalFightOrganismState.getCurrActionState().getId())), self());
            }
            FightOrganismState fightOrganismState1 = fightOrganismStateMap.get(id);
            if (Objects.nonNull(fightOrganismState1)) {
                fightOrganismStates.add(fightOrganismState1);
            }
        });
        createPlayerEntitiesAfter.organisms().forEach(organism -> {
            FightOrganismState fightOrganismState1 = fightOrganismStateMap.get(organism.getId());
            if (Objects.nonNull(fightOrganismState1)) {
                fightOrganismStates.add(fightOrganismState1);
            }
        });
        ActorRef client = createPlayerEntitiesAfter.clientMap().get(playerId);
        fightOrganismStates.forEach(fightOrganismState1 -> {
            if (Objects.nonNull(client)) {
                client.tell(new SendToClientJ(StateProtocols.ADD_STATE,
                        StateProtocolBuilder.getSc10061(fightOrganismState1.getId(), fightOrganismState1.getCurrActionState().getId())), self());
            }
        });
    }


    private void organismCancelState(OrganismCancelState organismCancelState) {
        long organismId = organismCancelState.organismId();
        FightOrganismState state = fightOrganismStateMap.get(organismId);
        state.cancelState(organismCancelState.stateType(), organismCancelState.scene());
    }

    private void organismChangeState(OrganismChangeState organismChangeState) {
        long organismId = organismChangeState.organismId();
        FightOrganismState state = fightOrganismStateMap.get(organismId);
        state.changeState(organismChangeState.stateType(), organismChangeState.scene());
    }

    @Override
    protected void afterDbReturnMessage(DBReturnMessage dbReturnMessage) {
        switch (dbReturnMessage.operateType()) {
            case PlayerLoginDbReturn playerLoginDbReturn -> playerLoginDbReturn(dbReturnMessage, playerLoginDbReturn);
            default -> throw new IllegalStateException("Unexpected value: " + dbReturnMessage.operateType());
        }
    }

    private void playerLoginDbReturn(DBReturnMessage dbReturnMessage, PlayerLoginDbReturn playerLoginDbReturn) {

    }

    private void playerLogin(PlayerLogin playerLogin) {
        long playerId = playerLogin.playerInfo().id();
        FightOrganismState fightOrganismState = fightOrganismStateMap.get(playerId);
        if (Objects.isNull(fightOrganismState)) {
            fightOrganismState = new FightOrganismState(playerId);
            fightOrganismStateMap.put(playerId, fightOrganismState);
        }
        resetState(fightOrganismState);
    }

    private void skillUseState(SkillUseState skillUseState) {
        CommonOperateTypeInfo commonOperateTypeInfo = (CommonOperateTypeInfo) skillUseState.operateTypeInfo();
        long playerId = commonOperateTypeInfo.r().uID();
        Skill.CS10052 cs10052 = (Skill.CS10052) commonOperateTypeInfo.message();
        FightOrganismState fightOrganismState = getFightOrganismStateMgr(playerId);
        if (Objects.nonNull(fightOrganismState)) {
            if (fightOrganismState.isSkillUse(skillUseState)) {
                sender().tell(skillUseState, self());
            } else {
                commonOperateTypeInfo.r().client().tell(new SendToClientJ(CommonProtocols.APPLICATION_ERROR,
                        CommonProtocolBuilder.getSc10080(ApplicationErrorCode.USE_SKILL_STATE)), self());
            }
        }
    }

    @Override
    public boolean validDomain(AbstractEntityBase abstractEntityBase) {
        return abstractEntityBase.getClass() == StateEntity.class;
    }

    public FightOrganismState getFightOrganismStateMgr(long fightOrganismId) {
        return fightOrganismStateMap.get(fightOrganismId);
    }

    public void addFightOrganismStateMgr(FightOrganismState fightOrganismState) {
        fightOrganismStateMap.put(fightOrganismState.getId(), fightOrganismState);
    }
}

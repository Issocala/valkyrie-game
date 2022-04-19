package application.module.state.data;

import akka.actor.Props;
import application.client.Client.SendToClientJ;
import application.module.common.CommonProtocolBuilder;
import application.module.common.CommonProtocols;
import application.module.fight.skill.data.message.SkillUseState;
import application.module.player.data.message.event.PlayerLogin;
import application.module.player.operate.PlayerLoginDbReturn;
import application.module.state.base.FightOrganismState;
import application.module.state.data.domain.StateEntity;
import application.util.ApplicationErrorCode;
import application.util.CommonOperateTypeInfo;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.cache.DataInit;
import com.cala.orm.message.DBReturnMessage;
import com.cala.orm.message.DataBase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
            case PlayerLogin playerLogin -> playerLogin(playerLogin);
            default -> throw new IllegalStateException("Unexpected value: " + dataBase);
        }
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
        if (!fightOrganismStateMap.containsKey(playerId)) {
            fightOrganismStateMap.put(playerId, new FightOrganismState(playerId));
        }
    }

    private void skillUseState(SkillUseState skillUseState) {
        CommonOperateTypeInfo commonOperateTypeInfo = (CommonOperateTypeInfo) skillUseState.operateTypeInfo();
        long playerId = commonOperateTypeInfo.r().uID();
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

package application.module.state.data;

import akka.actor.Props;
import application.module.state.base.FightOrganismState;
import application.module.state.data.domain.StateEntity;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.message.DBReturnMessage;
import com.cala.orm.message.DataBase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-3-7
 * @Source 1.0
 */
public class StateData extends AbstractDataCacheManager<StateEntity> {

    public static Props create() {
        return Props.create(StateData.class, StateData::new).withDispatcher(DATA_AND_DB_DISPATCHER);
    }

    private final Map<Long, FightOrganismState> fightOrganismId2Mgr = new HashMap<>();

    private StateData() {
    }

    @Override
    public void dbReturnMessage(DBReturnMessage dbReturnMessage) {

    }

    @Override
    public void receive(DataBase dataBase) {

    }

    @Override
    public boolean validDomain(AbstractEntityBase abstractEntityBase) {
        return abstractEntityBase.getClass() == StateEntity.class;
    }

//    public FightOrganismState getFightOrganismStateMgr(long fightOrganismId) {
//        return fightOrganismId2Mgr.get(fightOrganismId);
//    }
//
//    public void addFightOrganismStateMgr(FightOrganismState fightOrganismState) {
//        fightOrganismId2Mgr.put(fightOrganismState.getId(), fightOrganismState);
//    }
}

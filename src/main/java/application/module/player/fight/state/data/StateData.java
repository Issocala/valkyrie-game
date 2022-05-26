package application.module.player.fight.state.data;

import akka.actor.Props;
import application.module.player.fight.state.data.entity.StateEntity;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.cache.DataInit;
import com.cala.orm.message.DBReturnMessage;
import com.cala.orm.message.DataBase;

/**
 * @author Luo Yong
 * @date 2022-3-7
 * @Source 1.0
 */
public class StateData extends AbstractDataCacheManager<StateEntity> {

    public static Props create() {
        return Props.create(StateData.class, StateData::new).withDispatcher(DATA_AND_DB_DISPATCHER);
    }


    private StateData() {
    }

    @Override
    protected void dataInit(DataInit dataInit) {

    }

    @Override
    public void receive(DataBase dataBase) {

    }

    @Override
    public boolean validDomain(AbstractEntityBase abstractEntityBase) {
        return abstractEntityBase.getClass() == StateEntity.class;
    }

}

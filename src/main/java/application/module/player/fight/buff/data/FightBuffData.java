package application.module.player.fight.buff.data;

import akka.actor.Props;
import application.module.player.fight.buff.data.entity.FightBuffEntity;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.cache.DataInit;
import com.cala.orm.message.DataBase;

/**
 * @author Luo Yong
 * @date 2022-4-1
 * @Source 1.0
 */
public class FightBuffData extends AbstractDataCacheManager<FightBuffEntity> {

    public static Props create() {
        return Props.create(FightBuffData.class, FightBuffData::new).withDispatcher(DATA_AND_DB_DISPATCHER);
    }


    @Override
    protected void dataInit(DataInit dataInit) {

    }

    @Override
    public void receive(DataBase dataBase) {

    }

    @Override
    public boolean validDomain(AbstractEntityBase abstractEntityBase) {
        return FightBuffEntity.class == abstractEntityBase.getClass();
    }
}

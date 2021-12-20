package application.module.player.base.data;

import akka.actor.Props;
import application.module.player.base.domain.PlayerEntity;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.message.DataBase;

/**
 * @author Luo Yong
 * @date 2021-11-15
 * @Source 1.0
 */
public class PlayerEntityData extends AbstractDataCacheManager<PlayerEntity> {

    public static Props create() {
        return Props.create(PlayerEntityData.class, PlayerEntityData::new).withDispatcher(DB_DISPATCHER);
    }

    private PlayerEntityData() {
    }

    @Override
    public void receive(DataBase dataBase) {

    }

    @Override
    public boolean validDomain(AbstractEntityBase v) {
        return v.getClass() == PlayerEntity.class;
    }

}

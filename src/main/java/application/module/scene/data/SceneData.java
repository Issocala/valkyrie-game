package application.module.scene.data;

import akka.actor.Props;
import application.module.scene.data.domain.SceneEntity;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.cache.DataInit;
import com.cala.orm.message.DataBase;

/**
 * @author Luo Yong
 * @date 2022-3-3
 * @Source 1.0
 */
public class SceneData extends AbstractDataCacheManager<SceneEntity> {

    public static Props create() {
        return Props.create(SceneData.class, SceneData::new).withDispatcher(DATA_AND_DB_DISPATCHER);
    }

    private SceneData() {
    }

    @Override
    protected void dataInit(DataInit dataInit) {

    }

    @Override
    public void receive(DataBase dataBase) {
    }


    @Override
    public boolean validDomain(AbstractEntityBase abstractEntityBase) {
        return abstractEntityBase.getClass() == SceneEntity.class;
    }
}

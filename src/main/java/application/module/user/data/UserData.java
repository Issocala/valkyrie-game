package application.module.user.data;

import akka.actor.Props;
import application.module.user.data.domain.User;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.message.DataBase;

/**
 * @author Luo Yong
 * @date 2022-1-7
 * @Source 1.0
 */
public class UserData extends AbstractDataCacheManager<User> {

    private UserData() {
    }

    public static Props create() {
        return Props.create(UserData.class, UserData::new).withDispatcher(DB_DISPATCHER);
    }

    @Override
    public void receive(DataBase dataBase) {

    }

    @Override
    public boolean validDomain(AbstractEntityBase abstractEntityBase) {
        return abstractEntityBase.getClass() == User.class;
    }
}

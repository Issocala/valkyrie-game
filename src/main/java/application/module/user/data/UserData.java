package application.module.user.data;

import akka.actor.Props;
import application.module.user.data.domain.User;
import application.module.user.data.message.UserGetByAccount;
import application.module.user.data.message.UserInsertByAccount;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.cache.DataInit;
import com.cala.orm.db.message.DbInsert;
import com.cala.orm.db.message.DbQueryOneBySql;
import com.cala.orm.message.DataBase;

/**
 * @author Luo Yong
 * @date 2022-1-7
 * @Source 1.0
 */
public class UserData extends AbstractDataCacheManager<User> {

    private UserData() {
    }

    @Override
    protected void dataInit(DataInit dataInit) {

    }

    public static Props create() {
        return Props.create(UserData.class, UserData::new).withDispatcher(DATA_AND_DB_DISPATCHER);
    }

    @Override
    public void receive(DataBase dataBase) {
        switch (dataBase) {
            case UserGetByAccount userGetByAccount -> userGetByAccount(userGetByAccount);
            case UserInsertByAccount userInsertByAccount -> userInsertByAccount(userInsertByAccount);
            default -> throw new IllegalStateException("Unexpected value: " + dataBase);
        }
    }

    private void userInsertByAccount(UserInsertByAccount userInsertByAccount) {
        var user = (User) userInsertByAccount.abstractEntityBase();
        getDbManager().tell(new DbInsert(userInsertByAccount.ref(), user, userInsertByAccount.operateType(), true), self());
    }

    private void userGetByAccount(UserGetByAccount userGetByAccount) {
        var user = (User) userGetByAccount.abstractEntityBase();
        String sql = "select * from user where account = \"" + user.getAccount() + "\"";
        getDbManager().tell(new DbQueryOneBySql(userGetByAccount.ref(), sql, User.class, userGetByAccount.operateType()), self());
    }

    @Override
    public boolean validDomain(AbstractEntityBase abstractEntityBase) {
        return abstractEntityBase.getClass() == User.class;
    }
}

package application.module.user.data;

import akka.actor.Props;
import application.module.user.UserDataMessage;
import application.module.user.data.domain.User;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.message.DBReturnMessage;
import com.cala.orm.message.DataBase;
import com.cala.orm.message.DataReturnMessage;
import com.cala.orm.util.DbConnection;
import com.cala.orm.util.DbHelper;
import com.cala.orm.util.RuntimeResult;

import java.sql.SQLException;
import java.util.Objects;

/**
 * @author Luo Yong
 * @date 2022-1-7
 * @Source 1.0
 */
public class UserData extends AbstractDataCacheManager<User> {

    private UserData() {
    }

    @Override
    public void dbReturnMessage(DBReturnMessage dbReturnMessage) {

    }

    public static Props create() {
        return Props.create(UserData.class, UserData::new).withDispatcher(DATA_AND_DB_DISPATCHER);
    }

    @Override
    public void receive(DataBase dataBase) {
        switch (dataBase) {
            case UserDataMessage.UserGetByAccount userGetByAccount -> userGetByAccount(userGetByAccount);
            default -> throw new IllegalStateException("Unexpected value: " + dataBase);
        }
    }

    private void userGetByAccount(UserDataMessage.UserGetByAccount userGetByAccount) {
        var user = (User) userGetByAccount.abstractEntityBase();
        String sql = "select * from user where account = ?";
        try {
            user = DbHelper.queryOne(DbConnection.getUserConnection(), sql, User.class, user.getAccount());
            if (Objects.isNull(user)) {
                userGetByAccount.ref().tell(new DataReturnMessage(RuntimeResult.runtimeError(), null, userGetByAccount.operateType()), sender());
                return;
            }
            userGetByAccount.ref().tell(new DataReturnMessage(RuntimeResult.ok(), user, userGetByAccount.operateType()), sender());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean validDomain(AbstractEntityBase abstractEntityBase) {
        return abstractEntityBase.getClass() == User.class;
    }
}

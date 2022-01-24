package application.module.user.data;

import akka.actor.Props;
import application.module.user.data.domain.User;
import application.module.user.data.domain.UserDataMessage;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.message.DataBase;
import com.cala.orm.message.DataReturnMessage;
import com.cala.orm.message.MessageAndReply;
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

    public static Props create() {
        return Props.create(UserData.class, UserData::new).withDispatcher(DB_DISPATCHER);
    }

    @Override
    public void receive(DataBase dataBase) {
        switch (dataBase) {
            case UserDataMessage.UserGetByAccount userGetByAccount -> userGetByAccount(userGetByAccount);
            default -> throw new IllegalStateException("Unexpected value: " + dataBase);
        }
    }

    private void userGetByAccount(UserDataMessage.UserGetByAccount userGetByAccount) {
        MessageAndReply messageAndReply = userGetByAccount.messageAndReply();
        var user = (User) messageAndReply.abstractEntityBase();
        String sql = "select * from user where account = ?";
        try {
            user = DbHelper.queryOne(DbConnection.getUserConnection(), sql, User.class, user.getAccount());
            if (Objects.isNull(user)) {
                messageAndReply.ref().tell(new DataReturnMessage(RuntimeResult.runtimeError(), null, messageAndReply.operateType()), sender());
                return;
            }
            messageAndReply.ref().tell(new DataReturnMessage(RuntimeResult.ok(), user, messageAndReply.operateType()), sender());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean validDomain(AbstractEntityBase abstractEntityBase) {
        return abstractEntityBase.getClass() == User.class;
    }
}

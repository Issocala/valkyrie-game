package application.module.user;

import com.cala.orm.message.DataBase;
import com.cala.orm.message.MessageAndReply;

/**
 * @author Luo Yong
 * @date 2022-1-20
 * @Source 1.0
 */
public class UserDataMessage {

    public static record UserGetByAccount(MessageAndReply messageAndReply) implements DataBase {
    }

}
package application.module.player.base.data.domain;

import com.cala.orm.message.DataBase;
import com.cala.orm.message.MessageAndReply;

/**
 * @author Luo Yong
 * @date 2022-1-11
 * @Source 1.0
 */
public class PlayerDataMessage {

    public static record PlayerByUserId(MessageAndReply messageAndReply) implements DataBase {
    }

}

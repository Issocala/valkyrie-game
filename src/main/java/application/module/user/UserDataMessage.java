package application.module.user;

import akka.actor.ActorRef;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.message.DataBase;
import com.cala.orm.message.OperateType;

/**
 * @author Luo Yong
 * @date 2022-1-20
 * @Source 1.0
 */
public class UserDataMessage {

    public static record UserGetByAccount(ActorRef ref, AbstractEntityBase abstractEntityBase,
                                          OperateType operateType) implements DataBase {
    }

}
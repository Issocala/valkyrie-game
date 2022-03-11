package application.module.player;

import akka.actor.ActorRef;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.message.DataBase;
import com.cala.orm.message.OperateType;

/**
 * @author Luo Yong
 * @date 2022-1-11
 * @Source 1.0
 */
public class PlayerDataMessage {

    public static record PlayerByUserId(ActorRef ref, AbstractEntityBase abstractEntityBase,
                                        OperateType operateType) implements DataBase {
    }

    public static record PlayerByUserIdAndProfession(ActorRef ref, AbstractEntityBase abstractEntityBase,
                                                     OperateType operateType) implements DataBase {
    }

}

package application.module.user.data.message;

import akka.actor.ActorRef;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.message.DataBase;
import com.cala.orm.message.OperateType;

/**
 * @author Luo Yong
 * @date 2022-3-20
 * @Source 1.0
 */
public record UserInsertByAccount(ActorRef ref, AbstractEntityBase abstractEntityBase,
                                  OperateType operateType) implements DataBase {
}

package application.util;

import akka.actor.ActorRef;
import com.cala.orm.message.OperateType;

/**
 * @author Luo Yong
 * @date 2022-3-4
 * @Source 1.0
 */
public record DataMessageAndReply(ActorRef ref, OperateType operateType) {
}

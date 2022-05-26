package application.util;

import akka.actor.ActorRef;
import com.google.protobuf.GeneratedMessageV3;

/**
 * @author Luo Yong
 * @date 2022-5-24
 * @Source 1.0
 */
public class MessageUtil {

    public static void sendClient(ActorRef client, int pId, GeneratedMessageV3 v3, ActorRef sender) {
        client.tell(new application.client.Client.SendToClientJ(pId, v3), sender);
    }

}

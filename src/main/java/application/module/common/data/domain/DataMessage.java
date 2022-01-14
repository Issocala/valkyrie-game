package application.module.common.data.domain;

import akka.actor.ActorRef;

/**
 * @author Luo Yong
 * @date 2022-1-14
 * @Source 1.0
 */
public class DataMessage {
    public static record RequestData(Class<?> clazz) {
    }

    public static record DataResult(Class<?> clazz, ActorRef actorRef) {
    }
}

package application.module.common.data.entity;

import akka.actor.ActorRef;

import java.util.Map;

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

    public static record RequestAllData() {
    }

    public static record AllDataResult(Map<Class<?>, ActorRef> map) {
    }
}

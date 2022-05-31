package application.module.player.operate;

import akka.actor.ActorRef;

import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-5-16
 * @Source 1.0
 */
public record PlayerInit(ActorRef client, Map<Class<?>, ActorRef> dataMap, Map<Long, ActorRef> playerActorMap) {
}

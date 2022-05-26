package application.module.player.operate;

import akka.actor.ActorRef;

/**
 * @author Luo Yong
 * @date 2022-5-18
 * @Source 1.0
 */
public record CheckPlayerInitFinal(ActorRef client) {
}

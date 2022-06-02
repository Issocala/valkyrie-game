package application.module.player.operate;

import akka.actor.ActorRef;

/**
 * @author Luo Yong
 * @date 2022-6-1
 * @Source 1.0
 */
public record PlayerAddPlayerActor(long playerId, ActorRef playerActor) {
}

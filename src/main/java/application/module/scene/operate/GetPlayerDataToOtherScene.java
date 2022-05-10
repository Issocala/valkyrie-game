package application.module.scene.operate;

import akka.actor.ActorRef;

/**
 * @author Luo Yong
 * @date 2022-4-24
 * @Source 1.0
 */
public record GetPlayerDataToOtherScene(ActorRef client, long playerId, int sceneId, ActorRef scene) {
}

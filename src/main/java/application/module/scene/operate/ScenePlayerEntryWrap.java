package application.module.scene.operate;

import akka.actor.ActorRef;
import application.module.player.data.domain.PlayerInfo;

/**
 * @author Luo Yong
 * @date 2022-4-24
 * @Source 1.0
 */
public record ScenePlayerEntryWrap(ActorRef client, PlayerInfo playerInfo) {
}

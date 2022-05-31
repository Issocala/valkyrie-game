package application.module.player.operate;

import akka.actor.ActorRef;
import application.module.player.util.PlayerCreateEntityInfo;

/**
 *
 * 发送自己的创建实体时候的一些数据，以及获取目标的对应数据
 * info : 把这个数据发给自己
 * sendClient : 需要把自己的创建实体需要的数据发送过去
 * @author Luo Yong
 * @date 2022-5-31
 * @Source 1.0
 */
public record PlayerSendMyselfAndGetTarget(PlayerCreateEntityInfo info, ActorRef sendClient) {

}

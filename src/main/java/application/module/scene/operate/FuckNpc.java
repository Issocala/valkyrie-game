package application.module.scene.operate;

import akka.actor.ActorRef;
import com.cala.orm.message.DataBase;
import mobius.modular.client.Client;
import protocol.Scene;

/**
 * @author Luo Yong
 * @date 2022-5-7
 * @Source 1.0
 */
public record FuckNpc(Client.ReceivedFromClient r, Scene.CS10311 cs10311, ActorRef buffData, ActorRef attribute) implements DataBase {
}

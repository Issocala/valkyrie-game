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
public record PickUpItem(Client.ReceivedFromClient r, Scene.CS10310 cs10310, ActorRef attributeData) implements DataBase {
}

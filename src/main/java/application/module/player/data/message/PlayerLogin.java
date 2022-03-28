package application.module.player.data.message;


import com.cala.orm.message.DataBase;
import com.cala.orm.message.Event;
import mobius.modular.client.Client;

/**
 * @author Luo Yong
 * @date 2022-3-21
 * @Source 1.0
 */
public record PlayerLogin(Client.ReceivedFromClient r, long playerId) implements DataBase, Event {
}

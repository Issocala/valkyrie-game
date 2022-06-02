package application.module.player.data.message.event;

import com.cala.orm.message.DataBase;
import com.cala.orm.message.Event;
import mobius.modular.client.Client;

/**
 * @author Luo Yong
 * @date 2022-4-18
 * @Source 1.0
 */
public record PlayerLogout(long playerId) implements DataBase, Event {
}

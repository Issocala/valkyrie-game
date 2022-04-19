package application.module.scene.operate.event;

import com.cala.orm.message.DataBase;
import com.cala.orm.message.Event;
import mobius.modular.client.Client;

/**
 * @author Luo Yong
 * @date 2022-4-13
 * @Source 1.0
 */
public record CreateOrganismEntity(Client.ReceivedFromClient r, long organismId, byte organismType) implements DataBase, Event {
}

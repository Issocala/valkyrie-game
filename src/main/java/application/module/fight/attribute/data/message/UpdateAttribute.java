package application.module.fight.attribute.data.message;

import application.util.UpdateAttributeObject;
import com.cala.orm.message.DataBase;
import mobius.modular.client.Client;

/**
 * @author Luo Yong
 * @date 2022-3-9
 * @Source 1.0
 */
public record UpdateAttribute(long playerId, Short type, UpdateAttributeObject<?> o, Client.ReceivedFromClient r) implements DataBase {
}

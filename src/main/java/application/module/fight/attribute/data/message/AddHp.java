package application.module.fight.attribute.data.message;

import com.cala.orm.message.DataBase;
import mobius.modular.client.Client;

/**
 * @author Luo Yong
 * @date 2022-4-11
 * @Source 1.0
 */
public record AddHp(long organismId, Client.ReceivedFromClient r, long hp) implements DataBase {
}

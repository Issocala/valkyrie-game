package application.module.fight.buff.data.message;

import com.cala.orm.message.DataBase;
import mobius.modular.client.Client;

/**
 * @author Luo Yong
 * @date 2022-4-6
 * @Source 1.0
 */
public record RemoveBuff(Client.ReceivedFromClient r, int buffTemplateId, long fromId, long toId, long duration) implements DataBase {
}

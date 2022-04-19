package application.module.fight.buff.data.message;

import com.cala.orm.message.DataBase;
import mobius.modular.client.Client;

/**
 * @author Luo Yong
 * @date 2022-4-6
 * @Source 1.0
 */
public record AddBuff(Client.ReceivedFromClient r, int buffTemplateId, long fromId, long toId,
                      long duration) implements DataBase {

    public AddBuff(Client.ReceivedFromClient r, int buffTemplateId, long fromId, long toId) {
        this(r, buffTemplateId, fromId, toId, 0);
    }

    public AddBuff(Client.ReceivedFromClient r, int buffTemplateId, long fromId, long toId, long duration) {
        this.r = r;
        this.buffTemplateId = buffTemplateId;
        this.fromId = fromId;
        this.toId = toId;
        this.duration = duration;
    }
}

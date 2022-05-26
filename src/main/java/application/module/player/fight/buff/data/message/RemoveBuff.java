package application.module.player.fight.buff.data.message;

import application.module.scene.fight.buff.FightOrganismBuff;
import com.cala.orm.message.DataBase;
import mobius.modular.client.Client;

/**
 * @author Luo Yong
 * @date 2022-4-6
 * @Source 1.0
 */
public record RemoveBuff(Client.ReceivedFromClient r, int buffTemplateId, int coverCount, long fromId, long toId) implements DataBase {

    public RemoveBuff(Client.ReceivedFromClient r, FightOrganismBuff fightOrganismBuff) {
        this(r, fightOrganismBuff.getBuffTemplateId(), 1, 1, 1);
    }

    public RemoveBuff(Client.ReceivedFromClient r, int buffTemplateId, long fromId, long toId) {
        this(r, buffTemplateId, 1, fromId, toId);
    }

    public RemoveBuff(Client.ReceivedFromClient r, int buffTemplateId, int coverCount, long fromId, long toId) {
        this.r = r;
        this.buffTemplateId = buffTemplateId;
        this.coverCount = coverCount;
        this.fromId = fromId;
        this.toId = toId;
    }
}

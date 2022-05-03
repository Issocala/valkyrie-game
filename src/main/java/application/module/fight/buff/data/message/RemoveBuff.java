package application.module.fight.buff.data.message;

import akka.actor.ActorRef;
import application.module.fight.buff.data.domain.FightOrganismBuff;
import com.cala.orm.message.DataBase;
import mobius.modular.client.Client;

/**
 * @author Luo Yong
 * @date 2022-4-6
 * @Source 1.0
 */
public record RemoveBuff(Client.ReceivedFromClient r, int buffTemplateId, int coverCount, long fromId, long toId) implements DataBase {

    public RemoveBuff(Client.ReceivedFromClient r, FightOrganismBuff fightOrganismBuff) {
        this(r, fightOrganismBuff.getBuffTemplateId(), 1, fightOrganismBuff.getFromId(), fightOrganismBuff.getToId());
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

package application.module.fight.buff.data.message;

import akka.actor.ActorRef;
import com.cala.orm.message.DataBase;
import mobius.modular.client.Client;

/**
 * @author Luo Yong
 * @date 2022-4-6
 * @Source 1.0
 */
public record AddBuff(Client.ReceivedFromClient r, int buffTemplateId, long fromId, long toId,
                      long duration, ActorRef scene, ActorRef attributeData, ActorRef stateDate) implements DataBase {

    public AddBuff(Client.ReceivedFromClient r, int buffTemplateId, long fromId, long toId, ActorRef scene,
                   ActorRef attributeData, ActorRef stateDate) {
        this(r, buffTemplateId, fromId, toId, 0, scene, attributeData, stateDate);
    }

    public AddBuff(Client.ReceivedFromClient r, int buffTemplateId, long fromId, long toId, long duration,
                   ActorRef scene, ActorRef attributeData, ActorRef stateDate) {
        this.r = r;
        this.buffTemplateId = buffTemplateId;
        this.fromId = fromId;
        this.toId = toId;
        this.duration = duration;
        this.scene = scene;
        this.attributeData = attributeData;
        this.stateDate = stateDate;
    }
}

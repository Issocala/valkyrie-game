package application.module.fight.buff.data.message;

import akka.actor.ActorRef;
import com.cala.orm.message.DataBase;
import mobius.modular.client.Client;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-4-6
 * @Source 1.0
 */
public record AddBuff(Client.ReceivedFromClient r, int buffTemplateId, int coverCount, long fromId, long toId,
                      long duration, ActorRef scene, ActorRef attributeData, ActorRef stateDate, Map<Short, Long> attributeMap) implements DataBase {

    public AddBuff(Client.ReceivedFromClient r, int buffTemplateId, long fromId, long toId, ActorRef scene,
                   ActorRef attributeData, ActorRef stateDate) {
        this(r, buffTemplateId, 1, fromId, toId, 0, scene, attributeData, stateDate, null);
    }

    public AddBuff(Client.ReceivedFromClient r, int buffTemplateId, long fromId, long toId, ActorRef scene,
                   ActorRef attributeData, ActorRef stateDate, Map<Short, Long> attributeMap) {
        this(r, buffTemplateId, 1, fromId, toId, 0, scene, attributeData, stateDate, attributeMap);
    }

    public AddBuff(Client.ReceivedFromClient r, int buffTemplateId, int coverCount, long fromId, long toId, ActorRef scene,
                   ActorRef attributeData, ActorRef stateDate) {
        this(r, buffTemplateId, coverCount, fromId, toId, 0, scene, attributeData, stateDate, null);
    }

    public AddBuff(Client.ReceivedFromClient r, int buffTemplateId, long fromId, long toId, long duration, ActorRef scene,
                   ActorRef attributeData, ActorRef stateDate, Map<Short, Long> attributeMap) {
        this(r, buffTemplateId, 1, fromId, toId, duration, scene, attributeData, stateDate, attributeMap);
    }

    public AddBuff(Client.ReceivedFromClient r, int buffTemplateId, int coverCount, long fromId, long toId, long duration,
                   ActorRef scene, ActorRef attributeData, ActorRef stateDate, Map<Short, Long> attributeMap) {
        this.r = r;
        this.buffTemplateId = buffTemplateId;
        this.coverCount = coverCount;
        this.fromId = fromId;
        this.toId = toId;
        this.duration = duration;
        this.scene = scene;
        this.attributeData = attributeData;
        this.stateDate = stateDate;
        this.attributeMap = attributeMap;
    }
}

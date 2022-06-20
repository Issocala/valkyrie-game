package application.module.player.fight.attribute;

import application.client.Client;
import protocol.Attribute;
import protocol.Base;

import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-4-2
 * @Source 1.0
 */
public class AttributeProtocolBuilder {

    public static Attribute.SC10040 get10040(long fightOrganismId, Map<Short, Long> attributeMap) {
        Attribute.SC10040.Builder builder = Attribute.SC10040.newBuilder().setFightOrganismId(fightOrganismId);
        attributeMap.forEach((k, v) ->
                builder.addAttributeMap(Base.AttributeMap.newBuilder().setKey(k).setValue(v).build()));
        return builder.build();
    }

    public static Client.SendToClientJ get10041(long fightOrganismId, long fighting) {
        Attribute.SC10041.Builder builder = Attribute.SC10041.newBuilder();
        builder.setFightOrganismId(fightOrganismId).setFighting(fighting);
        return new Client.SendToClientJ(AttributeProtocols.FIGHTING, builder.build());
    }
}

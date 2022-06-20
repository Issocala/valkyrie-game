package application.module.scene.fight.buff;

import application.client.Client;
import protocol.Buff;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-4-6
 * @Source 1.0
 */
public class FightBuffProtocolBuilder {

    public static Buff.SC10070 getSc10070(long organismId, Map<Integer, FightOrganismBuff> map) {
        List<Buff.BuffInfo> list = new ArrayList<>();
        map.values().forEach(b ->
                list.add(Buff.BuffInfo.newBuilder()
                        .setBuffTemplateId(b.getBuffTemplateId())
                        .setCurrCoverCount(b.getCurrCoverCount())
                        .build()));
        return Buff.SC10070.newBuilder()
                .setOrganismId(organismId)
                .addAllBuffInfo(list)
                .build();
    }

    public static Client.SendToClientJ getSc10071(long organismId, FightOrganismBuff b) {
        Buff.SC10071.Builder builder = Buff.SC10071.newBuilder();
        builder.setFightOrganismId(organismId)
                .setBuffInfo(Buff.BuffInfo.newBuilder()
                        .setBuffTemplateId(b.getBuffTemplateId())
                        .setCurrCoverCount(b.getCurrCoverCount())
                        .build());
        return new Client.SendToClientJ(FightBuffProtocols.ADD, builder.build());
    }

    public static Client.SendToClientJ getSc10072(long organismId, FightOrganismBuff b) {
        Buff.SC10072.Builder builder = Buff.SC10072.newBuilder();
        builder.setFightOrganismId(organismId)
                .setBuffInfo(Buff.BuffInfo.newBuilder()
                        .setBuffTemplateId(b.getBuffTemplateId())
                        .setCurrCoverCount(b.getCurrCoverCount())
                        .build());
        return new Client.SendToClientJ(FightBuffProtocols.REMOVE, builder.build());
    }

}

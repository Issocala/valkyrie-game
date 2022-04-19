package application.module.gm;

import application.module.gm.domain.GmCmdResult;
import protocol.Gm;

import java.util.List;

/**
 * @author HRT
 * @date 2022-4-12
 */
public class GmProtocolBuilder {

    public static Gm.SC10090 getSc10090(boolean ok, List<String> details) {
        return Gm.SC10090.newBuilder()
                .setOk(ok)
                .addAllDetails(details)
                .build();
    }

    public static Gm.SC10090 getSc10090(GmCmdResult result) {
        return getSc10090(result.ok(), result.details());
    }

}

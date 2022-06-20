package application.module.common;

import application.client.Client;
import protocol.Common;

/**
 * @author Luo Yong
 * @date 2022-3-28
 * @Source 1.0
 */
public class CommonProtocolBuilder {

    public static Client.SendToClientJ getSc10080(int applicationErrorId) {
        Common.SC10080.Builder builder = Common.SC10080.newBuilder()
                .setApplicationErrorId(applicationErrorId);

        return new Client.SendToClientJ(CommonProtocols.APPLICATION_ERROR, builder.build());
    }

    public static Client.SendToClientJ getSc10081() {
        Common.SC10081.Builder builder = Common.SC10081.newBuilder()
                .setServerTime(System.currentTimeMillis());
        return new Client.SendToClientJ(CommonProtocols.SERVER_TIME, builder.build());
    }

}

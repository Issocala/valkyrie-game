package application.module.common;

import protocol.Common;

/**
 * @author Luo Yong
 * @date 2022-3-28
 * @Source 1.0
 */
public class CommonProtocolBuilder {

    public static Common.SC10080 getSc10080(int applicationErrorId) {
        return Common.SC10080.newBuilder()
                .setApplicationErrorId(applicationErrorId)
                .build();
    }

}

package application.module.user;

import protocol.User;

/**
 * @author Luo Yong
 * @date 2022-1-20
 * @Source 1.0
 */
public class UserProtocolBuilder {
    public static User.SC10010 getSc10010(boolean success) {
        return User.SC10010.newBuilder()
                .setSuccess(success)
                .build();
    }

    public static User.SC10010 getSc10010(boolean success, String content) {
        return User.SC10010.newBuilder()
                .setSuccess(success)
                .setContent(content)
                .build();
    }

    public static User.SC10011 getSc10011(long userId) {
        return User.SC10011.newBuilder()
                .setSuccess(true)
                .setUserId(userId)
                .build();
    }

    public static User.SC10011 getSc10011(String content) {
        return User.SC10011.newBuilder()
                .setSuccess(false)
                .setContent(content)
                .build();
    }
}

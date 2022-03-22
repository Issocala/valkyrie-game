package application.module.player;

import application.module.player.data.domain.PlayerEntity;
import protocol.Player;

import java.util.List;

/**
 * @author Luo Yong
 * @date 2022-2-9
 * @Source 1.0
 */
public class PlayerProtocolBuilder {

    public static Player.PlayerInfo getPlayerInfo(PlayerEntity p) {
        return Player.PlayerInfo.newBuilder()
                .setRoleId(p.getId())
                .setName(p.getName())
                .setLevel(p.getLevel())
                .setGender(p.getGender())
                .setProfession(p.getProfession())
                .setLastLoginTime(p.getLastLoginTime())
                .build();
    }

    public static Player.SC10020 getSc10020(List<PlayerEntity> playerEntityList) {
        Player.SC10020.Builder builder = Player.SC10020.newBuilder();
        playerEntityList.forEach(playerEntity -> builder.addPlayerInfo(getPlayerInfo(playerEntity)));
        return builder.build();
    }

    public static Player.SC10021 getSc10021(boolean success, String content) {
        return Player.SC10021.newBuilder()
                .setSuccess(success)
                .setContent(content)
                .build();
    }

    public static Player.SC10021 getSc10021(boolean success, long roleId) {
        return Player.SC10021.newBuilder()
                .setSuccess(success)
                .setRoleId(roleId)
                .build();
    }

    public static Player.SC10022 getSc10022(boolean success, long roleId) {
        return Player.SC10022.newBuilder()
                .setSuccess(success)
                .setRoleId(roleId)
                .build();
    }

    public static Player.SC10022 getSc10022(boolean success, String content) {
        return Player.SC10022.newBuilder()
                .setSuccess(success)
                .setContent(content)
                .build();
    }
}

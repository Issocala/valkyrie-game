package application.module.player;

import application.module.player.data.domain.PlayerEntity;
import application.module.player.data.domain.PlayerInfo;
import protocol.Player;

import java.util.List;

/**
 * @author Luo Yong
 * @date 2022-2-9
 * @Source 1.0
 */
public class PlayerProtocolBuilder {

    public static Player.PlayerInfo getPlayerInfo(PlayerInfo playerInfo) {
        return Player.PlayerInfo.newBuilder()
                .setRoleId(playerInfo.getId())
                .setName(playerInfo.getName())
                .setLevel(playerInfo.getLevel())
                .setGender(playerInfo.getGender())
                .setProfession(playerInfo.getProfession())
                .setLastLoginTime(playerInfo.getLastLoginTime())
                .build();
    }

    public static Player.SC10020 getSc10020(List<PlayerEntity> playerEntityList) {
        Player.SC10020.Builder builder = Player.SC10020.newBuilder();
        playerEntityList.forEach(playerEntity -> builder.addPlayerInfo(getPlayerInfo(playerEntity.getPlayerInfo())));
        return builder.build();
    }

    public static Player.SC10021 getSc10021(boolean success, String content) {
        return Player.SC10021.newBuilder()
                .setSuccess(success)
                .setContent(content)
                .build();
    }

    public static Player.SC10022 getSc10022(boolean success, long roleId, String content) {
        return Player.SC10022.newBuilder()
                .setSuccess(success)
                .setRoleId(roleId)
                .setContent(content)
                .build();
    }
}

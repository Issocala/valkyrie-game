package application.module.player.operate.info;

import application.module.player.data.entity.PlayerEntity;
import com.cala.orm.message.OperateTypeInfo;
import mobius.modular.client.Client;

/**
 * @author Luo Yong
 * @date 2022-1-18
 * @Source 1.0
 */
public record PlayerOperateTypeInfo(Client.ReceivedFromClient r, PlayerEntity playerEntity) implements OperateTypeInfo {
}
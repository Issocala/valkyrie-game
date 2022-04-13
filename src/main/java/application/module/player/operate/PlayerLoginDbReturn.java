package application.module.player.operate;

import application.module.player.data.message.event.PlayerLogin;
import com.cala.orm.message.OperateType;

/**
 * @author Luo Yong
 * @date 2022-4-13
 * @Source 1.0
 */
public record PlayerLoginDbReturn(PlayerLogin playerLogin) implements OperateType {
}

package application.module.player.operate;

import application.module.organism.PlayerFight;
import com.cala.orm.message.OperateType;

/**
 * @author Luo Yong
 * @date 2022-5-18
 * @Source 1.0
 */
public record PlayerLoginOpt(PlayerFight playerFight) implements OperateType {
}

package application.module.player.fight.attribute.operate;

import application.module.player.util.PlayerOperateType;

import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-6-2
 * @Source 1.0
 */
public record PlayerAddAttributeOpt(Map<Short, Long> map, long playerId) implements PlayerOperateType {
}

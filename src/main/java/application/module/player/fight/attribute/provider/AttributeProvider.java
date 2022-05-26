package application.module.player.fight.attribute.provider;

import application.module.player.Player;

import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-2-16
 * @Source 1.0
 */
public interface AttributeProvider {

    Map<Short, Long> provider(Player player);

}

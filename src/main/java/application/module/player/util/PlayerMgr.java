package application.module.player.util;

import application.module.player.Player;
import com.cala.orm.cache.AbstractBase;
import com.cala.orm.message.OperateType;
import mobius.modular.client.Client;

import java.util.List;

/**
 * @author Luo Yong
 * @date 2022-5-13
 * @Source 1.0
 */
public interface PlayerMgr {

    default void receiver(Player player, OperateType p) {
    }

    default void receiver(Player player, AbstractBase abstractBase, OperateType p) {
    }

    default void receiver(Player player, List<AbstractBase> abstractBases, OperateType p) {
    }

    void receiver(Player player, Client.ReceivedFromClient r);

    void init(Player player);

    void destroy(Player player);

    List<Integer> getProtoIds();

    List<Class<? extends OperateType>> getOperateTypes();

}

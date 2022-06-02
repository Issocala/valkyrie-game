package application.module.player.fight.buff;

import application.module.player.Player;
import application.module.player.util.PlayerMgr;
import application.module.scene.fight.buff.FightBuffProtocols;
import com.cala.orm.message.OperateType;
import mobius.modular.client.Client;

import java.util.List;

/**
 * @author Luo Yong
 * @date 2022-5-18
 * @Source 1.0
 */
public class BuffPlayerMgr implements PlayerMgr {

    public final static List<Class<? extends OperateType>> operateTypeList;

    public static List<Integer> messageList;

    static {
        messageList = List.of(FightBuffProtocols.GET, FightBuffProtocols.ADD, FightBuffProtocols.REMOVE);
        operateTypeList = List.of();
    }
    @Override
    public void receiver(Player player, Client.ReceivedFromClient r) {

    }

    @Override
    public void init(Player player) {
        player.addInitFinalSet(this.getClass());
    }

    @Override
    public void destroy(Player player) {

    }

    @Override
    public List<Integer> getProtoIds() {
        return messageList;
    }

    @Override
    public List<Class<? extends OperateType>> getOperateTypes() {
        return operateTypeList;
    }
}

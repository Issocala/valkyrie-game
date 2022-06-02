package application.module.player.fight.state;

import application.module.player.Player;
import application.module.player.fight.skill.FightSkillProtocols;
import application.module.player.fight.skill.operate.PlayerSkillGetOpt;
import application.module.player.util.PlayerMgr;
import com.cala.orm.message.OperateType;
import mobius.modular.client.Client;

import java.util.List;

/**
 * @author Luo Yong
 * @date 2022-5-18
 * @Source 1.0
 */
public class StatePlayerMgr implements PlayerMgr {

    public final static List<Class<? extends OperateType>> operateTypeList;

    public static List<Integer> messageList;

    static {
        messageList = List.of();
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

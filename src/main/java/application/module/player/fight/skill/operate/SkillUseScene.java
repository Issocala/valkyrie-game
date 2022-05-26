package application.module.player.fight.skill.operate;

import application.module.player.util.PlayerOperateType;
import com.cala.orm.message.OperateType;
import mobius.modular.client.Client;
import protocol.Skill;

/**
 * @author Luo Yong
 * @date 2022-3-3
 * @Source 1.0
 */
public record SkillUseScene(Client.ReceivedFromClient r, Skill.CS10052 cs10052) implements PlayerOperateType {
}

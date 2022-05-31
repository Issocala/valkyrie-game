package application.module.player.fight.skill.operate;

import application.module.player.util.PlayerOperateType;
import mobius.modular.client.Client;
import protocol.Skill;

/**
 * @author Luo Yong
 * @date 2022-5-30
 * @Source 1.0
 */
public record SkillProcessUseScene(Client.ReceivedFromClient r, Skill.CS10055 cs10055) implements PlayerOperateType {
}

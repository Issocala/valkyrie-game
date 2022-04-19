package application.module.fight.skill.operate.info;

import application.module.fight.skill.base.context.UseSkillDataTemp;
import com.cala.orm.message.OperateTypeInfo;
import mobius.modular.client.Client;

/**
 * @author Luo Yong
 * @date 2022-3-28
 * @Source 1.0
 */
public record SkillUseInfo(Client.ReceivedFromClient r, UseSkillDataTemp useSkillDataTemp) implements OperateTypeInfo {
}

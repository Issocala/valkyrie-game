package application.module.player.fight.skill.data.message;

import application.module.scene.fight.skill.base.context.UseSkillDataTemp;
import com.cala.orm.message.DataBase;
import com.cala.orm.message.OperateType;

/**
 * @author Luo Yong
 * @date 2022-3-28
 * @Source 1.0
 */
public record SkillGetAllAttribute(UseSkillDataTemp useSkillDataTemp, boolean isSkillProcess) implements DataBase, OperateType {
}

package application.module.player.fight.skill.data.message;

import com.cala.orm.message.DataBase;
import com.cala.orm.message.OperateType;
import com.cala.orm.message.OperateTypeInfo;

/**
 * @author Luo Yong
 * @date 2022-3-3
 * @Source 1.0
 */
public record SkillUseState(OperateTypeInfo operateTypeInfo) implements OperateType, DataBase {
}

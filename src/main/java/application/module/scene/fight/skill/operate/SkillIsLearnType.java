package application.module.scene.fight.skill.operate;

import com.cala.orm.message.OperateType;
import com.cala.orm.message.OperateTypeInfo;

/**
 * @author Luo Yong
 * @date 2022-3-4
 * @Source 1.0
 */
public record SkillIsLearnType(OperateTypeInfo operateTypeInfo) implements OperateType {
}

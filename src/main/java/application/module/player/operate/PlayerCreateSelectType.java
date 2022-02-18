package application.module.player.operate;

import com.cala.orm.message.OperateType;
import com.cala.orm.message.OperateTypeInfo;

/**
 * @author Luo Yong
 * @date 2022-2-9
 * @Source 1.0
 */
public record PlayerCreateSelectType(OperateTypeInfo operateTypeInfo) implements OperateType {
}

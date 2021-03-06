package application.module.user.operate;

import com.cala.orm.message.OperateType;
import com.cala.orm.message.OperateTypeInfo;

/**
 * @author Luo Yong
 * @date 2022-1-18
 * @Source 1.0
 */
public record UserLoginType(OperateTypeInfo operateTypeInfo) implements OperateType {
}

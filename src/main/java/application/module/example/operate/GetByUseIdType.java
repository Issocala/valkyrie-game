package application.module.example.operate;

import com.cala.orm.message.OperateType;

/**
 * @author Luo Yong
 * @date 2021-12-20
 * @Source 1.0
 */
public class GetByUseIdType implements OperateType {
    public final static GetByUseIdType INSTANCE = new GetByUseIdType();

    private GetByUseIdType() {
    }
}

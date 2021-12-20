package application.module.example.operate;

import com.cala.orm.message.OperateType;

/**
 * @author Luo Yong
 * @date 2021-12-20
 * @Source 1.0
 */
public class GetType implements OperateType {
    public final static GetType INSTANCE = new GetType();

    private GetType() {
    }
}

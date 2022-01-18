package application.module.user.operate;

import com.cala.orm.message.OperateTypeInfo;
import mobius.modular.client.Client;
import protocol.P1;

/**
 * @author Luo Yong
 * @date 2022-1-18
 * @Source 1.0
 */
public record UserOperateTypeInfo(Client.ReceivedFromClient r, P1.cs1 cs1) implements OperateTypeInfo {
}

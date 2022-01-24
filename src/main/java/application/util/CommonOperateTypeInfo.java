package application.module.user.operate;

import com.cala.orm.message.OperateTypeInfo;
import com.google.protobuf.Message;
import mobius.modular.client.Client;

/**
 * @author Luo Yong
 * @date 2022-1-18
 * @Source 1.0
 */
public record CommonOperateTypeInfo(Client.ReceivedFromClient r, Message message) implements OperateTypeInfo {
}

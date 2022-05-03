package application.module.scene.operate;

import com.cala.orm.message.DataBase;
import mobius.modular.client.Client;

/**
 * @author Luo Yong
 * @date 2022-4-21
 * @Source 1.0
 */
public record PlayerReceive(Client.ReceivedFromClient r) implements DataBase {
}

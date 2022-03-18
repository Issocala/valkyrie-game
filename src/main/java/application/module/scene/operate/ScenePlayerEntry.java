package application.module.scene.operate;

import com.cala.orm.message.DataBase;
import mobius.modular.client.Client;
import protocol.Scene;

/**
 * @author Luo Yong
 * @date 2022-3-18
 * @Source 1.0
 */
public record ScenePlayerEntry(Client.ReceivedFromClient r, Scene.CS10030 cs10030) implements DataBase {
}

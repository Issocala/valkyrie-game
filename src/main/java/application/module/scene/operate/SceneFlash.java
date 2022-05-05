package application.module.scene.operate;

import com.cala.orm.message.DataBase;
import mobius.modular.client.Client;
import protocol.Scene;

/**
 * @author Luo Yong
 * @date 2022-3-18
 * @Source 1.0
 */
public record SceneFlash(Client.ReceivedFromClient r, Scene.CS10306 cs10306) implements DataBase {
}

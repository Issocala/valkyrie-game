package application.module.scene.operate;

import com.cala.orm.message.DataBase;
import mobius.modular.client.Client;
import protocol.Scene;

/**
 * @author Luo Yong
 * @date 2022-5-10
 * @Source 1.0
 */
public record SceneRush(Client.ReceivedFromClient r, Scene.CS10312 cs10312) implements DataBase {
}

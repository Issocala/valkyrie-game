package application.module.scene.operate;

import akka.actor.ActorRef;
import com.cala.orm.message.DataBase;

import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-3-3
 * @Source 1.0
 */
public record AllSceneInitFinally(Map<Integer, ActorRef> sceneId2SceneMap) implements DataBase {
}

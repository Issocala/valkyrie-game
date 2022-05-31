package application.module.scene.organism;

import akka.actor.ActorRef;
import application.module.organism.ItemOrganism;
import application.module.organism.ItemOrganism;
import application.module.scene.Scene;
import application.module.scene.SceneMgr;
import application.module.scene.SceneProtocolBuilder;
import application.module.scene.SceneProtocols;
import application.module.scene.operate.SceneOrganismExit;
import com.cala.orm.message.OperateType;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-5-17
 * @Source 1.0
 */
public class ItemSceneMgr implements SceneMgr {
    private Scene scene;
    private final Map<Long, ItemOrganism> itemMap = new HashMap<>();


    public void addItemOrganism(ItemOrganism itemOrganism) {
        addItemOrganism(itemOrganism, 0);
    }


    public void addItemOrganism(ItemOrganism itemOrganism, long duration) {
        this.itemMap.put(itemOrganism.getId(), itemOrganism);
        itemOrganism.sendSelfDataToSceneClient(scene);
        if (duration > 0) {
            ActorRef sceneActor = scene.getSceneActor();
            scene.getContext().system().scheduler().scheduleOnce(Duration.ofMillis(duration), sceneActor,
                    new SceneOrganismExit(itemOrganism.getId(), itemOrganism.getOrganismType()),
                    scene.getContext().dispatcher(), sceneActor);
        }
    }

    public void removeItemOrganism(long id) {
        this.itemMap.remove(id);
        scene.getPlayerSceneMgr().sendToAllClient(scene, SceneProtocols.SCENE_EXIT, SceneProtocolBuilder.getSc10301(id));
    }

    public ItemOrganism getItemOrganism(long id) {
        return this.itemMap.get(id);
    }

    public boolean containsItemOrganism(long id) {
        return this.itemMap.containsKey(id);
    }

    public Map<Long, ItemOrganism> getItemMap() {
        return itemMap;
    }

    @Override
    public void init(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void destroy(Scene scene) {

    }

    @Override
    public List<Class<? extends OperateType>> getOperateTypes() {
        return List.of();
    }
}

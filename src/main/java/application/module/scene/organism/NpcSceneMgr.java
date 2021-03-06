package application.module.scene.organism;

import akka.actor.ActorRef;
import application.module.organism.NpcOrganism;
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
 * @date 2022-5-16
 * @Source 1.0
 */
public class NpcSceneMgr implements SceneMgr {
    private Scene scene;
    private final Map<Long, NpcOrganism> npcMap = new HashMap<>();

    public void addNpcOrganism(NpcOrganism npcOrganism) {
        addNpcOrganism(npcOrganism, 0);
    }

    public void addNpcOrganism(NpcOrganism npcOrganism, long duration) {
        this.npcMap.put(npcOrganism.getId(), npcOrganism);
        npcOrganism.sendSelfDataToSceneClient(this.scene);
        if (duration > 0) {
            ActorRef sceneActor = scene.getSceneActor();
            scene.getContext().system().scheduler().scheduleOnce(Duration.ofMillis(duration), sceneActor,
                    new SceneOrganismExit(npcOrganism.getId(), npcOrganism.getOrganismType()),
                    scene.getContext().dispatcher(), sceneActor);
        }
    }

    public void removeNpcOrganism(long id) {
        this.npcMap.remove(id);
        scene.getPlayerSceneMgr().sendToAllClient(scene, SceneProtocols.SCENE_EXIT, SceneProtocolBuilder.getSc10301(id));
    }

    public NpcOrganism getNpcOrganism(long id) {
        return this.npcMap.get(id);
    }

    public boolean containsNpcOrganism(long id) {
        return this.npcMap.containsKey(id);
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

    public Map<Long, NpcOrganism> getNpcMap() {
        return npcMap;
    }
}

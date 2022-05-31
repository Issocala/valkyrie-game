package application.module.scene.organism;

import akka.actor.ActorRef;
import application.module.organism.EffectOrganism;
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
 * @date 2022-5-31
 * @Source 1.0
 */
public class EffectSceneMgr implements SceneMgr {
    private Scene scene;
    private final Map<Long, EffectOrganism> effectMap = new HashMap<>();

    public void addEffectOrganism(EffectOrganism effectOrganism) {
        addEffectOrganism(effectOrganism, 0);
    }

    public void addEffectOrganism(EffectOrganism effectOrganism, long duration) {
        this.effectMap.put(effectOrganism.getId(), effectOrganism);
        effectOrganism.sendSelfDataToSceneClient(this.scene);
        if (duration > 0) {
            ActorRef sceneActor = scene.getSceneActor();
            scene.getContext().system().scheduler().scheduleOnce(Duration.ofMillis(duration), sceneActor,
                    new SceneOrganismExit(effectOrganism.getId(), effectOrganism.getOrganismType()),
                    scene.getContext().dispatcher(), sceneActor);
        }
    }

    public void removeEffectOrganism(long id) {
        this.effectMap.remove(id);
        scene.getPlayerSceneMgr().sendToAllClient(scene, SceneProtocols.SCENE_EXIT, SceneProtocolBuilder.getSc10301(id));
    }

    public EffectOrganism getEffectOrganism(long id) {
        return this.effectMap.get(id);
    }

    public boolean containsEffectOrganism(long id) {
        return this.effectMap.containsKey(id);
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

    public Map<Long, EffectOrganism> getEffectMap() {
        return effectMap;
    }
}

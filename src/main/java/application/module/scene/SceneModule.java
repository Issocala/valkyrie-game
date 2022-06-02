package application.module.scene;

import akka.actor.ActorRef;
import application.module.common.data.domain.DataMessage;
import application.module.player.data.message.event.PlayerLogin;
import application.module.player.fight.attribute.data.message.PlayerDead;
import application.module.scene.data.SceneData;
import application.module.scene.operate.AllSceneInitFinally;
import application.module.scene.operate.SceneInit;
import application.module.scene.operate.SceneOut;
import com.cala.orm.message.DataReturnMessage;
import mobius.modular.client.Client;
import mobius.modular.module.api.AbstractModule;
import template.SceneDataTemplateHolder;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2021-12-31
 * @Source 1.0
 */
public class SceneModule extends AbstractModule {

    private ActorRef sceneData;

    public final static int MAIN_SCENE = 20003;

    private final Map<Integer, ActorRef> sceneId2SceneMap = new HashMap<>();

    @Override
    public void initData() {
        this.dataAgent().tell(new DataMessage.RequestData(SceneData.class), self());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(DataMessage.DataResult.class, this::dataResult)
                .match(DataReturnMessage.class, this::dataResultMessage)
                .match(SceneOut.class, this::sceneOut)
                .build();
    }

    private void playerDead(PlayerDead playerDead) {
        this.sceneData.tell(playerDead, self());
    }


    private void dataResultMessage(DataReturnMessage d) {

    }

    private void dataResult(DataMessage.DataResult d) {
        if (d.clazz() == SceneData.class) {
            this.sceneData = d.actorRef();
            sceneInit(new SceneInit());
        }
    }

    private void sceneInit(SceneInit sceneInit) {
        SceneDataTemplateHolder.getValues().forEach(template -> {
            int templateId = template.id();
            ActorRef scene = getContext().actorOf(SceneActor.create(templateId));
            this.sceneId2SceneMap.put(templateId, scene);
            scene.tell(sceneInit, self());
        });
        AllSceneInitFinally allSceneInitFinally = new AllSceneInitFinally(Map.copyOf(this.sceneId2SceneMap));
        this.sceneId2SceneMap.values().forEach(sceneActor ->
                sceneActor.tell(allSceneInitFinally, self()));
        this.sceneData.tell(allSceneInitFinally, self());
    }

    private void receivedFromClient(Client.ReceivedFromClient r) {
    }


    private void sceneOut(SceneOut sceneOut) {
        int sceneId = sceneOut.sceneId();
        getContext().stop(sceneId2SceneMap.get(sceneId));
        sceneId2SceneMap.remove(sceneId);
        sceneId2SceneMap.values().forEach(sceneActor ->
                sceneActor.tell(new AllSceneInitFinally(Map.copyOf(this.sceneId2SceneMap)), self()));
        getContext().getSystem().scheduler().scheduleOnce(Duration.ofSeconds(20), () -> {
            ActorRef scene = getContext().actorOf(SceneActor.create(sceneId));
            this.sceneId2SceneMap.put(sceneId, scene);
            scene.tell(new SceneInit(), self());
            AllSceneInitFinally allSceneInitFinally = new AllSceneInitFinally(Map.copyOf(this.sceneId2SceneMap));
            sceneId2SceneMap.values().forEach(sceneActor ->
                    sceneActor.tell(allSceneInitFinally, self()));
            this.sceneData.tell(allSceneInitFinally, self());

        }, getContext().dispatcher());
    }

    private void playerLogin(PlayerLogin playerLogin) {
        this.sceneData.tell(playerLogin, self());
    }
}

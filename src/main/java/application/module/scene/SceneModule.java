package application.module.scene;

import akka.actor.ActorRef;
import application.module.common.data.domain.DataMessage;
import application.module.scene.data.SceneData;
import application.module.scene.data.message.SceneInit;
import application.module.scene.operate.SceneMove;
import application.module.scene.operate.ScenePlayerEntry;
import application.module.scene.operate.ScenePlayerExit;
import application.module.scene.operate.SceneStop;
import com.cala.orm.message.DataReturnMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import mobius.modular.client.Client;
import mobius.modular.module.api.AbstractModule;
import protocol.Scene;

/**
 * @author Luo Yong
 * @date 2021-12-31
 * @Source 1.0
 */
public class SceneModule extends AbstractModule {

    private ActorRef sceneData;

    @Override
    public void initData() {
        this.dataAgent().tell(new DataMessage.RequestData(SceneData.class), self());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Client.ReceivedFromClient.class, this::receivedFromClient)
                .match(DataMessage.DataResult.class, this::dataResult)
                .match(DataReturnMessage.class, this::dataResultMessage)
                .build();
    }

    private void dataResultMessage(DataReturnMessage d) {

    }

    private void dataResult(DataMessage.DataResult d) {
        if (d.clazz() == SceneData.class) {
            this.sceneData = d.actorRef();
            this.sceneData.tell(new SceneInit(), self());
        }
    }

    private void receivedFromClient(Client.ReceivedFromClient r) {
        switch (r.protoID()) {
            case SceneProtocols.SCENE_ENTER -> enterScene(r);
            case SceneProtocols.SCENE_EXIT -> exitScene(r);
            case SceneProtocols.SCENE_MOVE -> move(r);
            case SceneProtocols.SCENE_STOP -> stop(r);
        }
    }

    private void stop(Client.ReceivedFromClient r) {
        try {
            var cs10033 = Scene.CS10033.parseFrom(r.message());
            this.sceneData.tell(new SceneStop(r, cs10033), self());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private void move(Client.ReceivedFromClient r) {
        try {
            var cs10032 = Scene.CS10032.parseFrom(r.message());
            this.sceneData.tell(new SceneMove(r, cs10032), self());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private void exitScene(Client.ReceivedFromClient r) {
        try {
            var cs10031 = Scene.CS10031.parseFrom(r.message());
            this.sceneData.tell(new ScenePlayerExit(r, cs10031), self());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private void enterScene(Client.ReceivedFromClient r) {
        try {
            var cs10030 = Scene.CS10030.parseFrom(r.message());
            this.sceneData.tell(new ScenePlayerEntry(r, cs10030), self());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}
package application.module.scene;

import akka.actor.ActorRef;
import application.module.common.data.domain.DataMessage;
import application.module.fight.attribute.data.AttributeData;
import application.module.fight.attribute.data.message.PlayerDead;
import application.module.fight.buff.data.FightBuffData;
import application.module.player.data.PlayerEntityData;
import application.module.player.data.message.event.PlayerLogin;
import application.module.player.data.message.event.PlayerLogout;
import application.module.scene.data.SceneData;
import application.module.scene.operate.*;
import application.module.state.data.StateData;
import com.cala.orm.message.DataReturnMessage;
import com.cala.orm.message.SubscribeEvent;
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
    private ActorRef playerEntityData;
    private ActorRef attributeData;
    private ActorRef stateData;
    private ActorRef buffData;

    @Override
    public void initData() {
        this.dataAgent().tell(new DataMessage.RequestData(SceneData.class), self());
        this.dataAgent().tell(new DataMessage.RequestData(PlayerEntityData.class), self());
        this.dataAgent().tell(new DataMessage.RequestData(AttributeData.class), self());
        this.dataAgent().tell(new DataMessage.RequestData(StateData.class), self());
        this.dataAgent().tell(new DataMessage.RequestData(FightBuffData.class), self());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Client.ReceivedFromClient.class, this::receivedFromClient)
                .match(DataMessage.DataResult.class, this::dataResult)
                .match(DataReturnMessage.class, this::dataResultMessage)
                .match(PlayerLogin.class, this::playerLogin)
                .match(PlayerLogout.class, this::playerLogout)
                .match(PlayerDead.class, this::playerDead)
                .build();
    }

    private void playerDead(PlayerDead playerDead) {
        this.sceneData.tell(playerDead, self());
    }

    private void playerLogout(PlayerLogout playerLogout) {
        this.sceneData.tell(playerLogout, self());
    }

    private void dataResultMessage(DataReturnMessage d) {

    }

    private void dataResult(DataMessage.DataResult d) {
        if (d.clazz() == SceneData.class) {
            this.sceneData = d.actorRef();

        } else if (d.clazz() == PlayerEntityData.class) {
            this.playerEntityData = d.actorRef();
            this.playerEntityData.tell(new SubscribeEvent(PlayerLogin.class, self()), self());
            this.playerEntityData.tell(new SubscribeEvent(PlayerLogout.class, self()), self());
        } else if (d.clazz() == AttributeData.class) {
            this.attributeData = d.actorRef();
            this.attributeData.tell(new SubscribeEvent(PlayerDead.class, self()), self());
        } else if (d.clazz() == StateData.class) {
            this.stateData = d.actorRef();
        } else if (d.clazz() == FightBuffData.class) {
            this.buffData = d.actorRef();
        }

        if (sceneData != null && this.playerEntityData != null && attributeData != null && stateData != null && buffData != null) {
            this.sceneData.tell(new SceneInit(stateData, buffData, attributeData), self());
        }
    }

    private void receivedFromClient(Client.ReceivedFromClient r) {
        switch (r.protoID()) {
            case SceneProtocols.SCENE_ENTER -> enterScene(r);
            case SceneProtocols.SCENE_EXIT -> exitScene(r);
            case SceneProtocols.SCENE_MOVE -> move(r);
            case SceneProtocols.SCENE_STOP -> stop(r);
            case SceneProtocols.SCENE_JUMP -> jump(r);
            case SceneProtocols.SCENE_FLASH -> flash(r);
            case SceneProtocols.RECEIVE -> receive(r);
            case SceneProtocols.PICK_UP_ITEM -> pickUpItem(r);
            case SceneProtocols.FUCK_NPC -> fuckNpc(r);
        }
    }

    private void fuckNpc(Client.ReceivedFromClient r) {
        try {
            var cs10311 = Scene.CS10311.parseFrom(r.message());
            this.sceneData.tell(new FuckNpc(r, cs10311, this.buffData, this.attributeData), self());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private void pickUpItem(Client.ReceivedFromClient r) {
        try {
            var cs10310 = Scene.CS10310.parseFrom(r.message());
            this.sceneData.tell(new PickUpItem(r, cs10310, this.buffData, this.attributeData), self());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private void receive(Client.ReceivedFromClient r) {
        this.sceneData.tell(new PlayerReceive(r), self());
    }

    private void flash(Client.ReceivedFromClient r) {
        try {
            var cs10306 = Scene.CS10306.parseFrom(r.message());
            this.sceneData.tell(new SceneFlash(r, cs10306), self());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private void jump(Client.ReceivedFromClient r) {
        try {
            var cs10305 = Scene.CS10305.parseFrom(r.message());
            this.sceneData.tell(new SceneJump(r, cs10305), self());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private void stop(Client.ReceivedFromClient r) {
        try {
            var cs10303 = Scene.CS10303.parseFrom(r.message());
            this.sceneData.tell(new SceneStop(r, cs10303), self());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private void move(Client.ReceivedFromClient r) {
        try {
            var cs10302 = Scene.CS10302.parseFrom(r.message());
            this.sceneData.tell(new SceneMove(r, cs10302), self());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private void exitScene(Client.ReceivedFromClient r) {
        try {
            var cs10301 = Scene.CS10301.parseFrom(r.message());
            this.sceneData.tell(new ScenePlayerExit(r, cs10301), self());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private void enterScene(Client.ReceivedFromClient r) {
        try {
            var cs10300 = Scene.CS10300.parseFrom(r.message());
            this.sceneData.tell(new ScenePlayerEntry(r, cs10300), self());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private void playerLogin(PlayerLogin playerLogin) {
        this.sceneData.tell(playerLogin, self());
    }
}

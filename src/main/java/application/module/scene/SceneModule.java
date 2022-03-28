package application.module.scene;

import akka.actor.ActorRef;
import application.module.common.data.domain.DataMessage;
import application.module.fight.skill.data.message.SkillUse;
import application.module.player.data.PlayerEntityData;
import application.module.player.data.message.PlayerLogin;
import application.module.scene.data.SceneData;
import application.module.scene.data.message.SceneInit;
import application.module.scene.operate.*;
import application.util.CommonOperateTypeInfo;
import com.cala.orm.message.DataReturnMessage;
import com.cala.orm.message.SubscribeEvent;
import com.google.protobuf.InvalidProtocolBufferException;
import mobius.modular.client.Client;
import mobius.modular.module.api.AbstractModule;
import protocol.Scene;
import protocol.Skill;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Luo Yong
 * @date 2021-12-31
 * @Source 1.0
 */
public class SceneModule extends AbstractModule {

    private ActorRef sceneData;
    private ActorRef playerEntityData;

    private final Map<Long, Long> playerId2SceneIdMap = new HashMap<>();

    private final Map<Long, ActorRef> sceneId2SceneMap = new HashMap<>();

    @Override
    public void initData() {
        this.dataAgent().tell(new DataMessage.RequestData(SceneData.class), self());
        this.dataAgent().tell(new DataMessage.RequestData(PlayerEntityData.class), self());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Client.ReceivedFromClient.class, this::receivedFromClient)
                .match(DataMessage.DataResult.class, this::dataResult)
                .match(DataReturnMessage.class, this::dataResultMessage)
                .match(PlayerLogin.class, this::playerLogin)
                .build();
    }

    private void dataResultMessage(DataReturnMessage d) {

    }

    private void dataResult(DataMessage.DataResult d) {
        if (d.clazz() == SceneData.class) {
            this.sceneData = d.actorRef();
            sceneInit(new SceneInit());
        } else if (d.clazz() == PlayerEntityData.class) {
            this.playerEntityData = d.actorRef();
            this.playerEntityData.tell(new SubscribeEvent(PlayerLogin.class, self()), self());
        }
    }

    private void receivedFromClient(Client.ReceivedFromClient r) {
        switch (r.protoID()) {
            case SceneProtocols.SCENE_ENTER -> enterScene(r);
            case SceneProtocols.SCENE_EXIT -> exitScene(r);
            case SceneProtocols.SCENE_MOVE -> move(r);
            case SceneProtocols.SCENE_STOP -> stop(r);
            case SceneProtocols.SCENE_JUMP -> jump(r);
        }
    }

    private void jump(Client.ReceivedFromClient r) {
        try {
            var cs10035 = Scene.CS10035.parseFrom(r.message());
            long playerId = r.uID();
            if (this.playerId2SceneIdMap.containsKey(playerId)) {
                long sceneId = this.playerId2SceneIdMap.get(playerId);
                this.sceneId2SceneMap.get(sceneId).tell(new SceneJump(r, cs10035), self());
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private void stop(Client.ReceivedFromClient r) {
        try {
            var cs10033 = Scene.CS10033.parseFrom(r.message());
            long playerId = r.uID();
            if (this.playerId2SceneIdMap.containsKey(playerId)) {
                long sceneId = this.playerId2SceneIdMap.get(playerId);
                this.sceneId2SceneMap.get(sceneId).tell(new SceneStop(r, cs10033), self());
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private void move(Client.ReceivedFromClient r) {
        try {
            var cs10032 = Scene.CS10032.parseFrom(r.message());
            long playerId = r.uID();
            if (this.playerId2SceneIdMap.containsKey(playerId)) {
                long sceneId = this.playerId2SceneIdMap.get(playerId);
                this.sceneId2SceneMap.get(sceneId).tell(new SceneMove(r, cs10032), self());
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private void exitScene(Client.ReceivedFromClient r) {
        try {
            var cs10031 = Scene.CS10031.parseFrom(r.message());
            long sceneId = cs10031.getSceneId();
            if (this.sceneId2SceneMap.containsKey(sceneId)) {
                this.sceneId2SceneMap.get(sceneId).tell(new ScenePlayerExit(r, cs10031), self());
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private void enterScene(Client.ReceivedFromClient r) {
        try {
            var cs10030 = Scene.CS10030.parseFrom(r.message());
            long playerId = r.uID();
            long sceneId = cs10030.getSceneId();
            if (this.sceneId2SceneMap.containsKey(sceneId)) {
                this.sceneId2SceneMap.get(sceneId).tell(new ScenePlayerEntry(r, cs10030), self());
                playerId2SceneIdMap.put(playerId, sceneId);
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private void playerLogin(PlayerLogin playerLogin) {
        // TODO 场景id应该是保存起来的离线场景，或者是主城id，看策划怎么设计
        long sceneId = 20003L;
        if (this.sceneId2SceneMap.containsKey(sceneId)) {
            sceneId2SceneMap.get(20003L).tell(playerLogin, self());
            playerId2SceneIdMap.put(playerLogin.playerId(), sceneId);
        }
    }

    private void skillUse(SkillUse skillUse) {
        CommonOperateTypeInfo commonOperateTypeInfo = (CommonOperateTypeInfo) skillUse.operateTypeInfo();
        Skill.CS10052 cs10052 = (Skill.CS10052) commonOperateTypeInfo.message();
        ActorRef actorRef = sceneId2SceneMap.get(cs10052.getSceneId());
        if (Objects.nonNull(actorRef)) {
            actorRef.tell(skillUse, self());
        }
    }

    private void sceneInit(SceneInit sceneInit) {
        this.sceneId2SceneMap.put(20003L, getContext().actorOf(application.module.scene.data.domain.Scene.create(20003L)));
    }
}

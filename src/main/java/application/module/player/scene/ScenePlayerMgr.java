package application.module.player.scene;

import akka.actor.ActorRef;
import application.module.player.Player;
import application.module.player.scene.operate.PlayerSceneGetOpt;
import application.module.player.scene.operate.PlayerSceneGetSceneOpt;
import application.module.player.util.IgnoreOpt;
import application.module.player.util.PlayerMgr;
import application.module.scene.SceneModule;
import application.module.scene.SceneProtocols;
import application.module.scene.data.SceneData;
import application.module.scene.data.entity.PositionInfo;
import application.module.scene.data.entity.SceneEntity;
import application.module.scene.operate.*;
import com.cala.orm.cache.AbstractBase;
import com.cala.orm.cache.message.DataGet;
import com.cala.orm.cache.message.DataInsert;
import com.cala.orm.message.OperateType;
import com.google.protobuf.InvalidProtocolBufferException;
import mobius.modular.client.Client;
import protocol.Scene;

import java.util.List;
import java.util.Objects;

/**
 * @author Luo Yong
 * @date 2022-5-17
 * @Source 1.0
 */
public class ScenePlayerMgr implements PlayerMgr {

    private boolean sceneSwitching;

    private ActorRef scene;

    private int preSceneId = SceneModule.MAIN_SCENE;

    private int currSceneId = SceneModule.MAIN_SCENE;

    public final static List<Class<? extends OperateType>> operateTypeList;

    public static List<Integer> messageList;

    static {
        messageList = List.of(SceneProtocols.SCENE_ENTER, SceneProtocols.SCENE_EXIT, SceneProtocols.SCENE_MOVE, SceneProtocols.SCENE_STOP,
                SceneProtocols.SCENE_JUMP, SceneProtocols.SCENE_FLASH, SceneProtocols.AI, SceneProtocols.HELP_USE_SKILL,
                SceneProtocols.RECEIVE, SceneProtocols.PICK_UP_ITEM, SceneProtocols.FUCK_NPC, SceneProtocols.SCENE_RUSH);

        operateTypeList = List.of(PlayerSceneGetOpt.class, PlayerSceneGetSceneOpt.class, PlayerEntrySuccessOpt.class);
    }

    @Override
    public void receiver(Player player, Client.ReceivedFromClient r) {
        if (isSceneSwitching()) {
            return;
        }
        try {
            switch (r.protoID()) {
                case SceneProtocols.SCENE_MOVE -> move(player, r);
                case SceneProtocols.SCENE_STOP -> stop(player, r);
                case SceneProtocols.SCENE_JUMP -> jump(player, r);
                case SceneProtocols.SCENE_FLASH -> flash(player, r);
                case SceneProtocols.SCENE_ENTER -> enterScene(player, r);
                case SceneProtocols.SCENE_EXIT -> exitScene(player, r);
                case SceneProtocols.RECEIVE -> receive(player, r);
                case SceneProtocols.PICK_UP_ITEM -> pickUpItem(player, r);
                case SceneProtocols.FUCK_NPC -> fuckNpc(player, r);
                case SceneProtocols.SCENE_RUSH -> rush(player, r);
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiver(Player player, AbstractBase abstractBase, OperateType p) {
        if (p instanceof PlayerSceneGetOpt playerSceneGetOpt) {
            playerSceneGetOpt(player, (SceneEntity) abstractBase, playerSceneGetOpt);
        } else {
            throw new IllegalStateException("Unexpected value: " + p.getClass().getName());
        }
    }

    @Override
    public void receiver(Player player, OperateType p) {
        if (p instanceof PlayerSceneGetSceneOpt playerSceneGetSceneOpt) {
            playerSceneGetSceneOpt(player, playerSceneGetSceneOpt);
        } else if (p instanceof PlayerEntrySuccessOpt playerEntrySuccessOpt) {
            setScene(playerEntrySuccessOpt.sceneActor());
            setPreSceneId(getCurrSceneId());
            setCurrSceneId(playerEntrySuccessOpt.sceneId());
            setSceneSwitching(false);
        } else {
            throw new IllegalStateException("Unexpected value: " + p.getClass().getName());
        }
    }

    private void playerSceneGetSceneOpt(Player player, PlayerSceneGetSceneOpt p) {
        setCurrSceneId(p.sceneTemplateId());
        setScene(p.scene());
        player.addInitFinalSet(this.getClass());
    }

    private void playerSceneGetOpt(Player player, SceneEntity sceneEntity, PlayerSceneGetOpt playerSceneGetOpt) {
        if (Objects.nonNull(sceneEntity)) {
            this.preSceneId = sceneEntity.getSceneTemplateId();
        } else {
            player.getDataMap().get(SceneData.class).tell(new DataInsert(player.getPlayerActor(),
                    new SceneEntity(player.getId(), this.preSceneId, new PositionInfo()),
                    IgnoreOpt.INSTANCE), player.getPlayerActor());
        }
        getSceneData(player).tell(new PlayerSceneGetSceneOpt(this.preSceneId, null), player.getPlayerActor());
    }

    private void rush(Player player, Client.ReceivedFromClient r) throws InvalidProtocolBufferException {
        var cs10312 = Scene.CS10312.parseFrom(r.message());
        this.scene.tell(new SceneRush(r, cs10312), player.getPlayerActor());
    }

    private void fuckNpc(Player player, Client.ReceivedFromClient r) throws InvalidProtocolBufferException {
        var cs10311 = Scene.CS10311.parseFrom(r.message());
        this.scene.tell(new FuckNpc(r, cs10311, null), player.getPlayerActor());
    }

    private void pickUpItem(Player player, Client.ReceivedFromClient r) throws InvalidProtocolBufferException {
        var cs10310 = Scene.CS10310.parseFrom(r.message());
        this.scene.tell(new PickUpItem(r, cs10310, null), player.getScene());
    }

    private void receive(Player player, Client.ReceivedFromClient r) {
        this.scene.tell(new PlayerReceive(r), player.getPlayerActor());
    }

    private void exitScene(Player player, Client.ReceivedFromClient r) {

    }

    private void flash(Player player, Client.ReceivedFromClient r) throws InvalidProtocolBufferException {
        var cs10306 = Scene.CS10306.parseFrom(r.message());
        this.scene.tell(new SceneFlash(r, cs10306), player.getPlayerActor());
    }

    private void jump(Player player, Client.ReceivedFromClient r) throws InvalidProtocolBufferException {
        var cs10305 = Scene.CS10305.parseFrom(r.message());
        this.scene.tell(new SceneJump(r, cs10305), player.getPlayerActor());
    }

    private void stop(Player player, Client.ReceivedFromClient r) throws InvalidProtocolBufferException {
        var cs10303 = Scene.CS10303.parseFrom(r.message());
        this.scene.tell(new SceneStop(r, cs10303), player.getPlayerActor());
    }

    private void move(Player player, Client.ReceivedFromClient r) throws InvalidProtocolBufferException {
        var cs10302 = Scene.CS10302.parseFrom(r.message());
        this.scene.tell(new SceneMove(r, cs10302), player.getPlayerActor());
    }

    private void enterScene(Player player, Client.ReceivedFromClient r) throws InvalidProtocolBufferException {
        var cs10300 = Scene.CS10300.parseFrom(r.message());
        this.scene.tell(new ScenePlayerEntry(r, cs10300), player.getPlayerActor());
        setSceneSwitching(true);
    }

    public ActorRef getSceneData(Player player) {
        return player.getDataMap().get(SceneData.class);
    }

    @Override
    public void init(Player player) {
        getSceneData(player).tell(new DataGet(player.getPlayerActor(), new SceneEntity(player.getId()),
                new PlayerSceneGetOpt()), player.getPlayerActor());
    }

    @Override
    public void destroy(Player player) {

    }

    public boolean isSceneSwitching() {
        return sceneSwitching;
    }

    //get and set

    @Override
    public List<Integer> getProtoIds() {
        return messageList;
    }

    @Override
    public List<Class<? extends OperateType>> getOperateTypes() {
        return operateTypeList;
    }


    public void setSceneSwitching(boolean sceneSwitching) {
        this.sceneSwitching = sceneSwitching;
    }

    public ActorRef getScene() {
        return scene;
    }

    public void setScene(ActorRef scene) {
        this.scene = scene;
    }

    public int getPreSceneId() {
        return preSceneId;
    }

    public void setPreSceneId(int preSceneId) {
        this.preSceneId = preSceneId;
    }

    public int getCurrSceneId() {
        return currSceneId;
    }

    public void setCurrSceneId(int currSceneId) {
        this.currSceneId = currSceneId;
    }
}

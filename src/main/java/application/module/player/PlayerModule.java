package application.module.player;

import akka.actor.ActorRef;
import application.guid.IdUtils;
import application.module.common.data.domain.DataMessage;
import application.module.player.fight.attribute.data.AttributeData;
import application.module.player.data.PlayerEntityData;
import application.module.player.data.entity.PlayerData;
import application.module.player.data.entity.PlayerEntity;
import application.module.player.data.entity.PlayerInfo;
import application.module.player.data.message.event.PlayerLogin;
import application.module.player.data.message.event.PlayerLogout;
import application.module.player.data.message.event.PlayerRegister;
import application.module.player.operate.*;
import application.module.player.operate.info.PlayerOperateTypeInfo;
import application.module.scene.data.SceneData;
import application.module.scene.operate.event.CreatePlayerEntitiesAfter;
import application.util.CommonOperateTypeInfo;
import com.cala.orm.cache.message.DataGet;
import com.cala.orm.cache.message.DataInsert;
import com.cala.orm.message.*;
import com.google.protobuf.InvalidProtocolBufferException;
import mobius.modular.client.Client;
import mobius.modular.module.api.AbstractModule;
import protocol.Player;

import java.util.*;

/**
 * @author Luo Yong
 * @date 2022-1-14
 * @Source 1.0
 */
public class PlayerModule extends AbstractModule {

    private ActorRef playerEntityData;
    private ActorRef sceneData;
    private Map<Class<?>, ActorRef> dataMap;
    private final Map<Long, ActorRef> playerActorMap = new HashMap<>();

    @Override
    public void initData() {
        this.dataAgent().tell(new DataMessage.RequestData(PlayerEntityData.class), self());
        this.dataAgent().tell(new DataMessage.RequestData(SceneData.class), self());
        this.dataAgent().tell(new DataMessage.RequestData(AttributeData.class), self());
        this.dataAgent().tell(new DataMessage.RequestAllData(), self());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(DataMessage.DataResult.class, this::dataResult)
                .match(Client.ReceivedFromClient.class, this::onReceiveFromClient)
                .match(DataReturnMessage.class, this::dataResultMessage)
                .match(DataReturnManyMessage.class, this::dataResultManyMessage)
                .match(CreatePlayerEntitiesAfter.class, this::createPlayerEntitiesAfter)
                .match(DataMessage.AllDataResult.class, this::allDataResult)
                .build();
    }

    private void allDataResult(DataMessage.AllDataResult allDataResult) {
        setDataMap(allDataResult.map());
    }

    private void createPlayerEntitiesAfter(CreatePlayerEntitiesAfter createPlayerEntitiesAfter) {
        this.playerEntityData.tell(createPlayerEntitiesAfter, self());
    }

    private void dataResultManyMessage(DataReturnManyMessage dataReturnManyMessage) {
        OperateType operateType = dataReturnManyMessage.operateType();
        if (dataReturnManyMessage.result().isOK()) {
            switch (operateType) {
                case PlayerGetAllType playerGetAllType -> playerGetAll(dataReturnManyMessage, playerGetAllType);
                case PlayerCreateSelectType playerCreateSelectType -> playerCreateSelectType(dataReturnManyMessage, playerCreateSelectType);
                default -> getLog().error(new IllegalStateException(), "Unexpected value: " + operateType);
            }
        } else {
            switch (operateType) {
                case PlayerGetAllType playerGetAllType -> playerGetAllTypeError(playerGetAllType);
                case PlayerCreateSelectType playerCreateSelectType -> playerCreateSelectType(dataReturnManyMessage, playerCreateSelectType);
                default -> throw new IllegalStateException("Unexpected value: " + operateType);
            }
        }
    }

    private void dataResultMessage(DataReturnMessage dataReturnMessage) {
        OperateType operateType = dataReturnMessage.operateType();
        if (dataReturnMessage.result().isOK()) {
            switch (operateType) {
                case PlayerCreateInsertType playerCreateInsertType -> playerCreateInsertType(dataReturnMessage, playerCreateInsertType);
                case PlayerLoginSelectType playerLoginSelectType -> playerLoginSelectType(dataReturnMessage, playerLoginSelectType);
                default -> getLog().error(new IllegalStateException(), "Unexpected value: " + operateType);
            }
        } else {
            switch (operateType) {
                case PlayerCreateInsertType playerCreateInsertType -> playerCreateInsertTypeError(playerCreateInsertType);
                case PlayerLoginSelectType playerLoginSelectType -> playerLoginSelectTypeError(playerLoginSelectType);
                default -> throw new IllegalStateException("Unexpected value: " + operateType);
            }
        }
    }

    private void playerLoginSelectTypeError(PlayerLoginSelectType playerLoginSelectType) {
        var commonOperateTypeInfo = (CommonOperateTypeInfo) playerLoginSelectType.operateTypeInfo();
        var r = commonOperateTypeInfo.r();
        r.client().tell(new application.client.Client.SendToClientJ(PlayerProtocols.LOGIN,
                PlayerProtocolBuilder.getSc10022(false, "i don`t know.")), self());
    }

    private void playerCreateInsertTypeError(PlayerCreateInsertType playerCreateInsertType) {
        var commonOperateTypeInfo = (CommonOperateTypeInfo) playerCreateInsertType.operateTypeInfo();
        var r = commonOperateTypeInfo.r();
        r.client().tell(new application.client.Client.SendToClientJ(PlayerProtocols.CREATE,
                PlayerProtocolBuilder.getSc10021(false, "i don`t know.")), self());
    }

    private void playerGetAllTypeError(PlayerGetAllType playerGetAllType) {
        var commonOperateTypeInfo = (CommonOperateTypeInfo) playerGetAllType.operateTypeInfo();
        var r = commonOperateTypeInfo.r();
        r.client().tell(new application.client.Client.SendToClientJ(PlayerProtocols.GETAll,
                PlayerProtocolBuilder.getSc10020(new ArrayList<>())), self());
    }

    private void playerLoginSelectType(DataReturnMessage dataReturnMessage, PlayerLoginSelectType playerLoginSelectType) {
        var commonOperateTypeInfo = (CommonOperateTypeInfo) playerLoginSelectType.operateTypeInfo();
        login(commonOperateTypeInfo.r(), (PlayerEntity) dataReturnMessage.message());
    }

    private void playerCreateInsertType(DataReturnMessage dataReturnMessage, PlayerCreateInsertType playerCreateInsertType) {
        //TODO 处理注册成功后的情况
        var playerOperateTypeInfo = (PlayerOperateTypeInfo) playerCreateInsertType.operateTypeInfo();
        PlayerEntity playerEntity = (PlayerEntity) dataReturnMessage.message();
        this.playerEntityData.tell(new Publish(new PlayerRegister(playerOperateTypeInfo.r(), playerEntity.getPlayerInfo())), self());
        playerOperateTypeInfo.r().client().tell(new application.client.Client.SendToClientJ(PlayerProtocols.CREATE,
                PlayerProtocolBuilder.getSc10021(true, playerEntity.getId())), self());
    }

    private void playerCreateSelectType(DataReturnManyMessage dataReturnManyMessage, PlayerCreateSelectType playerCreateSelectType) {
        var commonOperateTypeInfo = (CommonOperateTypeInfo) playerCreateSelectType.operateTypeInfo();
        var r = commonOperateTypeInfo.r();
        var cs10021 = (Player.CS10021) commonOperateTypeInfo.message();
        List<PlayerEntity> playerEntityList = new ArrayList<>();
        dataReturnManyMessage.list().forEach(e -> playerEntityList.add((PlayerEntity) e));
        for (PlayerEntity playerEntity : playerEntityList) {
            if (playerEntity.getProfession() == cs10021.getProfession()) {
                r.client().tell(new application.client.Client.SendToClientJ(PlayerProtocols.CREATE,
                        PlayerProtocolBuilder.getSc10021(false, "职业已经存在呢，亲~~~~")), self());
                return;
            }
        }
        PlayerEntity playerEntity = createPlayerEntity(cs10021, r.uID());
        playerEntityData.tell(new DataInsert(self(), playerEntity,
                new PlayerCreateInsertType(new PlayerOperateTypeInfo(commonOperateTypeInfo.r(), playerEntity))), self());
    }

    private PlayerEntity createPlayerEntity(Player.CS10021 cs10021, long uId) {
        long id = IdUtils.fastSimpleUUIDLong();
        byte gender = (byte) cs10021.getGender();
        byte profession = (byte) cs10021.getProfession();
        long lastLoginTime = System.currentTimeMillis();
        return new PlayerEntity.Builder(id)
                .setName(cs10021.getName())
                .setAccountId(uId)
                .setGender(gender)
                .setProfession(profession)
                .setLevel(1)
                .setLastLoginTime(lastLoginTime)
                .setPlayerInfo(new PlayerInfo(id, cs10021.getName(), 1, gender, profession, lastLoginTime))
                .setPlayerData(new PlayerData(cs10021.getName(), 0))
                .build();
    }

    private void playerGetAll(DataReturnManyMessage dataReturnMessage, PlayerGetAllType playerGetAllType) {
        List<PlayerEntity> playerEntityList = new ArrayList<>();
        dataReturnMessage.list().forEach(e -> playerEntityList.add((PlayerEntity) e));
        var commonOperateTypeInfo = (CommonOperateTypeInfo) playerGetAllType.operateTypeInfo();
        var r = commonOperateTypeInfo.r();
        r.client().tell(new application.client.Client.SendToClientJ(PlayerProtocols.GETAll,
                PlayerProtocolBuilder.getSc10020(playerEntityList)), self());
    }

    private void onReceiveFromClient(Client.ReceivedFromClient r) {
        int pId = r.protoID();
        switch (pId) {
            case PlayerProtocols.GETAll -> getAllRole(r);
            case PlayerProtocols.CREATE -> create(r);
            case PlayerProtocols.LOGIN -> loginSelect(r);
            case PlayerProtocols.DELETE -> delete(r);
            case PlayerProtocols.LOGOUT -> logout(r);
        }
    }

    private void logout(Client.ReceivedFromClient r) {
        ActorRef actorRef = playerActorMap.get(r.uID());
        if (Objects.nonNull(actorRef)) {
            getContext().stop(actorRef);
            playerActorMap.remove(r.uID());
        }
        this.playerEntityData.tell(new Publish(new PlayerLogout(r)), self());
    }

    private void delete(Client.ReceivedFromClient r) {

    }

    private void loginSelect(Client.ReceivedFromClient r) {
        try {
            Player.CS10022 cs10022 = Player.CS10022.parseFrom(r.message());
            var playerEntity = PlayerEntity.of(cs10022.getRoleId());
            playerEntityData.tell(new DataGet(self(), playerEntity,
                    new PlayerLoginSelectType(new CommonOperateTypeInfo(r, cs10022))), self());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private void login(Client.ReceivedFromClient r, PlayerEntity playerEntity) {
        long playerId = playerEntity.getId();
        ActorRef oldPlayerActor = playerActorMap.get(playerId);
        if (Objects.nonNull(oldPlayerActor)) {
            getContext().stop(oldPlayerActor);
        }
        playerActorMap.put(playerId, r.client());
        ActorRef playerActor = getContext().actorOf(PlayerActor.props(playerEntity));
        playerActor.tell(new PlayerInit(r.client(), getDataMap()), self());
        r.client().tell(new application.client.Client.SendToClientJ(PlayerProtocols.LOGIN,
                PlayerProtocolBuilder.getSc10022(true, playerEntity.getId())), self());
        this.playerEntityData.tell(new Publish(new PlayerLogin(r, playerEntity.getPlayerInfo())), self());
        r.client().tell(new application.client.Client.LoginSuccess(playerEntity.getId()), self());
    }

    private void create(Client.ReceivedFromClient r) {
        try {
            Player.CS10021 cs10021 = Player.CS10021.parseFrom(r.message());
            if (!PlayerConfig.Gender.valid((byte) cs10021.getGender()) || !PlayerConfig.Profession.valid((byte) cs10021.getProfession())) {
                r.client().tell(new application.client.Client.SendToClientJ(PlayerProtocols.CREATE,
                        PlayerProtocolBuilder.getSc10021(false, "性别或者职业有问题哦~")), self());
                return;
            }
            //TODO 名字是否已存在或者非法认证
            var playerEntity = PlayerEntity.of(r.uID());
            playerEntityData.tell(new PlayerDataMessage.PlayerByUserId(self(),
                    playerEntity, new PlayerCreateSelectType(new CommonOperateTypeInfo(r, cs10021))), self());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private void getAllRole(Client.ReceivedFromClient r) {
        var playerEntity = PlayerEntity.of(r.uID());
        playerEntityData.tell(new PlayerDataMessage.PlayerByUserId(self(),
                playerEntity, new PlayerGetAllType(new CommonOperateTypeInfo(r, null))), self());
    }

    private void dataResult(DataMessage.DataResult dataResult) {
        if (dataResult.clazz() == PlayerEntityData.class) {
            this.playerEntityData = dataResult.actorRef();
        }else if (dataResult.clazz() == SceneData.class) {
            this.sceneData = dataResult.actorRef();
            this.sceneData.tell(new SubscribeEvent(CreatePlayerEntitiesAfter.class, self()), self());
        }
    }

    //get and set

    public Map<Class<?>, ActorRef> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<Class<?>, ActorRef> dataMap) {
        this.dataMap = dataMap;
    }
}

package application.module.player;

import akka.actor.ActorRef;
import application.guid.IdUtils;
import application.module.common.data.domain.DataMessage;
import application.module.player.data.PlayerEntityData;
import application.module.player.data.domain.PlayerEntity;
import application.module.player.operate.PlayerCreateInsertType;
import application.module.player.operate.PlayerCreateSelectType;
import application.module.player.operate.PlayerGetAllType;
import application.module.player.operate.PlayerLoginSelectType;
import application.module.player.operate.info.PlayerOperateTypeInfo;
import application.util.CommonOperateTypeInfo;
import com.cala.orm.cache.message.DataGet;
import com.cala.orm.cache.message.DataInsert;
import com.cala.orm.message.DataReturnMessage;
import com.cala.orm.message.OperateType;
import com.google.protobuf.InvalidProtocolBufferException;
import mobius.modular.client.Client;
import mobius.modular.module.api.AbstractModule;
import protocol.Player;

import java.util.List;

/**
 * @author Luo Yong
 * @date 2022-1-14
 * @Source 1.0
 */
public class PlayerModule extends AbstractModule {

    private ActorRef playerEntityData;

    @Override
    public void initData() {
        this.dataAgent().tell(new DataMessage.RequestData(PlayerEntityData.class), self());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(DataMessage.DataResult.class, this::dataResult)
                .match(Client.ReceivedFromClient.class, this::onReceiveFromClient)
                .match(DataReturnMessage.class, this::dataResultMessage)
                .build();
    }

    private void dataResultMessage(DataReturnMessage dataReturnMessage) {
        OperateType operateType = dataReturnMessage.operateType();
        if (dataReturnMessage.result().isOK()) {
            switch (operateType) {
                case PlayerGetAllType playerGetAllType -> playerGetAll(dataReturnMessage, playerGetAllType);
                case PlayerCreateSelectType playerCreateSelectType -> playerCreateSelectType(dataReturnMessage, playerCreateSelectType);
                case PlayerCreateInsertType playerCreateInsertType -> playerCreateInsertType(dataReturnMessage, playerCreateInsertType);
                case PlayerLoginSelectType playerLoginSelectType -> playerLoginSelectType(dataReturnMessage, playerLoginSelectType);
                default -> getLog().error(new IllegalStateException(), "Unexpected value: " + operateType);
            }
        }
    }

    private void playerLoginSelectType(DataReturnMessage dataReturnMessage, PlayerLoginSelectType playerLoginSelectType) {
        var commonOperateTypeInfo = (CommonOperateTypeInfo) playerLoginSelectType.operateTypeInfo();
        login(commonOperateTypeInfo.r(), (PlayerEntity) dataReturnMessage.message());
    }

    private void playerCreateInsertType(DataReturnMessage dataReturnMessage, PlayerCreateInsertType playerCreateInsertType) {
        //TODO 处理注册成功后的情况
        if (dataReturnMessage.result().isOK()) {
            var playerOperateTypeInfo = (PlayerOperateTypeInfo) playerCreateInsertType.operateTypeInfo();
            //注册成功直接登入
            login(playerOperateTypeInfo.r(), playerOperateTypeInfo.playerEntity());
        }
    }

    private void playerCreateSelectType(DataReturnMessage dataReturnMessage, PlayerCreateSelectType playerCreateSelectType) {
        var commonOperateTypeInfo = (CommonOperateTypeInfo) playerCreateSelectType.operateTypeInfo();
        var r = commonOperateTypeInfo.r();
        var cs10021 = (Player.CS10021) commonOperateTypeInfo.message();
        List<PlayerEntity> playerEntityList = (List<PlayerEntity>) dataReturnMessage.message();
        for (PlayerEntity playerEntity : playerEntityList) {
            if (playerEntity.getProfession() == cs10021.getProfession()) {
                return;
            }
        }
        PlayerEntity playerEntity = createPlayerEntity(cs10021, r.uID());
        playerEntityData.tell(new DataInsert(self(), playerEntity,
                new PlayerCreateInsertType(new PlayerOperateTypeInfo(commonOperateTypeInfo.r(), playerEntity))), self());
    }

    private PlayerEntity createPlayerEntity(Player.CS10021 cs10021, long uId) {
        return new PlayerEntity.Builder(IdUtils.fastSimpleUUIDLong())
                .setName(cs10021.getName())
                .setAccountId(uId)
                .setGender((byte) cs10021.getGender())
                .setProfession((byte) cs10021.getProfession())
                .setLastLoginTime(System.currentTimeMillis())
                .build();
    }

    private void playerGetAll(DataReturnMessage dataReturnMessage, PlayerGetAllType playerGetAllType) {
        List<PlayerEntity> playerEntityList = (List<PlayerEntity>) dataReturnMessage.message();
        var commonOperateTypeInfo = (CommonOperateTypeInfo) playerGetAllType.operateTypeInfo();
        var r = commonOperateTypeInfo.r();
        r.client().tell(new application.client.Client.SendToClientJ(PlayerProtocols.GETAll, PlayerProtocolBuilder.getSc10020(playerEntityList)), self());
    }

    private void onReceiveFromClient(Client.ReceivedFromClient r) {
        int pId = r.protoID();
        switch (pId) {
            case PlayerProtocols.GETAll -> getAllRole(r);
            case PlayerProtocols.CREATE -> create(r);
            case PlayerProtocols.LOGIN -> loginSelect(r);
            case PlayerProtocols.DELETE -> delete(r);
        }
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

    }

    private void create(Client.ReceivedFromClient r) {
        try {
            Player.CS10021 cs10021 = Player.CS10021.parseFrom(r.message());
            if (PlayerConfig.Gender.valid((byte) cs10021.getGender()) || PlayerConfig.Profession.valid((byte) cs10021.getProfession())) {
                return;
            }
            //TODO 名字是否已存在或者非法认证
            var playerEntity = PlayerEntity.of(r.uID());
            playerEntityData.tell(new PlayerDataMessage.PlayerByUserId(self(), playerEntity, new PlayerCreateSelectType(new CommonOperateTypeInfo(r, cs10021))), self());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private void getAllRole(Client.ReceivedFromClient r) {
        var playerEntity = PlayerEntity.of(r.uID());
        playerEntityData.tell(new PlayerDataMessage.PlayerByUserId(self(), playerEntity, new PlayerGetAllType(new CommonOperateTypeInfo(r, null))), self());
    }

    private void dataResult(DataMessage.DataResult dataResult) {
        if (dataResult.clazz() == PlayerEntityData.class) {
            this.playerEntityData = dataResult.actorRef();
        }
    }
}

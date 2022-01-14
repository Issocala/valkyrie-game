package application.module.example;

import akka.actor.ActorRef;
import application.data.AccountData;
import application.data.ActorDataDispatcher;
import application.data.DataAgent;
import application.module.common.data.domain.DataMessage;
import application.module.example.operate.GetByUseIdType;
import application.module.example.operate.GetType;
import application.module.example.operate.SaveType;
import application.module.player.base.data.PlayerEntityData;
import application.module.player.base.domain.Person;
import application.module.player.base.domain.PlayerDataMessage;
import application.module.player.base.domain.PlayerEntity;
import application.module.player.base.domain.PlayerInfo;
import application.module.user.UserData;
import com.cala.orm.cache.DbStatus;
import com.cala.orm.cache.SimpleAbstractEntityBase;
import com.cala.orm.message.DataBaseMessage;
import com.cala.orm.message.DataReturnMessage;
import com.cala.orm.message.MessageAndReply;
import com.google.protobuf.InvalidProtocolBufferException;
import mobius.modular.client.Client;
import mobius.modular.module.api.AbstractModule;
import protocol.P1;
import scala.Option;
import template.TestTemplateHolder;

import java.util.List;

/**
 * Created by RXL on 2021/12/1.
 * Maintainer:
 * RXL
 */
public class ExampleModule extends AbstractModule {

    private ActorRef userData;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        Client.ReceivedFromClient.class, r -> {
                            onReceiveFromClient(r);
                            getLog().debug("ReceivedFromClient :: " + r.uID());
                        })
                .match(DataMessage.DataResult.class, r -> {
                    if (r.clazz() == UserData.class) {
                        userData = r.actorRef();
                        System.out.println("accountDta: " + userData);
                        getLog().debug("accountDta: " + userData);
                    } else {
                        getLog().debug("xxx");
                    }
                })
                .match(DataReturnMessage.class, message -> {
                    if (message.result().isOK()) {
                        switch (message.operateType()) {
                            case GetType ignored -> {
                                PlayerEntity playerEntity = (PlayerEntity) message.message();
                                getLog().debug("playerEntry: " + playerEntity);
                                TestTemplateHolder.getData(1);
                            }
                            case SaveType ignored -> getLog().debug("dd");
                            case GetByUseIdType ignored -> {

                                List<PlayerEntity> playerEntityList = (List<PlayerEntity>) message.message();;
                            }
                            default -> System.out.println();
                        }
                    }
                })
                .build();
    }

    private void onReceiveFromClient(Client.ReceivedFromClient r) {
        var protocol = r.protoID();
        switch (protocol) {
            case 5 -> add(r);
            case 6 -> delete(r);
            case 7 -> update(r);
        }
    }

    private void update(Client.ReceivedFromClient r) {

    }

    private void delete(Client.ReceivedFromClient r) {

    }

    private void add(Client.ReceivedFromClient r) {
        try {
            var msg = P1.cs4.parseFrom(r.message());
            var account = msg.getAccount().getAccount();
            log().info(account);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData() {
        this.dataAgent().tell(new DataMessage.RequestData(UserData.class), self());
        DataBaseMessage.Get get = new DataBaseMessage.Get(
                new MessageAndReply(self(),
                        new PlayerEntity(1L, "x", (byte) 1, 1L, "1", 1, (byte) 1, (byte) 1, 1L, 1L, DbStatus.SAVE,
                                new PlayerInfo("x"), new Person.Builder().setAge(1).setName("wo").build()), GetType.INSTANCE));
        PlayerDataMessage.PlayerByUserId playerByUserId = new PlayerDataMessage.PlayerByUserId(new MessageAndReply(self(), new SimpleAbstractEntityBase(1L, DbStatus.NORMAL), GetByUseIdType.INSTANCE), "");
        this.dataAgent().tell(new ActorDataDispatcher.Message(PlayerEntityData.class, get, self()), self());
    }
}

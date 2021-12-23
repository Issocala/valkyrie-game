package application.module.example;

import akka.actor.ActorRef;
import application.data.AccountData;
import application.data.ActorDataDispatcher;
import application.data.DataAgent;
import application.module.player.base.domain.Person;
import application.module.player.base.domain.PlayerEntity;
import application.module.player.base.domain.PlayerInfo;
import application.module.player.base.data.PlayerEntityData;
import application.module.example.operate.GetType;
import application.module.example.operate.SaveType;
import com.cala.orm.cache.DbStatus;
import com.cala.orm.message.DataBaseMessage;
import com.cala.orm.message.DataReturnMessage;
import com.cala.orm.message.MessageAndReply;
import mobius.modular.module.api.AbstractModule;
import mobius.modular.client.Client;
import com.google.protobuf.InvalidProtocolBufferException;
import protocol.P1;
import scala.Option;


/**
 * Created by RXL on 2021/12/1.
 * Maintainer:
 * RXL
 */
public class ExampleModule extends AbstractModule {

    record DataResult<T>(T ds, Option<ActorRef> r) {
    }

    private ActorRef accountData;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        Client.ReceivedFromClient.class, r -> {
                            onReceiveFromClient(r);
                            getLog().debug("ReceivedFromClient :: " + r.uID());
                        })
                .match(DataResult.class, r -> {
                    if (r.ds() instanceof AccountData) {
                        accountData = (ActorRef) r.r().get();
                        System.out.println("accountDta: " + accountData);
                        getLog().debug("accountDta: " + accountData);
                    } else {
                        getLog().debug("xxx");
                    }
                })
                .match(DataReturnMessage.class, message -> {
                    if (message.success()) {
                        switch (message.operateType()) {
                            case GetType ignored -> {
                                PlayerEntity playerEntity = (PlayerEntity) message.abstractEntityBase();
                                getLog().debug("playerEntry: " + playerEntity);
                            }
                            case SaveType ignored -> getLog().debug("dd");
                            default -> System.out.println();
                        }
                    }
                })
//                .match(Object.class , o ->{
//                    getLog().debug("xxx"+o.getClass());
//                })
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
        this.dataAgent().tell(new DataAgent.RequestData<>(AccountData.getInstance()), sender());
        DataBaseMessage.Get get = new DataBaseMessage.Get(
                new MessageAndReply(self(),
                        new PlayerEntity(1L, "x", DbStatus.SAVE, new PlayerInfo("x"), new Person.Builder().setAge(1).setName("wo").build()), GetType.INSTANCE));
        dataAgent().tell(new ActorDataDispatcher.Message(PlayerEntityData.class, get, self()), self());
    }
}

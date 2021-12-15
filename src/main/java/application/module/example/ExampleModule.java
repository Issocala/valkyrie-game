package application.module.example;

import akka.actor.ActorRef;
import application.data.AccountData;
import application.data.DataAgent;
import mobius.modular.module.api.AbstractModule;
import mobius.modular.client.Client;
import com.google.protobuf.InvalidProtocolBufferException;
import protocol.P1;


/**
 * Created by RXL on 2021/12/1.
 * Maintainer:
 * RXL
 */
public class ExampleModule extends AbstractModule {


    private ActorRef accountData;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        Client.ReceivedFromClient.class, r -> {
                            onReceiveFromClient(r);
                            getLog().debug("ReceivedFromClient :: " + r.uID());
                        })
                .match(DataAgent.DataResult.class, r -> {
                    if (r.ds() instanceof AccountData) {
                        accountData = (ActorRef) r.r().get();
                        System.out.println("accountDta: " + accountData);
                        getLog().debug("accountDta: " + accountData);
                    } else {
                        getLog().debug("xxx");
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
            var msg = P1.cs5.parseFrom(r.message());
            var account = msg.getToAdd().getAccount();
            log().info(account);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData() {
        this.dataAgent().tell(new DataAgent.RequestData<>(AccountData.getInstance()), sender());
    }
}

package application.module.gm.cmd;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import application.module.gm.GmProtocolBuilder;
import application.module.gm.GmProtocols;
import application.module.gm.domain.GmCmdArgs;
import application.module.gm.domain.GmCmdResult;
import application.module.gm.message.GmCmdHandle;
import application.module.gm.message.GmCmdInit;
import application.module.gm.message.GmCmdInitFinish;
import application.module.gm.message.info.GmCmdInfo;
import mobius.modular.client.Client;
import protocol.Gm;

/**
 * @author HRT
 * @date 2022-4-11
 */
public abstract class GmCmd extends AbstractActor {

    private ActorRef dataAgent;

    protected abstract String getCmd();

    protected abstract String getDesc();

    protected abstract String getExample();

    protected abstract void onInit();

    protected abstract void onReceive(Object msg);

    protected abstract GmCmdResult onHandle(Client.ReceivedFromClient r, GmCmdArgs args);

    protected ActorRef dataAgent() {
        return dataAgent;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(GmCmdInit.class, this::onGmCmdInit)
                .match(GmCmdHandle.class, this::onGmCmdHandle)
                .matchAny(this::onReceive)
                .build();
    }

    private void onGmCmdInit(GmCmdInit msg) {
        dataAgent = msg.dataAgent();
        sender().tell(new GmCmdInitFinish(new GmCmdInfo(getCmd(), getDesc(), getExample(), self())), self());
        onInit();
    }

    private void onGmCmdHandle(GmCmdHandle msg) {
        Client.ReceivedFromClient r = msg.receivedFromClient();
        GmCmdResult gmCmdResult = onHandle(r, new GmCmdArgs(msg.args()));
        if (gmCmdResult != null) {
            sendGmCmdResult(r, gmCmdResult);
        }
    }

    protected void sendGmCmdResult(Client.ReceivedFromClient r, GmCmdResult gmCmdResult) {
        Gm.SC10090 sc10090 = GmProtocolBuilder.getSc10090(gmCmdResult);
        r.client().tell(new application.client.Client.SendToClientJ(GmProtocols.CMD, sc10090), self());
    }

}

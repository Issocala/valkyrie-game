package application.module.gm;

import akka.actor.ActorRef;
import akka.actor.Props;
import application.module.gm.cmd.GmCmd;
import application.module.gm.message.GmCmdHandle;
import application.module.gm.message.GmCmdInit;
import application.module.gm.message.GmCmdInitFinish;
import application.module.gm.message.info.GmCmdInfo;
import com.cala.orm.util.ClassScanningUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import com.typesafe.config.Config;
import mobius.modular.client.Client;
import mobius.modular.module.api.AbstractModule;
import protocol.Gm;
import util.Conf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author HRT
 * @date 2022-4-11
 */
public class GmModule extends AbstractModule {

    private static final String GM = "gs-app.gm";
    private static final String CMD_HELP = "help";

    private boolean enable;

    private final Map<String, GmCmdInfo> map = new HashMap<>();

    @Override
    public void initData() {
        Config config = Conf.config();
        enable = config.getBoolean(GM);
        if (enable) {
            onInitGmCmd();
        }
    }

    private void onInitGmCmd() {
        GmCmdInit gmCmdInit = new GmCmdInit(dataAgent());
        Set<Class<?>> gmCmdClasses = ClassScanningUtil.loopFindClassBySuper(GmCmd.class.getPackageName() + ".impl", GmCmd.class);
        for (Class<?> gmCmdClass : gmCmdClasses) {
            ActorRef actorRef = getContext().actorOf(Props.create(gmCmdClass), gmCmdClass.getName());
            actorRef.tell(gmCmdInit, self());
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(GmCmdInitFinish.class, this::onGmCmdInitFinish)
                .match(Client.ReceivedFromClient.class, this::onReceivedFromClient)
                .build();
    }

    private void onGmCmdInitFinish(GmCmdInitFinish msg) {
        GmCmdInfo info = msg.info();
        map.put(info.cmd(), info);
    }

    private void onReceivedFromClient(Client.ReceivedFromClient r) {
        int protoID = r.protoID();
        if (protoID == GmProtocols.CMD && enable) {
            // TODO 判断账号是否具有GM权限
            cmd(r);
        }
    }

    private void cmd(Client.ReceivedFromClient r) {
        try {
            Gm.CS10090 cs10090 = Gm.CS10090.parseFrom(r.message());
            String cmd = cs10090.getCmd();
            List<String> args = cs10090.getArgsList();
            GmCmdInfo gmCmdInfo = map.get(cmd);
            if (gmCmdInfo != null) {
                gmCmdInfo.gmCmd().tell(new GmCmdHandle(r, args), self());
            } else if (CMD_HELP.equals(cmd)) {
                cmdHelp(r);
            } else {
                getLog().error("cmd[{}] not found", cmd);
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private void cmdHelp(Client.ReceivedFromClient r) {
        List<String> details = map.values().stream().map(GmCmdInfo::toDesc).toList();
        Gm.SC10090 sc10090 = GmProtocolBuilder.getSc10090(true, details);
        r.client().tell(new application.client.Client.SendToClientJ(GmProtocols.CMD, sc10090), self());
    }

}

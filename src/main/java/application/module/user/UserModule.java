package application.module.user;

import akka.actor.ActorRef;
import application.guid.UUID;
import application.module.common.data.domain.DataMessage;
import application.module.user.data.UserData;
import application.module.user.data.domain.User;
import application.module.user.operate.UserGet;
import application.module.user.operate.UserOperateTypeInfo;
import com.cala.orm.message.DataBaseMessage;
import com.cala.orm.message.DataReturnMessage;
import com.cala.orm.message.MessageAndReply;
import com.google.protobuf.InvalidProtocolBufferException;
import mobius.core.digest.Codec;
import mobius.modular.client.Client;
import mobius.modular.module.api.AbstractModule;
import protocol.P1;

/**
 * @author Luo Yong
 * @date 2022-1-17
 * @Source 1.0
 */
public class UserModule extends AbstractModule {

    private ActorRef userData;

    @Override
    public void initData() {
        this.dataAgent().tell(new DataMessage.RequestData(UserData.class), self());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Client.ReceivedFromClient.class, this::receivedFromClient)
                .match(DataMessage.DataResult.class, this::dataResult)
                .match(DataReturnMessage.class, this::dataResultMessage)
                .build();
    }

    private void dataResultMessage(DataReturnMessage dataReturnMessage) {
        if (dataReturnMessage.result().isOK()) {
            if (dataReturnMessage.operateType() instanceof UserGet userGet) {
                var user = (User) dataReturnMessage.message();
                var userOperateTypeInfo = (UserOperateTypeInfo) userGet.operateTypeInfo();
                var cs1 = userOperateTypeInfo.cs1();
                if (user.getPassword().equals(codecPassword(cs1.getAccount().getPassword()))) {
                    userOperateTypeInfo.r().client().tell(
                            new application.client.Client.SendToClientJ(UserProtocols.LOGIN,
                                    P1.sc2.newBuilder().setSuccess(true).setUserId(user.getId()).build()), self());
                }
            }
        }
    }

    private void dataResult(DataMessage.DataResult dataResult) {
        if (dataResult.clazz() == UserData.class) {
            this.userData = dataResult.actorRef();
        }
    }

    private void receivedFromClient(Client.ReceivedFromClient r) {
        int id = r.protoID();
        switch (id) {
            case UserProtocols.REGISTER -> register(r);
            case UserProtocols.LOGIN -> login(r);
        }
    }

    private void login(Client.ReceivedFromClient r) {
        try {
            var cs1 = P1.cs1.parseFrom(r.message());
//            var user = new User(UUID.fastUUID().getLeastSignificantBits(), DbStatus.NORMAL, cs1.getAccount().getAccount(), cs1.getName(), codecPassword(cs1.getAccount().getPassword()), null, 1);
            var user = User.of(UUID.fastUUID().getLeastSignificantBits());
            this.userData.tell(new DataBaseMessage.Get(new MessageAndReply(self(), user, UserGet.INSTANCE)), self());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private String codecPassword(String password) {
        return Codec.md5(password);
    }

    private void register(Client.ReceivedFromClient r) {

    }
}

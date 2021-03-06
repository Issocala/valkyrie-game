package application.module.user;

import akka.actor.ActorRef;
import application.guid.IdUtils;
import application.module.common.data.entity.DataMessage;
import application.module.user.data.UserData;
import application.module.user.data.entity.User;
import application.module.user.data.message.UserGetByAccount;
import application.module.user.data.message.UserInsertByAccount;
import application.module.user.operate.UserLoginType;
import application.module.user.operate.UserRegisterType;
import application.util.CommonOperateTypeInfo;
import com.cala.orm.message.DataReturnMessage;
import com.cala.orm.message.OperateType;
import com.cala.orm.util.StringUtils;
import com.google.protobuf.InvalidProtocolBufferException;
import mobius.core.digest.Codec;
import mobius.modular.client.Client;
import mobius.modular.module.api.AbstractModule;

import java.util.Objects;

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
        OperateType operateType = dataReturnMessage.operateType();
        if (dataReturnMessage.result().isOK()) {
            switch (operateType) {
                case UserLoginType userLoginType -> userLoginOk(userLoginType, dataReturnMessage);
                case UserRegisterType userRegister -> userRegisterOk(userRegister);
                default -> getLog().error(new IllegalStateException(), "Unexpected value: " + operateType);
            }
        } else {
            switch (operateType) {
                case UserLoginType userLoginType -> userLoginTypeError(userLoginType);
                case UserRegisterType userRegisterType -> userRegisterTypeError(userRegisterType);
                default -> getLog().error(new IllegalStateException(), "Unexpected value: " + operateType);
            }
        }
    }

    private void userRegisterOk(UserRegisterType userRegister) {
        var commonOperateTypeInfo = (CommonOperateTypeInfo) userRegister.operateTypeInfo();
        commonOperateTypeInfo.r().client().tell(new application.client.Client.SendToClientJ(UserProtocols.REGISTER, UserProtocolBuilder.getSc10010(true)), self());
    }

    private void userRegisterTypeError(UserRegisterType userRegisterType) {
        var commonOperateTypeInfo = (CommonOperateTypeInfo) userRegisterType.operateTypeInfo();
        commonOperateTypeInfo.r().client().tell(new application.client.Client.SendToClientJ(UserProtocols.REGISTER, UserProtocolBuilder.getSc10010(false, "????????????????????????")), self());
    }

    private void userLoginTypeError(UserLoginType userLoginType) {
        var commonOperateTypeInfo = (CommonOperateTypeInfo) userLoginType.operateTypeInfo();
        commonOperateTypeInfo.r().client().tell(
                new application.client.Client.SendToClientJ(UserProtocols.LOGIN, UserProtocolBuilder.getSc10011("??????????????????????????????????????????")), self());
    }

    private void userLoginOk(UserLoginType userLoginType, DataReturnMessage dataReturnMessage) {
        var user = (User) dataReturnMessage.message();
        if (Objects.isNull(user)) {
            var commonOperateTypeInfo = (CommonOperateTypeInfo) userLoginType.operateTypeInfo();
            commonOperateTypeInfo.r().client().tell(
                    new application.client.Client.SendToClientJ(UserProtocols.LOGIN, UserProtocolBuilder.getSc10011("??????????????????????????????????????????")), self());
            return;
        }
        var commonOperateTypeInfo = (CommonOperateTypeInfo) userLoginType.operateTypeInfo();
        var cs10011 = (protocol.User.CS10011) commonOperateTypeInfo.message();
        if (user.getPassword().equals(codecPassword(cs10011.getAccount().getPassword()))) {
            commonOperateTypeInfo.r().client().tell(
                    new application.client.Client.SendToClientJ(UserProtocols.LOGIN, UserProtocolBuilder.getSc10011(user.getId())), self());
            commonOperateTypeInfo.r().client().tell(new application.client.Client.LoginSuccess(user.getId()), self());
        } else {
            commonOperateTypeInfo.r().client().tell(
                    new application.client.Client.SendToClientJ(UserProtocols.LOGIN, UserProtocolBuilder.getSc10011("????????????????????????????????????")), self());
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
            var cs10011 = protocol.User.CS10011.parseFrom(r.message());
            //todo ????????????????????????
            if (StringUtils.isEmpty(cs10011.getAccount().getAccount()) || StringUtils.isEmpty(cs10011.getAccount().getPassword())) {
                r.client().tell(new application.client.Client.SendToClientJ(UserProtocols.LOGIN, UserProtocolBuilder.getSc10011("????????????????????????????????????")), self());
            }
            var user = User.of(cs10011.getAccount().getAccount());
            this.userData.tell(new UserGetByAccount(self(), user, new UserLoginType(new CommonOperateTypeInfo(r, cs10011))), self());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private String codecPassword(String password) {
        return Codec.md5(password);
    }

    private void register(Client.ReceivedFromClient r) {
        try {
            var cs10010 = protocol.User.CS10010.parseFrom(r.message());
            var accountInfo = cs10010.getAccount();
            if (StringUtils.isEmpty(accountInfo.getAccount()) || StringUtils.isEmpty(accountInfo.getPassword())) {
                r.client().tell(UserProtocolBuilder.getSc10010(false, "???????????????????????????????????????????????????"), self());
                return;
            }
            if (accountInfo.getAccount().matches("\\W") || accountInfo.getPassword().matches("\\W")) {
                r.client().tell(UserProtocolBuilder.getSc10010(false, "???????????????????????????????????????????????????"), self());
                return;
            }
            var user = User.of(IdUtils.fastSimpleUUIDLong(), accountInfo.getAccount(), cs10010.getName(), codecPassword(accountInfo.getPassword()));
            this.userData.tell(new UserInsertByAccount(self(), user, new UserRegisterType(new CommonOperateTypeInfo(r, cs10010))), self());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}

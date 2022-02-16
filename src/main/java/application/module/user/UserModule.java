package application.module.user;

import akka.actor.ActorRef;
import application.guid.IdUtils;
import application.module.common.data.domain.DataMessage;
import application.module.user.data.UserData;
import application.module.user.data.domain.User;
import application.module.user.operate.UserLoginType;
import application.module.user.operate.UserRegisterInsertType;
import application.module.user.operate.UserRegisterType;
import application.util.CommonOperateTypeInfo;
import com.cala.orm.message.DataBaseMessage;
import com.cala.orm.message.DataReturnMessage;
import com.cala.orm.message.MessageAndReply;
import com.cala.orm.message.OperateType;
import com.cala.orm.util.StringUtils;
import com.google.protobuf.InvalidProtocolBufferException;
import mobius.core.digest.Codec;
import mobius.modular.client.Client;
import mobius.modular.module.api.AbstractModule;

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
                case UserRegisterType userRegisterType -> userRegisterOk(userRegisterType);
                case UserRegisterInsertType userRegisterInsertType -> userRegisterInsertTypeOk(userRegisterInsertType);
                default -> getLog().error(new IllegalStateException(), "Unexpected value: " + operateType);
            }
        } else {
            switch (operateType) {
                case UserLoginType userLoginType -> userLoginTypeError(userLoginType);
                case UserRegisterType userRegisterType -> userRegisterTypeError(userRegisterType);
                case UserRegisterInsertType userRegisterInsertType -> userRegisterInsertTypeError(userRegisterInsertType);
                default -> getLog().error(new IllegalStateException(), "Unexpected value: " + operateType);
            }
        }
    }

    private void userRegisterInsertTypeError(UserRegisterInsertType userRegisterInsertType) {
        var commonOperateTypeInfo = (CommonOperateTypeInfo) userRegisterInsertType.operateTypeInfo();
        commonOperateTypeInfo.r().client().tell(new application.client.Client.SendToClientJ(UserProtocols.REGISTER, UserProtocolBuilder.getSc10010(false, "请重试！")), self());
    }

    private void userRegisterInsertTypeOk(UserRegisterInsertType userRegisterInsertType) {
        var commonOperateTypeInfo = (CommonOperateTypeInfo) userRegisterInsertType.operateTypeInfo();
        commonOperateTypeInfo.r().client().tell(new application.client.Client.SendToClientJ(UserProtocols.REGISTER, UserProtocolBuilder.getSc10010(true)), self());
    }

    /**
     * 注册前的查询返回为失败，代表账号不存在，可以注册，发送插入请求
     */
    private void userRegisterTypeError(UserRegisterType userRegisterType) {
        var commonOperateTypeInfo = (CommonOperateTypeInfo) userRegisterType.operateTypeInfo();
        var cs10010 = (protocol.User.CS10010) commonOperateTypeInfo.message();
        var accountInfo = cs10010.getAccount();
        var user = User.of(IdUtils.fastSimpleUUIDLong(), accountInfo.getAccount(), cs10010.getName(), codecPassword(accountInfo.getPassword()));
        this.userData.tell(new DataBaseMessage.Insert(new MessageAndReply(self(), user, new UserRegisterInsertType(new CommonOperateTypeInfo(commonOperateTypeInfo.r(), cs10010)))), self());
    }

    private void userLoginTypeError(UserLoginType userLoginType) {
        var commonOperateTypeInfo = (CommonOperateTypeInfo) userLoginType.operateTypeInfo();
        commonOperateTypeInfo.r().client().tell(
                new application.client.Client.SendToClientJ(UserProtocols.LOGIN, UserProtocolBuilder.getSc10011("大概率你账号都不存在吧！！！")), self());
    }

    /**
     * 注册的查询返回成功，代表里面账号已经存在
     */
    private void userRegisterOk(UserRegisterType userRegisterType) {
        var commonOperateTypeInfo = (CommonOperateTypeInfo) userRegisterType.operateTypeInfo();
        commonOperateTypeInfo.r().client().tell(new application.client.Client.SendToClientJ(UserProtocols.REGISTER, UserProtocolBuilder.getSc10010(false, "账号已经存在！")), self());
    }

    private void userLoginOk(UserLoginType userLoginType, DataReturnMessage dataReturnMessage) {
        var user = (User) dataReturnMessage.message();
        var commonOperateTypeInfo = (CommonOperateTypeInfo) userLoginType.operateTypeInfo();
        var cs10011 = (protocol.User.CS10011) commonOperateTypeInfo.message();
        if (user.getPassword().equals(codecPassword(cs10011.getAccount().getPassword()))) {
            commonOperateTypeInfo.r().client().tell(
                    new application.client.Client.SendToClientJ(UserProtocols.LOGIN, UserProtocolBuilder.getSc10011(user.getId())), self());
            commonOperateTypeInfo.r().client().tell(new application.client.Client.LoginSuccess(user.getId()), self());
        } else {
            commonOperateTypeInfo.r().client().tell(
                    new application.client.Client.SendToClientJ(UserProtocols.LOGIN, UserProtocolBuilder.getSc10011("肯定是你密码输错了！！！")), self());
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
            //todo 名字是否非法认证
            if (StringUtils.isEmpty(cs10011.getAccount().getAccount()) || StringUtils.isEmpty(cs10011.getAccount().getPassword())) {
                r.client().tell(new application.client.Client.SendToClientJ(UserProtocols.LOGIN, UserProtocolBuilder.getSc10011("账户或密码不能为空！！！")), self());
            }
            var user = User.of(cs10011.getAccount().getAccount());
            this.userData.tell(new UserDataMessage.UserGetByAccount(new MessageAndReply(self(), user, new UserLoginType(new CommonOperateTypeInfo(r, cs10011)))), self());
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
                r.client().tell(UserProtocolBuilder.getSc10010(false, "算我球球你了，账号密码好好输入吧！"), self());
                return;
            }
            var user = User.of(accountInfo.getAccount());
            this.userData.tell(new UserDataMessage.UserGetByAccount(new MessageAndReply(self(), user, new UserRegisterType(new CommonOperateTypeInfo(r, cs10010)))), self());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}

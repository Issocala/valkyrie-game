package application.module.gm.cmd.impl;

import application.module.gm.cmd.sub.GmCmdSubMethod;
import application.module.gm.cmd.sub.GmCmdSub;
import application.module.gm.domain.GmCmdArgs;
import application.module.gm.domain.GmCmdResult;
import mobius.modular.client.Client;

/**
 * @author HRT
 * @date 2022-4-13
 */
public class Test2GmCmd extends GmCmdSub {

    @Override
    protected String getCmd() {
        return "test2";
    }

    @Override
    protected String getDesc() {
        return "测试2";
    }

    @Override
    protected String getExample() {
        return "test2 hello; test2 plus 1 2";
    }

    @Override
    protected void onInit() {

    }

    @Override
    protected void onReceive(Object msg) {

    }

    @GmCmdSubMethod("hello")
    private GmCmdResult onHello() {
        return GmCmdResult.ofOk("hi");
    }

    @GmCmdSubMethod
    private GmCmdResult hello1(Client.ReceivedFromClient r) {
        return GmCmdResult.ofOk("hi");
    }

    @GmCmdSubMethod
    private GmCmdResult hello2(Client.ReceivedFromClient r, GmCmdArgs args) {
        return GmCmdResult.ofOk("hi");
    }

    @GmCmdSubMethod
    private GmCmdResult plus(int a, int b) {
        int c = a + b;
        return GmCmdResult.ofOk(String.format("%s + %s = %s", a, b, c));
    }

    @GmCmdSubMethod
    private GmCmdResult plus1(Client.ReceivedFromClient r, int a, int b) {
        int c = a + b;
        return GmCmdResult.ofOk(String.format("%s + %s = %s", a, b, c));
    }

}

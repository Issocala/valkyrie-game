package application.module.gm.cmd.impl;

import application.module.gm.cmd.GmCmd;
import application.module.gm.domain.GmCmdArgs;
import application.module.gm.domain.GmCmdResult;
import mobius.modular.client.Client;

/**
 * @author HRT
 * @date 2022-4-11
 */
public class TestGmCmd extends GmCmd {

    @Override
    protected String getCmd() {
        return "test";
    }

    @Override
    protected String getDesc() {
        return "测试";
    }

    @Override
    protected String getExample() {
        return "test; test hello; test plus 1 2";
    }

    @Override
    protected void onInit() {

    }

    @Override
    protected void onReceive(Object msg) {

    }

    @Override
    protected GmCmdResult onHandle(Client.ReceivedFromClient r, GmCmdArgs args) {
        int size = args.size();
        if (size >= 1) {
            String subCmd = args.next();
            switch (subCmd) {
                case "hello":
                    return GmCmdResult.ofOk("hi");
                case "plus":
                    if (size < 3) {
                        return GmCmdResult.ofErr("args error");
                    }
                    int a = args.nextInt();
                    int b = args.nextInt();
                    int c = a + b;
                    return GmCmdResult.ofOk(String.format("%s + %s = %s", a, b, c));
                default:
                    return GmCmdResult.ofErr();
            }
        }
        return GmCmdResult.ofOk("test");
    }

}

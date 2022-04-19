package application.module.gm.domain;

import java.util.List;

/**
 * @author HRT
 * @date 2022-4-11
 */
public record GmCmdResult(boolean ok, List<String> details) {

    public static GmCmdResult ofOk() {
        return new GmCmdResult(true, List.of());
    }

    public static GmCmdResult ofOk(String ...details) {
        return new GmCmdResult(true, List.of(details));
    }

    public static GmCmdResult ofOk(List<String> details) {
        return new GmCmdResult(true, details);
    }

    public static GmCmdResult ofErr() {
        return new GmCmdResult(false, List.of());
    }

    public static GmCmdResult ofErr(String ...details) {
        return new GmCmdResult(false, List.of(details));
    }

    public static GmCmdResult ofErr(List<String> details) {
        return new GmCmdResult(false, details);
    }

}

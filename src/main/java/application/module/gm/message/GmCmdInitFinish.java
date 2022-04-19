package application.module.gm.message;

import application.module.gm.message.info.GmCmdInfo;

/**
 * @author HRT
 * @date 2022-4-11
 */
public record GmCmdInitFinish(GmCmdInfo info) implements GmCmdMessage {
}

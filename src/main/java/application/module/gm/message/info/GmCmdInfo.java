package application.module.gm.message.info;

import akka.actor.ActorRef;

/**
 * @author HRT
 * @date 2022-4-11
 */
public record GmCmdInfo(String cmd, String desc, String example, ActorRef gmCmd) {

    public String toDesc() {
        return String.format("【%s】%s, eg: %s", cmd, desc, example);
    }

}

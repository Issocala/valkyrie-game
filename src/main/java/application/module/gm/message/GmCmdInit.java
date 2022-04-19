package application.module.gm.message;

import akka.actor.ActorRef;

/**
 * @author HRT
 * @date 2022-4-11
 */
public record GmCmdInit(ActorRef dataAgent) implements GmCmdMessage {
}

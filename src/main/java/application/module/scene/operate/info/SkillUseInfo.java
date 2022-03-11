package application.module.scene.operate.info;

import akka.actor.ActorRef;
import application.module.fight.base.context.UseSkillDataTemp;
import com.cala.orm.message.OperateTypeInfo;
import mobius.modular.client.Client;

/**
 * @author Luo Yong
 * @date 2022-3-7
 * @Source 1.0
 */
public record SkillUseInfo(ActorRef actorRef, UseSkillDataTemp useSkillDataTemp,
                           Client.ReceivedFromClient r) implements OperateTypeInfo {
}

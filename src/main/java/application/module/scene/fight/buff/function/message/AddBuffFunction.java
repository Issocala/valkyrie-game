package application.module.scene.fight.buff.function.message;

import application.module.scene.fight.buff.FightOrganismBuff;
import com.cala.orm.message.DataBase;
import mobius.modular.client.Client;

/**
 * @author Luo Yong
 * @date 2022-4-6
 * @Source 1.0
 */
public record AddBuffFunction(Client.ReceivedFromClient r, FightOrganismBuff fightOrganismBuff) implements DataBase {
}

package application.module.fight.buff.function.message;

import application.module.fight.buff.data.domain.FightOrganismBuff;
import com.cala.orm.message.DataBase;
import mobius.modular.client.Client;

/**
 * @author Luo Yong
 * @date 2022-4-6
 * @Source 1.0
 */
public record RemoveBuffFunction(Client.ReceivedFromClient r, FightOrganismBuff fightOrganismBuff) implements DataBase {
}

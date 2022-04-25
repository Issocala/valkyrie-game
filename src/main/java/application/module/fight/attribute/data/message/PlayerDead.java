package application.module.fight.attribute.data.message;

import com.cala.orm.message.DataBase;
import com.cala.orm.message.Event;

/**
 * @author Luo Yong
 * @date 2022-4-21
 * @Source 1.0
 */
public record PlayerDead(long playerId, long sourceId) implements Event, DataBase {
}

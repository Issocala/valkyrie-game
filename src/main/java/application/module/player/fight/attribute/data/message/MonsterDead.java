package application.module.player.fight.attribute.data.message;

import com.cala.orm.message.DataBase;
import com.cala.orm.message.Event;

/**
 * @author Luo Yong
 * @date 2022-4-21
 * @Source 1.0
 */
public record MonsterDead(long organismId, byte organismType, long sourceId,
                          byte sourceType) implements Event, DataBase {
}

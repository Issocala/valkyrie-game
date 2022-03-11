package application.module.fight.attribute.data.message;

import com.cala.orm.message.DataBase;

import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-3-9
 * @Source 1.0
 */
public record AttributeUpdateFightAttribute(Map<Short, Long> result, long playerId) implements DataBase {
}

package application.module.player.fight.state.data.entity;

import com.cala.orm.annotation.DbDeserialize;
import com.cala.orm.cache.AbstractEntityBase;

/**
 * @author Luo Yong
 * @date 2022-3-7
 * @Source 1.0
 */
@DbDeserialize
public class StateEntity extends AbstractEntityBase {
    public StateEntity(Long id) {
        super(id);
    }

    public static StateEntity of(long playerId) {
        return new StateEntity(playerId);
    }
}

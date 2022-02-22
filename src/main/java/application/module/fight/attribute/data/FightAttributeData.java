package application.module.fight.attribute.data;

import akka.actor.Props;
import application.module.fight.attribute.data.domain.FightAttribute;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.message.DataBase;

/**
 * @author Luo Yong
 * @date 2022-2-16
 * @Source 1.0
 */
public class FightAttributeData extends AbstractDataCacheManager<FightAttribute> {

    public static Props create() {
        return Props.create(FightAttributeData.class, FightAttributeData::new).withDispatcher(DB_DISPATCHER);
    }

    private FightAttributeData() {
    }

    @Override
    public void receive(DataBase dataBase) {

    }

    @Override
    public boolean validDomain(AbstractEntityBase abstractEntityBase) {
        return abstractEntityBase.getClass() == FightAttribute.class;
    }
}

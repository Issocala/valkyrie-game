package application.module.fight.attribute.data;

import akka.actor.Props;
import application.module.fight.attribute.data.domain.Attribute;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.message.DBReturnMessage;
import com.cala.orm.message.DataBase;

/**
 * @author Luo Yong
 * @date 2022-2-16
 * @Source 1.0
 */
public class AttributeData extends AbstractDataCacheManager<Attribute> {

    public static Props create() {
        return Props.create(AttributeData.class, AttributeData::new).withDispatcher(DATA_AND_DB_DISPATCHER);
    }

    private AttributeData() {
    }

    @Override
    public void dbReturnMessage(DBReturnMessage dbReturnMessage) {

    }

    @Override
    public void receive(DataBase dataBase) {

    }

    @Override
    public boolean validDomain(AbstractEntityBase abstractEntityBase) {
        return abstractEntityBase.getClass() == Attribute.class;
    }
}

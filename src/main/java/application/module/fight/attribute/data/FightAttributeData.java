package application.module.fight.attribute.data;

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


    @Override
    public void receive(DataBase dataBase) {

    }

    @Override
    public boolean validDomain(AbstractEntityBase abstractEntityBase) {
        return abstractEntityBase.getClass() == FightAttribute.class;
    }
}

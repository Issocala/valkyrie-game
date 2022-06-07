package application.module.common.data;

import application.module.common.data.entity.CommonEntity;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.cache.DataInit;
import com.cala.orm.message.DataBase;

/**
 * @author Luo Yong
 * @date 2022-6-6
 * @Source 1.0
 */
public class CommonData extends AbstractDataCacheManager<CommonEntity> {
    @Override
    protected void dataInit(DataInit dataInit) {

    }

    @Override
    public void receive(DataBase dataBase) {

    }

    @Override
    public boolean validDomain(AbstractEntityBase abstractEntityBase) {
        return CommonEntity.class == abstractEntityBase.getClass();
    }
}

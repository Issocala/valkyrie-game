package application.module.player.data;

import akka.actor.Props;
import application.module.player.PlayerDataMessage;
import application.module.player.data.domain.PlayerEntity;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.db.message.DbQueryManyBySql;
import com.cala.orm.db.message.DbQueryOneBySql;
import com.cala.orm.message.DataBase;

/**
 * @author Luo Yong
 * @date 2021-11-15
 * @Source 1.0
 */
public class PlayerEntityData extends AbstractDataCacheManager<PlayerEntity> {

    public static Props create() {
        return Props.create(PlayerEntityData.class, PlayerEntityData::new).withDispatcher(DATA_AND_DB_DISPATCHER);
    }

    private PlayerEntityData() {
    }

    @Override
    public void receive(DataBase dataBase) {
        switch (dataBase) {
            case PlayerDataMessage.PlayerByUserId playerByUserId -> getPlayerByUserId(playerByUserId);
            case PlayerDataMessage.PlayerByUserIdAndProfession playerByUserIdAndProfession -> getPlayerByUserIdAndProfession(playerByUserIdAndProfession);
            default -> throw new IllegalStateException("Unexpected value: " + dataBase);
        }
    }

    private void getPlayerByUserIdAndProfession(PlayerDataMessage.PlayerByUserIdAndProfession playerByUserIdAndProfession) {
        PlayerEntity playerEntity = (PlayerEntity) playerByUserIdAndProfession.abstractEntityBase();
        String sql = "select * from playerEntity where accountId = " +
                playerEntity.getId() +
                " and profession = " +
                playerEntity.getProfession();
        getDbManager().tell(new DbQueryOneBySql(playerByUserIdAndProfession.ref(),
                sql, PlayerEntity.class, playerByUserIdAndProfession.operateType()), self());
    }

    private void getPlayerByUserId(PlayerDataMessage.PlayerByUserId playerByUserId) {
        AbstractEntityBase abstractEntityBase = playerByUserId.abstractEntityBase();
        String sql = "select * from playerEntity where accountId = " + abstractEntityBase.getId();
        getDbManager().tell(new DbQueryManyBySql(playerByUserId.ref(),
                sql, PlayerEntity.class, playerByUserId.operateType()), self());
    }

    @Override
    public boolean validDomain(AbstractEntityBase v) {
        return v.getClass() == PlayerEntity.class;
    }

}

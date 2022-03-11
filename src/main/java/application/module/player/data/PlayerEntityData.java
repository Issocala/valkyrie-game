package application.module.player.data;

import akka.actor.Props;
import application.module.player.PlayerDataMessage;
import application.module.player.data.domain.PlayerEntity;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.message.DBReturnMessage;
import com.cala.orm.message.DataBase;
import com.cala.orm.message.DataReturnMessage;
import com.cala.orm.util.DbConnection;
import com.cala.orm.util.DbHelper;
import com.cala.orm.util.RuntimeResult;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

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
    public void dbReturnMessage(DBReturnMessage dbReturnMessage) {

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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select * from playerEntity where accountId = ");
        stringBuilder.append(playerEntity.getId());
        stringBuilder.append(" and profession = ");
        stringBuilder.append(playerEntity.getProfession());
        try {
            playerEntity = DbHelper.queryOne(DbConnection.getUserConnection(), stringBuilder.toString(), PlayerEntity.class);
            if (Objects.nonNull(playerEntity)) {
                playerByUserIdAndProfession.ref().tell(new DataReturnMessage(RuntimeResult.ok(), playerEntity, playerByUserIdAndProfession.operateType()), sender());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getPlayerByUserId(PlayerDataMessage.PlayerByUserId playerByUserId) {
        AbstractEntityBase abstractEntityBase = playerByUserId.abstractEntityBase();
        String sql = "select * from playerEntity where accountId = " + abstractEntityBase.getId();
        try {
            List<PlayerEntity> playerEntityList = DbHelper.queryMany(DbConnection.getUserConnection(), sql, PlayerEntity.class);
//            playerByUserId.ref().tell(new DataReturnMessage(RuntimeResult.ok(), List.copyOf(playerEntityList), playerByUserId.operateType()), sender());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean validDomain(AbstractEntityBase v) {
        return v.getClass() == PlayerEntity.class;
    }

}

package application.module.player.base.data;

import akka.actor.Props;
import application.module.player.base.data.domain.PlayerDataMessage;
import application.module.player.base.data.domain.PlayerEntity;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.message.DataBase;
import com.cala.orm.message.DataReturnMessage;
import com.cala.orm.message.MessageAndReply;
import com.cala.orm.util.DbConnection;
import com.cala.orm.util.DbHelper;
import com.cala.orm.util.RuntimeResult;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Luo Yong
 * @date 2021-11-15
 * @Source 1.0
 */
public class PlayerEntityData extends AbstractDataCacheManager<PlayerEntity> {

    public static Props create() {
        return Props.create(PlayerEntityData.class, PlayerEntityData::new).withDispatcher(DB_DISPATCHER);
    }

    private PlayerEntityData() {
    }

    @Override
    public void receive(DataBase dataBase) {
        switch (dataBase) {
            case PlayerDataMessage.PlayerByUserId playerByUserId -> getPlayerByUserId(playerByUserId);
            default -> System.out.println();
        }
    }

    private void getPlayerByUserId(PlayerDataMessage.PlayerByUserId playerByUserId) {
        MessageAndReply messageAndReply = playerByUserId.messageAndReply();
        AbstractEntityBase abstractEntityBase = messageAndReply.abstractEntityBase();
        String sql = "select * from playerEntity where accountId = " + abstractEntityBase.getId();
        try {
            List<PlayerEntity> playerEntityList = DbHelper.queryMany(DbConnection.getUserConnection(), sql, PlayerEntity.class);
            messageAndReply.ref().tell(new DataReturnMessage(RuntimeResult.ok(), playerEntityList, messageAndReply.operateType()), sender());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean validDomain(AbstractEntityBase v) {
        return v.getClass() == PlayerEntity.class;
    }

}

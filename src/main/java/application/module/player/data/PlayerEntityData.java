package application.module.player.data;

import akka.actor.ActorRef;
import akka.actor.Props;
import application.client.Client;
import application.module.player.PlayerDataMessage;
import application.module.player.PlayerProtocolBuilder;
import application.module.player.PlayerProtocols;
import application.module.player.data.domain.PlayerEntity;
import application.module.scene.operate.event.CreatePlayerEntitiesAfter;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.cache.DataInit;
import com.cala.orm.db.message.DbQueryManyBySql;
import com.cala.orm.db.message.DbQueryOneBySql;
import com.cala.orm.message.DataBase;

import java.util.ArrayList;
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
    protected void dataInit(DataInit dataInit) {

    }

    @Override
    public void receive(DataBase dataBase) {
        switch (dataBase) {
            case PlayerDataMessage.PlayerByUserId playerByUserId -> getPlayerByUserId(playerByUserId);
            case PlayerDataMessage.PlayerByUserIdAndProfession playerByUserIdAndProfession -> getPlayerByUserIdAndProfession(playerByUserIdAndProfession);
            case CreatePlayerEntitiesAfter createPlayerEntitiesAfter -> createPlayerEntitiesAfter(createPlayerEntitiesAfter);
            default -> throw new IllegalStateException("Unexpected value: " + dataBase.getClass().getName());
        }
    }

    private void createPlayerEntitiesAfter(CreatePlayerEntitiesAfter createPlayerEntitiesAfter) {
        long playerId = createPlayerEntitiesAfter.playerId();
        List<PlayerEntity> playerEntityList = List.of((PlayerEntity) get(playerId));
        List<PlayerEntity> otherPlayerEntityList = new ArrayList<>();
        createPlayerEntitiesAfter.clientMap().forEach((id, client) -> {
            if (id != playerId) {
                client.tell(new Client.SendToClientJ(PlayerProtocols.EntityInfo, PlayerProtocolBuilder.getSc10025(playerEntityList)), self());
            }
            otherPlayerEntityList.add((PlayerEntity) get(id));
        });
        ActorRef client = createPlayerEntitiesAfter.clientMap().get(playerId);
        if (Objects.nonNull(client)) {
            client.tell(new Client.SendToClientJ(PlayerProtocols.EntityInfo,
                    PlayerProtocolBuilder.getSc10025(otherPlayerEntityList)), self());
        }
    }

    private void getPlayerByUserIdAndProfession(PlayerDataMessage.PlayerByUserIdAndProfession playerByUserIdAndProfession) {
        PlayerEntity playerEntity = (PlayerEntity) playerByUserIdAndProfession.abstractEntityBase();
        String sql = "select * from playerentity where accountId = " +
                playerEntity.getId() +
                " and profession = " +
                playerEntity.getProfession();
        getDbManager().tell(new DbQueryOneBySql(playerByUserIdAndProfession.ref(),
                sql, PlayerEntity.class, playerByUserIdAndProfession.operateType()), self());
    }

    private void getPlayerByUserId(PlayerDataMessage.PlayerByUserId playerByUserId) {
        AbstractEntityBase abstractEntityBase = playerByUserId.abstractEntityBase();
        String sql = "select * from playerentity where accountId = " + abstractEntityBase.getId();
        getDbManager().tell(new DbQueryManyBySql(playerByUserId.ref(),
                sql, PlayerEntity.class, playerByUserId.operateType()), self());
    }

    @Override
    public boolean validDomain(AbstractEntityBase v) {
        return v.getClass() == PlayerEntity.class;
    }

}

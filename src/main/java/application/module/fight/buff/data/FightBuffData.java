package application.module.fight.buff.data;

import akka.actor.ActorRef;
import akka.actor.Props;
import application.module.fight.buff.data.domain.FightBuffEntity;
import application.module.fight.buff.data.message.AddBuff;
import application.module.fight.buff.data.message.RemoveBuff;
import com.cala.orm.cache.AbstractDataCacheManager;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.cache.DataInit;
import com.cala.orm.message.DataBase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Luo Yong
 * @date 2022-4-1
 * @Source 1.0
 */
public class FightBuffData extends AbstractDataCacheManager<FightBuffEntity> {

    private final Map<Long, ActorRef> fightOrganismBuffContainerMap = new HashMap<>();

    public static Props create() {
        return Props.create(FightBuffData.class, FightBuffData::new).withDispatcher(DATA_AND_DB_DISPATCHER);
    }

    @Override
    protected void dataInit(DataInit dataInit) {

    }

    @Override
    public void receive(DataBase dataBase) {
        switch (dataBase) {
            case AddBuff addBuff -> addBuff(addBuff);
            case RemoveBuff removeBuff -> removeBuff(removeBuff);
            default -> throw new IllegalStateException("Unexpected value: " + dataBase);
        }
    }

    private void removeBuff(RemoveBuff removeBuff) {
        ActorRef fightOrganismBuffContainer = fightOrganismBuffContainerMap.get(removeBuff.toId());
        if (Objects.isNull(fightOrganismBuffContainer)) {
            fightOrganismBuffContainer = getContext().actorOf(FightOrganismBuffContainer.create());
            fightOrganismBuffContainerMap.put(removeBuff.toId(), fightOrganismBuffContainer);
        }
        fightOrganismBuffContainer.tell(removeBuff, self());
    }

    private void addBuff(AddBuff addBuff) {
        ActorRef fightOrganismBuffContainer = fightOrganismBuffContainerMap.get(addBuff.toId());
        if (Objects.isNull(fightOrganismBuffContainer)) {
            fightOrganismBuffContainer = getContext().actorOf(FightOrganismBuffContainer.create());
            fightOrganismBuffContainerMap.put(addBuff.toId(), fightOrganismBuffContainer);
        }
        fightOrganismBuffContainer.tell(addBuff, sender());
    }

    @Override
    public boolean validDomain(AbstractEntityBase abstractEntityBase) {
        return FightBuffEntity.class == abstractEntityBase.getClass();
    }

    public void validAddBuff(AddBuff addBuff) {

    }
}

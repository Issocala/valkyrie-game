package application.module.fight.buff;

import akka.actor.ActorRef;
import application.module.common.data.domain.DataMessage;
import application.module.fight.buff.data.FightBuffData;
import application.module.fight.buff.function.FightBuffFunctionContainer;
import application.module.player.data.PlayerEntityData;
import application.module.player.data.message.event.PlayerLogin;
import application.module.scene.data.SceneData;
import application.module.scene.operate.event.CreateOrganismEntityAfter;
import application.module.scene.operate.event.CreatePlayerEntitiesAfter;
import application.module.scene.operate.event.PlayerReceiveAfter;
import com.cala.orm.message.SubscribeEvent;
import mobius.modular.module.api.AbstractModule;

/**
 * @author Luo Yong
 * @date 2022-3-2
 * @Source 1.0
 */
public class FightBuffModule extends AbstractModule {

    private ActorRef fightBuffData;
    private ActorRef sceneData;
    private ActorRef playerData;

    @Override
    public void initData() {
        FightBuffFunctionContainer.register(getContext());
        this.dataAgent().tell(new DataMessage.RequestData(FightBuffData.class), self());
        this.dataAgent().tell(new DataMessage.RequestData(SceneData.class), self());
        this.dataAgent().tell(new DataMessage.RequestData(PlayerEntityData.class), self());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(DataMessage.DataResult.class, this::dataResult)
                .build();
    }

    private void dataResult(DataMessage.DataResult dataResult) {
        if (dataResult.clazz() == FightBuffData.class) {
            this.fightBuffData = dataResult.actorRef();
        }else if (dataResult.clazz() == SceneData.class) {
            this.sceneData = dataResult.actorRef();
            this.sceneData.tell(new SubscribeEvent(CreatePlayerEntitiesAfter.class, this.fightBuffData), self());
            this.sceneData.tell(new SubscribeEvent(CreateOrganismEntityAfter.class, this.fightBuffData), self());
            this.sceneData.tell(new SubscribeEvent(PlayerReceiveAfter.class, this.fightBuffData), self());
        }else if (dataResult.clazz() == PlayerEntityData.class) {
            this.playerData = dataResult.actorRef();
            this.playerData.tell(new SubscribeEvent(PlayerLogin.class, fightBuffData), self());
        }
    }
}
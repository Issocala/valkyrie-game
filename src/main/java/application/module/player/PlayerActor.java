package application.module.player;

import akka.actor.ActorRef;
import akka.actor.Props;
import application.module.common.data.domain.DataMessage;
import application.module.organism.PlayerFight;
import application.module.player.data.entity.PlayerEntity;
import application.module.player.fight.attribute.AttributePlayerMgr;
import application.module.player.operate.CheckPlayerInitFinal;
import application.module.player.operate.PlayerInit;
import application.module.player.operate.PlayerLoginOpt;
import application.module.player.util.IgnoreOpt;
import com.cala.orm.message.DataReturnManyMessage;
import com.cala.orm.message.DataReturnMessage;
import com.cala.orm.message.OperateType;
import mobius.core.java.api.AbstractLogActor;
import mobius.modular.client.Client;
import scala.collection.immutable.HashSet;

import java.time.Duration;

/**
 * @author Luo Yong
 * @date 2022-5-13
 * @Source 1.0
 */
public class PlayerActor extends AbstractLogActor {

    private final static int MAX_COUNT = 5;

    private Player player;

    private PlayerEntity playerEntity;

    public PlayerActor() {
    }

    public PlayerActor(PlayerEntity playerEntity) {
        this.playerEntity = playerEntity;
    }

    public static Props props(PlayerEntity playerEntity) {
        return Props.create(PlayerActor.class, playerEntity);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(DataMessage.DataResult.class, this::dataResult)
                .match(Client.ReceivedFromClient.class, this::onReceiveFromClient)
                .match(DataReturnMessage.class, this::dataResultMessage)
                .match(DataReturnManyMessage.class, this::dataResultManyMessage)
                .match(OperateType.class, this::operateType)
                .match(PlayerInit.class, this::playerInit)
                .match(CheckPlayerInitFinal.class, this::checkPlayerInitFinal)
                .build();
    }

    private void checkPlayerInitFinal(CheckPlayerInitFinal checkPlayerInitFinal) {
        if (getPlayer().isAllMgrInitFinal()) {
            playerInitAfter(checkPlayerInitFinal);
        } else {
            if (player.getCurrCount() <= MAX_COUNT) {
                player.setCurrCount(player.getCurrCount() + 1);
                getContext().getSystem().scheduler().scheduleOnce(Duration.ofSeconds(1), self(), checkPlayerInitFinal, getContext().dispatcher(), self());
            }
        }
    }

    private void operateType(OperateType o) {
        player.getOperateTypeMgrMap().get(o.getClass()).receiver(this.player, o);
    }

    private void playerInit(PlayerInit playerInit) {
        player = new Player(this.playerEntity, self(), playerInit.dataMap());
        player.init(playerInit);
        scala.collection.immutable.Set<Integer> set = new HashSet<>();
        for (int i : player.getMessageMap().keySet()) {
            set = set.incl(i);
        }
        playerInit.client().tell(new application.client.Client.UpdateMessage(set, self()), self());

        checkPlayerInitFinal(new CheckPlayerInitFinal(playerInit.client()));
    }

    private void playerInitAfter(CheckPlayerInitFinal checkPlayerInitFinal) {
        // TODO: 2022-5-18 处理属性
        AttributePlayerMgr attributePlayerMgr = (AttributePlayerMgr) player.getMgr(AttributePlayerMgr.class);
        attributePlayerMgr.initAfter(player);
        PlayerFight playerFight = new PlayerFight(player.getId(), playerEntity.getProfession());
        playerFight.setClient(checkPlayerInitFinal.client());
        playerFight.init(player);

        player.getScene().tell(new PlayerLoginOpt(playerFight), ActorRef.noSender());
    }

    private void dataResultManyMessage(DataReturnManyMessage d) {
        if (d.result().isOK()) {
            player.getOperateTypeMgrMap().get(d.operateType().getClass()).receiver(player, d.list(), d.operateType());
        }
    }

    private void dataResultMessage(DataReturnMessage d) {
        if (d.result().isOK()) {
            if (d.operateType() instanceof IgnoreOpt) {
                return;
            }
            player.getOperateTypeMgrMap().get(d.operateType().getClass()).receiver(player, d.message(), d.operateType());
        }
    }

    private void onReceiveFromClient(Client.ReceivedFromClient r) {
        if (player.getMessageMap().containsKey(r.protoID())) {
            player.getMessageMap().get(r.protoID()).receiver(player, r);
        }
    }

    private void dataResult(DataMessage.DataResult d) {

    }

    @Override
    public void preStart() {

    }

    @Override
    public void postStop() {
        player.getClient().tell(application.client.Client.ReLogin$.MODULE$, self());
    }

    @Override
    public void postRestart(Throwable reason) {
        preStart();
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerEntity getPlayerEntity() {
        return playerEntity;
    }

}

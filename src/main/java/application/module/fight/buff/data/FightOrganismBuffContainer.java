package application.module.fight.buff.data;

import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import application.module.fight.buff.FightBuffProtocolBuilder;
import application.module.fight.buff.FightBuffProtocols;
import application.module.fight.buff.data.domain.FightOrganismBuff;
import application.module.fight.buff.data.message.*;
import application.module.fight.buff.function.message.AddBuffFunction;
import application.module.fight.buff.function.message.RemoveBuffFunction;
import application.module.fight.buff.function.message.TickBuffFunction;
import application.module.fight.buff.type.BuffRepeatRuleType;
import application.module.player.data.message.event.PlayerLogin;
import application.module.scene.operate.AoiSendMessageToClient;
import application.module.scene.operate.event.CreateOrganismEntityAfter;
import application.module.scene.operate.event.CreatePlayerEntitiesAfter;
import mobius.core.java.api.AbstractLogActor;
import mobius.modular.client.Client;
import template.FightBuffTemplate;

import java.time.Duration;
import java.util.*;

/**
 * @author Luo Yong
 * @date 2022-4-2
 * @Source 1.0
 */
public class FightOrganismBuffContainer extends AbstractLogActor {

    private final Map<Integer, FightOrganismBuff> fightOrganismBuffMap = new HashMap<>();

    /**
     * 免疫某种效果类型集合
     */
    private final Set<Integer> immuneTypeSet = new HashSet<>();

    private final Map<Long, Cancellable> cancellableMap = new HashMap<>();

    private final Map<Long, Cancellable> removeCancellableMap = new HashMap<>();

    public static Props create() {
        return Props.create(FightOrganismBuffContainer.class);
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(AddBuff.class, this::addBuff)
                .match(RemoveBuff.class, this::removeBuff)
                .match(TickBuffFunction.class, this::tickBuffFunction)
                .match(RemoveBuffFunction.class, this::remove)
                .match(GetAllBuff.class, this::getAllBuff)
                .match(CreatePlayerEntitiesAfter.class, this::createPlayerEntitiesAfter)
                .match(CreateOrganismEntityAfter.class, this::createOrganismEntityAfter)
                .match(PlayerLogin.class, this::playerLogin)
                .build();
    }

    private void playerLogin(PlayerLogin playerLogin) {

    }

    private void createOrganismEntityAfter(CreateOrganismEntityAfter createOrganismEntityAfter) {

    }

    private void createPlayerEntitiesAfter(CreatePlayerEntitiesAfter createPlayerEntitiesAfter) {
        long playerId = createPlayerEntitiesAfter.playerId();
        Map<Long, ActorRef> clientMap = createPlayerEntitiesAfter.clientMap();
        ActorRef client = clientMap.get(playerId);
        clientMap.forEach((id, client1) -> {
            if (Objects.isNull(client1)) {
                return;
            }
            if (id != playerId) {
                returnClientAllBuff(playerId, client1);
            }
            returnClientAllBuff(id, client);
        });
        createPlayerEntitiesAfter.organisms().forEach(organism -> returnClientAllBuff(organism.getId(), client));
    }

    private void getAllBuff(GetAllBuff getAllBuff) {
        List<FightBuffInfo> fightBuffInfoList = new ArrayList<>();
        fightOrganismBuffMap.values().forEach(fightOrganismBuff ->
                fightBuffInfoList.add(new FightBuffInfo(fightOrganismBuff.getBuffTemplateId(), fightOrganismBuff.getCurrCoverCount())));
        sender().tell(new GetAllBuffReturn(fightBuffInfoList), self());
    }

    public void addBuff(AddBuff addBuff) {
        int buffTemplateId = addBuff.buffTemplateId();
        FightOrganismBuff fightOrganismBuff = this.fightOrganismBuffMap.get(buffTemplateId);
        long fromId = addBuff.fromId();
        long toId = addBuff.toId();
        long duration = addBuff.duration();
        if (Objects.nonNull(fightOrganismBuff)) {
            FightBuffTemplate fightBuffTemplate = fightOrganismBuff.getFightBuffTemplate();
            if (Objects.isNull(fightBuffTemplate)) {
                return;
            }
            if (fightBuffTemplate.buffCoverType() == BuffRepeatRuleType.NO_REPLACE_BUFF) {
                return;
            }
            if (fightBuffTemplate.buffCoverType() == BuffRepeatRuleType.REPEAT_EFFECT_AND_REPLACE_TIME) {

                if (fightBuffTemplate.buffCoverCount() > 1 && fightOrganismBuff.getCurrCoverCount() < fightBuffTemplate.buffCoverCount()) {
                    cancellable(fightOrganismBuff.getId());
//                    FightOrganismBuff fightOrganismBuff1 = new FightOrganismBuff(buffTemplateId, System.currentTimeMillis(), fromId, toId, duration);
                    fightOrganismBuff.setCurrCoverCount(fightOrganismBuff.getCurrCoverCount() + 1);
                    fightOrganismBuff.setScene(addBuff.scene());
                    fightOrganismBuff.setAttributeData(addBuff.attributeData());
                    fightOrganismBuff.setAttributeMap(addBuff.attributeMap());
                    fightOrganismBuff.setStateData(addBuff.stateDate());
                    if (fightBuffTemplate.buffPeriodType() == 1) {
                        fightOrganismBuff.getFunction().tell(new AddBuffFunction(addBuff.r(), fightOrganismBuff), self());
                        if (fightOrganismBuff.getDuration() != 0) {
                            scheduleRemoveOnce(addBuff.r(), fightOrganismBuff);
                        }
                    } else if (fightBuffTemplate.buffPeriodType() == 2) {
                        scheduleTickOnce(addBuff.r(), fightOrganismBuff);
                    }
                }
            } else if (fightBuffTemplate.buffCoverType() == BuffRepeatRuleType.REPEAT_EFFECT_BUFF) {
            }
        } else {
            fightOrganismBuff = new FightOrganismBuff(buffTemplateId, System.currentTimeMillis(), fromId, toId, duration);
            fightOrganismBuff.setScene(addBuff.scene());
            fightOrganismBuff.setAttributeData(addBuff.attributeData());
            fightOrganismBuff.setStateData(addBuff.stateDate());
            fightOrganismBuff.setAttributeMap(addBuff.attributeMap());
            this.fightOrganismBuffMap.put(buffTemplateId, fightOrganismBuff);
            FightBuffTemplate fightBuffTemplate = fightOrganismBuff.getFightBuffTemplate();
            if (fightBuffTemplate.buffPeriodType() == 1) {
                fightOrganismBuff.getFunction().tell(new AddBuffFunction(addBuff.r(), fightOrganismBuff), self());
                if (fightOrganismBuff.getDuration() != 0) {
                    scheduleRemoveOnce(addBuff.r(), fightOrganismBuff);
                }
            } else if (fightBuffTemplate.buffPeriodType() == 2) {
                scheduleTickOnce(addBuff.r(), fightOrganismBuff);
            }
        }
        addBuff.scene().tell(new AoiSendMessageToClient(FightBuffProtocols.ADD,
                FightBuffProtocolBuilder.getSc10071(toId, fightOrganismBuff), toId), self());
    }

    public void removeBuff(RemoveBuff removeBuff) {
        FightOrganismBuff fightOrganismBuff = this.fightOrganismBuffMap.get(removeBuff.buffTemplateId());
        if (Objects.isNull(fightOrganismBuff)) {
            return;
        }
        if (removeBuff.coverCount() == -1) {
            remove(new RemoveBuffFunction(removeBuff.r(), fightOrganismBuff));
        } else {
            remove(new RemoveBuffFunction(removeBuff.r(), fightOrganismBuff), removeBuff.coverCount());
        }
    }

    private void remove(RemoveBuffFunction remove, int coverCount) {
        FightOrganismBuff buff = remove.fightOrganismBuff();
        int buffTemplateId = buff.getBuffTemplateId();
        for (int i = 0; i < coverCount; i++) {
            buff.setCurrCoverCount(buff.getCurrCoverCount() - 1);
            if (buff.getCurrCoverCount() <= 0) {
                this.fightOrganismBuffMap.remove(buffTemplateId);
            }
            buff.getFunction().tell(new RemoveBuffFunction(remove.r(), buff), self());
            cancellable(buff.getId());
        }
        buff.getScene().tell(new AoiSendMessageToClient(FightBuffProtocols.REMOVE,
                FightBuffProtocolBuilder.getSc10072(buff.getToId(), buff), buff.getToId()), self());
    }

    private void remove(RemoveBuffFunction removeBuffFunction) {
        FightOrganismBuff fightOrganismBuff = removeBuffFunction.fightOrganismBuff();
        int buffTemplateId = fightOrganismBuff.getBuffTemplateId();
        if (this.fightOrganismBuffMap.containsKey(buffTemplateId)) {
            remove(removeBuffFunction, fightOrganismBuff.getCurrCoverCount());
        }
    }

    private void validAddBuff(int buffTemplateId) {

    }

    public Map<Integer, FightOrganismBuff> getFightOrganismBuffMap() {
        return fightOrganismBuffMap;
    }

    private void tickBuffFunction(TickBuffFunction tickBuffFunction) {
        FightOrganismBuff fightOrganismBuff = tickBuffFunction.fightOrganismBuff();
        if (removeCancellableMap.containsKey(fightOrganismBuff.getId())) {
            fightOrganismBuff.getFunction().tell(tickBuffFunction, self());
        }
    }

    public void cancellable(long id) {
        if (this.cancellableMap.containsKey(id)) {
            Cancellable cancellable = this.cancellableMap.get(id);
            if (!cancellable.isCancelled()) {
                cancellable.cancel();
            }
            cancellableMap.remove(id);
        }
        if (this.removeCancellableMap.containsKey(id)) {
            Cancellable cancellable = this.removeCancellableMap.get(id);
            if (!cancellable.isCancelled()) {
                cancellable.cancel();
            }
            removeCancellableMap.remove(id);
        }
    }

    public void scheduleTickOnce(Client.ReceivedFromClient r, FightOrganismBuff fightOrganismBuff) {
        FightBuffTemplate fightBuffTemplate = fightOrganismBuff.getFightBuffTemplate();
        Cancellable cancellable = getContext().system().scheduler().scheduleOnce(Duration.ofMillis(fightBuffTemplate.buffDelay()), getSelf(),
                new TickBuffFunction(r, fightOrganismBuff), getContext().getSystem().dispatcher(), ActorRef.noSender());
        cancellableMap.put(fightOrganismBuff.getId(), cancellable);
    }

    public void scheduleRemoveOnce(Client.ReceivedFromClient r, FightOrganismBuff fightOrganismBuff) {
        Cancellable cancellable = getContext().system().scheduler().scheduleOnce(Duration.ofMillis(fightOrganismBuff.getDuration()),
                self(), new RemoveBuffFunction(r, fightOrganismBuff), getContext().getSystem().dispatcher(), ActorRef.noSender());
        removeCancellableMap.put(fightOrganismBuff.getId(), cancellable);
    }

    private void returnClientAllBuff(long fightOrganismId, ActorRef client) {
        if (Objects.nonNull(client)) {
            client.tell(new application.client.Client.SendToClientJ(FightBuffProtocols.GET,
                    FightBuffProtocolBuilder.getSc10070(fightOrganismId, this.fightOrganismBuffMap)), self());
        }
    }
}

package application.module.fight.buff.data;

import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import application.module.fight.buff.data.domain.FightOrganismBuff;
import application.module.fight.buff.data.message.AddBuff;
import application.module.fight.buff.data.message.RemoveBuff;
import application.module.fight.buff.function.message.AddBuffFunction;
import application.module.fight.buff.function.message.RemoveBuffFunction;
import application.module.fight.buff.function.message.TickBuffFunction;
import mobius.modular.client.Client;
import template.FightBuffTemplate;

import java.time.Duration;
import java.util.*;

/**
 * @author Luo Yong
 * @date 2022-4-2
 * @Source 1.0
 */
public class FightOrganismBuffContainer extends UntypedAbstractActor {

    private final Map<Integer, List<FightOrganismBuff>> fightOrganismBuffMap = new HashMap<>();

    /**
     * 免疫某种效果类型集合
     */
    private final Set<Integer> immuneTypeSet = new HashSet<>();

    //TODO 处理这里没有及时清理，当前并未完全解决
    private final Map<Long, Cancellable> cancellableMap = new WeakHashMap<>();

    public static Props create() {
        return Props.create(FightOrganismBuffContainer.class);
    }

    @Override
    public void onReceive(Object message) {
        switch (message) {
            case AddBuff addBuff -> addBuff(addBuff);
            case RemoveBuff removeBuff -> removeBuff(removeBuff);
            case TickBuffFunction tickBuffFunction -> tickBuffFunction(tickBuffFunction);
            default -> throw new IllegalStateException("Unexpected value: " + message);
        }
    }

    public void addBuff(AddBuff addBuff) {
        int buffTemplateId = addBuff.buffTemplateId();
        long fromId = addBuff.fromId();
        long toId = addBuff.toId();
        long duration = addBuff.duration();
        FightOrganismBuff fightOrganismBuff = new FightOrganismBuff(buffTemplateId, System.currentTimeMillis(), fromId, toId, duration);
        List<FightOrganismBuff> fightOrganismBuffs = this.fightOrganismBuffMap.get(buffTemplateId);
        if (Objects.isNull(fightOrganismBuffs)) {
            fightOrganismBuffs = new ArrayList<>();
            this.fightOrganismBuffMap.put(buffTemplateId, fightOrganismBuffs);
        } else {
            //TODO 这里处理只允许存在一个的buff逻辑，List不为空说明已经有buff了，不允许添加
        }
        FightBuffTemplate fightBuffTemplate = fightOrganismBuff.getFightBuffTemplate();
        if (Objects.isNull(fightBuffTemplate)) {
            return;
        }
        if (fightOrganismBuffs.size() >= fightBuffTemplate.buffCoverCount()) {
            return;
        }
        fightOrganismBuffs.add(fightOrganismBuff);
        fightOrganismBuff.getFunction().tell(new AddBuffFunction(addBuff.r(), fightOrganismBuff), self());
        if (fightBuffTemplate.buffCount() > 0 || fightBuffTemplate.buffCount() == -1) {
            scheduleTickOnce(addBuff.r(), fightOrganismBuff);
        }
    }

    public void removeBuff(RemoveBuff removeBuff) {

    }

    private void remove(RemoveBuffFunction removeBuffFunction, FightOrganismBuff fightOrganismBuff) {
        int buffTemplateId = fightOrganismBuff.getBuffTemplateId();
        List<FightOrganismBuff> fightOrganismBuffs = this.fightOrganismBuffMap.get(buffTemplateId);
        if (Objects.nonNull(fightOrganismBuffs)) {
            boolean removed = fightOrganismBuffs.removeIf(buff -> buff == fightOrganismBuff);
            if (removed) {
                fightOrganismBuff.getFunction().tell(new RemoveBuffFunction(removeBuffFunction.r(), fightOrganismBuff), self());
            }
            if (fightOrganismBuffs.isEmpty()) {
                this.fightOrganismBuffMap.remove(buffTemplateId);
            }
        }
        cancellable(fightOrganismBuff.getId());
    }

    private void validAddBuff(int buffTemplateId) {

    }

    public Map<Integer, List<FightOrganismBuff>> getFightOrganismBuffMap() {
        return fightOrganismBuffMap;
    }

    private void tickBuffFunction(TickBuffFunction tickBuffFunction) {
        FightOrganismBuff fightOrganismBuff = tickBuffFunction.fightOrganismBuff();
        fightOrganismBuff.addTriggerCountOnce();
        FightBuffTemplate fightBuffTemplate = fightOrganismBuff.getFightBuffTemplate();
        fightOrganismBuff.getFunction().tell(tickBuffFunction, self());
        if (fightBuffTemplate.buffCount() == -1) {
            scheduleTickOnce(tickBuffFunction.r(), fightOrganismBuff);
        } else {
            if (fightOrganismBuff.getCurrTriggerCount() <= fightBuffTemplate.buffCount()) {
                scheduleTickOnce(tickBuffFunction.r(), fightOrganismBuff);
            } else {
                remove(new RemoveBuffFunction(tickBuffFunction.r(), fightOrganismBuff), fightOrganismBuff);
            }
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
    }

    public void scheduleTickOnce(Client.ReceivedFromClient r, FightOrganismBuff fightOrganismBuff) {
        Cancellable cancellable = getContext().system().scheduler().scheduleOnce(Duration.ofMillis(fightOrganismBuff.getDuration()), getSelf(),
                new TickBuffFunction(r, fightOrganismBuff), getContext().getSystem().dispatcher(), ActorRef.noSender());
        cancellableMap.put(fightOrganismBuff.getId(), cancellable);
    }
}

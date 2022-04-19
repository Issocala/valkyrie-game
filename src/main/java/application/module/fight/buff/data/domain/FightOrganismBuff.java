package application.module.fight.buff.data.domain;

import akka.actor.ActorRef;
import application.guid.IdUtils;
import application.module.fight.buff.function.FightBuffFunctionContainer;
import application.util.LongId;
import template.FightBuffTemplate;
import template.FightBuffTemplateHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-4-1
 * @Source 1.0
 */
public class FightOrganismBuff extends LongId {

    /**
     * buff模板id
     */
    private final int buffTemplateId;

    private final FightBuffTemplate fightBuffTemplate;

    /**
     * 创建时间
     */
    private final long createTime;

    /**
     * 持续时间
     */
    private final long duration;

    /**
     * 到期时间
     */
    private long expiredTime;

    /**
     * 附加属性
     */
    private final Map<Short, Long> attributeMap = new HashMap<>();

    /**
     * 创建场景id
     */
    private long createSceneId;

    /**
     * buff的来源
     */
    private final long fromId;

    /**
     * buff作用的目标
     */
    private final long toId;

    /**
     * 当前触发的次数
     */
    private int currTriggerCount;

    private ActorRef function;

    public FightOrganismBuff() {
        this(0);
    }

    public FightOrganismBuff(int buffTemplateId) {
        this(buffTemplateId, 0, 0, 0);
    }

    public FightOrganismBuff(int buffTemplateId, long createTime, long fromId, long toId) {
        this(buffTemplateId, createTime, fromId, toId, 0);
    }

    public FightOrganismBuff(int buffTemplateId, long createTime, long fromId, long toId, long duration) {
        super(IdUtils.fastSimpleUUIDLong());
        this.buffTemplateId = buffTemplateId;
        this.fightBuffTemplate = FightBuffTemplateHolder.getData(buffTemplateId);
        this.createTime = createTime;
        this.fromId = fromId;
        this.toId = toId;
        this.duration = duration == 0 ? fightBuffTemplate.buffDelay() : duration;
        this.function = FightBuffFunctionContainer.getBuffFunction(FightBuffTemplateHolder.getData(buffTemplateId).buffProcess());
    }

    public void addTriggerCountOnce() {
        this.currTriggerCount += 1;
    }

    public long getCreateTime() {
        return createTime;
    }

    public long getDuration() {
        return duration;
    }

    public long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Map<Short, Long> getAttributeMap() {
        return attributeMap;
    }

    public long getCreateSceneId() {
        return createSceneId;
    }

    public void setCreateSceneId(long createSceneId) {
        this.createSceneId = createSceneId;
    }

    public long getFromId() {
        return fromId;
    }

    public long getToId() {
        return toId;
    }

    public int getBuffTemplateId() {
        return buffTemplateId;
    }

    public int getCurrTriggerCount() {
        return currTriggerCount;
    }

    public void setCurrTriggerCount(int currTriggerCount) {
        this.currTriggerCount = currTriggerCount;
    }

    public ActorRef getFunction() {
        return function;
    }

    public void setFunction(ActorRef function) {
        this.function = function;
    }

    public FightBuffTemplate getFightBuffTemplate() {
        return fightBuffTemplate;
    }

}


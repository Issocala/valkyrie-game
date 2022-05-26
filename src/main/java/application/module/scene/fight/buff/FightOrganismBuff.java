package application.module.scene.fight.buff;

import application.guid.IdUtils;
import application.module.organism.FightOrganism;
import application.module.scene.fight.buff.function.FightBuffFunctionContainer;
import application.module.scene.fight.buff.function.FightOrganismBuffFunction;
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

    private final FightOrganismBuffFunction function;

    /**
     * 到期时间
     */
    private long expiredTime;

    /**
     * 附加一层新属性
     */
    private Map<Short, Long> attributeMap = new HashMap<>();

    /**
     * 当前buff累加属性
     */
    private final Map<Short, Long> countAttributeMap = new HashMap<>();

    /**
     * 创建场景id
     */
    private long createSceneId;

    /**
     * buff的来源
     */
    private final FightOrganism from;

    /**
     * buff作用的目标
     */
    private final FightOrganism to;


    /**
     * 当前叠加的次数
     */
    private int currCoverCount = 1;

    public FightOrganismBuff() {
        this(0);
    }

    public FightOrganismBuff(int buffTemplateId) {
        this(buffTemplateId, null, null);
    }

    public FightOrganismBuff(int buffTemplateId, FightOrganism from, FightOrganism to) {
        this(buffTemplateId, from, to, 0);
    }

    public FightOrganismBuff(int buffTemplateId, FightOrganism from, FightOrganism to, long duration) {
        super(IdUtils.fastSimpleUUIDLong());
        this.buffTemplateId = buffTemplateId;
        this.fightBuffTemplate = FightBuffTemplateHolder.getData(buffTemplateId);
        this.createTime = System.currentTimeMillis();
        this.from = from;
        this.to = to;
        this.duration = duration == 0 ? fightBuffTemplate.duration() : duration;
        this.function = FightBuffFunctionContainer.getBuffFunction(FightBuffTemplateHolder.getData(buffTemplateId).buffProcess());
    }

    public boolean addSelf() {
        function.addBuffFunction(from, to, this);
        return true;
    }

    public boolean removeSelf() {
        function.removeBuffFunction(from, to, this);
        return true;
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

    public void setAttributeMap(Map<Short, Long> attributeMap) {
        this.attributeMap = attributeMap;
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

    public FightOrganism getFrom() {
        return from;
    }

    public FightOrganism getTo() {
        return to;
    }

    public int getCurrCoverCount() {
        return currCoverCount;
    }

    public void setCurrCoverCount(int currCoverCount) {
        this.currCoverCount = currCoverCount;
    }

    public int getBuffTemplateId() {
        return buffTemplateId;
    }

    public FightBuffTemplate getFightBuffTemplate() {
        return fightBuffTemplate;
    }

    public FightOrganismBuffFunction getFunction() {
        return function;
    }

    public Map<Short, Long> getCountAttributeMap() {
        return countAttributeMap;
    }

}


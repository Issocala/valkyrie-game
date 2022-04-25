package application.module.fight.skill.base.context;

import application.module.fight.buff.data.message.FightBuffInfo;
import application.module.organism.OrganismType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-3-15
 * @Source 1.0
 */
public class TargetParameter {

    private long targetId;
    private Map<Short, Long> attributeMap;
    private int targetLevel;
    private int organismType;
    private Map<Short, Long> changeAttributeMap;
    private List<FightBuffInfo> fightBuffInfoList = new ArrayList<>();

    public TargetParameter(long targetId) {
        this(targetId, null, 1, OrganismType.PLAYER, null);
    }

    public TargetParameter(long targetId, int targetLevel, int organismType) {
        this(targetId, null, targetLevel, organismType, null);
    }

    public TargetParameter(long targetId, Map<Short, Long> attributeMap, int targetLevel,
                           int organismType, Map<Short, Long> changeAttributeMap) {
        this.targetId = targetId;
        this.attributeMap = attributeMap;
        this.targetLevel = targetLevel;
        this.organismType = organismType;
        this.changeAttributeMap = changeAttributeMap;
    }

    public long getTargetId() {
        return targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }

    public Map<Short, Long> getAttributeMap() {
        return attributeMap;
    }

    public void setAttributeMap(Map<Short, Long> attributeMap) {
        this.attributeMap = attributeMap;
    }

    public int getTargetLevel() {
        return targetLevel;
    }

    public void setTargetLevel(int targetLevel) {
        this.targetLevel = targetLevel;
    }

    public int getOrganismType() {
        return organismType;
    }

    public void setOrganismType(byte organismType) {
        this.organismType = organismType;
    }

    public Map<Short, Long> getChangeAttributeMap() {
        return changeAttributeMap;
    }

    public void setChangeAttributeMap(Map<Short, Long> changeAttributeMap) {
        this.changeAttributeMap = changeAttributeMap;
    }

    public List<FightBuffInfo> getFightBuffInfoList() {
        return fightBuffInfoList;
    }

    public void setFightBuffInfoList(List<FightBuffInfo> fightBuffInfoList) {
        this.fightBuffInfoList = fightBuffInfoList;
    }
}

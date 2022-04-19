package application.module.fight.skill.base.context;

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
    private byte organismType;

    public TargetParameter(long targetId) {
        this(targetId, null, 1, (byte) 0);
    }

    public TargetParameter(long targetId, Map<Short, Long> attributeMap, int targetLevel, byte organismType) {
        this.targetId = targetId;
        this.attributeMap = attributeMap;
        this.targetLevel = targetLevel;
        this.organismType = organismType;
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

    public byte getOrganismType() {
        return organismType;
    }

    public void setOrganismType(byte organismType) {
        this.organismType = organismType;
    }
}

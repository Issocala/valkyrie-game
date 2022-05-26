package application.module.scene.fight.skill.base.context;

import application.module.organism.FightOrganism;
import application.module.scene.fight.buff.FightOrganismBuff;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Luo Yong
 * @date 2022-3-15
 * @Source 1.0
 */
public class TargetParameter {

    private FightOrganism targetOrganism;

    private Map<Short, Long> changeAttributeMap;

    public TargetParameter(FightOrganism targetOrganism) {
        this(targetOrganism, new HashMap<>());
    }

    public TargetParameter(FightOrganism targetOrganism, Map<Short, Long> attributeMap) {
        this.targetOrganism = targetOrganism;
        this.changeAttributeMap = attributeMap;
    }

    public FightOrganism getTargetOrganism() {
        return targetOrganism;
    }

    public void setTargetOrganism(FightOrganism targetOrganism) {
        this.targetOrganism = targetOrganism;
    }

    public Map<Short, Long> getChangeAttributeMap() {
        return changeAttributeMap;
    }

    public void setChangeAttributeMap(Map<Short, Long> changeAttributeMap) {
        this.changeAttributeMap = changeAttributeMap;
    }

    public Map<Short, Long> getFightMap() {
        return targetOrganism.getFightMap();
    }

    public long getId() {
        return targetOrganism.getId();
    }

    public void addHp(FightOrganism attach, long hp) {
        targetOrganism.addHp(attach, hp);
    }

    public void addMp(FightOrganism attach, long mp) {
        targetOrganism.addMp(attach, mp);
    }

    public void addBuff(int templateId, FightOrganism from, FightOrganism to) {
        addBuff(templateId, from, to, 0);
    }

    public void addBuff(int templateId, FightOrganism from, FightOrganism to, long duration) {
        targetOrganism.getFightBuffMgr().addBuff(templateId, from, to, duration);
    }

    public void addBuff(FightOrganismBuff buff) {
        targetOrganism.getFightBuffMgr().addBuff(buff);
    }

    public Set<Integer> getEnableSkillSet() {
        return targetOrganism.getFightSkillMgr().getEnableSkillSet();
    }
}

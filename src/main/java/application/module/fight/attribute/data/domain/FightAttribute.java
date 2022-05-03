package application.module.fight.attribute.data.domain;

import application.module.organism.FightOrganism;

import java.util.Map;

import static application.module.fight.attribute.AttributeTemplateId.*;
import static application.module.fight.attribute.data.AttributeData.doAddAttribute;

/**
 * @author Luo Yong
 * @date 2022-4-18
 * @Source 1.0
 */
public class FightAttribute {

    private FightOrganism monsterOrganism;

    private Map<Short, Long> fightAttributeMap;

    public FightAttribute() {
    }

    public FightAttribute(FightOrganism monsterOrganism, Map<Short, Long> fightAttributeMap) {
        this.monsterOrganism = monsterOrganism;
        this.fightAttributeMap = fightAttributeMap;
        if (fightAttributeMap.containsKey(MAX_HP)) {
            fightAttributeMap.put(VAR_HP, fightAttributeMap.get(MAX_HP));
        }
        if (fightAttributeMap.containsKey(MAX_MP)) {
            fightAttributeMap.put(VAR_MP, fightAttributeMap.get(MAX_MP));
        }
    }

    public void addFightAttribute(Map<Short, Long> map) {
        doAddAttribute(fightAttributeMap, map);
    }

    public Map<Short, Long> getFightAttributeMap() {
        return fightAttributeMap;
    }

    public FightOrganism getMonsterOrganism() {
        return monsterOrganism;
    }

    public void setMonsterOrganism(FightOrganism monsterOrganism) {
        this.monsterOrganism = monsterOrganism;
    }

    public void setFightAttributeMap(Map<Short, Long> fightAttributeMap) {
        this.fightAttributeMap = fightAttributeMap;
    }
}

package application.module.fight.attribute.data.domain;

import application.module.organism.FightOrganism;

import java.util.Map;

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

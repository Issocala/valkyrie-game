package application.module.fight.attribute.data.domain;

import application.module.organism.MonsterOrganism;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-4-18
 * @Source 1.0
 */
public class MonsterAttribute {

    private MonsterOrganism monsterOrganism;

    private final Map<Short, Long> fightAttributeMap = new HashMap<>();

}

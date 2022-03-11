package application.module.fight.base.function;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-2-28
 * @Source 1.0
 */
public class FightSkillFunctionContainer {

    private final static Map<String, FightSkillFunction> map = new HashMap<>();

    public static FightSkillFunction getFunction(String name) {
        return map.get(name);
    }

    public static void add(String name, FightSkillFunction fightSkillFunction) {
        map.put(name, fightSkillFunction);
    }

}

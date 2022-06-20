package application.module.scene.gameinstance.wrap;

import application.condition.util.ConditionParser;
import application.module.scene.trigger.entrance.GamePlayerTriggerParser;
import template.GameInstanceTemplateHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-6-15
 * @Source 1.0
 */
public class GameInstanceWrapContainer {

    private final static Map<Integer, GameInstanceWrap> map = new HashMap<>();

    public static void init() {
        GameInstanceTemplateHolder.getValues().forEach(template -> {
            GameInstanceWrap wrap = GameInstanceWrap.of();
            parseEntryCondition(wrap, template.enterCondition());
            parseFailedCondition(wrap, template.failedCondition());
            parseTrigger(wrap, template.triggers());
            map.put(template.id(), wrap);
        });
    }

    /**
     * 解析进入条件
     */
    public static void parseEntryCondition(GameInstanceWrap wrap, String[] ss) {
             wrap.setEnterCondition(ConditionParser.parseCondition(ss));
    }

    /**
     * 解析副本失败条件
     */
    public static void parseFailedCondition(GameInstanceWrap wrap, String[] ss) {
        wrap.setFailedCondition(ConditionParser.parseCondition(ss));
    }

    /**
     * 解析触发器
     */
    public static void parseTrigger(GameInstanceWrap wrap, int[] triggers) {
        GamePlayerTriggerParser triggerParser = GamePlayerTriggerParser.of();
        wrap.setGamePlayerTriggerContainer(triggerParser.parserTriggerWrap(triggers));
    }

    public static GameInstanceWrap getData(int id) {
        return map.get(id);
    }
}

package application.module.scene.trigger.entrance;

import application.module.scene.trigger.action.TriggerActionContainer;
import application.module.scene.trigger.action.wrap.TriggerActionWrap;
import application.module.scene.trigger.condition.wrap.TriggerConditionWrap;
import application.module.scene.trigger.event.wrap.DefaultTriggerEventWrap;
import application.module.scene.trigger.event.wrap.TriggerEventWrap;
import application.module.scene.trigger.wrap.TriggerWrap;
import com.cala.orm.util.ClassScanningUtil;
import template.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author Luo Yong
 * @date 2022-6-15
 * @Source 1.0
 */
public class GamePlayerTriggerParser {

    private  Map<Integer, TriggerActionWrap> actionWrapTempMap;

    private  Map<Integer, TriggerConditionWrap> conditionWrapTempMap;

    public static GamePlayerTriggerParser of() {
        return new GamePlayerTriggerParser();
    }

    public TriggerWrapContainer parserTriggerWrap(int[] triggers) {
        this.actionWrapTempMap = new HashMap<>();
        this.conditionWrapTempMap = new HashMap<>();
        TriggerWrapContainer container = TriggerWrapContainer.of();
        for (int id : triggers) {
            TriggerTemplate template = TriggerTemplateHolder.getData(id);
            container.addTriggerWrap(parseTriggerTemplate(template));
        }
        //渣男！用完就回收！拔吊无情！
        this.actionWrapTempMap = null;
        this.conditionWrapTempMap = null;
        return container;
    }

    public TriggerWrap parseTriggerTemplate(TriggerTemplate template) {
        TriggerWrap triggerWrap = TriggerWrap.of();
        triggerWrap.setTriggerId(template.id());
        triggerWrap.setTriggerCount(template.triggerCount());
        triggerWrap.getEventWraps().addAll(parseTriggerEventWraps(template));
        triggerWrap.getConditionWraps().addAll(parseTriggerConditionWraps(template));
        triggerWrap.getActionWraps().addAll(parseTriggerActionWraps(template));
        return triggerWrap;
    }

    public List<TriggerEventWrap> parseTriggerEventWraps(TriggerTemplate template) {
        List<TriggerEventWrap> eventWraps = new ArrayList<>();
        for (String eventTemplateId : template.triggerEvent()) {
            eventWraps.add(DefaultTriggerEventWrap.of(eventTemplateId));
        }
        return eventWraps;
    }

    public List<TriggerConditionWrap> parseTriggerConditionWraps(TriggerTemplate template) {
        List<TriggerConditionWrap> conditionWraps = new ArrayList<>();
        for (int conditionId : template.triggerAction()) {
            TriggerConditionWrap wrap = this.conditionWrapTempMap.get(conditionId);
            if (Objects.isNull(wrap)) {
                wrap = parseTriggerConditionWrap(TriggerConditionsTemplateHolder.getData(conditionId));
            }
            conditionWraps.add(wrap);
        }
        return conditionWraps;
    }

    public TriggerConditionWrap parseTriggerConditionWrap(TriggerConditionsTemplate template) {
        int id = template.id();
        Class<?> function = TriggerActionContainer.getClass(template.function());
        Objects.requireNonNull(function, "function is null by triggerConditionId = " + id);
        Class<?> functionWrap = ClassScanningUtil.getClassGenricType(function, 0);
        try {
            Constructor<?> constructor = functionWrap.getDeclaredConstructor(int.class, String.class);
            TriggerConditionWrap wrap = (TriggerConditionWrap) constructor.newInstance(id, template.function());
            wrap.parse(template.data());
            return wrap;
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TriggerActionWrap> parseTriggerActionWraps(TriggerTemplate template) {
        List<TriggerActionWrap> actionWraps = new ArrayList<>();
        for (int conditionId : template.triggerAction()) {
            TriggerActionWrap wrap = this.actionWrapTempMap.get(conditionId);
            if (Objects.isNull(wrap)) {
                wrap = parseTriggerActionWrap(TriggerActionsTemplateHolder.getData(conditionId));
            }
            actionWraps.add(wrap);
        }
        return actionWraps;
    }

    public TriggerActionWrap parseTriggerActionWrap(TriggerActionsTemplate template) {
        int id = template.id();
        Class<?> function = TriggerActionContainer.getClass(template.function());
        Objects.requireNonNull(function, "function is null by triggerActionId = " + id);
        Class<?> functionWrap = ClassScanningUtil.getClassGenricType(function, 0);
        try {
            Constructor<?> constructor = functionWrap.getDeclaredConstructor(int.class, String.class);
            TriggerActionWrap wrap = (TriggerActionWrap) constructor.newInstance(id, template.function());
            wrap.parse(template.data());
            return wrap;
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}

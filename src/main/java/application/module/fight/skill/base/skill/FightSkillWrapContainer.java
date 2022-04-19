package application.module.fight.skill.base.skill;

import template.FightSkillProcessTemplate;
import template.FightSkillProcessTemplateHolder;
import template.FightSkillTemplate;
import template.FightSkillTemplateHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-2-24
 * @Source 1.0
 */
public class FightSkillWrapContainer {

    private final static Map<Integer, FightSkillWrap> templateId2FightSkillWrapMap = new HashMap<>();

    private final static Map<Integer, FightPassiveSkillWrap> templateId2FightPassiveSkillWrapMap = new HashMap<>();

    public static void init() {
        FightSkillTemplateHolder.getValues().forEach(fightSkillTemplate -> {
            FightSkillWrap fightSkillWrap = new FightSkillWrap(fightSkillTemplate);
            for (int processId : fightSkillTemplate.skillProcess()) {
                FightSkillProcessTemplate fightSkillProcessTemplate = FightSkillProcessTemplateHolder.getData(processId);
                fightSkillWrap.addFightSkillProcessTemplate(fightSkillProcessTemplate);
            }
            templateId2FightSkillWrapMap.put(fightSkillTemplate.id(), fightSkillWrap);
        });


    }

    public static boolean containsTemplate(int templateId) {
        return templateId2FightSkillWrapMap.containsKey(templateId);
    }

    public static boolean containsPassiveTemplate(int templateId) {
        return templateId2FightPassiveSkillWrapMap.containsKey(templateId);
    }

    public static FightPassiveSkillWrap getFightPassiveSkillWrap(int templateId) {
        return templateId2FightPassiveSkillWrapMap.get(templateId);
    }

    public static FightSkillWrap getFightSkillWrap(int templateId) {
        return templateId2FightSkillWrapMap.get(templateId);
    }

    public static FightSkillTemplate getFightSkillTemplate(int templateId) {
        FightSkillWrap fightSkillWrap = templateId2FightSkillWrapMap.get(templateId);
        return fightSkillWrap == null ? null : fightSkillWrap.getFightSkillTemplate();
    }

    public static FightSkillTemplate getFightPassiveSkillTemplate(int templateId) {
        FightPassiveSkillWrap fightPassiveSkillWrap = templateId2FightPassiveSkillWrapMap.get(templateId);
        return fightPassiveSkillWrap == null ? null : fightPassiveSkillWrap.getFightSkillTemplate();
    }

    public Map<Integer, FightSkillWrap> getTemplateId2FightSkillWrapMap() {
        return templateId2FightSkillWrapMap;
    }

    public Map<Integer, FightPassiveSkillWrap> getTemplateId2FightPassiveSkillWrapMap() {
        return templateId2FightPassiveSkillWrapMap;
    }
}

package application.module.fight.base.skill;

import application.util.StringUtils;
import template.FightSkillProcessTemplate;
import template.FightSkillProcessTemplateHolder;
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

    public static void init() {
        FightSkillTemplateHolder.getValues().forEach(fightSkillTemplate -> {
            String[] process = StringUtils.toStringArray(fightSkillTemplate.skillProcess());
            FightSkillWrap fightSkillWrap = new FightSkillWrap(fightSkillTemplate);
            for (String s : process) {
                int processId = Integer.parseInt(s);
                FightSkillProcessTemplate fightSkillProcessTemplate = FightSkillProcessTemplateHolder.getData(processId);
                fightSkillWrap.addFightSkillProcessTemplate(fightSkillProcessTemplate);
            }
            templateId2FightSkillWrapMap.put(fightSkillTemplate.id(), fightSkillWrap);
        });
    }

    public static FightSkillWrap getFightSkillWrap(int templateId) {
        return templateId2FightSkillWrapMap.get(templateId);
    }

    public Map<Integer, FightSkillWrap> getTemplateId2FightSkillWrapMap() {
        return templateId2FightSkillWrapMap;
    }
}

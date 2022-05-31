package application.module.organism;

import application.module.scene.Scene;
import application.module.scene.fight.attribute.FightAttributeMgr;
import application.module.scene.fight.state.base.StateType;
import application.util.StringUtils;
import application.util.Vector3;
import template.OrganismDataTemplate;
import template.OrganismDataTemplateHolder;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static application.module.player.fight.attribute.AttributeTemplateId.*;

/**
 * @author Luo Yong
 * @date 2022-4-18
 * @Source 1.0
 */
public class MonsterOrganism extends FightOrganism {

    public MonsterOrganism(int organismTemplateId) {
        this(organismTemplateId, Vector3.ZERO);
    }

    public MonsterOrganism(int organismTemplateId, Vector3 vector3) {
        super(OrganismType.MONSTER, vector3, organismTemplateId);
    }

    public static MonsterOrganism of(Scene scene, int organismTemplateId) {
        MonsterOrganism monsterOrganism = new MonsterOrganism(organismTemplateId);
        monsterOrganism.init(scene);
        return monsterOrganism;
    }

    public static MonsterOrganism of(Scene scene, int organismTemplateId, Vector3 vector3) {
        MonsterOrganism monsterOrganism = new MonsterOrganism(organismTemplateId, vector3);
        monsterOrganism.init(scene);
        return monsterOrganism;
    }

    public void init(Scene scene) {
        setScene(scene);
        OrganismDataTemplate organismDataTemplate = OrganismDataTemplateHolder.getData(getOrganismTemplateId());
        Map<Short, Long> fightAttributeMap = StringUtils.toAttributeMap(organismDataTemplate.attributeMap());
        if (fightAttributeMap.containsKey(MAX_HP)) {
            fightAttributeMap.put(VAR_HP, FightAttributeMgr.getValue(fightAttributeMap, MAX_HP));
        }
        if (fightAttributeMap.containsKey(MAX_MP)) {
            fightAttributeMap.put(VAR_MP, FightAttributeMgr.getValue(fightAttributeMap, MAX_MP));
        }
        getFightAttributeMgr().setFightMap(fightAttributeMap);

        Set<Integer> set = Arrays.stream(organismDataTemplate.skills()).boxed().collect(Collectors.toSet());
        getFightSkillMgr().setEnableSkillSet(set);
    }


    @Override
    protected void addHpAfter(FightOrganism attach, long currHp) {
        if (currHp == 0) {
            getFightStateMgr().changeState(StateType.ActionType.DEAD_STATE, getScene());
            getScene().getMonsterSceneMgr().removeMonsterOrganism(getId());

        }
    }

    @Override
    protected void addMpAfter(FightOrganism attach, long currMp) {

    }

    @Override
    public boolean isEnemy(FightOrganism fightOrganism) {
        return false;
    }
}

package application.module.organism;

import application.module.scene.Scene;
import application.module.scene.data.entity.PositionInfo;
import application.module.scene.fight.attribute.FightAttributeMgr;
import application.util.StringUtils;
import template.OrganismDataTemplate;
import template.OrganismDataTemplateHolder;

import java.util.Map;

import static application.module.player.fight.attribute.AttributeTemplateId.*;

/**
 * @author Luo Yong
 * @date 2022-4-18
 * @Source 1.0
 */
public class MonsterOrganism extends FightOrganism {


    public MonsterOrganism(int organismTemplateId) {
        this(organismTemplateId, null);
    }

    public MonsterOrganism(int organismTemplateId, PositionInfo positionInfo) {
        super(OrganismType.MONSTER, positionInfo, organismTemplateId);
    }

    private void init(Scene scene) {
        OrganismDataTemplate organismDataTemplate = OrganismDataTemplateHolder.getData(getOrganismTemplateId());
        Map<Short, Long> fightAttributeMap = StringUtils.toAttributeMap(organismDataTemplate.attributeMap());
        if (fightAttributeMap.containsKey(MAX_HP)) {
            fightAttributeMap.put(VAR_HP, FightAttributeMgr.getValue(fightAttributeMap, MAX_HP));
        }
        if (fightAttributeMap.containsKey(MAX_MP)) {
            fightAttributeMap.put(VAR_MP, FightAttributeMgr.getValue(fightAttributeMap, MAX_MP));
        }
        getFightAttributeMgr().setFightMap(fightAttributeMap);
    }

    @Override
    protected void addHpAfter(FightOrganism attach, long currHp) {

    }

    @Override
    protected void addMpAfter(FightOrganism attach, long currMp) {

    }
}

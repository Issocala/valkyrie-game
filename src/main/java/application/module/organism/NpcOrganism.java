package application.module.organism;

import application.module.scene.data.entity.PositionInfo;

/**
 * @author Luo Yong
 * @date 2022-4-22
 * @Source 1.0
 */
public class NpcOrganism extends FightOrganism {


    public NpcOrganism(int organismTemplateId) {
        super(OrganismType.NPC, organismTemplateId);
    }

    public NpcOrganism(int organismTemplateId, PositionInfo positionInfo) {
        super(OrganismType.NPC, positionInfo, organismTemplateId);
    }

    @Override
    protected void addHpAfter(FightOrganism attach, long currHp) {

    }

    @Override
    protected void addMpAfter(FightOrganism attach, long currMp) {

    }
}

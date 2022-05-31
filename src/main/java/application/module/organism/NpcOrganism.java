package application.module.organism;

import application.module.scene.Scene;
import application.util.Vector3;

/**
 * @author Luo Yong
 * @date 2022-4-22
 * @Source 1.0
 */
public class NpcOrganism extends FightOrganism {


    public NpcOrganism(int organismTemplateId) {
        super(OrganismType.NPC, organismTemplateId);
    }

    public NpcOrganism(int organismTemplateId, Vector3 v) {
        super(OrganismType.NPC, v, organismTemplateId);
    }

    public static NpcOrganism of(Scene scene, int organismTemplateId) {
        NpcOrganism npcOrganism = new NpcOrganism(organismTemplateId);
        npcOrganism.init(scene);
        return npcOrganism;
    }

    public static NpcOrganism of(Scene scene, int organismTemplateId, Vector3 v) {
        NpcOrganism npcOrganism = new NpcOrganism(organismTemplateId, v);
        npcOrganism.init(scene);
        return npcOrganism;
    }

    public void init(Scene scene) {
        setScene(scene);
    }

    @Override
    protected void addHpAfter(FightOrganism attach, long currHp) {

    }

    @Override
    protected void addMpAfter(FightOrganism attach, long currMp) {

    }

    @Override
    public boolean isEnemy(FightOrganism fightOrganism) {
        return false;
    }
}

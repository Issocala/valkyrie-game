package application.module.organism;

import application.module.scene.Scene;
import application.util.Vector3;

/**
 * @author Luo Yong
 * @date 2022-5-31
 * @Source 1.0
 */
public class EffectOrganism extends FightOrganism{

    public EffectOrganism(int organismTemplateId) {
        super(OrganismType.EFFECT, organismTemplateId);
    }

    public EffectOrganism(int organismTemplateId, Vector3 v) {
        super(OrganismType.EFFECT, v, organismTemplateId);
    }

    public static EffectOrganism of (Scene scene, int organismTemplateId) {
        EffectOrganism effectOrganism = new EffectOrganism(organismTemplateId);
        effectOrganism.init(scene);
        return effectOrganism;
    }

    public static EffectOrganism of (Scene scene, int organismTemplateId, Vector3 v) {
        EffectOrganism effectOrganism = new EffectOrganism(organismTemplateId, v);
        effectOrganism.init(scene);
        return effectOrganism;
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

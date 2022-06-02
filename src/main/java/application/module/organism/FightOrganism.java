package application.module.organism;


import akka.actor.ActorRef;
import application.guid.IdUtils;
import application.module.player.fight.attribute.AttributeProtocolBuilder;
import application.module.player.fight.attribute.AttributeProtocols;
import application.module.player.fight.skill.FightSkillProtocols;
import application.module.scene.Scene;
import application.module.scene.SceneProtocolBuilder;
import application.module.scene.SceneProtocols;
import application.module.scene.operate.SceneTick;
import application.util.Vector3;
import application.module.scene.fight.attribute.FightAttributeMgr;
import application.module.scene.fight.buff.FightBuffMgr;
import application.module.scene.fight.buff.FightBuffProtocolBuilder;
import application.module.scene.fight.buff.FightBuffProtocols;
import application.module.scene.fight.skill.FightSkillMgr;
import application.module.scene.fight.skill.FightSkillProtocolBuilder;
import application.module.scene.fight.state.FightStateMgr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static application.module.player.fight.attribute.AttributeTemplateId.*;

/**
 * 有战斗能力的生物
 *
 * @author Luo Yong
 * @date 2022-2-25
 * @Source 1.0
 */
public abstract class FightOrganism extends Organism {

    private FightAttributeMgr fightAttributeMgr;

    private FightBuffMgr fightBuffMgr;

    private FightStateMgr fightStateMgr;

    private FightSkillMgr fightSkillMgr;

    private List<FightOrganism> childOrganism = new ArrayList<>();


    public FightOrganism(byte organismType, int organismTemplateId) {
        this(IdUtils.fastSimpleUUIDLong(), organismType, organismTemplateId);
    }

    public FightOrganism(byte organismType, Vector3 vector3, int organismTemplateId) {
        this(IdUtils.fastSimpleUUIDLong(), organismType, vector3, organismTemplateId);
    }

    public FightOrganism(long id, byte organismType, int organismTemplateId) {
        this(id, organismType, Vector3.ZERO, organismTemplateId);
    }

    public FightOrganism(long id, byte organismType, Vector3 vector3, int organismTemplateId) {
        super(id, organismType, vector3, organismTemplateId);
        this.fightAttributeMgr = new FightAttributeMgr();
        this.fightAttributeMgr.setOwner(this);
        this.fightBuffMgr = new FightBuffMgr();
        this.fightBuffMgr.setOwner(this);
        this.fightStateMgr = new FightStateMgr();
        this.fightStateMgr.setOwner(this);
        this.fightSkillMgr = new FightSkillMgr();
        this.fightSkillMgr.setOwner(this);
    }

    public void tick(SceneTick sceneTick) {
        fightBuffMgr.tick();
    }

    public void addHp(FightOrganism attach, long hp) {
        if (hp == 0) {
            return;
        }
        Map<Short, Long> fightAttributeMap = fightAttributeMgr.getFightMap();
        long currHp = fightAttributeMap.getOrDefault(VAR_HP, 0L) + hp;
        if (currHp <= 0) {
            currHp = 0;
            // TODO: 2022-5-12 这里可以处理一些免死之类的 免试记得设置currHp为1
        }
        currHp = Math.min(FightAttributeMgr.getValue(fightAttributeMap, MAX_HP), currHp);
        fightAttributeMap.put(VAR_HP, currHp);
        getScene().getPlayerSceneMgr().sendToAllClient(getScene(), AttributeProtocols.FIGHT_ATTRIBUTE_GET,
                AttributeProtocolBuilder.get10040(getId(), Map.copyOf(fightAttributeMap)));
        addHpAfter(attach, currHp);
    }

    protected abstract void addHpAfter(FightOrganism attach, long currHp);

    public void addMp(FightOrganism attach, long mp) {
        if (mp == 0) {
            return;
        }
        Map<Short, Long> fightAttributeMap = fightAttributeMgr.getFightMap();
        long currHp = fightAttributeMap.getOrDefault(VAR_MP, 0L) + mp;
        if (currHp <= 0) {
            currHp = 0;
            // TODO: 2022-5-12 这里可以处理一些免死之类的 免死记得设置currHp为1
        }
        currHp = Math.min(FightAttributeMgr.getValue(fightAttributeMap, MAX_MP), currHp);
        fightAttributeMap.put(VAR_MP, currHp);
        getScene().getPlayerSceneMgr().sendToAllClient(getScene(), AttributeProtocols.FIGHT_ATTRIBUTE_GET,
                AttributeProtocolBuilder.get10040(getId(), Map.copyOf(fightAttributeMap)));
        addMpAfter(attach, mp);
    }

    protected abstract void addMpAfter(FightOrganism attach, long currMp);

    public boolean changeState(short id, Scene scene) {
        return getFightStateMgr().changeState(id, scene);
    }

    public boolean cancelState(short id, Scene scene) {
        return getFightStateMgr().cancelState(id, scene);
    }

    public boolean isState(short id) {
        return getFightStateMgr().isState(id);
    }

    public Map<Short, Long> getFightMap() {
        return this.getFightAttributeMgr().getFightMap();
    }

    public void sendSelfData(ActorRef client) {
        client.tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_RETURN_ENTITY,
                SceneProtocolBuilder.getSc10304(getId(), getOrganismType(), getPoint(), getOrganismTemplateId())), getScene().getSceneActor());
        if (getOrganismType() == OrganismType.PLAYER) {
            client.tell(new application.client.Client.SendToClientJ(FightSkillProtocols.GET_ALL,
                    FightSkillProtocolBuilder.getSc10050(getId(), getFightSkillMgr().getEnableSkillSet())), getScene().getSceneActor());
        }
        client.tell(new application.client.Client.SendToClientJ(AttributeProtocols.FIGHT_ATTRIBUTE_GET,
                AttributeProtocolBuilder.get10040(getId(), getFightMap())), getScene().getSceneActor());

        client.tell(new application.client.Client.SendToClientJ(FightBuffProtocols.GET,
                FightBuffProtocolBuilder.getSc10070(getId(), getFightBuffMgr().getFightOrganismBuffs())), getScene().getSceneActor());
    }

    public void sendSelfDataToSceneClient(Scene scene) {
        scene.getPlayerSceneMgr().getPlayerFightMap().values().forEach(playerFight -> {
            ActorRef client = playerFight.getClient();
            client.tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_RETURN_ENTITY,
                    SceneProtocolBuilder.getSc10304(getId(), getOrganismType(), getPoint(), getOrganismTemplateId())), getScene().getSceneActor());

            if (getOrganismType() == OrganismType.PLAYER) {
                client.tell(new application.client.Client.SendToClientJ(FightSkillProtocols.GET_ALL,
                        FightSkillProtocolBuilder.getSc10050(getId(), getFightSkillMgr().getEnableSkillSet())), getScene().getSceneActor());
            }

            client.tell(new application.client.Client.SendToClientJ(AttributeProtocols.FIGHT_ATTRIBUTE_GET,
                    AttributeProtocolBuilder.get10040(getId(), getFightMap())), getScene().getSceneActor());

            client.tell(new application.client.Client.SendToClientJ(FightBuffProtocols.GET,
                    FightBuffProtocolBuilder.getSc10070(getId(), getFightBuffMgr().getFightOrganismBuffs())), getScene().getSceneActor());
        });
    }

    public void switchScene() {
        fightBuffMgr.switchScene();
    }

    public abstract boolean isEnemy(FightOrganism fightOrganism);

    //get and set

    public FightAttributeMgr getFightAttributeMgr() {
        return fightAttributeMgr;
    }

    public void setFightAttributeMgr(FightAttributeMgr fightAttributeMgr) {
        this.fightAttributeMgr = fightAttributeMgr;
    }

    public FightBuffMgr getFightBuffMgr() {
        return fightBuffMgr;
    }

    public void setFightBuffMgr(FightBuffMgr fightBuffMgr) {
        this.fightBuffMgr = fightBuffMgr;
    }

    public FightStateMgr getFightStateMgr() {
        return fightStateMgr;
    }

    public void setFightStateMgr(FightStateMgr fightStateMgr) {
        this.fightStateMgr = fightStateMgr;
    }

    public FightSkillMgr getFightSkillMgr() {
        return fightSkillMgr;
    }

    public void setFightSkillMgr(FightSkillMgr fightSkillMgr) {
        this.fightSkillMgr = fightSkillMgr;
    }
}

package application.module.organism;

import akka.actor.ActorRef;
import application.module.player.Player;
import application.module.player.data.entity.PlayerInfo;
import application.module.player.fight.attribute.AttributePlayerMgr;
import application.module.player.fight.attribute.AttributeProtocolBuilder;
import application.module.player.fight.attribute.AttributeProtocols;
import application.module.player.fight.buff.BuffPlayerMgr;
import application.module.player.fight.skill.FightSkillProtocols;
import application.module.player.fight.skill.SkillPlayerMgr;
import application.module.player.fight.state.StatePlayerMgr;
import application.module.scene.Scene;
import application.module.scene.SceneProtocolBuilder;
import application.module.scene.SceneProtocols;
import application.module.scene.fight.buff.FightBuffProtocolBuilder;
import application.module.scene.fight.buff.FightBuffProtocols;
import application.module.scene.fight.skill.FightSkillProtocolBuilder;
import application.module.scene.fight.state.base.StateType;
import application.util.Vector3;

/**
 * @author Luo Yong
 * @date 2022-5-16
 * @Source 1.0
 */
public class PlayerFight extends FightOrganism {

    private int perSceneId;

    private ActorRef playerActor;

    private ActorRef client;

    private PlayerInfo playerInfo;

    private Vector3 vector3;

    public PlayerFight(long playerId, int profession) {
        super(playerId, OrganismType.PLAYER, profession);
    }

    public void init(Player player) {
        setPlayerInfo(player.getPlayerEntity().getPlayerInfo());
        setPlayerActor(player.getPlayerActor());
        setPerSceneId(player.getSceneMgr().getPreSceneId());
        SkillPlayerMgr skillMgr = (SkillPlayerMgr) player.getMgrMap().get(SkillPlayerMgr.class);
        this.getFightSkillMgr().setEnableSkillSet(skillMgr.getSkill().getEnableSkillSet());

        AttributePlayerMgr attributePlayerMgr = (AttributePlayerMgr) player.getMgr(AttributePlayerMgr.class);
        this.getFightAttributeMgr().setFightMap(attributePlayerMgr.getFightAttributeMap());

        BuffPlayerMgr buffPlayerMgr = (BuffPlayerMgr) player.getMgr(BuffPlayerMgr.class);

        StatePlayerMgr statePlayerMgr = (StatePlayerMgr) player.getMgr(StatePlayerMgr.class);

    }
    public void sendSelfDataToSceneOtherClient(Scene scene) {
        scene.getPlayerSceneMgr().getPlayerFightMap().values().forEach(playerFight -> {
            if (playerFight == this) {
                return;
            }
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

    //get and set

    public long getId() {
        return playerInfo.id();
    }

    public int getPerSceneId() {
        return perSceneId;
    }

    public void setPerSceneId(int perSceneId) {
        this.perSceneId = perSceneId;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    public ActorRef getPlayerActor() {
        return playerActor;
    }

    public void setPlayerActor(ActorRef playerActor) {
        this.playerActor = playerActor;
    }

    public ActorRef getClient() {
        return client;
    }

    public void setClient(ActorRef client) {
        this.client = client;
    }

    @Override
    public Vector3 getPoint() {
        return vector3;
    }

    @Override
    public void setPoint(Vector3 point) {
        this.vector3 = point;
    }

    @Override
    protected void addHpAfter(FightOrganism attach, long currHp) {
        if (currHp == 0) {
            getFightStateMgr().changeState(StateType.ActionType.DEAD_STATE, getScene());
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

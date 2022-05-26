package application.module.organism;

import akka.actor.ActorRef;
import application.module.player.Player;
import application.module.player.data.entity.PlayerInfo;
import application.module.player.fight.attribute.AttributePlayerMgr;
import application.module.player.fight.buff.BuffPlayerMgr;
import application.module.player.fight.skill.SkillPlayerMgr;
import application.module.player.fight.state.StatePlayerMgr;
import application.module.scene.data.entity.PositionInfo;

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

    private PositionInfo positionInfo;

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
    public PositionInfo getPositionInfo() {
        return positionInfo;
    }

    @Override
    public void setPositionInfo(PositionInfo positionInfo) {
        this.positionInfo = positionInfo;
    }

    @Override
    protected void addHpAfter(FightOrganism attach, long currHp) {
        if (currHp == 0) {
            // TODO: 2022-5-19 改变玩家状态
        }
    }

    @Override
    protected void addMpAfter(FightOrganism attach, long currMp) {

    }
}

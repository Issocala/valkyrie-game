package application.module.scene.organism;

import akka.actor.ActorRef;
import application.client.Client;
import application.module.player.PlayerFight;
import application.module.player.fight.skill.operate.SkillUseScene;
import application.module.player.util.PlayerOperateType;
import application.module.scene.Scene;
import application.module.scene.SceneMgr;
import application.module.scene.SceneProtocolBuilder;
import application.module.scene.SceneProtocols;
import application.module.scene.operate.AoiSendMessageToClient;
import com.cala.orm.message.OperateType;
import com.google.protobuf.GeneratedMessageV3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Luo Yong
 * @date 2022-5-16
 * @Source 1.0
 */
public class PlayerSceneMgr implements SceneMgr {

    private final Map<Long, PlayerFight> playerFightMap = new HashMap<>();

    private long aiAgentPlayerId;

    @Override
    public void receiver(Scene scene, OperateType type) {
        if (type instanceof AoiSendMessageToClient aoi) {
            sendToAllClient(scene, aoi.protoId(), aoi.message());
        } else {
            throw new IllegalStateException("Unexpected value: " + type.getClass().getName());
        }
    }

    public void receiver(Scene scene, PlayerOperateType type) {
        if (type instanceof SkillUseScene skillUseScene) {
            mobius.modular.client.Client.ReceivedFromClient r = skillUseScene.r();
            PlayerFight playerFight = this.playerFightMap.get(r.uID());
            if (Objects.nonNull(playerFight)) {
                playerFight.setClient(r.client());
                playerFight.getFightSkillMgr().activeUseSkill(scene, skillUseScene.cs10052());
            }
        }
    }


    @Override
    public void init(Scene scene) {

    }

    @Override
    public void destroy(Scene scene) {

    }

    public void addPlayerFight(PlayerFight playerFight) {
        this.playerFightMap.put(playerFight.getId(), playerFight);
    }

    public void removePlayerFight(long id) {
        this.playerFightMap.remove(id);
    }

    public PlayerFight getPlayerFight(long id) {
        return this.playerFightMap.get(id);
    }

    public boolean containsPlayerFight(long id) {
        return this.playerFightMap.containsKey(id);
    }

    public void sendToAllClient(Scene scene, int protoId, GeneratedMessageV3 message) {
        playerFightMap.values().forEach(playerFight -> playerFight.getClient().tell(
                new application.client.Client.SendToClientJ(protoId, message), scene.getSceneActor()));
    }

    public void sendToOtherClient(Scene scene, int protoId, GeneratedMessageV3 message, long organismId) {
        playerFightMap.values().forEach(playerFight -> {
            if (playerFight.getId() != organismId) {
                playerFight.getClient().tell(new application.client.Client.SendToClientJ(protoId,
                        message), scene.getSceneActor());
            }
        });
    }

    public void selectClient(Scene scene) {
        if (this.aiAgentPlayerId == 0) {
            for (Map.Entry<Long, PlayerFight> entry : this.playerFightMap.entrySet()) {
                ActorRef client = entry.getValue().getClient();
                if (Objects.nonNull(client)) {
                    aiAgentPlayerId = entry.getKey();
                    client.tell(new Client.SendToClientJ(SceneProtocols.AI, SceneProtocolBuilder.getSc10307()), scene.getSceneActor());
                    return;
                }
            }
        } else {
            if (!this.playerFightMap.containsKey(this.aiAgentPlayerId)) {
                this.aiAgentPlayerId = 0;
            } else {
                ActorRef actorRef = this.playerFightMap.get(this.aiAgentPlayerId).getClient();
                if (Objects.isNull(actorRef)) {
                    this.aiAgentPlayerId = 0;
                }
            }
        }
    }

    @Override
    public List<Class<? extends OperateType>> getOperateTypes() {
        return List.of(AoiSendMessageToClient.class);
    }

    public Map<Long, PlayerFight> getPlayerFightMap() {
        return playerFightMap;
    }

    public ActorRef getClient(long playerId) {
        return this.playerFightMap.get(playerId).getClient();
    }

    public long getAiAgentPlayerId() {
        return aiAgentPlayerId;
    }
}

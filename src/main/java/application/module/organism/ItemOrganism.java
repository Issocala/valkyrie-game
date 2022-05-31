package application.module.organism;

import akka.actor.ActorRef;
import application.module.scene.Scene;
import application.module.scene.SceneProtocolBuilder;
import application.module.scene.SceneProtocols;
import application.util.Vector3;

/**
 * @author Luo Yong
 * @date 2022-5-9
 * @Source 1.0
 */
public class ItemOrganism extends Organism {

    public ItemOrganism(int organismTemplateId) {
        super(OrganismType.ITEM, organismTemplateId);
    }

    public ItemOrganism(int organismTemplateId, Vector3 vector3) {
        super(OrganismType.ITEM, vector3, organismTemplateId);
    }

    public static ItemOrganism of(Scene scene, int organismTemplateId) {
        ItemOrganism item = new ItemOrganism(organismTemplateId);
        item.init(scene);
        return item;
    }

    public static ItemOrganism of(Scene scene, int organismTemplateId, Vector3 v) {
        ItemOrganism item = new ItemOrganism(organismTemplateId, v);
        item.init(scene);
        return item;
    }

    public void init(Scene scene) {
        setScene(scene);
    }

    public void sendSelfData(ActorRef client) {
        client.tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_RETURN_ENTITY,
                SceneProtocolBuilder.getSc10304(getId(), getOrganismType(), getPoint(), getOrganismTemplateId())), getScene().getSceneActor());
    }

    public void sendSelfDataToSceneClient(Scene scene) {
        scene.getPlayerSceneMgr().getPlayerFightMap().values().forEach(playerFight -> {
            ActorRef client = playerFight.getClient();
            client.tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_RETURN_ENTITY,
                    SceneProtocolBuilder.getSc10304(getId(), getOrganismType(), getPoint(), getOrganismTemplateId())), getScene().getSceneActor());
        });
    }

}

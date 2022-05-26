package application.module.organism;

import akka.actor.ActorRef;
import application.module.scene.SceneProtocolBuilder;
import application.module.scene.SceneProtocols;
import application.module.scene.data.entity.PositionInfo;

/**
 * @author Luo Yong
 * @date 2022-5-9
 * @Source 1.0
 */
public class ItemOrganism extends Organism {

    public ItemOrganism(int organismTemplateId) {
        super(OrganismType.ITEM, organismTemplateId);
    }

    public ItemOrganism(int organismTemplateId, PositionInfo positionInfo) {
        super(OrganismType.ITEM, positionInfo, organismTemplateId);
    }

    public void sendSelfData(ActorRef client) {
        client.tell(new application.client.Client.SendToClientJ(SceneProtocols.SCENE_RETURN_ENTITY,
                SceneProtocolBuilder.getSc10304(getId(), getOrganismType(), getPositionInfo(), getOrganismTemplateId())), getScene().getSceneActor());
    }

}

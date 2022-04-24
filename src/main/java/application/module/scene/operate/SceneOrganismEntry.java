package application.module.scene.operate;

import application.module.organism.Organism;
import application.module.scene.data.domain.PositionInfo;

/**
 * @author Luo Yong
 * @date 2022-4-19
 * @Source 1.0
 */
public record SceneOrganismEntry(Organism organism, PositionInfo positionInfo) {
}

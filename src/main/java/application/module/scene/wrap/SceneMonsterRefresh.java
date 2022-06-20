package application.module.scene.wrap;

import application.util.Vector3;

/**
 * @author Luo Yong
 * @date 2022-6-7
 * @Source 1.0
 */
public record SceneMonsterRefresh(int monsterTemplateId, int monsterCount, Vector3[] birthPoints,
                                  Vector3 direction) {

}

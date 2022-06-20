package application.module.scene.wrap;

import application.util.Vector3;
import template.SceneDataTemplate;

/**
 * @author Luo Yong
 * @date 2022-6-7
 * @Source 1.0
 */
public record SceneTemplateWrap(SceneDataTemplate template, Vector3[] birthPoints, Vector3[] revivePoints,
                                Vector3[] transferPoints) {

}

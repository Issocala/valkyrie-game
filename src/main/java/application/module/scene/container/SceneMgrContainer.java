package application.module.scene.container;

import application.module.scene.SceneMgr;
import application.module.scene.organism.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Luo Yong
 * @date 2022-5-16
 * @Source 1.0
 */
public class SceneMgrContainer {

    public void init() {
    }

    public static Set<SceneMgr> getSceneMgr() {
        Set<SceneMgr> set = new HashSet<>();

        set.add(new PlayerSceneMgr());
        set.add(new MonsterSceneMgr());
        set.add(new NpcSceneMgr());
        set.add(new ItemSceneMgr());
        set.add(new EffectSceneMgr());

        return set;
    }
}

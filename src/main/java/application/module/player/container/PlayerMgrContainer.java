package application.module.player.container;

import application.module.player.fight.state.StatePlayerMgr;
import application.module.player.util.PlayerMgr;
import application.module.player.fight.attribute.AttributePlayerMgr;
import application.module.player.fight.buff.BuffPlayerMgr;
import application.module.player.fight.skill.SkillPlayerMgr;
import application.module.player.scene.ScenePlayerMgr;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Luo Yong
 * @date 2022-5-13
 * @Source 1.0
 */
public class PlayerMgrContainer {

    public void init() {

    }

    public static Set<PlayerMgr> getPlayerMgr() {
        Set<PlayerMgr> set = new HashSet<>();
        set.add(new ScenePlayerMgr());
        set.add(new AttributePlayerMgr());
        set.add(new SkillPlayerMgr());
        set.add(new BuffPlayerMgr());
        set.add(new StatePlayerMgr());
        return set;
    }

}

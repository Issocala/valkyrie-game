package application.module.scene.fight.state.base;

import application.module.scene.fight.state.FightStateMgr;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Luo Yong
 * @date 2022-2-28
 * @Source 1.0
 */
public abstract class FSMStateBase {
    /**
     * 状态id
     */
    private final short id;

    /**
     * 可以转换的状态
     */
    private final Set<Short> transitionSet = new HashSet<>();

    public FSMStateBase(short id) {
        this.id = id;
    }

    public abstract void enter(FightStateMgr fightStateMgr);

    public abstract void exit(FightStateMgr fightStateMgr);

    public void addTransition(short transitionId) {
        transitionSet.add(transitionId);
    }

    public boolean isTransition(short transitionId) {
        return this.transitionSet.contains(transitionId);
    }

    public void addTransition(short... transitionIds) {
        for (short id : transitionIds) {
            transitionSet.add(id);
        }
    }

    public abstract boolean isBroadcast();

    public short getId() {
        return id;
    }

    public Set<Short> getTransitionSet() {
        return transitionSet;
    }
}

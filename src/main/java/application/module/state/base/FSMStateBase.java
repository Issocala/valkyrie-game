package application.module.state.base;

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

    public abstract void enter(FightOrganismState fightOrganismState);

    public abstract void exit(FightOrganismState fightOrganismState);

    public void addTransition(short transitionId) {
        transitionSet.add(id);
    }

    public boolean isTransition(short id) {
        return this.transitionSet.contains(id);
    }

    public void addTransition(short... transitionIds) {
        for (short id : transitionIds) {
            transitionSet.add(id);
        }
    }

    public boolean isTransitionSet(short id) {
        return this.transitionSet.contains(id);
    }

    public abstract boolean isBroadcast();

    public short getId() {
        return id;
    }

    public Set<Short> getTransitionSet() {
        return transitionSet;
    }
}

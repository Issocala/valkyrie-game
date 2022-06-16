package application.module.scene.trigger.entrance;

import application.module.scene.trigger.wrap.TriggerWrap;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luo Yong
 * @date 2022-6-11
 * @Source 1.0
 */
public class TriggerWrapContainer {

    private final List<TriggerWrap> triggerWraps = new ArrayList<>();

    public static TriggerWrapContainer of() {
        return new TriggerWrapContainer();
    }

    public void addTriggerWrap(TriggerWrap triggerWrap) {
        this.triggerWraps.add(triggerWrap);
    }


    public TriggerWrap getTriggerWrap(int id) {
        for (TriggerWrap triggerWrap : this.triggerWraps) {
            if (triggerWrap.getTriggerId() == id) {
                return triggerWrap;
            }
        }
        return null;
    }

    public List<TriggerWrap> getTriggerWraps() {
        return triggerWraps;
    }

}

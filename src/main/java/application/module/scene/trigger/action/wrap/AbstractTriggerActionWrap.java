package application.module.scene.trigger.action.wrap;

/**
 * @author Luo Yong
 * @date 2022-6-11
 * @Source 1.0
 */
public abstract class AbstractTriggerActionWrap implements TriggerActionWrap {

    private final int id;

    private final String actionClassName;


    public AbstractTriggerActionWrap(int id, String actionClassName) {
        this.id = id;
        this.actionClassName = actionClassName;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getActionClassName() {
        return this.actionClassName;
    }
}

package application.module.scene.trigger.condition.wrap;

/**
 * @author Luo Yong
 * @date 2022-6-11
 * @Source 1.0
 */
public abstract class AbstractTriggerConditionWrap implements TriggerConditionWrap {

    private final int id;

    private final String conditionClassName;


    public AbstractTriggerConditionWrap(int id, String conditionClassName) {
        this.id = id;
        this.conditionClassName = conditionClassName;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getConditionClassName() {
        return this.conditionClassName;
    }
}

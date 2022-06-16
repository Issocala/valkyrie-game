package application.module.scene.trigger.event;

/**
 * @author Luo Yong
 * @date 2022-6-11
 * @Source 1.0
 */
public class DefaultTriggerEvent<T> implements TriggerEvent<T> {

    private final String eventId;

    private T data;

    public DefaultTriggerEvent(String eventId) {
        this.eventId = eventId;
    }

    public DefaultTriggerEvent(String eventId, T data) {
        this.eventId = eventId;
        this.data = data;
    }

    @Override

    public String getId() {
        return this.eventId;
    }

    @Override
    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

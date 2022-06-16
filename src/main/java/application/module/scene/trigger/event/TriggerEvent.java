package application.module.scene.trigger.event;

/**
 * @author Luo Yong
 * @date 2022-6-11
 * @Source 1.0
 */
public interface TriggerEvent<T> {

    String getId();

    T getData();

}

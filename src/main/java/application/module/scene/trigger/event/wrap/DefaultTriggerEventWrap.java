package application.module.scene.trigger.event.wrap;

/**
 * @author Luo Yong
 * @date 2022-6-11
 * @Source 1.0
 */
public record DefaultTriggerEventWrap(String eventTemplateId) implements TriggerEventWrap{

    public static DefaultTriggerEventWrap of(String eventTemplateId) {
        return new DefaultTriggerEventWrap(eventTemplateId);
    }


    @Override
    public String getEventId() {
        return this.eventTemplateId;
    }

}

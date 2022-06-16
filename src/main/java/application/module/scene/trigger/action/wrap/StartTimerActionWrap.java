package application.module.scene.trigger.action.wrap;

import application.util.StringUtils;

/**
 * @author Luo Yong
 * @date 2022-6-13
 * @Source 1.0
 */
public class StartTimerActionWrap extends AbstractTriggerActionWrap{

    private int timerId;

    private int seconds;

    private boolean isPeriod;

    public StartTimerActionWrap(int id, String actionClassName) {
        super(id, actionClassName);
    }

    @Override
    public void parse(String s) {
        String[] ss = StringUtils.toStringArray(s);
        this.timerId = Integer.parseInt(ss[0]);
        this.seconds = Integer.parseInt(ss[1]);
        this.isPeriod = Boolean.parseBoolean(ss[0]);
    }

    public int getTimerId() {
        return timerId;
    }

    public int getSeconds() {
        return seconds;
    }

    public boolean isPeriod() {
        return isPeriod;
    }
}

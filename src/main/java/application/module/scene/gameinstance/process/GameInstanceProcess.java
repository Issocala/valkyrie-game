package application.module.scene.gameinstance.process;

/**
 * @author Luo Yong
 * @date 2022-6-8
 * @Source 1.0
 */
public class GameInstanceProcess {

    // 创建时间
    private final long createTime;
    // 副本结束时间
    private final long endTime;

    // 副本正式关闭时间
    private long closeTime;

    public GameInstanceProcess(long createTime, long endTime) {
        this.createTime = createTime;
        this.endTime = endTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(long closeTime) {
        this.closeTime = closeTime;
    }
}

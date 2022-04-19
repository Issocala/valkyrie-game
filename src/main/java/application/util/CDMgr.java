package application.util;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * * @author Luo Yong
 * * @date 2022-2-14
 * * @Source 1.0
 */
public class CDMgr {
    private final Map<Long, CDTimeManager> id2CDTimeManagerMap = new HashMap<>();
    private long publicFightCDMillis;
    private long lastPublicFightTimeMillis;

    public CDMgr(long publicFightCDMillis) {
        super();
        this.publicFightCDMillis = publicFightCDMillis;
        this.lastPublicFightTimeMillis = 0;
    }

    public CDMgr(long publicFightCDMillis, long lastPublicFightTimeMillis) {
        super();
        this.publicFightCDMillis = publicFightCDMillis;
        this.lastPublicFightTimeMillis = lastPublicFightTimeMillis;
    }

    private final class CDTimeManager {
        private int id;
        private long fightCDMillis;

        private long lastFightTimeMillis;

        public CDTimeManager(int id, long fightCDMillis) {
            super();
            this.setId(id);
            this.fightCDMillis = fightCDMillis;
            this.lastFightTimeMillis = 0;
        }

        public CDTimeManager(int skillTempId, long fightCDMillis, long lastFightTimeMillis) {
            super();
            this.setId(skillTempId);
            this.fightCDMillis = fightCDMillis;
            this.lastFightTimeMillis = lastFightTimeMillis;
        }

        public boolean isCDStarted() {
            return this.lastFightTimeMillis != 0;
        }

        public void update() {
            this.lastFightTimeMillis = System.currentTimeMillis();
            lastPublicFightTimeMillis = System.currentTimeMillis();
        }

        public void update(long now) {
            this.lastFightTimeMillis = now;
            lastPublicFightTimeMillis = now;
        }

        /**
         * 是否已经冷却 包括公共冷却时间
         */
        public boolean isOutOfCD() {
            long now = System.currentTimeMillis();
            boolean isOutOfThisCD = now - this.lastFightTimeMillis > this.fightCDMillis;
            boolean isOutOfPublicCD = now - lastPublicFightTimeMillis > publicFightCDMillis;
            return isOutOfThisCD && isOutOfPublicCD;
        }

        /**
         * 是否已经冷却 忽略公共冷却
         */
        public boolean isOutOfThisCD() {
            long now = System.currentTimeMillis();
            return now - this.lastFightTimeMillis > this.fightCDMillis;
        }

        public long getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getFightCDMillis() {
            return fightCDMillis;
        }

        public void setFightCDMillis(long time) {
            this.fightCDMillis = time;
        }

        public long getLastFightTimeMillis() {
            return lastFightTimeMillis;
        }

        public void setLastFightTimeMillis(long time) {
            this.lastFightTimeMillis = time;
        }

        @Override
        public String toString() {
            return "CDTimeManager [id=" + id + ", fightCDMillis=" + fightCDMillis + ", lastFightTimeMillis=" + lastFightTimeMillis + "]";
        }
    }

    private void addCDManager(CDTimeManager cdManager) {
        this.id2CDTimeManagerMap.put(cdManager.getId(), cdManager);
    }

    public CDTimeManager getCDManager(long id) {
        return this.id2CDTimeManagerMap.get(id);
    }

    public void startCD(int id, long fightCDMillis) {
        CDTimeManager cdManager = getCDManager(id);
        if (cdManager == null) {
            addCDManager(new CDTimeManager(id, fightCDMillis));
        }
        checkNotNull(getCDManager(id));
    }

    public void startCD(int id, long fightCDMillis, long startFromWhen) {
        CDTimeManager cdManager = getCDManager(id);
        if (cdManager == null) {
            addCDManager(new CDTimeManager(id, fightCDMillis, startFromWhen));
        }
        checkNotNull(getCDManager(id));
    }

    public void resetCD(int id, long fightCDMillis) {
        if (getCDManager(id) == null) {
            addCDManager(new CDTimeManager(id, fightCDMillis, 0L));
        }
        CDTimeManager cdManager = getCDManager(id);
        cdManager.setFightCDMillis(fightCDMillis);
        checkNotNull(getCDManager(id));
    }

    /**
     * 是否已经冷却 计算公共冷却时间
     */
    public boolean isOutOfCD(int id) {
        CDTimeManager cdManager = getCDManager(id);
        return !cdManager.isOutOfCD();
    }

    /**
     * 是否已经冷却 忽略公共冷却
     */
    public boolean isOutOfThisCD(int id) {
        CDTimeManager cdManager = getCDManager(id);
        checkArgument(cdManager != null, "CD of id " + id + " is not started.");
        return cdManager.isOutOfThisCD();
    }

    public void update(int id) {
        CDTimeManager cdManager = getCDManager(id);
        checkArgument(cdManager != null, "CD of id " + id + " is not started.");
        cdManager.update();
    }

    public void update(int id, long now) {
        CDTimeManager cdManager = getCDManager(id);
        checkArgument(cdManager != null, "CD of id " + id + " is not started.");
        cdManager.update(now);
    }

    public boolean isCDStarted(long id) {
        CDTimeManager cdManager = this.id2CDTimeManagerMap.get(id);
        return cdManager != null && cdManager.isCDStarted();
    }

    public void setPublicFightCDMillis(long publicFightCDMillis) {
        this.publicFightCDMillis = publicFightCDMillis;
    }

    public long getPublicFightCDMillis() {
        return publicFightCDMillis;
    }

    public long getLastPublicFightTimeMillis() {
        return lastPublicFightTimeMillis;
    }

    public long getFightCDMillis(int id) {
        CDTimeManager cdManager = getCDManager(id);
        checkArgument(cdManager != null, "CD of id " + id + " is not started.");
        return cdManager.getFightCDMillis();
    }

    public void setFightCDMillis(int id, long time) {
        CDTimeManager cdManager = getCDManager(id);
        checkArgument(cdManager != null, "CD of id " + id + " is not started.");
        cdManager.setFightCDMillis(time);
    }

    public long getLastFightTimeMillis(int id) {
        CDTimeManager cdManager = getCDManager(id);
        checkArgument(cdManager != null, "CD of id " + id + " is not started.");
        return cdManager.getLastFightTimeMillis();
    }

    public long getRemainFightTimeMillis(int id) {
        CDTimeManager cdManager = getCDManager(id);
        checkArgument(cdManager != null, "CD of id " + id + " is not started.");
        long now = System.currentTimeMillis();
        long cdTime = getFightCDMillis(id) + getLastFightTimeMillis(id) - now;
        return cdTime <= 0 ? 0 : cdTime;
    }

    public void setLastFightTimeMillis(int id, long time) {
        CDTimeManager cdManager = getCDManager(id);
        checkArgument(cdManager != null, "CD of id " + id + " is not started.");
        cdManager.setLastFightTimeMillis(time);
    }

    public boolean isCDTime(int id, long CDTime) {
        if (isCDStarted(id)) {
            startCD(id, CDTime * 1000);
            return false;
        }
        return isOutOfCD(id);
    }

    public boolean isOutOfThisCD(int id, long CDTime) {
        if (isCDStarted(id)) {
            startCD(id, CDTime);
            return false;
        }

        return !isOutOfThisCD(id);
    }

    public boolean isOutOfThisCDOnSecond(int id, long CDTime) {
        return isOutOfThisCD(id, CDTime * TimeMacro.SECOND);
    }

    @Override
    public String toString() {
        return "CDMgr [cds=" + id2CDTimeManagerMap + ", publicFightCDMillis=" + publicFightCDMillis + ", lastPublicFightTimeMillis=" + lastPublicFightTimeMillis + "]";
    }

    public void clearCD(int id) {
        this.id2CDTimeManagerMap.remove(id);
    }

}
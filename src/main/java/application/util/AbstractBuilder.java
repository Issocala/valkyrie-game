package application.util;

import com.cala.orm.cache.DbStatus;

/**
 * @author Luo Yong
 * @date 2022-1-7
 * @Source 1.0
 */
public class AbstractBuilder {
    private Long id;
    private DbStatus dbStatus;

    public Long getId() {
        return id;
    }

    public AbstractBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public DbStatus getDbStatus() {
        return dbStatus;
    }

    public AbstractBuilder setDbStatus(DbStatus dbStatus) {
        this.dbStatus = dbStatus;
        return this;
    }
}

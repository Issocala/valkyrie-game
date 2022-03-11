package application.util;

/**
 * @author Luo Yong
 * @date 2022-1-7
 * @Source 1.0
 */
public class AbstractBuilder {
    private Long id;

    public AbstractBuilder() {
    }

    public AbstractBuilder(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public AbstractBuilder setId(Long id) {
        this.id = id;
        return this;
    }
}

package application.module.organism;

import application.util.LongId;

/**
 * 生物
 *
 * @author Luo Yong
 * @date 2022-2-25
 * @Source 1.0
 */
public abstract class Organism extends LongId {
    public Organism(long id) {
        super(id);
    }
}

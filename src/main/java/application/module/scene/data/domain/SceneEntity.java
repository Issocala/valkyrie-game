package application.module.scene.data.domain;

import com.cala.orm.annotation.DbDeserialize;
import com.cala.orm.cache.AbstractEntityBase;

/**
 * @author Luo Yong
 * @date 2022-3-3
 * @Source 1.0
 */
@DbDeserialize
public class SceneEntity extends AbstractEntityBase {

    /**
     * @param id
     */
    public SceneEntity(Long id) {
        super(id);
    }

}

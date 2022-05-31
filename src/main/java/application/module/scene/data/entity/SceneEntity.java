package application.module.scene.data.entity;

import application.util.AbstractBuilder;
import application.util.Vector3;
import com.cala.orm.annotation.DbDeserialize;
import com.cala.orm.annotation.DbPojoBuilder;
import com.cala.orm.cache.AbstractEntityBase;
import com.cala.orm.converter.Convert;
import com.cala.orm.converter.JsonAttributeConverter;

import javax.persistence.Column;

/**
 * @author Luo Yong
 * @date 2022-3-3
 * @Source 1.0
 */
@DbDeserialize(builder = SceneEntity.Builder.class)
public class SceneEntity extends AbstractEntityBase {

    /**
     * 停留的上一个常驻场景id,重新上线进入的场景
     */
    @Column
    private final int sceneTemplateId;
    /**
     * 停留的上一个常驻场景的位置
     */
    @Column
    @Convert(converter = JsonAttributeConverter.class)
    private final Vector3 point;

    /**
     * @param id
     */
    public SceneEntity(Long id) {
        this(id, 0, Vector3.ZERO);
    }

    public SceneEntity(Long id, int sceneTemplateId, Vector3 point) {
        super(id);
        this.sceneTemplateId = sceneTemplateId;
        this.point = point;
    }

    @DbPojoBuilder
    public static class Builder extends AbstractBuilder {
        private int sceneTemplateId;

        private Vector3 point;

        public Builder setSceneTemplateId(int sceneTemplateId) {
            this.sceneTemplateId = sceneTemplateId;
            return this;
        }

        public Builder setPositionInfo(Vector3 v) {
            this.point = v;
            return this;
        }

        public SceneEntity build(){
            return new SceneEntity(getId(), sceneTemplateId, point);
        }
    }

    public int getSceneTemplateId() {
        return sceneTemplateId;
    }

    public Vector3 getPoint() {
        return point;
    }
}

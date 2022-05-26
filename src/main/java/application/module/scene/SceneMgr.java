package application.module.scene;

import com.cala.orm.cache.AbstractBase;
import com.cala.orm.message.OperateType;
import mobius.modular.client.Client;

import java.util.List;

/**
 * @author Luo Yong
 * @date 2022-5-16
 * @Source 1.0
 */
public interface SceneMgr {

    default void receiver(Scene scene, OperateType p) {
    }

    default void receiver(Scene scene, AbstractBase abstractBase, OperateType p) {
    }

    default void receiver(Scene scene, List<AbstractBase> abstractBases, OperateType p) {
    }

    void init(Scene scene);

    void destroy(Scene scene);

    List<Class<? extends OperateType>> getOperateTypes();

}

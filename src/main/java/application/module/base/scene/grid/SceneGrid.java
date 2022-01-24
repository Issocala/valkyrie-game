package application.module.base.scene.grid;

/**
 * 地形格子基类
 * @author Luo Yong
 * @date 2021-12-27
 * @Source 1.0
 */
public interface SceneGrid {

    int getGridWidth();

    int getGridHeight();

    int getRow();

    int getColumn();

    boolean isMovable();

    boolean isBlocked();

    boolean isWalkable();

}
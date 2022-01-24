package application.module.base.scene.grid;

import java.util.Objects;

/**
 * 场景-地形-格
 *
 * @author Luo Yong
 * @date 2021-12-27
 * @Source 1.0
 */
public record SceneTerrainGrid(short row, short column, byte terrainType) implements SceneGrid {

    private final static byte DEFAULT_GRID_WIDTH = 4;
    private final static byte DEFAULT_GRID_HEIGHT = 4;

    private static byte GRID_WIDTH = DEFAULT_GRID_WIDTH;
    private static byte GRID_HEIGHT = DEFAULT_GRID_HEIGHT;


    public static void setGridWidth(byte gridWidth) {
        GRID_WIDTH = gridWidth;
    }

    public static void setGridHeight(byte gridHeight) {
        GRID_HEIGHT = gridHeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SceneTerrainGrid that = (SceneTerrainGrid) o;
        return row == that.row && column == that.column && terrainType == that.terrainType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column, terrainType);
    }

    @Override
    public int getGridWidth() {
        return GRID_WIDTH;
    }

    @Override
    public int getGridHeight() {
        return GRID_HEIGHT;
    }

    @Override
    public int getRow() {
        return this.row;
    }

    @Override
    public int getColumn() {
        return this.column;
    }

    @Override
    public boolean isMovable() {
        return !isBlocked();
    }

    @Override
    public boolean isBlocked() {
        return this.terrainType == SceneTerrainType.BLOCKED;
    }

    @Override
    public boolean isWalkable() {
        return this.terrainType == SceneTerrainType.WALKABLE;
    }
}

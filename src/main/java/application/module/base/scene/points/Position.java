package application.module.base.scene.points;

import java.util.Objects;

/**
 *
 * 坐标点
 *
 * @author Luo Yong
 * @date 2021-12-29
 * @Source 1.0
 */
public final record Position(float x, float y, float dir) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Float.compare(position.x, x) == 0 && Float.compare(position.y, y) == 0 && Float.compare(position.dir, dir) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, dir);
    }
}

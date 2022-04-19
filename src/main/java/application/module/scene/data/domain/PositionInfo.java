package application.module.scene.data.domain;

/**
 * @author Luo Yong
 * @date 2022-3-18
 * @Source 1.0
 */
public class PositionInfo {

    private float positionX;
    private float positionY;
    private int face;

    public PositionInfo() {
    }

    public PositionInfo(float positionX, float positionY, int face) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.face = face;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public int getFace() {
        return face;
    }

    public void setFace(int face) {
        this.face = face;
    }
}

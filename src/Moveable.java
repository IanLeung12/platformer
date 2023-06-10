abstract public class Moveable extends GameObject{

    private int xSpeed, ySpeed = 0;

    Moveable(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public int getXSpeed() {
        return xSpeed;
    }

    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public int getYSpeed() {
        return ySpeed;
    }

    public void setYSpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }











}

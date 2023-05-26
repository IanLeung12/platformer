abstract public class Moveable extends Alive{

    private int xSpeed, ySpeed;

    private final int gravity;

    Moveable(int x, int y, int width, int height, double health) {
        super(x, y, width, height, health);
        this.gravity = 2;
    }

    public void move() {
        this.setLocation((int) (this.getX() + xSpeed), (int) (this.getY() + ySpeed));

    }

    public int getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }
}

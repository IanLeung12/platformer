abstract public class Moveable extends Alive{

    private int xSpeed, ySpeed = 0;
    private int immunityTimer;
    private final int maxImmunity = 0;

    Moveable(int x, int y, int width, int height, double health, double totalHealth) {
        super(x, y, width, height, health, totalHealth);
    }




    //knockback(gameObject otherObject) {}


    public abstract void move();
//        this.setLocation((int) (this.getX() + xSpeed), (int) (this.getY() + ySpeed));



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


    public int getImmunityTimer() {
        return immunityTimer;
    }

    public void setImmunityTimer(int immunityTimer) {
        this.immunityTimer = immunityTimer;
    }

    public int getMaxImmunity() {
        return maxImmunity;
    }
}

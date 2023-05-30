abstract public class Moveable extends Alive{

    private int xSpeed, ySpeed;
    private final int gravity;
    private int immunityTimer;
    private final int maxImmunity;






    Moveable(int x, int y, int width, int height, double health, double totalHealth) {
        super(x, y, width, height, health, totalHealth);
        this.gravity = 2;
        this.maxImmunity = 69696996;
    }




    //knockback(gameObject otherObject) {}


    public abstract void move();
//        this.setLocation((int) (this.getX() + xSpeed), (int) (this.getY() + ySpeed));



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


    public int getGravity() {
        return gravity;
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

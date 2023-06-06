abstract public class Moveable extends Alive{

    private int xSpeed, ySpeed = 0;
    private int immunityTimer;
    private final int maxImmunity = 0;
    private int direction;


    Moveable(int x, int y, int width, int height, double health, double totalHealth) {
        super(x, y, width, height, health, totalHealth);
    }




    public void knockback(GameObject otherObject) {

        double dX = (this.getCenterX() - otherObject.getCenterX());
        double dY = (otherObject.getCenterY() - this.getCenterY());

        double interval = 35/(Math.abs(dX) + Math.abs(dY));

        this.setXSpeed((int) (dX * interval));

        if ((this.getXSpeed() < 10) && (this.getXSpeed() != 0)) {
            this.setXSpeed(this.getXSpeed()/Math.abs(this.getXSpeed()) * 20);
        } if (this.getXSpeed() == 0) {
            this.setXSpeed(-20 * this.getDirection());
        }

        this.setYSpeed((int) (dY * interval));

        this.immunityTimer = this.maxImmunity;

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


    public int getImmunityTimer() {
        return immunityTimer;
    }

    public void setImmunityTimer(int immunityTimer) {
        this.immunityTimer = immunityTimer;
    }

    public int getMaxImmunity() {
        return maxImmunity;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}

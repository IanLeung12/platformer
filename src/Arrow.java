public class Arrow extends Attacks{

    private int xSpeed, ySpeed;
    Arrow(int x, int y, int targetX, int targetY, boolean isFriendly) {
        super(x, y, 100, 50, 20, targetX > x ? 1 : -1, isFriendly, 100);
        double dX = targetX - this.getCenterX();
        double dY = -(targetY - this.getCenterY());
        double interval = 40/(Math.abs(dX) + Math.abs(dY) + 1);
        this.xSpeed = (int) (dX * interval);
        this.ySpeed = (int) ((dY * interval) + (xSpeed == 0 ? 0 : dX/xSpeed/2));
    }

    public void move() {
        this.translate(xSpeed, -ySpeed);
        this.ySpeed --;
    }
}

public class Arrow extends Projectile{

    Arrow(int x, int y, int targetX, int targetY, int direction, boolean isFriendly) {
        super(x, y, 100, 50, 20, direction, isFriendly, 100);
        double dX = targetX - this.getCenterX();
        double dY = -(targetY - this.getCenterY());
        double interval = 40/(Math.abs(dX) + Math.abs(dY) + 1);
        this.setXSpeed((int) (dX * interval));
        this.setYSpeed((int) ((dY * interval) + (this.getXSpeed() == 0 ? 0 : dX/this.getXSpeed()/2)));
    }

    public void move() {
        super.move();
        this.setYSpeed(this.getYSpeed() - 1);
    }
}

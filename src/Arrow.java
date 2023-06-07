public class Arrow extends Projectile{

    Arrow(int x, int y, int targetX, int targetY, int direction, boolean isFriendly, int Power) {
        super(x, y, 100, 50, (int) (5 + Power * 0.5), direction, isFriendly, 100);
        double dX = targetX - this.getCenterX();
        double dY = -(targetY - this.getCenterY());
        double interval = Power/(Math.abs(dX) + Math.abs(dY) + 1);
        this.setXSpeed((int) (dX * interval));
        this.setYSpeed((int) ((dY * interval) + (this.getXSpeed() == 0 ? dY * interval : dX/this.getXSpeed()/2)));
        if (this.getYSpeed() > Power * 2) {
            this.setYSpeed(Power * 2);
        }
    }

    public void move() {
        super.move();
        this.setYSpeed(this.getYSpeed() - 1);
    }
}

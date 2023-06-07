public class Rocket extends Projectile{

    Rocket(int x, int y, int targetX, int targetY, int direction, boolean isFriendly) {
        super(x, y, 50, 25, 30, direction, isFriendly, 60);
        double dX = targetX - this.getCenterX();
        double dY = -(targetY - this.getCenterY());
        double interval = 30/(Math.abs(dX) + Math.abs(dY) + 1);
        this.setXSpeed((int) (dX * interval));
        this.setYSpeed((int) (dY * interval));
    }
}

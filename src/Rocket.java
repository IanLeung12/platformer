public class Rocket extends Projectile{

    Rocket(int x, int y, int targetX, int targetY, int direction, boolean isFriendly, double damageBoost) {
        super(x, y, 25, 25, (int) (30 * damageBoost), direction, isFriendly, 60, Constants.rocketCost);
        double dX = targetX - this.getCenterX();
        double dY = -(targetY - this.getCenterY());
        double interval = 30/(Math.abs(dX) + Math.abs(dY) + 1);
        this.setXSpeed((int) (dX * interval));
        this.setYSpeed((int) (dY * interval));
    }
}

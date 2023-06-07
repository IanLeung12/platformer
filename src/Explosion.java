public class Explosion extends Attack{

    private final int maxRadius;

    private int radius;

    private int lastRadius;

    Explosion(int x, int y, boolean isFriendly, int maxRadius) {
        super(x, y, (int) (200 / Math.sqrt(2)), (int) (200 / Math.sqrt(2)), 40, 0, isFriendly, 240);
        this.maxRadius = maxRadius;
        this.radius = 0;
        this.lastRadius = 0;
    }

    public void expand() {
        this.lastRadius = radius;
        this.radius = maxRadius * this.getAbilityDuration()/this.getMaxAbilityDuration();
        this.translate(-radius + lastRadius, -radius + lastRadius);
    }

    public int getMaxRadius() {
        return maxRadius;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}

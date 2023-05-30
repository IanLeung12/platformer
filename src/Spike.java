
abstract public class Spike extends Alive{
    private boolean breakable;
    private double damage;

    Spike(int x, int y, int width, int height, double health, double totalHealth, boolean breakable, double damage) {
        super(x, y, width, height, health, totalHealth);
        this.breakable = breakable;
        this.damage = damage;
    }

    public void setBreakable(boolean breakable) {
        this.breakable = breakable;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public boolean isBreakable() {
        return this.breakable;
    }

    public double getDamage() {
        return this.damage;
    }
}

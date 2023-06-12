
public class Spike extends Wall{
    private double damage;

    Spike(int x, int y, int width, int height, int damage) {
        super(x, y, width, height);
        this.damage = damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getDamage() {
        return this.damage;
    }
}

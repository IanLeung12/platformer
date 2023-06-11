
public class Spike extends Wall{
    private double damage;

    Spike(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.damage = 25;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getDamage() {
        return this.damage;
    }
}

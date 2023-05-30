abstract public class Alive extends GameObject{

    private double health;
    private final double totalHealth;

    Alive(int x, int y, int width, int height, double health, double totalHealth) {
        super(x, y, width, height);
        this.health = health;
        this.totalHealth = totalHealth;
    }



    public double getHealth() {
        return health;
    }
    public void setHealth(double health) {
        this.health = health;
    }

    public double getTotalHealth() {
        return totalHealth;
    }


}

abstract public class Alive extends GameObject{

    private double health;

    Alive(int x, int y, int width, int height, double health) {
        super(x, y, width, height);
        this.health = health;
    }
}

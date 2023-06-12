public class Hammer extends Attack{

    Hammer(int x, int y, int direction, boolean isFriendly, double damageBoost) {
        super(x, y, 250, 250, (int) (40 * damageBoost), direction, isFriendly, 20);
    }
}

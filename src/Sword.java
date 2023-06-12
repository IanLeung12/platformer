public class Sword extends Attack{//


    // sword
    Sword(int x, int y, int direction, boolean isFriendly, double damageBoost) {
        super(x, y, 150, 200, (int) (30 * damageBoost), direction, isFriendly, 12);
    }
}

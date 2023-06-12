/**
 * File Name - [Jumper.java]
 * Description - enemy
 * @Author - Michael Khart & Ian Leung
 * @Date - June 8, 2023
 */

public class Spike extends Wall{
    private double damage;

    /**
     * spike
     *  amkes a spike object
     * @param x - the x
     * @param y - the y
     * @param width - the width
     * @param height - the length
     * @param damage - the damage
     */

    Spike(int x, int y, int width, int height, int damage) {
        super(x, y, width, height);
        this.damage = damage;
    }


    /**
     * get damage
     * gets damage
     * @return - damaghe
     */
    public double getDamage() {
        return this.damage;
    }
}

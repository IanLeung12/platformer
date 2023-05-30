abstract public class Enemy extends Moveable {

    private double damage;
    private int goldReward;
    private int respawnTimer;
    private int fullRespawnTimer;


    Enemy(int x, int y, int width, int height, double health, double totalHealth, double damage, int goldReward, int respawnTimer, int fullRespawnTimer) {
        super(x, y, width, height, health, totalHealth);
        this.damage = damage;
        this.goldReward = goldReward;
        this.respawnTimer = respawnTimer;
        this.fullRespawnTimer = fullRespawnTimer;
    }

    // no respawn
    Enemy(int x, int y, int width, int height, double health, double totalHealth, double damage, int goldReward) {
        super(x, y, width, height, health, totalHealth);
        this.damage = damage;
        this.goldReward = goldReward;

    }


    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public int getGoldReward() {
        return goldReward;
    }

    public void setGoldReward(int goldReward) {
        this.goldReward = goldReward;
    }

    public int getRespawnTimer() {
        return respawnTimer;
    }

    public void setRespawnTimer(int respawnTimer) {
        this.respawnTimer = respawnTimer;
    }

    public int getFullRespawnTimer() {
        return fullRespawnTimer;
    }

    public void setFullRespawnTimer(int fullRespawnTimer) {
        this.fullRespawnTimer = fullRespawnTimer;
    }
}

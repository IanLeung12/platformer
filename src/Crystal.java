public class Crystal extends Wall {

    private String boostType;

    private int boostValue;

    private int health;

    private final int maxHealth;

    private int immunityTimer;

    private int respawnTimer;


    Crystal(int x, int y, int width, int height, String boostType) {
        super(x, y, width, height, false);
        this.boostType = boostType;
        this.boostValue = (int) (Math.random() * 3 + 5) * 10;
        this.maxHealth = 35;
        this.health = 35;

    }

    public void crystalTick() {
        if (this.immunityTimer > 0) {
            this.immunityTimer--;
        }
        if (this.respawnTimer > 0) {
            this.respawnTimer--;
        }

        if (this.health < 0) {
            this.respawnTimer = 1500;
            this.health = maxHealth;
        }
    }

    public void takeHit(Attack attack) {
        if ((immunityTimer == 0) && (this.respawnTimer == 0)) {
            this.health -= attack.getAttackDamage();
            this.immunityTimer = 25;
        }
    }

    public String getBoostType() {
        return boostType;
    }

    public void setBoostType(String boostType) {
        this.boostType = boostType;
    }

    public int getBoostValue() {
        return boostValue;
    }

    public void setBoostValue(int boostValue) {
        this.boostValue = boostValue;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getRespawnTimer() {
        return respawnTimer;
    }

    public void setRespawnTimer(int respawnTimer) {
        this.respawnTimer = respawnTimer;
    }
}


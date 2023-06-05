abstract public class Attacks extends GameObject{

    private double attackDamage;
    private boolean isFriendly;
    private int abilityDuration;
    private final int maxAbilityDuration;

    private final int direction;

    Attacks(int x, int y, int width, int height, int attackDamage, int direction, boolean isFriendly,int maxDuration) {
        super(x, y, width, height);
        this.attackDamage = attackDamage;
        this.direction = direction;
        this.isFriendly = isFriendly;
        this.abilityDuration = 0;
        this.maxAbilityDuration = maxDuration;
    }

    public double getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(double attackDamage) {
        this.attackDamage = attackDamage;
    }

    public boolean isFriendly() {
        return isFriendly;
    }

    public void setFriendly(boolean friendly) {
        isFriendly = friendly;
    }

    public int getAbilityDuration() {
        return abilityDuration;
    }

    public void setAbilityDuration(int abilityDuration) {
        this.abilityDuration = abilityDuration;
    }

    public int getMaxAbilityDuration() {
        return maxAbilityDuration;
    }

    public int getDirection() {
        return direction;
    }
}

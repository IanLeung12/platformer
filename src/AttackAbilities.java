abstract public class AttackAbilities extends GameObject{

    private double energyCost;
    private double attackDamage;
    private int cooldownTimer;
    private final int fullCooldown;
    private boolean isFriendly;
    private int abilityDuration;
    private final int maxAbilityDuration;
    private int mouseX, mouseY;

    AttackAbilities(int x, int y, int width, int height, int energyCost, int attackDamage, int cooldownTimer, int fullCooldown, boolean isFriendly, int abilityDuration, int maxDuration, int mouseX, int mouseY) {
        super(x, y, width, height);
        this.attackDamage = attackDamage;
        this.cooldownTimer = cooldownTimer;
        this.fullCooldown = fullCooldown;
        this.isFriendly = isFriendly;
        this.abilityDuration = abilityDuration;
        this.maxAbilityDuration = maxDuration;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.energyCost = energyCost;

    }


    public double getEnergyCost() {
        return energyCost;
    }

    public void setEnergyCost(double energyCost) {
        this.energyCost = energyCost;
    }

    public double getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(double attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getCooldownTimer() {
        return cooldownTimer;
    }

    public void setCooldownTimer(int cooldownTimer) {
        this.cooldownTimer = cooldownTimer;
    }

    public int getFullCooldown() {
        return fullCooldown;
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

    public int getMouseX() {
        return mouseX;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }
}

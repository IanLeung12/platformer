
import java.util.ArrayList;

public class Player extends Moveable {

    private double totalGold;
    private double energy;
    private boolean inAnim; // ian what initial value
    private int animFrames;// ian what initial value
    private String currentAnim;// ian what initial value
    private int totalJumps;
    private int maxJumps;
    private boolean dashUnlocked;
    private boolean bashUnlocked;
    private AttackAbilities currentWeapon;
    private int[] abilityDirection; // no initial value
    private boolean abilityActive;
    private boolean attackActive;

    private boolean movingRight;

    private boolean movingLeft;

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    private ArrayList<String> Weapons;

    Player(int x, int y, int width, int height, double health, double totalHealth) {
        super(x, y, width, height, health, totalHealth);
        this.totalGold = 0;
        this.energy = 3;
        this.totalJumps = 0;
        this.maxJumps = 1;
        this.dashUnlocked = false;
        this.bashUnlocked = false;

        // add a single weapon into weapons ===============================

    }

    public void move() {

        this.setLocation((int) this.getX() + this.getXSpeed(), (int) this.getY() - this.getYSpeed());
        this.setYSpeed(this.getYSpeed() - this.getGravity());

        if (movingRight && this.getXSpeed() < 25) {
            this.setXSpeed(this.getXSpeed() + 2);
        } else if (movingLeft && this.getXSpeed() > -25) {
            this.setXSpeed(this.getXSpeed() - 2);
        } else if (this.getXSpeed() != 0) {
            this.setXSpeed(this.getXSpeed() - this.getXSpeed()/Math.abs(this.getXSpeed()) * 2);
            if (Math.abs(this.getXSpeed()) <= 2) {
                this.setXSpeed(0);
            }
        }


    }

    public void jump() {
       // if (this.getYSpeed() > 0) {
        //    this.setYSpeed(this.getYSpeed() + 40);
       // } else {
            this.setYSpeed(40);
     //   }

    }

    public void fixCollision(GameObject collider) {

        double playerBottom = this.getY() + this.getHeight();
        double colliderTop = collider.getY();
        double playerRight = this.getX() + this.getWidth();
        double colliderLeft = collider.getX();
        double playerLeft = this.getX();
        double colliderRight = collider.getX() + collider.getWidth();

        if (playerBottom > colliderTop && this.getY() + this.getYSpeed() < colliderTop) {
            // Player is colliding with the top of the collider
            this.setLocation((int) this.getX(), (int) (colliderTop - this.getHeight()));
            this.setYSpeed(0); // Stop the player's vertical movement
        } else if (this.getY() < colliderTop + collider.getHeight() && playerBottom > colliderTop + collider.getHeight()) {
            // Player is colliding with the bottom of the collider
            this.setLocation((int) this.getX(), (int) (colliderTop + collider.getHeight()));
            this.setYSpeed(0); // Stop the player's vertical movement
        } else if (playerRight > colliderLeft && playerLeft < colliderLeft && playerBottom > colliderTop && this.getY() < colliderTop + collider.getHeight()) {
            // Player is colliding with the left side of the collider
            this.setLocation((int) (colliderLeft - this.getWidth()), (int) this.getY());
            this.setXSpeed(-this.getXSpeed()); // Reverse the player's horizontal speed
        } else if (this.getX() < colliderRight && playerRight > colliderRight && playerBottom > colliderTop && this.getY() < colliderTop + collider.getHeight()) {
            // Player is colliding with the right side of the collider
            this.setLocation((int) (colliderRight), (int) this.getY());
            this.setXSpeed(-this.getXSpeed()); // Reverse the player's horizontal speed
        }

    }

    public void updatePlayer() {
    }


    //collision(GameObject otherObject) {}
    // attack()
    // jump()
    //dash(speedX, speedY)
    // bash()



    public double getTotalGold() {
        return totalGold;
    }

    public void setTotalGold(double totalGold) {
        this.totalGold = totalGold;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public boolean isInAnim() {
        return inAnim;
    }

    public void setInAnim(boolean inAnim) {
        this.inAnim = inAnim;
    }

    public int getAnimFrames() {
        return animFrames;
    }

    public void setAnimFrames(int animFrames) {
        this.animFrames = animFrames;
    }

    public String getCurrentAnim() {
        return currentAnim;
    }

    public void setCurrentAnim(String currentAnim) {
        this.currentAnim = currentAnim;
    }

    public int getTotalJumps() {
        return totalJumps;
    }

    public void setTotalJumps(int totalJumps) {
        this.totalJumps = totalJumps;
    }

    public int getMaxJumps() {
        return maxJumps;
    }

    public void setMaxJumps(int maxJumps) {
        this.maxJumps = maxJumps;
    }

    public boolean isDashUnlocked() {
        return dashUnlocked;
    }

    public void setDashUnlocked(boolean dashUnlocked) {
        this.dashUnlocked = dashUnlocked;
    }

    public boolean isBashUnlocked() {
        return bashUnlocked;
    }

    public void setBashUnlocked(boolean bashUnlocked) {
        this.bashUnlocked = bashUnlocked;
    }

    public AttackAbilities getCurrentWeapon() {
        return currentWeapon;
    }

    public void setCurrentWeapon(AttackAbilities currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    public int[]getAbilityDirection() {
        return abilityDirection;
    }

    public void setAbilityDirection(int[] abilityDirection) {
        this.abilityDirection = abilityDirection;
    }

    public boolean isAbilityActive() {
        return abilityActive;
    }

    public void setAbilityActive(boolean abilityActive) {
        this.abilityActive = abilityActive;
    }

    public boolean isAttackActive() {
        return attackActive;
    }

    public void setAttackActive(boolean attackActive) {
        this.attackActive = attackActive;
    }

    public ArrayList<String> getWeapons() {
        return Weapons;
    }

    public void setWeapons(ArrayList<String> weapons) {
        Weapons = weapons;
    }
}

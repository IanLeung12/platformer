
import java.util.ArrayList;

public class Player extends Moveable {

    private double totalGold;
    private double energy;
    private boolean inAnim; // ian what initial value
    private int animFrames;// ian what initial value
    private String currentAnim;// ian what initial value
    private int jumpNum;
    private int maxJumps;
    private boolean dashUnlocked;
    private boolean bashUnlocked;
    private AttackAbilities currentWeapon;
    private int[] abilityDirection = {0, 0};
    private int[] abilityTravelled = {0, 0};
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
        this.jumpNum = 0;
        this.maxJumps = 2;
        this.dashUnlocked = false;
        this.bashUnlocked = false;


        // add a single weapon into weapons ===============================

    }

    public void move() {
        if (abilityActive) {
            this.movementAbility();
        } else {
            this.setLocation((int) this.getX() + this.getXSpeed(), (int) this.getY() - this.getYSpeed());
            this.setYSpeed(this.getYSpeed() - Constants.getGravity());

            if (movingRight && this.getXSpeed() < Constants.getMaxXSpeed()) {
                this.setXSpeed(this.getXSpeed() + Constants.getXSpeedAddition());
            } else if (movingLeft && this.getXSpeed() > (-1) * (Constants.getMaxXSpeed())) {
                this.setXSpeed(this.getXSpeed() - Constants.getXSpeedAddition());
            } else if (this.getXSpeed() != 0) {
                this.setXSpeed(this.getXSpeed() - this.getXSpeed()/Math.abs(this.getXSpeed()) * 2);
                if (Math.abs(this.getXSpeed()) <= Constants.getXSpeedAddition()) {
                    this.setXSpeed(0);
                }
            }
        }




    }

    public void jump() {

        if (this.getJumpNum() < this.getMaxJumps()) {
            this.setYSpeed(Constants.getJumpBoost());
            this.setJumpNum(this.getJumpNum() + 1);
        }

    }

    public void movementAbility() {

        if ((Math.abs(this.abilityTravelled[0]) < Constants.getMovementAbilityTotal()) && (Math.abs(this.abilityTravelled[1]) < Constants.getMovementAbilityTotal())) {
            this.setLocation((int) (this.getX() + this.abilityDirection[0]), (int) (this.getY() + this.abilityDirection[1]));
            this.abilityTravelled[0] += abilityDirection[0];
            this.abilityTravelled[1] += abilityDirection[1];
        } else if ((Math.abs(this.abilityTravelled[0]) >= Constants.getMovementAbilityTotal()) || (Math.abs(this.abilityTravelled[1]) >= Constants.getMovementAbilityTotal())) {

            if (this.abilityTravelled[0] < 0) {
                this.setXSpeed(Constants.getXSpeedAddition() * -20);

            } else if (this.abilityTravelled[0] > 0) {
                this.setXSpeed(Constants.getXSpeedAddition() * 20);

            }


            this.abilityTravelled[0] = 0;
            this.abilityTravelled[1] = 0;
            this.abilityActive = false;


        }



    }



    public void fixCollision(GameObject otherObject) {

        double playerBottom = this.getY() + this.getHeight();
        double otherObjectTop = otherObject.getY();
        double playerRight = this.getX() + this.getWidth();
        double otherObjectLeft = otherObject.getX();
        double playerLeft = this.getX();
        double otherObjectRight = otherObject.getX() + otherObject.getWidth();

        if (playerBottom > otherObjectTop && this.getY() + this.getYSpeed() < otherObjectTop) {
            this.setLocation((int) this.getX(), (int) (otherObjectTop - this.getHeight()));
            this.setYSpeed(0); // Stop the player's vertical movement
            this.setJumpNum(0);
            this.setAbilityActive(false);
        } else if (this.getY() < otherObjectTop + otherObject.getHeight() && playerBottom > otherObjectTop + otherObject.getHeight()) {
            this.setLocation((int) this.getX(), (int) (otherObjectTop + otherObject.getHeight()));
            this.setYSpeed(0);
        } else if (playerRight > otherObjectLeft && playerLeft < otherObjectLeft && playerBottom > otherObjectTop && this.getY() < otherObjectTop + otherObject.getHeight()) {
            this.setLocation((int) (otherObjectLeft - this.getWidth()), (int) this.getY());
            this.setXSpeed(-this.getXSpeed()); // Reverse the player's horizontal speed
            this.setJumpNum(0);
            this.setAbilityActive(false);
        } else if (this.getX() < otherObjectRight && playerRight > otherObjectRight && playerBottom > otherObjectTop && this.getY() < otherObjectTop + otherObject.getHeight()) {
            this.setLocation((int) (otherObjectRight), (int) this.getY());
            this.setXSpeed(-this.getXSpeed()); // Reverse the player's horizontal speed
            this.setJumpNum(0);
            this.setAbilityActive(false);

        }

    }

    public void updatePlayer() {
    }


    //collision(GameObject otherObject) {}
    // attack()
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

    public int getJumpNum() {
        return jumpNum;
    }

    public void setJumpNum(int jumpNum) {
        this.jumpNum = jumpNum;
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

    public void setAbilityDirection(int xChange, int yChange) {
        this.abilityDirection[0] = xChange;
        this.abilityDirection[1] = yChange;
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

    public int[] getAbilityTravelled() {
        return abilityTravelled;
    }

    public void setAbilityTravelled(int[] abilityTravelled) {
        this.abilityTravelled = abilityTravelled;
    }
}

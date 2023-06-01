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
    private boolean dashUsed = false;
    private boolean bashUsed = false;
    private AttackAbilities currentWeapon;
    private int[] abilityDirection = {0, 0};
    private int[] abilityTravelled = {0, 0};
    private boolean abilityActive = false;
    private boolean attackActive;
    private boolean movingRight;
    private boolean movingLeft;
    private int direction;
    private ArrayList<String> Weapons;

    Player(int x, int y, int width, int height, double health, double totalHealth) {
        super(x, y, width, height, health, totalHealth);
        this.totalGold = 0;
        this.energy = 3;
        this.jumpNum = 0;
        this.maxJumps = 2;
        this.direction = 1;
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
        this.dashUsed = true;
        this.setBashUsed(true);
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

        if (otherObject instanceof Spike) {
            double dX = (this.getCenterX() - otherObject.getCenterX());
            double dY = (otherObject.getCenterY() - this.getCenterY());

            double interval = 35/(Math.abs(dX) + Math.abs(dY));

            this.setXSpeed((int) (dX * interval));
            if ((this.getXSpeed() < 10) && (this.getXSpeed() != 0)) {
                this.setXSpeed(this.getXSpeed()/Math.abs(this.getXSpeed()) * 20);
            } if (this.getXSpeed() == 0) {
                this.setXSpeed(-20 * this.getDirection());
            }
            this.setYSpeed((int) (dY * interval));
            System.out.println(this.getYSpeed());

        } else {
            double playerBottom = this.getY() + this.getHeight();
            double otherObjectTop = otherObject.getY();
            double playerRight = this.getX() + this.getWidth();
            double colliderLeft = otherObject.getX();
            double playerLeft = this.getX();
            double colliderRight = otherObject.getX() + otherObject.getWidth();

            if (playerBottom > otherObjectTop && this.getY() + this.getYSpeed() < otherObjectTop) {
                this.setLocation((int) this.getX(), (int) (otherObjectTop - this.getHeight()));
                this.setYSpeed(0); // Stop the player's vertical movement
                this.setJumpNum(0);
                this.dashUsed = false;
            } else if (this.getY() < otherObjectTop + otherObject.getHeight() && playerBottom > otherObjectTop + otherObject.getHeight()) {
                this.setLocation((int) this.getX(), (int) (otherObjectTop + otherObject.getHeight()));
                this.setYSpeed(0);
            } else if (playerRight > colliderLeft && playerLeft < colliderLeft && playerBottom > otherObjectTop && this.getY() < otherObjectTop + otherObject.getHeight()) {
                this.setLocation((int) (colliderLeft - this.getWidth()), (int) this.getY());
                this.setXSpeed(0); // Reverse the player's horizontal speed
                //this.setDirection(-this.direction);
                this.setJumpNum(0);
                this.dashUsed = false;
            } else if (this.getX() < colliderRight && playerRight > colliderRight && playerBottom > otherObjectTop && this.getY() < otherObjectTop + otherObject.getHeight()) {
                this.setLocation((int) (colliderRight), (int) this.getY());
                this.setXSpeed(0); // Reverse the player's horizontal speed
                //this.setDirection(-this.direction);
                this.setJumpNum(0);
                this.dashUsed = false;

            }
        }

    }

    public void updatePlayer() {
    }


    //collision(GameObject otherObject) {}
    // attack()
    // jump()
    //dash(speedX, speedY)
    // bash()


    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

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

    public void setAbilityDirection(int xChange, int yChange) {
        this.abilityDirection[0] = xChange;
        this.abilityDirection[1] = yChange;
    }

    public int[] getAbilityTravelled() {
        return abilityTravelled;
    }

    public void setAbilityTravelled(int[] abilityTravelled) {
        this.abilityTravelled = abilityTravelled;
    }

    public boolean isDashUsed() {
        return dashUsed;
    }

    public void setDashUsed(boolean dashUsed) {
        this.dashUsed = dashUsed;
    }

    public boolean isBashUsed() {
        return bashUsed;
    }

    public void setBashUsed(boolean bashUsed) {
        this.bashUsed = bashUsed;
    }

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

}
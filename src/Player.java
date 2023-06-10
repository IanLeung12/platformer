import java.util.ArrayList;

public class Player extends Alive {//

    private double totalGold;
    private double energy;

    private double maxEnergy;
    private boolean inAnim; // ian what initial value
    private int animFrames;// ian what initial value
    private String currentAnim;// ian what initial value
    private int jumpNum;
    private int maxJumps;
    private boolean dashUnlocked;
    private boolean bashUnlocked;
    private boolean dashUsed = false;
    private boolean bashUsed = false;
    private String currentWeapon = "Sword";
    private int abilityTravelled = 0;
    private boolean attackActive;
    private boolean movingRight;
    private boolean movingLeft;

    private int[] respawnPoint;

    private Wall lastWall;
    private ArrayList<String> Weapons;



    Player(int x, int y, int width, int height, double MaxHealth, double maxEnergy) {
        super(x, y, width, height, MaxHealth, MaxHealth);
        this.energy = maxEnergy;
        this.maxEnergy = maxEnergy;
        this.totalGold = 0;
        this.jumpNum = 0;
        this.maxJumps = 2;
        this.setImmunityTimer(1);
        this.respawnPoint = new int[]{x, y};
        this.dashUnlocked = false;
        this.bashUnlocked = false;


        // add a single weapon into weapons ===============================

    }

    public void move() {

        if (isAbilityActive()) {
            this.movementAbility();
        }

        this.translate(this.getXSpeed(),  -this.getYSpeed());
        this.setYSpeed(this.getYSpeed() - Constants.getGravity());

        if (movingRight && this.getXSpeed() < Constants.getMaxXSpeed()) {
            this.setXSpeed(this.getXSpeed() + Constants.getXSpeedAddition());
        } else if (movingLeft && this.getXSpeed() > (-1) * (Constants.getMaxXSpeed())) {
            this.setXSpeed(this.getXSpeed() - Constants.getXSpeedAddition());
        } else if (this.getXSpeed() != 0) {
            this.setXSpeed(this.getXSpeed() - this.getXSpeed()/Math.abs(this.getXSpeed()) * Constants.getXSpeedAddition());
            if (Math.abs(this.getXSpeed()) <= Constants.getXSpeedAddition()) {
                this.setXSpeed(0);
            }
        }

        if (this.getHealth() > this.getMaxHealth()) {
            this.setHealth(this.getMaxHealth());
        }
    }

    public void jump() {

        if (this.getJumpNum() < this.getMaxJumps()) {
            this.setYSpeed(Constants.getJumpBoost());
            this.setJumpNum(this.getJumpNum() + 1);
        }

    }

    public void dash() {
        this.setAbilityActive(true);
        this.setAbilityDirection(Constants.getAbilitySpeed() * this.getDirection(), 0);
        this.dashUsed = true;

    }
    public void bash(int targetX, int targetY) {

        this.setAbilityActive(true);
        double dX = targetX - this.getCenterX();
        double dY = -(targetY - this.getCenterY());
        double interval = Constants.getAbilitySpeed()/Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));

        this.setAbilityDirection((int) (dX * interval), (int) (dY * interval));
        this.bashUsed = true;
    }

    public void movementAbility() {

        if (this.abilityTravelled < Constants.getMovementAbilityTotal()) {
            this.setXSpeed(this.getAbilityDirection(0));
            this.setYSpeed(this.getAbilityDirection(1));
            this.abilityTravelled += Math.sqrt(Math.pow(this.getAbilityDirection(0), 2) + Math.pow(this.getAbilityDirection(1), 2));
        } else {
            this.abilityTravelled = 0;
            this.setAbilityActive(false);
        }
    }

    public void collide(GameObject otherObject) {

        if ((otherObject instanceof Spike) || (otherObject instanceof Enemy)) {
            if (this.getImmunityTimer() == 0) {
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
                this.setImmunityTimer(1);
                if (otherObject instanceof Enemy) {
                    this.setHealth(this.getHealth() - ((Enemy) otherObject).getDamage());
                } else {
                    this.setHealth(this.getHealth() - ((Spike) otherObject).getDamage());
                }
            }
        } else {
            double playerBottom = this.getY() + this.getHeight();
            double otherObjectTop = otherObject.getY();
            double playerRight = this.getX() + this.getWidth();
            double colliderLeft = otherObject.getX();
            double playerLeft = this.getX();
            double colliderRight = otherObject.getX() + otherObject.getWidth();

            if ((playerBottom > otherObjectTop) && (this.getY() + this.getYSpeed() < otherObjectTop) && (playerRight - this.getXSpeed() - 2 > colliderLeft) && (playerLeft - this.getXSpeed() + 2 < colliderRight)) {
                this.setLocation((int) this.getX(), (int) (otherObjectTop - this.getHeight()));
                this.setYSpeed(0); // Stop the player's vertical movement
                this.wallReset();

                if (otherObject != lastWall) {
                    this.respawnPoint[0] = (int) this.getX();
                    this.respawnPoint[1] = (int) this.getY();
                } else {
                    lastWall = (Wall) otherObject;
                }



            } else if (this.getY() < otherObjectTop + otherObject.getHeight() && playerBottom > otherObjectTop + otherObject.getHeight() && (playerRight - this.getXSpeed() - 2 > colliderLeft) && (playerLeft - this.getXSpeed() + 2 < colliderRight)) {
                this.setLocation((int) this.getX(), (int) (otherObjectTop + otherObject.getHeight()));
                this.setYSpeed(0);

            } else if (playerRight > colliderLeft && playerLeft < colliderLeft && playerBottom > otherObjectTop && this.getY() < otherObjectTop + otherObject.getHeight()) {
                this.setLocation((int) (colliderLeft - this.getWidth()), (int) this.getY());
                this.setXSpeed(0); // Reverse the player's horizontal speed
                if (this.getYSpeed() < 0) {
                    this.setYSpeed(this.getYSpeed() + 3);
                }
                this.wallReset();

            } else if (this.getX() < colliderRight && playerRight > colliderRight && playerBottom > otherObjectTop && this.getY() < otherObjectTop + otherObject.getHeight()) {
                this.setLocation((int) (colliderRight), (int) this.getY());
                this.setXSpeed(0); // Reverse the player's horizontal speed
                if (this.getYSpeed() < 0) {
                    this.setYSpeed(this.getYSpeed() + 3);
                }
                this.wallReset();
            }
        }

    }

    public void wallReset() {
        this.jumpNum = 0;
        this.dashUsed = false;
        this.bashUsed = false;
    }

    public void updatePlayer() {}


    //collision(GameObject otherObject) {}
    // attack()
    // jump()
    //dash(speedX, speedY)
    // bash()


    public double getMaxEnergy() {
        return maxEnergy;
    }

    public void setMaxEnergy(double maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public String getCurrentWeapon() {
        return currentWeapon;
    }

    public void setCurrentWeapon(String currentWeapon) {
        this.currentWeapon = currentWeapon;
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

    public int getAbilityTravelled() {
        return abilityTravelled;
    }

    public void setAbilityTravelled(int abilityTravelled) {
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

    public int[] getRespawnPoint() {
        return respawnPoint;
    }

    public void setRespawnPoint(int[] respawnPoint) {
        this.respawnPoint = respawnPoint;
    }
}
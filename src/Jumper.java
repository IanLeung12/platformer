import java.util.ArrayList;

public class Jumper extends Enemy{

    private int chargeUpCounter = Constants.jumperGameLoopChargeUp;
    private int landedFrenzy = 0;
//    private double jumpCalculationDisplacementX;
//    private double jumpCalculationDisplacementY;
//    private double targetX, targetY, startingX, startingY;
//    private double previousXChange, previousYChange;






    Jumper(int x, int y, int width, int height, int respawnTimer, int fullRespawnTimer) {
        super(x, y, width, height, Constants.getMosquitoTotalHealth(), Constants.getMosquitoTotalHealth(), Constants.getMosquitoDamage(), Constants.getMosquitoGoldReward(), respawnTimer, fullRespawnTimer);

        this.setTotalCooldownTimer(100);
        this.setXSpeed(Constants.jumperSpeed);

    }

    Jumper(int x, int y, int width, int height, double health, double totalHealth, double damage, double goldReward) {

        super(x, y, width, height, health, totalHealth, damage, goldReward);

        this.setTotalCooldownTimer(100);
        this.setXSpeed(Constants.jumperSpeed);

    }

    public void move(Player player, ArrayList<GameObject> proximity) {

        if (isAbilityActive()) {

            if (chargeUpCounter > 0) {
                chargeUp(player);
                //porabolicMovement();

                System.out.println("chargin");
            } else {
                System.out.println("jumpind");
                porabolicMovement();
            }


        } else {

            if (this.distanceToPlayer(player, proximity, false) > Constants.jumperVision) {

                this.defaultMovement();


            } else if (this.distanceToPlayer(player, proximity, false) <= Constants.jumperVision) {

               //if (this.getCooldownTimerAbility() <= 0) {

                    this.setAbilityActive(true);
                    this.setUpFrenzy(player);
                 //   this.chargeUp(player);

//                } else {
//                    this.cooldownMovement(player);
//                    System.out.println("cooldown");
//                }





            }
        }
    }

    public void defaultMovement() {

        if (Math.abs(this.getXSpeed()) > Constants.jumperSpeed) {
            this.setXSpeed((this.getXSpeed()/2));
        }

        this.setYSpeed(this.getYSpeed() - Constants.getGravity());
        this.translate(this.getXSpeed(), -this.getYSpeed());

    }

    public void cooldownMovement(Player player) {



    }

    public void chargeUp(Player player) {

        this.setXSpeed(this.getXSpeed() / 2);
        this.setYSpeed(this.getYSpeed() / 2);

        this.chargeUpCounter--;

        if (chargeUpCounter == 0) {
            setUpFrenzy(player);
        }

    }


    public void setUpFrenzy(Player player) {

        int power = Constants.jumperJumpPower;

        double dX = player.getCenterX() - this.getCenterX();
        double dY = -(player.getCenterY() - this.getCenterY());

        double interval = power/(Math.abs(dX) + Math.abs(dY) + 1);

        this.setXSpeed((int) (dX * interval));
        this.setYSpeed((int) ((dY * interval) + (this.getXSpeed() == 0 ? dY * interval : dX/this.getXSpeed()/2)));


    }


    public void porabolicMovement() {

        this.setYSpeed(this.getYSpeed() - 1);
        this.translate(this.getXSpeed(), -this.getYSpeed());

    }

    public void update() {

        this.immunityTick();

        if (this.getCooldownTimerAbility() > 0)  {
            this.setCooldownTimerAbility(this.getCooldownTimerAbility() - 1);
        }

    }


    public void collision(GameObject otherObject) {

        if (otherObject instanceof Spike) {
            //this.setHealth(-1);
        } else {

            double playerBottom = this.getY() + this.getHeight();
            double otherObjectTop = otherObject.getY();
            double playerRight = this.getX() + this.getWidth();
            double colliderLeft = otherObject.getX();
            double playerLeft = this.getX();
            double colliderRight = otherObject.getX() + otherObject.getWidth();

            if ((playerBottom > otherObjectTop) && (this.getY() + this.getYSpeed() < otherObjectTop) && (playerRight - this.getXSpeed() - 2 > colliderLeft) && (playerLeft - this.getXSpeed() + 2 < colliderRight)) {

                this.setLocation((int) this.getX(), (int) (otherObjectTop - this.getHeight()));
                this.setYSpeed(((this.getYSpeed() / 4) * 3) * (-1));

                this.setAbilityActive(false);

            } else if (this.getY() < otherObjectTop + otherObject.getHeight() && playerBottom > otherObjectTop + otherObject.getHeight() && (playerRight - this.getXSpeed() - 2 > colliderLeft) && (playerLeft - this.getXSpeed() + 2 < colliderRight)) {

                this.setLocation((int) this.getX(), (int) (otherObjectTop + otherObject.getHeight()));
                this.setYSpeed(((this.getYSpeed() / 4) * 3) * (-1));

                this.setAbilityActive(false);


            } else if (playerRight > colliderLeft && playerLeft < colliderLeft && playerBottom > otherObjectTop && this.getY() < otherObjectTop + otherObject.getHeight()) {
                this.setLocation((int) (colliderLeft - this.getWidth()), (int) this.getY());
                this.setXSpeed(this.getXSpeed() * -1);

                this.setAbilityActive(false);


            } else if (this.getX() < colliderRight && playerRight > colliderRight && playerBottom > otherObjectTop && this.getY() < otherObjectTop + otherObject.getHeight()) {
                this.setLocation((int) (colliderRight), (int) this.getY());
                this.setXSpeed(this.getXSpeed() * -1);

                this.setAbilityActive(false);

            }
        }
    }





}

import java.util.ArrayList;

public class Jumper extends Enemy{

    private int chargeUpCounter = Constants.jumperGameLoopChargeUp;
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

            porabolicMovement();
            System.out.println(" jump ");


        } else {

            if (this.distanceToPlayer(player, proximity, false) > Constants.jumperVision) {

                this.defaultMovement();
                System.out.println(" default movemnt");


            } else if (this.distanceToPlayer(player, proximity, false) <= Constants.jumperVision) {

                this.setAbilityActive(false);

                this.setUpPorabolicJumpingMovement(player, 20);
                System.out.println(" start a    a ");


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

        double cooldownXSpeed;

        double dX = player.getCenterX() - this.getCenterX();

        if (dX > 0) {
            cooldownXSpeed = Constants.getMosquitoSpeed() * (-1);
        } else if (dX < 0) {
            cooldownXSpeed = Constants.getMosquitoSpeed();
        } else {
            cooldownXSpeed = 0;
        }

        if (Math.abs(this.getYSpeed()) > (Constants.getMosquitoSpeed())) {
            this.setYSpeed((this.getYSpeed()/2));
        }

        this.translate((int) ( cooldownXSpeed ) , (this.getYSpeed()));

    }


    public void movementAbility(Player player, ArrayList<GameObject> proximity ) {

    }

    public void chargeUp(Player player) {

    }


    public void setUpPorabolicJumpingMovement(Player player, int power) {

        setAbilityActive(true);

        double dX = player.getCenterX() - this.getCenterX();
        double dY = -(player.getCenterY() - this.getCenterY());

        double interval = power/(Math.abs(dX) + Math.abs(dY) + 1);



        this.setXSpeed((int) (dX * interval));
        this.setYSpeed((int) ((dY * interval) + (this.getXSpeed() == 0 ? dY * interval : dX/this.getXSpeed()/2)));

        if (this.getYSpeed() > power * 2) {
            this.setYSpeed(power * 2);
        }


//        startingX = this.getCenterX();
//        startingY = this.getCenterY();
//
//        jumpCalculationDisplacementX = this.getCenterX();
//        jumpCalculationDisplacementY = this.getCenterY();
//
//        targetX = player.getCenterX() - jumpCalculationDisplacementX;
//        targetY = player.getCenterY() - jumpCalculationDisplacementY;

    }


    public void porabolicMovement() {

        this.setYSpeed(this.getYSpeed() - 1);
        this.translate(this.getXSpeed(), -this.getYSpeed());




//        double newX = this.getCenterX() + Constants.jumperJumpXIncrament;
//        double newY = startingY - Constants.jumperJumpYMax - porabola(newX - startingX, targetX, targetY);
//
//
//
//        this.setYSpeed((int) (newY - this.previousYChange));
//
//
//
//        this.translate((int) (newX - this.getCenterX()), (int) (newY - this.getCenterY()));
//
//        if (this.getCenterX() >= targetX + startingX && this.getCenterY() <= targetY + startingY) {
//            this.setAbilityActive(false);
//        }
    }
//
//    public double porabola(double x, double x1, double y1) {
//        double t = (4 * Math.pow(x1, 2)) * (1 - (y1 / Constants.jumperJumpYMax));
//        double s = (2 * x1) - Math.sqrt(t);
//        return (-(4 * y1 * x) / s) * (((y1 * x) / (Constants.jumperJumpYMax * s)) - 1);
//    }

    public void update() {

        if (this.getImmunityTimer() > 0) {
            this.setImmunityTimer(this.getImmunityTimer() - 1);
        }

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

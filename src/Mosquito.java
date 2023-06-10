import java.util.ArrayList;

public class Mosquito extends Enemy{

    private int abilityTravelled = 0;
    private int chargeUpCounter = 15;


    Mosquito(int x, int y, int width, int height, int respawnTimer, int fullRespawnTimer) {
        super(x, y, width, height, Constants.getMosquitoTotalHealth(), Constants.getMosquitoTotalHealth(), Constants.getMosquitoDamage(), Constants.getMosquitoGoldReward(), respawnTimer, fullRespawnTimer);


        this.setTotalCooldownTimer(100);
        this.setXSpeed(Constants.getMosquitoSpeed());

    }

    Mosquito(int x, int y, int width, int height, double health, double totalHealth, double damage, double goldReward) {

        super(x, y, width, height, health, totalHealth, damage, goldReward);

        this.setTotalCooldownTimer(100);
        this.setXSpeed(Constants.getMosquitoSpeed());

    }

    public void move(Player player, ArrayList<GameObject> proximity) {

        if (isAbilityActive()) {

            if (this.chargeUpCounter > 0) {
                this.chargeUp(player);
            } else {
                this.movementAbility();
            }

        } else {
            if (this.distanceToPlayer(player, proximity) > Constants.getMosquitoVision()) {

                this.defaultMovement();

            } else if (this.distanceToPlayer(player, proximity) <= Constants.getMosquitoVision()) {

                if (this.getCooldownTimerAbility() == 0) {

                    this.chargeUpCounter = 15;
                    this.chargeUp(player);

                } else {

                    this.defaultMovement();

                }

            }
        }

    }

    public void defaultMovement() {

        if (this.getXSpeed() > Constants.getMosquitoSpeed()) {
            this.setXSpeed((this.getXSpeed()/2));
        }

        this.translate(this.getXSpeed(),0);

    }





    public void movementAbility() {

        if (this.abilityTravelled < Constants.getMovementAbilityTotal()  ) {
            this.setXSpeed(this.getAbilityDirection(0));
            this.setYSpeed(this.getAbilityDirection(1));
            this.translate(this.getXSpeed(), -this.getYSpeed());
            this.abilityTravelled += Math.abs(this.getAbilityDirection(0)) + Math.abs(this.getAbilityDirection(1));

        } else {
            this.abilityTravelled = 0;
            this.setAbilityActive(false);
            this.setCooldownTimerAbility(this.getTotalCooldownTimer());
        }

    }

    public void chargeUp(Player player) {

        this.setAbilityActive(true);


        double dX = player.getCenterX() - this.getCenterX();
        double dY = -(player.getCenterY() - this.getCenterY());

        double interval = Constants.getMosquitoMovementAbilitySpeed() / (Math.abs(dX) + Math.abs(dY) + 1);

        this.setAbilityDirection((int) (dX * interval), (int) (dY * interval));

        this.translate(-( this.getAbilityDirection(0)/10) , (this.getAbilityDirection(1)/10) );


        this.chargeUpCounter--;

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
                this.setYSpeed(this.getYSpeed() * (-1));

            } else if (this.getY() < otherObjectTop + otherObject.getHeight() && playerBottom > otherObjectTop + otherObject.getHeight() && (playerRight - this.getXSpeed() - 2 > colliderLeft) && (playerLeft - this.getXSpeed() + 2 < colliderRight)) {
                this.setLocation((int) this.getX(), (int) (otherObjectTop + otherObject.getHeight()));

                this.setYSpeed(this.getYSpeed() * (-1));

            } else if (playerRight > colliderLeft && playerLeft < colliderLeft && playerBottom > otherObjectTop && this.getY() < otherObjectTop + otherObject.getHeight()) {
                this.setLocation((int) (colliderLeft - this.getWidth()), (int) this.getY());
                this.setXSpeed(this.getXSpeed() * -1);

            } else if (this.getX() < colliderRight && playerRight > colliderRight && playerBottom > otherObjectTop && this.getY() < otherObjectTop + otherObject.getHeight()) {
                this.setLocation((int) (colliderRight), (int) this.getY());
                this.setXSpeed(this.getXSpeed() * -1);

            }
        }
    }


    public void update() {

        if (this.getImmunityTimer() > 0) {
            this.setImmunityTimer(this.getImmunityTimer() - 1);
        }

        if (this.getCooldownTimerAbility() > 0)  {
            this.setCooldownTimerAbility(this.getCooldownTimerAbility() - 1);
        }












    }
}

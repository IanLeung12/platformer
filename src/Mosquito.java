import java.util.ArrayList;

public class Mosquito extends Enemy{

    private int chargeUpCounter = Constants.getMosquitoGameLoopChargeUp();
    private int chargeUpDistanceTraveled = 0;
    private int startingChargeUpX;
    private int startingChargeUpY;



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
                this.movementAbility(player, proximity);
            }

        } else {
            if (this.distanceToPlayer(player, proximity, false) > Constants.getMosquitoVision()) {

                this.defaultMovement();

            } else if (this.distanceToPlayer(player, proximity, false) <= Constants.getMosquitoVision()) {

                if (this.getCooldownTimerAbility() == 0) {

                    chargeUpDistanceTraveled = 0;
                    startingChargeUpX = (int) this.getCenterX();
                    startingChargeUpY = (int) this.getCenterY();
                    this.chargeUpCounter = Constants.getMosquitoGameLoopChargeUp();

                    this.chargeUp(player);

                } else {

                    this.cooldownMovement(player);

                }

            }
        }

    }

    public void defaultMovement() {

        if (Math.abs(this.getXSpeed()) > Constants.getMosquitoSpeed()) {
            this.setXSpeed((this.getXSpeed()/2));
        }

        this.translate(this.getXSpeed(),0);

    }

    public void cooldownMovement(Player player) {

        double cooldownXSpeed;

        double dX = player.getCenterX() - this.getCenterX();
        double dY = -(player.getCenterY() - this.getCenterY());

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



        if (this.distanceToPlayer(player, proximity, true) < (Constants.getMosquitoMovementAbilityTotal() + chargeUpDistanceTraveled)) {
            this.setXSpeed(this.getAbilityDirection(0));
            this.setYSpeed(this.getAbilityDirection(1));
            this.translate(this.getXSpeed(), -this.getYSpeed());

        } else {
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

        int xTranslation = -(int) (this.getAbilityDirection(0) * interval * (this.chargeUpCounter / (Constants.getMosquitoGameLoopChargeUp() /2 )));
        int yTranslation = (int) (this.getAbilityDirection(1) * interval * (this.chargeUpCounter / (Constants.getMosquitoGameLoopChargeUp() /2 )));

        this.translate(xTranslation, yTranslation);

        this.chargeUpCounter--;

        if (chargeUpCounter == 0) {
            this.chargeUpDistanceTraveled = (int) (Math.sqrt(Math.pow((this.getCenterX() - this.startingChargeUpX), 2) + Math.pow((this.getCenterY() - this.startingChargeUpY), 2)));
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

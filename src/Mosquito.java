import java.util.ArrayList;

public class Mosquito extends Enemy{


    private int abilityTravelled = 0;







    Mosquito(int x, int y, int width, int height, int respawnTimer, int fullRespawnTimer) {
        super(x, y, width, height, Constants.getMosquitoTotalHealth(), Constants.getMosquitoTotalHealth(), Constants.getMosquitoDamage(), Constants.getMosquitoGoldReward(), respawnTimer, fullRespawnTimer);


        
        this.setXSpeed(Constants.getMosquitoSpeed());

    }

    Mosquito(int x, int y, int width, int height, double health, double totalHealth, double damage, double goldReward) {

        super(x, y, width, height, health, totalHealth, damage, goldReward);

        this.setXSpeed(Constants.getMosquitoSpeed());

    }






    public void bash(int targetX, int targetY) {

        this.setAbilityActive(true);

        double dX = targetX - this.getCenterX();
        double dY = -(targetY - this.getCenterY());

        double interval = Constants.getAbilitySpeed()/(Math.abs(dX) + Math.abs(dY) + 1);

        this.setAbilityDirection((int) (dX * interval), (int) (dY * interval));
    }

    public void movementAbility() {

        if (this.abilityTravelled < Constants.getMovementAbilityTotal()  ) {
            this.setXSpeed(this.getAbilityDirection(0));
            this.setYSpeed(this.getAbilityDirection(1));
            this.abilityTravelled += Math.abs(this.getAbilityDirection(0)) + Math.abs(this.getAbilityDirection(0));
        } else {
            this.abilityTravelled = 0;
            this.setAbilityActive(false);
        }
    }

    public void move(Player player, ArrayList<GameObject> proximity) {

        if (isAbilityActive()) {
            this.movementAbility();
        }



        if (this.distanceToPlayer(player, proximity) > Constants.getMosquitoVision()) {

            this.translate(this.getXSpeed(),0);


        } else if (this.distanceToPlayer(player, proximity) <= Constants.getMosquitoVision()) {

            this.setAbilityActive(true);

            this.bash((int) player.getCenterX(), (int) player.getCenterY());

            this.movementAbility();
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
                //  =============================================================
                //  add ians knockbad method think here so if it hits the ground itll bounce off a bit
                //  =============================================================

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

        if (this.getXSpeed() > 0) {
            this.setDirection(-1);
        } else if (this.getXSpeed() < 0) {
            this.setDirection(1);
        }








    }
}

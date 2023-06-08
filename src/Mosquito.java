import java.util.ArrayList;

public class Mosquito extends Enemy{//



    Mosquito(int x, int y, int width, int height, double health, double totalHealth, double damage, int goldReward, int respawnTimer, int fullRespawnTimer) {
        super(x, y, width, height, Constants.getMosquitoTotalHealth(), Constants.getMosquitoTotalHealth(), Constants.getMosquitoDamage(), Constants.getMosquitoGoldReward(), respawnTimer, fullRespawnTimer);


        
        this.setXSpeed(Constants.getMosquitoSpeed());

    }

    Mosquito(int x, int y, int width, int height, double health, double totalHealth, double damage, int goldReward) {


        super(x, y, width, height, Constants.getMosquitoTotalHealth(), Constants.getMosquitoTotalHealth(), Constants.getMosquitoDamage(), Constants.getMosquitoGoldReward());

        this.setXSpeed(Constants.getMosquitoSpeed());

    }





    public void move(Player player, ArrayList<GameObject> proximity) {

        if (this.getImmunityTimer() > 0) {
            // continue knockback
        } else {
            if (this.distanceToPlayer(player, proximity) > Constants.getMosquitoVision()) {

                this.setLocation((int) this.getX() + this.getXSpeed(), (int) this.getY());


            } else if (this.distanceToPlayer(player, proximity) <= Constants.getMosquitoVision()) {

                if (player.getCenterX() - this.getCenterX() > 0) {
                    this.setXSpeed(Constants.getMosquitoSpeed());
                } else if (player.getCenterX() - this.getCenterX() < 0) {
                    this.setXSpeed(Constants.getMosquitoSpeed() * (-1));
                } else {
                    this.setXSpeed(0);
                }

                if (player.getCenterY() - this.getCenterY() > 0) {
                    this.setYSpeed(Constants.getMosquitoSpeed() * (-1));
                } else if (player.getCenterY() - this.getCenterY() < 0) {
                    this.setYSpeed(Constants.getMosquitoSpeed());
                } else {
                    this.setXSpeed(0);
                }

                this.setLocation((int) this.getX() + this.getXSpeed(), (int) this.getY() - this.getYSpeed());

            }
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

public class Slime extends Enemy{//



    Slime(int x, int y, int width, int height, double health, double totalHealth, double damage, int goldReward, int respawnTimer, int fullRespawnTimer) {
        super(x, y, width, height, health, totalHealth, damage, goldReward, respawnTimer, fullRespawnTimer);

        this.setXSpeed(Constants.getSlimeSpeed());

    }

    Slime(int x, int y, int width, int height, double health, double totalHealth, double damage, int goldReward) {
        super(x, y, width, height, health, totalHealth, damage, goldReward);

        this.setXSpeed(Constants.getSlimeSpeed());

    }





    public void move(Player player) {

        if (this.getImmunityTimer() > 0) {
            // continue knockback
        } else {
            if (this.distanceToPlayer(player) > Constants.getSlimeVision()) {

                this.setLocation((int) this.getX() + this.getXSpeed(), (int) this.getY() - this.getYSpeed());
                this.setYSpeed(this.getYSpeed() - Constants.getGravity());

            } else if (this.distanceToPlayer(player) <= Constants.getSlimeVision()) {

                if (player.getCenterX() - this.getCenterX() >= 0) {
                    this.setXSpeed(Constants.getSlimeSpeed());
                } else if (player.getCenterX() - this.getCenterX() < 0) {
                    this.setXSpeed(Constants.getSlimeSpeed() * (-1));
                }

                this.setLocation((int) this.getX() + this.getXSpeed(), (int) this.getY() - this.getYSpeed());
                this.setYSpeed(this.getYSpeed() - Constants.getGravity());


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
                this.setYSpeed(0);

                if (onEdge(otherObject)) {

                    this.setXSpeed(this.getXSpeed() * -1);
                    this.setLocation((int) this.getX() + (2 * this.getXSpeed()), (int) this.getY() - this.getYSpeed());
                }

            } else if (this.getY() < otherObjectTop + otherObject.getHeight() && playerBottom > otherObjectTop + otherObject.getHeight() && (playerRight - this.getXSpeed() - 2 > colliderLeft) && (playerLeft - this.getXSpeed() + 2 < colliderRight)) {
                this.setLocation((int) this.getX(), (int) (otherObjectTop + otherObject.getHeight()));
                this.setYSpeed(0);
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

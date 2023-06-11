import java.util.ArrayList;

public class Slime extends Enemy{//



    Slime(int x, int y, int width, int height, double health, double totalHealth, double damage, int goldReward, int respawnTimer, int fullRespawnTimer) {
        super(x, y, width, height, Constants.getSlimeTotalHealth(), Constants.getSlimeTotalHealth(), Constants.getSlimeDamage(), Constants.getSlimeGoldReward(), respawnTimer, fullRespawnTimer);

        this.setXSpeed(Constants.getSlimeSpeed());

    }

    Slime(int x, int y, int width, int height) {
        super(x, y, width, height, Constants.getSlimeTotalHealth(), Constants.getSlimeTotalHealth(), Constants.getSlimeDamage(), Constants.getSlimeGoldReward());

        this.setXSpeed(Constants.getSlimeSpeed());

    }

    Slime(int x, int y, int width, int height, double health, double totalHealth, double damage, double goldReward) {
        super(x, y, width, height, health, totalHealth, damage, goldReward);

        this.setXSpeed(Constants.getSlimeSpeed());

    }







    public void move(Player player, ArrayList<GameObject> proximity) {


        if (this.distanceToPlayer(player, proximity, false) > Constants.getSlimeVision()) {

            this.setYSpeed(this.getYSpeed() - Constants.getGravity());
            this.translate(this.getXSpeed(), -this.getYSpeed());

        } else if (this.distanceToPlayer(player, proximity, false) <= Constants.getSlimeVision()) {

            if ((player.getCenterX() - this.getCenterX() >= 0) && (this.getXSpeed() < Constants.getSlimeSpeed())) {
                this.setXSpeed(this.getXSpeed() + 1);
            } else if ((player.getCenterX() - this.getCenterX() < 0) && (this.getXSpeed() > -Constants.getSlimeSpeed())) {
                this.setXSpeed(this.getXSpeed() - 1);
            }

            this.setYSpeed(this.getYSpeed() - Constants.getGravity());
            this.translate(this.getXSpeed(), -this.getYSpeed());
        }

        if (Math.abs(this.getXSpeed()) > Constants.getSlimeSpeed()) {
            this.setXSpeed(this.getXSpeed() - this.getXSpeed()/Math.abs(this.getXSpeed()) * 2);
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
                    this.setLocation((int) this.getX() + this.getXSpeed(), (int) this.getY() - this.getYSpeed());

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

        this.immunityTick();

        if (this.getXSpeed() > 0) {
            this.setDirection(-1);
        } else if (this.getXSpeed() < 0) {
            this.setDirection(1);
        }








    }
}

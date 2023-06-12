import java.util.ArrayList;

public class Slime extends Enemy{//





    Slime(int x, int y, int width, int height, double health, double totalHealth, double damage, double goldReward, double respawnX, double respawnY) {
        super(x, y, width, height, health, totalHealth, damage, goldReward, respawnX, respawnY);

        this.setXSpeed(Constants.slimeSpeed);

    }

    Slime(int x, int y, int width, int height, double health, double totalHealth, double damage, double goldReward, double respawnX, double respawnY, boolean obeliskEnemy) {
        super(x, y, width, height, health, totalHealth, damage, goldReward, respawnX, respawnY, obeliskEnemy);

        this.setXSpeed(Constants.slimeSpeed);

    }







    public void move(Player player, ArrayList<Wall> proximity) {

        double distance = Math.sqrt( Math.pow((player.getCenterY() - this.getCenterY()) , 2) +  Math.pow((player.getCenterX() - this.getCenterX()) , 2) );

        if (distance < Constants.slimeVision) {
            if (this.distanceToPlayer(player, proximity, false) <= Constants.slimeVision) {

                if ((player.getCenterX() - this.getCenterX() >= 0) && (this.getXSpeed() < Constants.slimeSpeed)) {
                    this.setXSpeed(this.getXSpeed() + 1);
                } else if ((player.getCenterX() - this.getCenterX() < 0) && (this.getXSpeed() > -Constants.slimeSpeed)) {
                    this.setXSpeed(this.getXSpeed() - 1);
                }

                this.setYSpeed(this.getYSpeed() - Constants.gravity);
                this.translate(this.getXSpeed(), -this.getYSpeed());

            } else {

                this.setYSpeed(this.getYSpeed() - Constants.gravity);
                this.translate(this.getXSpeed(), -this.getYSpeed());
            }
        } else {

            this.setYSpeed(this.getYSpeed() - Constants.gravity);
            this.translate(this.getXSpeed(), -this.getYSpeed());

        }

        if (Math.abs(this.getXSpeed()) > Constants.slimeSpeed) {
            this.setXSpeed(this.getXSpeed() - this.getXSpeed()/Math.abs(this.getXSpeed()) * 2);
        }

    }

    public void collision(GameObject otherObject) {

        if (otherObject instanceof Spike) {
            this.setHealth(-1);
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

        this.setRespawnTimer(this.getRespawnTimer() - 1);


    }
}

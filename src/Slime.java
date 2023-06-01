public class Slime extends Enemy{



    Slime(int x, int y, int width, int height, double health, double totalHealth, double damage, int goldReward, int respawnTimer, int fullRespawnTimer) {
        super(x, y, width, height, health, totalHealth, damage, goldReward, respawnTimer, fullRespawnTimer);

        this.setXSpeed(Constants.getSlimeSpeed());

    }


    public void collision(GameObject otherObject) {

    }


    public void move(Player player) {
        if (Math.abs(this.getCenterX() - player.getCenterX()) > (Constants.getSlimeVision()) + (player.getWidth()/2) + (this.getWidth()/2)) {
            System.out.println("enemy without player in radius");
        } else if (Math.abs(this.getCenterX() - player.getCenterX()) < (Constants.getSlimeVision()) + (player.getWidth()/2) + (this.getWidth()/2)) {
            if (player.getCenterX() - this.getCenterX() > 0) {
                this.setXSpeed(Constants.getSlimeSpeed());
            } else if (player.getCenterX() - this.getCenterX() < 0) {
                this.setXSpeed(Constants.getSlimeSpeed() * (-1));
            }
        }

    }


}

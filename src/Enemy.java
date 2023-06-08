import java.lang.reflect.Array;
import java.util.ArrayList;
abstract public class Enemy extends Moveable {//

    private double damage;
    private int goldReward;
    private int respawnTimer;
    private int fullRespawnTimer;


    Enemy(int x, int y, int width, int height, double health, double totalHealth, double damage, int goldReward, int respawnTimer, int fullRespawnTimer) {
        super(x, y, width, height, health, totalHealth);
        this.damage = damage;
        this.goldReward = goldReward;
        this.respawnTimer = respawnTimer;
        this.fullRespawnTimer = fullRespawnTimer;
    }

    // no respawn
    Enemy(int x, int y, int width, int height, double health, double totalHealth, double damage, int goldReward) {
        super(x, y, width, height, health, totalHealth);
        this.damage = damage;
        this.goldReward = goldReward;
    }

    public abstract void move(Player player, ArrayList<GameObject> proximity);

    public abstract void update();

    public double distanceToPlayer(Player player, ArrayList<GameObject> listObjects) {

        double distance;

        boolean enemyVerticleExit = false;
        boolean enemyHorizontalExit = false;

        boolean playerVerticleExit = false;
        boolean playerHorizontalExit = false;

        double enemyYpointIntersection = 0;
        double enemyXpointIntersection = 0;

        double playerYpointIntersection = 0;
        double playerXpointIntersection = 0;

        String playerPosition = "";
        String enemyPosition = "";

        double enemyY = 0;
        double playerY = 0;

        double m, b;


        if (Math.abs(player.getCenterX()) - Math.abs(this.getCenterX()) == 0) {
            m = Integer.MAX_VALUE;
        } else {
            m = ((player.getCenterY() - this.getCenterY()) / (player.getCenterX() - this.getCenterX()));
        }


        b = ((this.getCenterY() - (m * this.getCenterX())));

        enemyY = ((m * this.getX()) + b);
        playerY = ((m * player.getX()) + b);



        if ((enemyY <= this.getY() + this.getHeight()) && (enemyY >= this.getY())) {
            enemyHorizontalExit = true;
        } else {
            enemyVerticleExit = true;
        }

        if ((playerY <= player.getY() + player.getHeight()) && (playerY >= player.getY())) {
            playerHorizontalExit = true;
        } else {
            playerVerticleExit = true;
        }


        if (enemyHorizontalExit) {
            if (player.getX() - this.getX() >= 0) {
                playerPosition = "right";
                enemyPosition = "left";
            } else if (player.getX() - this.getX() < 0) {
                playerPosition = "left";
                enemyPosition = "right";

            }


        } else if (enemyVerticleExit) {

            if (player.getY() - this.getY() >= 0) {
                playerPosition = "down";
                enemyPosition = "up";
            } else if (player.getY() - this.getY() < 0) {
                playerPosition = "up";
                enemyPosition = "down";
            }
        }



        if (enemyVerticleExit) {

            if (playerPosition.equals("up")) {
                enemyYpointIntersection = (this.getY());
                enemyXpointIntersection = ((enemyYpointIntersection - b) / m);

            } else if (playerPosition.equals("down")) {
                enemyYpointIntersection = (this.getY() + this.getHeight());
                enemyXpointIntersection = ((enemyYpointIntersection - b) / m);

            }

        } else if (enemyHorizontalExit) {

            if (playerPosition.equals("right")) {
                enemyXpointIntersection = (this.getX() + this.getWidth());
                enemyYpointIntersection = ((m * enemyXpointIntersection) + b);

            } else if (playerPosition.equals("left")) {
                enemyXpointIntersection = (this.getX());
                enemyYpointIntersection = ((m * enemyXpointIntersection) + b);
            }

        }


        if (playerVerticleExit) {

            if (enemyPosition.equals("up")) {
                playerYpointIntersection = (player.getY());
                playerXpointIntersection = ((playerYpointIntersection - b) / m);

            } else if (enemyPosition.equals("down")) {
                playerYpointIntersection = (player.getY() + player.getHeight());
                playerXpointIntersection = ((playerYpointIntersection - b) / m);

            }

        } else if (playerHorizontalExit) {

            if (enemyPosition.equals("right")) {
                playerXpointIntersection = (player.getX() + player.getWidth());
                playerYpointIntersection = ((m * playerXpointIntersection) + b);

            } else if (enemyPosition.equals("left")) {
                playerXpointIntersection = (player.getX());
                playerYpointIntersection = ((m * playerXpointIntersection) + b);
            }

        }

        distance = Math.sqrt( Math.pow((playerXpointIntersection - enemyXpointIntersection), 2) + Math.pow((playerYpointIntersection - enemyYpointIntersection), 2) );

        int numPoints = 0;
        int numXPoints = (int) Math.abs(enemyXpointIntersection - playerXpointIntersection) / Constants.getRayTracingStep();
        int numYPoints = (int) Math.abs(enemyYpointIntersection - playerYpointIntersection) / Constants.getRayTracingStep();

        double testX = enemyXpointIntersection;
        double testY = enemyYpointIntersection;

        boolean intersected = false;

        if (enemyVerticleExit) {

            if (playerPosition.equals("up")) {

                for (int i = 1; i < (numYPoints - 1); i++) {

                    testY = enemyYpointIntersection - (i * Constants.getRayTracingStep());
                    testX = ((testY - b) / m);

                    for (GameObject gameObject: listObjects) {
                        if (gameObject.contains(testX, testY)) {
                            intersected = true;
                        }
                    }


                }



            } else if (playerPosition.equals("down")) {

                for (int i = 1; i < (numYPoints - 1); i++) {

                    testY = enemyYpointIntersection + (i * Constants.getRayTracingStep());
                    testX = ((testY - b) / m);


                    for (GameObject gameObject: listObjects) {
                        if (gameObject.contains(testX, testY)) {
                            intersected = true;
                        }
                    }

                }

            }

        } else if (enemyHorizontalExit) {

            if (playerPosition.equals("right")) {

                for (int i = 1; i < (numXPoints - 1); i++) {

                    testX = enemyXpointIntersection + (i * Constants.getRayTracingStep());
                    testY = (m * testX) + b;


                    for (GameObject gameObject: listObjects) {
                        if (gameObject.contains(testX, testY)) {
                            intersected = true;
                        }
                    }

                }


            } else if (playerPosition.equals("left")) {


                for (int i = 1; i < (numXPoints - 1); i++) {

                    testX = enemyXpointIntersection - (i * Constants.getRayTracingStep());
                    testY = (m * testX) + b;


                    for (GameObject gameObject: listObjects) {
                        if (gameObject.contains(testX, testY)) {
                            intersected = true;
                        }
                    }

                }

            }


        }



        if (!(intersected)) {
            return distance;
        } else {
            return Integer.MAX_VALUE;
        }

    }






    public boolean onEdge(GameObject otherObject) {
        double bottomRightX = (int) (this.getX() + this.getWidth());
        double bottomRightY = (int) (this.getY() + this.getHeight());
        double bottomLeftY = (int) (this.getY() + this.getHeight());

        if (((otherObject.contains(bottomRightX, bottomRightY)) && (!otherObject.contains(this.getX(), bottomLeftY))) || ((!otherObject.contains(bottomRightX, bottomRightY)) && (otherObject.contains(this.getX(), bottomLeftY)))) {
            return true;
        } else {
            return false;
        }

    }




    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public int getGoldReward() {
        return goldReward;
    }

    public void setGoldReward(int goldReward) {
        this.goldReward = goldReward;
    }

    public int getRespawnTimer() {
        return respawnTimer;
    }

    public void setRespawnTimer(int respawnTimer) {
        this.respawnTimer = respawnTimer;
    }

    public int getFullRespawnTimer() {
        return fullRespawnTimer;
    }

    public void setFullRespawnTimer(int fullRespawnTimer) {
        this.fullRespawnTimer = fullRespawnTimer;
    }
}

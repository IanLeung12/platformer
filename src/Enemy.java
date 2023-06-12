import java.lang.reflect.Array;
import java.util.ArrayList;
abstract public class Enemy extends Alive {//

    private double damage;
    private double goldReward;
    private int respawnTimer;
    private int cooldownTimerAbility;
    private int totalCooldownTimer;
    private double respawnX, respawnY;



    Enemy(int x, int y, int width, int height, double health, double totalHealth, double damage, double goldReward, double respawnX, double respawnY) {
        super(x, y, width, height, health, totalHealth, 15);
        this.damage = damage;
        this.goldReward = goldReward;
        this.respawnX = respawnX;
        this.respawnY = respawnY;
    }


    public abstract void move(Player player, ArrayList<Wall> proximity);

    public abstract void update();

    abstract public void collision(GameObject otherObject);

    public void knockback(Attack attack) {
        double interval, dX, dY;
        if (this.getImmunityTimer() == 0) {
            if (attack instanceof Explosion) {
                dX = (this.getCenterX() - attack.getCenterX());
                dY = (attack.getCenterY() - this.getCenterY());
                double distance = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));
                distance -= Math.sqrt((Math.pow(this.getWidth()/2, 2) + Math.pow(this.getHeight()/2, 2)));
                if (distance <= ((Explosion) attack).getRadius()) {
                    interval = 75/(Math.abs(dX) + Math.abs(dY) + 1);
                } else {
                    interval = 0;
                }
            } else {
                this.setXSpeed((int) (attack.getAttackDamage() * attack.getDirection()));
                this.setYSpeed(10);
                dX = (this.getCenterX() - attack.getCenterX());
                dY = (attack.getX() + attack.getHeight() - this.getCenterY());
                interval = 15/(Math.abs(dX) + Math.abs(dY) + 1);
            }

            this.setImmunityTimer(1);
            this.setHealth(this.getHealth() - attack.getAttackDamage());
            this.setXSpeed(this.getXSpeed() + (int) (dX * interval));
            this.setYSpeed(this.getYSpeed() + (int) (dY * interval));
        }
    }



    public double distanceToPlayer(Player player, ArrayList<Wall> listObjects, boolean justDistance) {

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

        int numPoints = 0;

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

        if (justDistance) {
            return distance;
        }

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
        double middleBottomRightX = bottomRightX - 10;
        double bottomY = (int) (this.getY() + this.getHeight());
        double middleBottomLeftX = this.getX() + 10;


        if (   ( ((otherObject.contains(bottomRightX, bottomY)) && ((otherObject.contains(middleBottomRightX, bottomY))) )  && ((!otherObject.contains(this.getX(), bottomY)) && (!otherObject.contains(middleBottomLeftX, bottomY)) ) )
            || ( ((!otherObject.contains(bottomRightX, bottomY)) && ((!otherObject.contains(middleBottomRightX, bottomY))) )  && ((otherObject.contains(this.getX(), bottomY)) && (otherObject.contains(middleBottomLeftX, bottomY)) ) )   )
        {
            this.setHealth(-1);
            return true;
        } else if (    ( ((otherObject.contains(middleBottomRightX, bottomY)) && (otherObject.contains(middleBottomLeftX, bottomY))) && (((otherObject.contains(this.getX(), bottomY)) || (otherObject.contains(bottomRightX, bottomY))) && ((!otherObject.contains(this.getX(), bottomY)) || (!otherObject.contains(bottomRightX, bottomY)) )    )          )
                &&   ( (((!otherObject.contains(this.getX(), bottomY)) || (!otherObject.contains(bottomRightX, bottomY))) && ((!otherObject.contains(this.getX(), bottomY)) || (!otherObject.contains(bottomRightX, bottomY)) ) )    )    ) {
            return true;
        } else {
            return false;
        }

    }


    public double getRespawnX() {
        return respawnX;
    }

    public double getRespawnY() {
        return respawnY;
    }

    public void setRespawnX(double respawnX) {
        this.respawnX = respawnX;
    }

    public void setRespawnY(double respawnY) {
        this.respawnY = respawnY;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getGoldReward() {
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

    public int getCooldownTimerAbility() {
        return cooldownTimerAbility;
    }

    public void setCooldownTimerAbility(int cooldownTimerAbility) {
        this.cooldownTimerAbility = cooldownTimerAbility;
    }

    public int getTotalCooldownTimer() {
        return totalCooldownTimer;
    }

    public void setTotalCooldownTimer(int totalCooldownTimer) {
        this.totalCooldownTimer = totalCooldownTimer;
    }
}

abstract public class Enemy extends Moveable {

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

    public abstract void move(Player player);

    public double distanceToPlayer(Player player) {

        boolean enemyVerticleExit = false;
        boolean enemyHorizontalExit = false;

        boolean playerVerticleExit = false;
        boolean playerHorizontalExit = false;

        double enemyYpointIntersection = 0;
        double enemyXpointIntersection = 0;

        double playerYpointIntersection = 0;
        double playerXpointIntersection = 0;

        boolean enemyRight = false;
        boolean enemyLeft = false;
        boolean enemyUp = false;
        boolean enemyDown = false;

        boolean playerRight = false;
        boolean playerLeft = false;
        boolean playerUp = false;
        boolean playerDown = false;

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
            if (player.getX() - this.getX() <= 0) {
                enemyLeft = true;
                playerRight = true;
            } else {
                enemyRight = true;
                playerLeft = true;
            }

        } else if (enemyVerticleExit) {
            if (player.getY() - this.getY() >= 0) {
                enemyDown = true;
                playerUp = true;
            } else {
                enemyUp = true;
                playerDown = true;
            }

        } else {
            System.out.println("error in things ");
        }




        //.out.println("up " + enemyUp + " down " + enemyDown + " right " + enemyRight + " left " + enemyLeft);

        if (enemyVerticleExit) {

            if (enemyUp) {
                enemyYpointIntersection = (this.getY());
                enemyXpointIntersection = ((enemyYpointIntersection - b) / m);

            } else if (enemyDown) {
                enemyYpointIntersection = (this.getY() + this.getHeight());
                enemyXpointIntersection = ((enemyYpointIntersection - b) / m);

            }

        } else if (enemyHorizontalExit) {

            if (enemyRight) {
                enemyXpointIntersection = (this.getX() + this.getWidth());
                enemyYpointIntersection = ((m * enemyXpointIntersection) + b);

            } else if (enemyLeft) {
                enemyXpointIntersection = (this.getX());
                enemyYpointIntersection = ((m * enemyXpointIntersection) + b);
            }

        }

        if (playerVerticleExit) {

            if (playerUp) {
                playerYpointIntersection = (player.getY());
                playerXpointIntersection = ((playerYpointIntersection - b) / m);

            } else if (playerDown) {
                playerYpointIntersection = (player.getY() + player.getHeight());
                playerXpointIntersection = ((playerYpointIntersection - b) / m);

            }

        } else if (playerHorizontalExit) {

            if (playerRight) {
                playerXpointIntersection = (player.getX() + player.getWidth());
                playerYpointIntersection = ((m * playerXpointIntersection) + b);
            } else if (playerLeft) {
                playerXpointIntersection = (player.getX());
                playerYpointIntersection = ((m * playerXpointIntersection) + b);
            }

        }

        return Math.sqrt( Math.pow((playerXpointIntersection - enemyXpointIntersection), 2) + Math.pow((playerYpointIntersection - enemyYpointIntersection), 2) );

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

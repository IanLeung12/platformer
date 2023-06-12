import java.util.ArrayList;

public class Jumper extends Enemy{

    private int chargeUpCounter = Constants.jumperGameLoopChargeUp;
    private int landedFrenzy = 0;
    private double respawnX, respawnY;




    Jumper(int x, int y, int width, int height, double health, double totalHealth, double damage, double goldReward, double respawnX, double respawnY) {

        super(x, y, width, height, health, totalHealth, damage, goldReward, respawnX, respawnY);

        this.respawnX = respawnX;
        this.respawnY = respawnY;
        this.setTotalCooldownTimer(100);
        this.setXSpeed(Constants.jumperSpeed);

    }

    Jumper(int x, int y, int width, int height, double health, double totalHealth, double damage, double goldReward, double respawnX, double respawnY, boolean obeliskEnemy) {

        super(x, y, width, height, health, totalHealth, damage, goldReward, respawnX, respawnY, obeliskEnemy);

        this.respawnX = respawnX;
        this.respawnY = respawnY;
        this.setTotalCooldownTimer(100);
        this.setXSpeed(Constants.jumperSpeed);

    }





    public void move(Player player, ArrayList<Wall> proximity) {

        if (isAbilityActive()) {

            if (chargeUpCounter > 0) {
                chargeUp(player);

            } else {
                this.translate(this.getXSpeed(), -this.getYSpeed());
                this.setYSpeed(this.getYSpeed() - Constants.gravity);

                if (landedFrenzy > Constants.jumperMaxJumps) {
                    landedFrenzy = 0;
                    this.setAbilityActive(false);
                    this.setCooldownTimerAbility(Constants.jumperAbilityCooldown);
                    chargeUpCounter = Constants.jumperGameLoopChargeUp;
                }

            }


        } else {

            double distance = Math.sqrt( Math.pow((player.getCenterY() - this.getCenterY()) , 2) +  Math.pow((player.getCenterX() - this.getCenterX()) , 2) );

            if (distance < Constants.jumperVision) {
                if (this.distanceToPlayer(player, proximity, false) <= Constants.jumperVision) {

                    if (this.getCooldownTimerAbility() <= 0) {

                        this.setAbilityActive(true);
                        this.chargeUp(player);

                    } else {
                        this.translate(this.getXSpeed(), -this.getYSpeed());
                        this.setYSpeed(this.getYSpeed() - Constants.gravity);

                        if (this.getCooldownTimerAbility() > 0) {
                            this.setCooldownTimerAbility(this.getCooldownTimerAbility() - 1);
                        }

                    }
                } else {
                    this.translate(this.getXSpeed(), -this.getYSpeed());
                    this.setYSpeed(this.getYSpeed() - Constants.gravity);

                    if (this.getCooldownTimerAbility() > 0) {
                        this.setCooldownTimerAbility(this.getCooldownTimerAbility() - 1);
                    }
                }
            } else {

                this.translate(this.getXSpeed(), -this.getYSpeed());
                this.setYSpeed(this.getYSpeed() - Constants.gravity);

                if (this.getCooldownTimerAbility() > 0) {
                    this.setCooldownTimerAbility(this.getCooldownTimerAbility() - 1);
                }

            }
        }

        if (this.getXSpeed() > 0) {
            this.setDirection(1);
        } else if (this.getXSpeed() < 0){
            this.setDirection(-1);
        }
    }

    public void chargeUp(Player player) {

        this.setXSpeed(this.getXSpeed() / 2);
        this.setYSpeed(this.getYSpeed() / 2);

        this.chargeUpCounter--;

        if (chargeUpCounter == 0) {
            setUpFrenzy(player);
        }

    }


    public void setUpFrenzy(Player player) {

        double dX = player.getCenterX() - this.getCenterX();
        double dY = (this.getCenterY() - player.getCenterY());

        if (dY <= 450) {
            double r1 = (-60 - Math.sqrt(3600 - 8 * dY))/-4;
            double r2 = (-60 + Math.sqrt(3600 - 8 * dY))/-4;
            double ticks = Math.max(r1, r2);
            this.setXSpeed((int) Math.round(dX/(ticks*2)));
            this.setYSpeed(60);
            System.out.println(ticks + " " + dX + " " + this.getXSpeed() + " " + dY);
        } else {
            this.setYSpeed(80);
            this.setXSpeed((int) Math.round(dX/30));
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
                this.setXSpeed(this.getXSpeed()/2);

                this.landedFrenzy++;

            } else if (this.getY() < otherObjectTop + otherObject.getHeight() && playerBottom > otherObjectTop + otherObject.getHeight() && (playerRight - this.getXSpeed() - 2 > colliderLeft) && (playerLeft - this.getXSpeed() + 2 < colliderRight)) {

                this.setLocation((int) this.getX(), (int) (otherObjectTop + otherObject.getHeight()));
                this.setYSpeed(0);

                this.landedFrenzy++;

            } else if (playerRight > colliderLeft && playerLeft < colliderLeft && playerBottom > otherObjectTop && this.getY() < otherObjectTop + otherObject.getHeight()) {
                this.setLocation((int) (colliderLeft - this.getWidth()), (int) this.getY());
                this.setXSpeed(this.getXSpeed() * -1);

                this.landedFrenzy++;


            } else if (this.getX() < colliderRight && playerRight > colliderRight && playerBottom > otherObjectTop && this.getY() < otherObjectTop + otherObject.getHeight()) {
                this.setLocation((int) (colliderRight), (int) this.getY());
                this.setXSpeed(this.getXSpeed() * -1);

                this.landedFrenzy++;



            }
        }
    }

    public void update() {

        this.immunityTick();

        this.setRespawnTimer(this.getRespawnTimer() - 1);



    }

}

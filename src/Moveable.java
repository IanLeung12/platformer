abstract public class Moveable extends Alive{

    private int xSpeed, ySpeed = 0;
    private int immunityTimer;
    private final int maxImmunity = 0;
    private int direction;
    private int[] abilityDirection = {0, 0};
    private boolean abilityActive = false;





    Moveable(int x, int y, int width, int height, double health, double totalHealth) {
        super(x, y, width, height, health, totalHealth);
    }




    public void knockback(Attack attack) {
        double interval, dX, dY;
        if (attack instanceof Explosion) {
            dX = (this.getCenterX() - attack.getCenterX());
            dY = (attack.getCenterY() - this.getCenterY());
            double distance = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));
            distance -= Math.sqrt((Math.pow(this.getWidth()/2, 2) + Math.pow(this.getHeight()/2, 2)));
            if (distance <= ((Explosion) attack).getRadius()) {
                interval = 50/(Math.abs(dX) + Math.abs(dY) + 1);
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

        this.setXSpeed(this.getXSpeed() + (int) (dX * interval));
        this.setYSpeed(this.getYSpeed() + (int) (dY * interval));
    }

    public int getXSpeed() {
        return xSpeed;
    }

    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public int getYSpeed() {
        return ySpeed;
    }

    public void setYSpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }


    public int getImmunityTimer() {
        return immunityTimer;
    }

    public void setImmunityTimer(int immunityTimer) {
        this.immunityTimer = immunityTimer;
    }

    public int getMaxImmunity() {
        return maxImmunity;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isAbilityActive() {
        return abilityActive;
    }

    public void setAbilityActive(boolean abilityActive) {
        this.abilityActive = abilityActive;
    }

    public int getAbilityDirection(int index) {
        return abilityDirection[index];
    }

    public void setAbilityDirection(int x, int y) {
        this.abilityDirection[0] = x;
        this.abilityDirection[1] = y;
    }









}

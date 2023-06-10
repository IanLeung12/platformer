abstract class Projectile extends Attack{

    private int xSpeed;

    private int ySpeed;

    private int energyCost;

    Projectile(int x, int y, int width, int height, int attackDamage, int direction, boolean isFriendly,int maxDuration, int energyCost) {
        super(x, y, width, height, attackDamage, direction, isFriendly, maxDuration);
        this.energyCost = energyCost;
    }

    public void move() {
        this.translate(xSpeed, -ySpeed);
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
}



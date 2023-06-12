public class Orb extends Moveable{

    private boolean following;

    private final int boostValue;

    private String boostType;

    Orb(int x, int y, int width, int height, int boostValue, String boostType) {
        super(x, y, width, height);
        this.following = false;
        this.boostValue = boostValue;
        this.boostType = boostType;
    }

    public boolean move(Player player) {
        double dX = player.getCenterX() - this.getCenterX();
        double dY = player.getCenterY() - this.getCenterY();
        double distance = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2)) + 1;

        if (distance > 3000) {
            return false;
        } else if (distance < 750) {
            following = true;
            double interval = 20/distance;
            this.setXSpeed((int) (dX * interval + Math.random() * 11 - 5));
            this.setYSpeed((int) -(dY * interval + Math.random() * 11 - 5));

        } else {
            this.following = false;
            if (this.getXSpeed() != 0) {
                this.setXSpeed(this.getXSpeed() - this.getXSpeed()/Math.abs(this.getXSpeed()));
            }
        }
        this.translate(this.getXSpeed(), -this.getYSpeed());
        this.setYSpeed(this.getYSpeed() - Constants.gravity);
        return true;
    }

    public void collision(GameObject otherObject) {

        if (!following) {
            if (!(otherObject instanceof Spike)) {
                double playerBottom = this.getY() + this.getHeight();
                double otherObjectTop = otherObject.getY();
                double playerRight = this.getX() + this.getWidth();
                double colliderLeft = otherObject.getX();
                double playerLeft = this.getX();
                double colliderRight = otherObject.getX() + otherObject.getWidth();

                if ((playerBottom > otherObjectTop) && (this.getY() + this.getYSpeed() < otherObjectTop) && (playerRight - this.getXSpeed() - 2 > colliderLeft) && (playerLeft - this.getXSpeed() + 2 < colliderRight)) {
                    this.setLocation((int) this.getX(), (int) (otherObjectTop - this.getHeight()));
                    this.setYSpeed(0);

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
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public int getBoostValue() {
        return boostValue;
    }

    public String getBoostType() {
        return boostType;
    }

    public void setBoostType(String boostType) {
        this.boostType = boostType;
    }
}

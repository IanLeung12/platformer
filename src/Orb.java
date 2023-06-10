public class Orb extends Moveable{

    private boolean following;
    Orb(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.following = false;
    }

    public void move(Player player) {
        double dX = player.getCenterX() - this.getCenterX();
        double dY = player.getCenterY() - this.getCenterY();
        double distance = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2)) + 1;

        if (distance < 500) {
            following = true;
            double interval = 30/distance;
            this.setXSpeed((int) (dX * interval));
            this.setYSpeed((int) -(dY * interval));
        } else {
            this.following = false;
            if (this.getXSpeed() != 0) {
                this.setXSpeed(this.getXSpeed() - this.getXSpeed()/Math.abs(this.getXSpeed()));
            }
        }
        this.translate(this.getXSpeed(), -this.getYSpeed());
        this.setYSpeed(this.getYSpeed() - Constants.gravity);
    }

    public void collision(GameObject otherObject) {

        if (!following) {
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
        } else if (following) {
            if (otherObject instanceof Player) {
                System.out.println("colliding w player");
                ((Player) otherObject).setTotalGold(((Player) otherObject).getTotalGold() + Constants.orbValue);

            }
        }


    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }
}

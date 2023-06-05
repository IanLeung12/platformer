public class Arrow extends Attacks{

    private int xSpeed, ySpeed;
    Arrow(int x, int y, int targetX, int targetY, boolean isFriendly) {
        super(x, y, 100, 50, 20, targetX > x ? 1 : -1, isFriendly, 100);
        int distance = (int) Math.sqrt(Math.pow((x - targetX), 2) + Math.pow((y - targetY), 2));
        int ticks = distance / 15;
        this.ySpeed = ticks * 3;
        this.xSpeed = (targetX - x)/ticks;
    }

    public void move() {
        this.translate(xSpeed, -ySpeed);
        this.ySpeed = ySpeed - Constants.getGravity();
    }
}

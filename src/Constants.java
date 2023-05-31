public class Constants {

    static int gravity = 1;
    static int jumpBoost = 10;
    static int dashX = 5;





    public static int getJumpBoost() {
        return jumpBoost;
    }

    public static void setJumpBoost(int jumpBoost) {
        Constants.jumpBoost = jumpBoost;
    }

    public static int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        Constants.gravity = gravity;
    }

    public static int getDashX() {
        return dashX;
    }

    public static void setDashX(int dashX) {
        Constants.dashX = dashX;
    }
}

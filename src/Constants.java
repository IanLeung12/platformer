public class Constants {

    static int gravity = 2;
    static int jumpBoost = 30;
    static int dashX = 40;
    static int movementAbilityTotal = 500;
    static int maxXSpeed = 26;
    static int XSpeedAddition = 2;







    public static int getMovementAbilityTotal() {
        return movementAbilityTotal;
    }

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

    public static int getMaxXSpeed() {
        return maxXSpeed;
    }

    public static int getXSpeedAddition() {
        return XSpeedAddition;
    }




}

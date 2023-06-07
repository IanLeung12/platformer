public class Constants {

    static int gravity = 2;
    static int jumpBoost = 30;
    static int abilitySpeed = 60;
    static int movementAbilityTotal = 450;
    static int maxXSpeed = 26;
    static int XSpeedAddition = 2;
    static int slimeVision = 400;
    static int slimeSpeed = 5;
    static int rayTracingStep = 10;


    public static int getRayTracingStep() {
        return rayTracingStep;
    }

    public static int getSlimeSpeed() {
        return slimeSpeed;
    }

    public static int getSlimeVision() {

        return slimeVision;
    }

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

    public static int getAbilitySpeed() {
        return abilitySpeed;
    }

    public static void setAbilitySpeed(int abilitySpeed) {
        Constants.abilitySpeed = abilitySpeed;
    }

    public static int getMaxXSpeed() {
        return maxXSpeed;
    }

    public static int getXSpeedAddition() {
        return XSpeedAddition;
    }




}

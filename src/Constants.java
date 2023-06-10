public class Constants {

    static int gravity = 2;
    static int jumpBoost = 30;
    static int abilitySpeed = 60;

    static int movementAbilityTotal = 500;

    static int maxXSpeed = 26;
    static int XSpeedAddition = 2;
    static int slimeVision = 400;
    static int slimeSpeed = 5;
    static int slimeTotalHealth = 100;
    static int slimeDamage = 1;
    static int slimeGoldReward = 100;
    static int mosquitoVision = 600;
    static int mosquitoSpeed = 2;
    static int mosquitoTotalHealth = 100;
    static int mosquitoDamage = 1;
    static int mosquitoGoldReward = 100;
    static int mosquitoMovementAbilitySpeed = 80;
    static int mosquitoMovementAbilityTotal = 500;
    static int mosquitoGameLoopChargeUp = 20;


    public static int getMovementAbilityTotal() {
        return movementAbilityTotal;
    }

    public static int getMosquitoGameLoopChargeUp() {
        return mosquitoGameLoopChargeUp;
    }

    public static int getMosquitoMovementAbilitySpeed() {
        return mosquitoMovementAbilitySpeed;
    }

    public static int getMosquitoMovementAbilityTotal() {
        return mosquitoMovementAbilityTotal;
    }

    public static int getSlimeGoldReward() {
        return slimeGoldReward;
    }

    public static int getMosquitoTotalHealth() {
        return mosquitoTotalHealth;
    }

    public static int getMosquitoDamage() {
        return mosquitoDamage;
    }

    public static int getMosquitoGoldReward() {
        return mosquitoGoldReward;
    }

    static int rayTracingStep = 10;






    public static int getSlimeTotalHealth() {
        return slimeTotalHealth;
    }

    public static int getSlimeDamage() {
        return slimeDamage;
    }


    public static int getMosquitoVision() {
        return mosquitoVision;
    }

    public static int getMosquitoSpeed() {
        return mosquitoSpeed;
    }

    public static int getRayTracingStep() {
        return rayTracingStep;
    }

    public static int getSlimeSpeed() {
        return slimeSpeed;
    }

    public static int getSlimeVision() {

        return slimeVision;
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

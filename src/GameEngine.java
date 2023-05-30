import java.util.ArrayList;

public class GameEngine {

    private Player player;
    private ArrayList<GameObject> surroundings;
    private AttackAbilities[] attacks;
    private boolean abilityActive;
    private boolean attackActive;
    private Enemy[] enemies;

    // private Shop shop;                       not created yet
    private int frameNum;


    GameEngine() {
        this.player = new Player(400, 400, 50, 100, 100, 100);
        this.surroundings = new ArrayList<>();
        surroundings.add(new Wall(200, 800, 500, 300));

    }
    /*
    constructor =================================================================
    constructor =================================================================
    constructor =================================================================
    constructor =================================================================
    constructor =================================================================
     */


    public void spawnEnemies() {}
    public void spawnProjectile() {}
    public void moveAll() {
        player.move();
    }
    public void checkCollisions() {}
    public void save() {}

    // ================================================================
    // print writter
    // mouse listener stuff
    // ================================================================


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<GameObject> getSurroundings() {
        return surroundings;
    }

    public void setSurroundings(ArrayList<GameObject> surroundings) {
        this.surroundings = surroundings;
    }

    public AttackAbilities[] getAttacks() {
        return attacks;
    }

    public void setAttacks(AttackAbilities[] attacks) {
        this.attacks = attacks;
    }

    public boolean isAbilityActive() {
        return abilityActive;
    }

    public void setAbilityActive(boolean abilityActive) {
        this.abilityActive = abilityActive;
    }

    public boolean isAttackActive() {
        return attackActive;
    }

    public void setAttackActive(boolean attackActive) {
        this.attackActive = attackActive;
    }

    public Enemy[] getEnemies() {
        return enemies;
    }

    public void setEnemies(Enemy[] enemies) {
        this.enemies = enemies;
    }

    public int getFrameNum() {
        return frameNum;
    }

    public void setFrameNum(int frameNum) {
        this.frameNum = frameNum;
    }
}

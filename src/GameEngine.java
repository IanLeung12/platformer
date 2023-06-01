import java.util.ArrayList;

public class GameEngine {

    private Player player;
    private ArrayList<GameObject> surroundings;

    private ArrayList<AttackAbilities> attacks;
    private boolean abilityActive;
    private boolean attackActive;
    private Enemy[] enemies;

    // private Shop shop;                       not created yet
    private int frameNum;


    GameEngine() {
        this.player = new Player(600, 500, 75, 150, 100, 100);
        this.surroundings = new ArrayList<>();
        this.attacks = new ArrayList<>();
        surroundings.add(new Wall(200, 800, 500, 300));
        surroundings.add(new Wall(700, 300, 400, 1000));
        surroundings.add(new Wall(1300, 400, 500, 100));
        surroundings.add(new Wall(-200, 1500, 2000, 200));
        surroundings.add(new Wall(-200, -400, 200, 3000));
        surroundings.add(new Wall(1800, -400, 200, 3000));


    }

    public void spawnEnemies() {}
    public void spawnProjectile() {}
    public void moveAll() {
        player.move();
        for (int i = attacks.size() - 1; i >= 0; i --) {
            AttackAbilities attack = attacks.get(i);
            if (attack.getAbilityDuration() > attack.getMaxAbilityDuration()) {
                attacks.remove(i);
            }
            attack.setAbilityDuration(attack.getAbilityDuration() + 1);
        }
    }

    public void checkCollisions() {
        for (GameObject object: surroundings) {
            if (player.getBounds().intersects(object)) {
                player.fixCollision(object);
                player.setAbilityActive(false);
            }
        }
    }
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

    public ArrayList<AttackAbilities> getAttacks() {
        return attacks;
    }

    public void setAttacks(ArrayList<AttackAbilities> attacks) {
        this.attacks = attacks;
    }

    public ArrayList<GameObject> getSurroundings() {
        return surroundings;
    }

    public void setSurroundings(ArrayList<GameObject> surroundings) {
        this.surroundings = surroundings;
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

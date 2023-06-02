import java.util.ArrayList;

public class GameEngine {

    private Player player;
    private ArrayList<Wall> surroundings;
    private ArrayList<AttackAbilities> attacks;
    private boolean abilityActive;
    private boolean attackActive;
    private ArrayList<Enemy> enemies;

    // private Shop shop;                       not created yet
    private int frameNum;


    GameEngine() {
        this.player = new Player(600, 500, 75, 150, 100, 100);
        this.surroundings = new ArrayList<>();
        this.attacks = new ArrayList<>();
        this.enemies = new ArrayList<>();
        surroundings.add(new Wall(200, 800, 500, 300));
        surroundings.add(new Wall(700, 300, 400, 1000));
        surroundings.add(new Wall(1300, 400, 500, 100));
        surroundings.add(new Wall(-200, 1500, 2000, 200));
        surroundings.add(new Wall(-200, -1000, 200, 3000));
        surroundings.add(new Wall(1800, -1000, 200, 3000));
        surroundings.add(new Spike(700, -300, 200, 200, false));
        enemies.add(new Slime(1400, 300, 100, 100, 100, 100, 10, 100));

    }

    public void spawnEnemies() {}
    public void spawnProjectile() {}
    public void moveAll() {

        player.move();

        for (int i = enemies.size() - 1; i >= 0; i --) {

            Enemy enemy = (Enemy) enemies.get(i);

            if (enemy.getHealth() > 0) {
                enemy.move(player);
            } else if (enemy.getHealth() < 0) {
                enemies.remove(i);
            }

        }

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

        for (Enemy enemy: enemies) {

            System.out.println("1");
            Slime slimeEnemy = (Slime) enemy;

            for (GameObject object: surroundings) {
                System.out.println("2");
                if (slimeEnemy.getBounds().intersects(object)) {
                    System.out.println("3");
                    slimeEnemy.collision(object);

                }
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

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public void setAttacks(ArrayList<AttackAbilities> attacks) {
        this.attacks = attacks;
    }

    public ArrayList<Wall> getSurroundings() {
        return surroundings;
    }

    public void setSurroundings(ArrayList<Wall> surroundings) {
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



    public int getFrameNum() {
        return frameNum;
    }

    public void setFrameNum(int frameNum) {
        this.frameNum = frameNum;
    }
}

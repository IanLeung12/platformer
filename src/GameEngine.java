
import java.util.ArrayList;

public class GameEngine {

    private Player player;
    private ArrayList<Wall> surroundings;
    private ArrayList<Attack> attacks;
    private boolean abilityActive;
    private boolean attackActive;
    private ArrayList<Enemy> enemies;

    // private Shop shop;                       not created yet
    private int frameNum;
    private ArrayList<GameObject> proximity;

    boolean paused = false;

//

    GameEngine() {
        this.player = new Player(500, 900, 75, 150, 100, 100);
        this.surroundings = new ArrayList<>();
        this.attacks = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.proximity = new ArrayList<>();
        surroundings.add(new Wall(200, 800, 500, 300));
        surroundings.add(new Wall(700, 300, 400, 1000));
        surroundings.add(new Wall(1300, 400, 500, 100));
        surroundings.add(new Wall(-200, 1500, 5500, 200)); // width thing
        surroundings.add(new Wall(-200, -1000, 200, 3000)); // left
        surroundings.add(new Wall(5000, -1000, 200, 3000)); // right wall
        surroundings.add(new Wall(1500, 400, 100, 400));
        surroundings.add(new Wall(1500, 1090, 100, 400));
        surroundings.add(new Wall(2000, 1100, 800, 200));
        surroundings.add(new Spike(1490, 400, 10, 400, false));
        surroundings.add(new Spike(1490, 1090, 10, 400, false));
        surroundings.add(new Spike(700, -300, 200, 200, false));
        surroundings.add(new Wall(3000, 1000, 100, 500));
        surroundings.add(new Wall(4000, 1000, 100, 500));
        //enemies.add(new Slime(1400, 300, 100, 100, 100, 100, 10, 100));
        enemies.add(new Slime(2000, 1400, 100, 100, 100, 100, 10, 100));
    }

    public void updateProximity(ArrayList<GameObject> proximity, ArrayList<Wall> surroundings, ArrayList<Enemy> enemies) {
        proximity.clear();

        proximity.addAll(surroundings);
        proximity.addAll(enemies);




    }

    public void spawnEnemies() {}
    public void spawnProjectile() {}
    public void moveAll() {

        updateProximity(proximity, surroundings, enemies);

        if (!paused) {
            player.move();
        }

        for (int i = enemies.size() - 1; i >= 0; i --) {

            Enemy enemy = (Enemy) enemies.get(i);

            if (enemy.getHealth() > 0) {

                enemy.move(player, proximity);

            } else if (enemy.getHealth() < 0) {
                enemies.remove(i);
            }

        }

        for (int i = attacks.size() - 1; i >= 0; i --) {
            Attack attack = attacks.get(i);

            if (attack.getAbilityDuration() > attack.getMaxAbilityDuration()) {
                if (attack instanceof Rocket) {
                    attacks.set(i, new Explosion((int) (attack.getCenterX()), (int) (attack.getCenterY()), true, 300));
                } else {
                    attacks.remove(i);
                }
            }

            if (attack instanceof Projectile) {
                ((Projectile) attack).move();
            }

            if (attack instanceof Explosion) {
                ((Explosion) attack).expand();
            }

            attack.setAbilityDuration(attack.getAbilityDuration() + 1);
        }

        for (Enemy enemy : enemies) {
            enemy.update();
        }


    }

    public void checkCollisions() {
        for (GameObject object: surroundings) {
            if (player.getBounds().intersects(object)) {
                player.fixCollision(object);
                player.setAbilityActive(false);
            }

            for (Enemy enemy: enemies) {
                Slime slimeEnemy = (Slime) enemy;
                if (slimeEnemy.getBounds().intersects(object)) {
                    slimeEnemy.collision(object);
                }

                if (player.getBounds().intersects(enemy)) {
                    player.fixCollision(enemy);
                    player.setAbilityActive(false);
                }
            }
        }

        for (int i = 0; i < attacks.size(); i ++) {
            Attack attack = attacks.get(i);
            for (Enemy enemy: enemies) {
                if (enemy.intersects(attack) && attack.isFriendly()) {
                    if (attack instanceof Rocket) {
                        attacks.set(i, new Explosion((int) (attack.getCenterX()), (int) (attack.getCenterY()), true, 300));
                    } else {
                        enemy.knockback(attack);
                    }

                }
            }

            if (attack instanceof Rocket) {
                for (Wall wall: surroundings) {
                    if (attack.getBounds().intersects(wall)) {
                        attacks.set(i, new Explosion((int) (attack.getCenterX()), (int) (attack.getCenterY()), true, 300));
                    }
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

    public ArrayList<Attack> getAttacks() {
        return attacks;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public void setAttacks(ArrayList<Attack> attacks) {
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
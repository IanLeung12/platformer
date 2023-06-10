import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;

public class GameEngine {

    private Player player;
    private ArrayList<Wall> surroundings;
    private ArrayList<Attack> attacks;
    private boolean abilityActive;
    private boolean attackActive;
    private ArrayList<Enemy> enemies;
    private ArrayList<Orb> orbs;

    // private Shop shop;                       not created yet
    private int frameNum;
    private ArrayList<GameObject> proximity;

    boolean paused = false;

    boolean playing = true;

    private int refreshDelay;


    GameEngine() throws FileNotFoundException {
        Scanner input = new Scanner(new File("src/Save.txt"));
        this.player = new Player((int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), input.nextDouble());
        this.surroundings = new ArrayList<>();
        this.attacks = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.proximity = new ArrayList<>();
        this.orbs = new ArrayList<>();

        this.refreshDelay = 17;

        while (input.hasNext()) {
            String objectType = input.next();
            switch (objectType) {
                case "Wall":
                    surroundings.add(new Wall((int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble()));
                    break;
                case "Spike":
                    surroundings.add(new Spike((int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble()));
                    break;
                case "Slime":
                    enemies.add(new Slime((int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), Constants.slimeGoldReward));
                    break;
                case "Mosquito":
                    enemies.add(new Mosquito((int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), Constants.mosquitoGoldReward));
                    break;
            }
        }

        input.close();
    }

    public void updateProximity(ArrayList<GameObject> proximity, ArrayList<Wall> surroundings, ArrayList<Enemy> enemies) {

        proximity.clear();

        proximity.addAll(surroundings);
        proximity.addAll(enemies);
        proximity.addAll(orbs);




    }

    public void spawnEnemies() {}
    public void spawnProjectile() {}
    public void moveAll() {

        updateProximity(proximity, surroundings, enemies);

        if (!paused) {
            player.move();
            player.immunityTick();
        }

        if (player.getHealth() <= 0) {
            player = new Player(player.getRespawnPoint()[0], player.getRespawnPoint()[1], 75, 150, player.getTotalHealth());
        }

        for (int i = enemies.size() - 1; i >= 0; i --) {

            Enemy enemy = (Enemy) enemies.get(i);

            if (enemy.getHealth() > 0) {

                enemy.move(player, proximity);

            } else if (enemy.getHealth() < 0) {

                createOrbs(enemy, orbs);

                enemies.remove(i);
            }

            enemy.immunityTick();

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

        for (Orb orb : orbs) {
            orb.move(player);
        }
    }

    public void checkCollisions() {
        for (GameObject object: surroundings) {
            if (player.intersects(object)) {
                player.collide(object);
                player.setAbilityActive(false);
            }

            for (Enemy enemy: enemies) {
                if (enemy instanceof Slime) {
                    Slime slimeEnemy = (Slime) enemy;
                    if (slimeEnemy.intersects(object)) {
                        slimeEnemy.collision(object);
                    }
                } else if (enemy instanceof Mosquito)  {
                    Mosquito mosquitoEnemy = (Mosquito) enemy;
                    if (mosquitoEnemy.intersects(object)) {
                        mosquitoEnemy.collision(object);
                    }
                }

                if (player.intersects(enemy)) {
                    player.collide(enemy);
                    player.setAbilityActive(false);
                }
            }


            for (Orb orb : orbs) {
                if (orb.intersects(object)) {
                    orb.collision(object);
                }
            }

        }

        for (int i = attacks.size() - 1; i >= 0; i --) {
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

            if (attack instanceof Projectile) {
                for (Wall wall: surroundings) {
                    if (attack.intersects(wall)) {
                        if (attack instanceof Rocket) {
                            attacks.set(i, new Explosion((int) (attack.getCenterX()), (int) (attack.getCenterY()), true, 300));
                        } else {
                            attacks.remove(i);
                        }

                    }
                }
            }
        }

        for (int i = orbs.size() - 1; i >= 0; i -- ) {
            if (orbs.get(i).intersects(player)) {
                orbAbsorb(orbs.get(i));
                orbs.remove(i);
            }
        }
    }

    public void save() throws FileNotFoundException {

        System.out.println("saveing");
        PrintWriter output = new PrintWriter(new File("src/Save2.txt"));
        output.println(player.getX() + " " + player.getY() + " " +  player.getWidth() + " " + player.getHeight() + " " + player.getHealth() + " " + player.getTotalHealth());
        for (Wall wall: this.surroundings) {
            output.println((wall instanceof Spike ? "Spike " : "Wall ") + wall.getX() + " " + wall.getY() + " " + " " + wall.getWidth() + " " + wall.getHeight());
        }
        for (Enemy enemy: this.enemies) {
            output.println(enemy.getClass().getName() + " " + enemy.getX() + " " + enemy.getY() + " " +  enemy.getWidth() + " " + enemy.getHeight() + " " +
                    enemy.getHealth() + " " + enemy.getTotalHealth() + " " + enemy.getDamage() + " " + enemy.getGoldReward());
        }
        output.close();
    }

    public void createOrbs(Enemy enemy, ArrayList<Orb> orbs ) {
        for (int i = (int) enemy.getGoldReward(); i > 0; i = i - Constants.orbValue) {
            System.out.println(i);
            orbs.add(new Orb((int) enemy.getCenterX(), (int) enemy.getCenterY(), Constants.orbDimensions, Constants.orbDimensions, Constants.orbValue, "Gold"));
        }

        for (int i = 0; i < 3; i ++) {
            if (Math.random() < enemy.getGoldReward() / 300.0) {
                orbs.add(new Orb((int) enemy.getCenterX(), (int) enemy.getCenterY(), Constants.orbDimensions, Constants.orbDimensions, 25, "Health"));
            }
        }


    }

    public void orbAbsorb(Orb orb) {
        switch (orb.getBoostType()) {
            case "Gold":
                player.setTotalGold(player.getTotalGold() + orb.getBoostValue());
                break;
            case "Health":
                player.setHealth(player.getHealth() + orb.getBoostValue());
                break;
        }
    }



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

    public int getRefreshDelay() {
        return refreshDelay;
    }

    public void setRefreshDelay(int refreshDelay) {
        this.refreshDelay = refreshDelay;
    }

    public int getFrameNum() {
        return frameNum;
    }

    public void setFrameNum(int frameNum) {
        this.frameNum = frameNum;
    }

    public ArrayList<Orb> getOrbs() {
        return orbs;
    }

    public void setOrbs(ArrayList<Orb> orbs) {
        this.orbs = orbs;
    }

}
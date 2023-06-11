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
    private ArrayList<Enemy> respawnList;
    private ArrayList<Orb> orbs;

    // private Shop shop;                       not created yet
    private int frameNum;
    private ArrayList<GameObject> proximity;

    boolean paused = false;

    boolean playing = true;

    private int energy;

    private int maxEnergy;

    private int refreshDelay;


    GameEngine() throws FileNotFoundException {
        Scanner input = new Scanner(new File("src/Save.txt"));
        this.player = new Player((int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), input.nextDouble(), input.nextDouble());
        this.surroundings = new ArrayList<>();
        this.attacks = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.proximity = new ArrayList<>();
        this.orbs = new ArrayList<>();
        this.respawnList = new ArrayList<>();

        this.refreshDelay = 17;

        while (input.hasNext()) {
            String objectType = input.next();
            switch (objectType) {
                case "Wall":
                    surroundings.add(new Wall((int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), input.nextBoolean()));
                    break;
                case "Spike":
                    surroundings.add(new Spike((int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble()));
                    break;
                case "Slime":
                    enemies.add(new Slime((int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), Constants.slimeGoldReward, (int) input.nextDouble(), (int) input.nextDouble()));
                    break;
                case "Mosquito":
                    enemies.add(new Mosquito((int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), Constants.mosquitoGoldReward, (int) input.nextDouble(), (int) input.nextDouble()));
                    break;
                case "Jumper":
                    enemies.add(new Jumper((int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), (int) input.nextDouble(), Constants.jumperGoldReward, (int) input.nextDouble(), (int) input.nextDouble()));
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

    public void moveAll() {

        updateProximity(proximity, surroundings, enemies);

        if (!paused) {
            player.move();
            player.immunityTick();
        }

        if (player.getHealth() <= 0) {
            player.respawn();
        }

        for (int i = enemies.size() - 1; i >= 0; i --) {

            Enemy enemy = enemies.get(i);

            if (enemy.getHealth() > 0) {

                enemy.move(player, proximity);

            } else if (enemy.getHealth() <= 0) {

                createOrbs(enemy, orbs);

                respawnList.add(enemy);
                enemy.setRespawnTimer(Constants.respawnTimerEnemy);
                System.out.println("enemy died " + enemy.getCenterX());

                enemies.remove(i);
            }
        }

        respawnList.removeIf(enemy -> ((enemy.getRespawnX()) == 0 && (enemy.getRespawnY() == 0)) );


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

        for (Enemy enemy : respawnList) {
            System.out.println("enemy :" + enemy + "  has this num loops left: " + enemy.getRespawnTimer());

            if ((enemy.getRespawnTimer() == 0) && (((enemy.getRespawnX()) != 0 && (enemy.getRespawnY() != 0)))) {
                System.out.println("the enemy has passed and will be created");

                if (enemy instanceof Slime) {
                    Slime slime = (Slime) enemy;
                    slime.setXSpeed(0);
                    slime.setYSpeed(0);
                    slime.setAbilityActive(false);
                    slime.setImmunityTimer(10);
                    slime.setHealth(Constants.slimeTotalHealth);
                    slime.setLocation((int) slime.getRespawnX(), (int) slime.getRespawnY());

                } else if (enemy instanceof Mosquito) {
                    Mosquito mosquito = (Mosquito) enemy;
                    mosquito.setXSpeed(0);
                    mosquito.setYSpeed(0);
                    mosquito.setAbilityActive(false);
                    mosquito.setImmunityTimer(1);
                    mosquito.setHealth(Constants.mosquitoTotalHealth);
                   // mosquito.setLocation((int) player.getCenterX(), (int) player.getCenterY() );

                    mosquito.setLocation((int) mosquito.getRespawnX(), (int) mosquito.getRespawnY());


                } else if (enemy instanceof Jumper) {
                    Jumper jumper = (Jumper) enemy;
                    jumper.setXSpeed(0);
                    jumper.setYSpeed(0);
                    jumper.setAbilityActive(false);
                    jumper.setImmunityTimer(10);
                    jumper.setHealth(Constants.jumperMaxHP);
                    jumper.setLocation((int) jumper.getRespawnX(), (int) jumper.getRespawnY());

                }

                enemies.add(enemy);

                System.out.println("respawned" + enemy + "    and respanw x and y are "  + enemy.getRespawnX() + "   " + enemy.getRespawnY());
            }

            System.out.println(enemy.getRespawnTimer());

            enemy.update();

        }


        respawnList.removeIf(enemy -> enemy.getRespawnTimer() < 0);

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
                } else if (enemy instanceof Jumper)  {
                    Jumper jumperEnemy = (Jumper) enemy;
                    if (jumperEnemy.intersects(object)) {
                        jumperEnemy.collision(object);
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
                            break;
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

        PrintWriter output = new PrintWriter(new File("src/Save2.txt"));
        output.println(player.getX() + " " + player.getY() + " " +  player.getWidth() + " " + player.getHeight() + " " + player.getHealth() + " " + player.getMaxHealth());
        for (Wall wall: this.surroundings) {
            output.println((wall instanceof Spike ? "Spike " : "Wall ") + wall.getX() + " " + wall.getY() + " " + " " + wall.getWidth() + " " + wall.getHeight());
        }
        for (Enemy enemy: this.enemies) {
            output.println(enemy.getClass().getName() + " " + enemy.getX() + " " + enemy.getY() + " " +  enemy.getWidth() + " " + enemy.getHeight() + " " +
                    enemy.getHealth() + " " + enemy.getMaxHealth() + " " + enemy.getDamage() + " " + enemy.getGoldReward() + " " + enemy.getRespawnX() + " " + enemy.getRespawnY());
        }
        output.close();
    }

    public void createOrbs(Enemy enemy, ArrayList<Orb> orbs ) {
        for (int i = (int) enemy.getGoldReward(); i > 0; i = i - Constants.orbValue) {
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
                player.setTotalGold((int) (player.getTotalGold() + orb.getBoostValue()));
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

    public ArrayList<Enemy> getRespawnList() {
        return respawnList;
    }
}
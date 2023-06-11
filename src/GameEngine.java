import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
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
        Scanner input = new Scanner(new File("src/Save2.txt"));
        this.player = new Player(input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextDouble(), input.nextDouble());
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
                    surroundings.add(new Wall(input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextBoolean()));
                    break;
                case "Spike":
                    surroundings.add(new Spike(input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt()));
                    break;
                case "Crystal":
                    surroundings.add(new Crystal(input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.next()));
                    break;
                case "Slime":
                    enemies.add(new Slime(input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt()));
                    break;
                case "Mosquito":
                    enemies.add(new Mosquito(input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt()));
                    break;
                case "Jumper":
                    enemies.add(new Jumper(input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt()));
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
            System.out.println(Arrays.toString(player.getRespawnPoint()));
            player.immunityTick();
        }

        if (player.getHealth() <= 0) {
            player.respawn();
        }

        for (int i = enemies.size() - 1; i >= 0; i --) {

            Enemy enemy = enemies.get(i);

            if (enemy.getHealth() > 0) {

                enemy.move(player, proximity);
                System.out.println("enemy respawn x :" + enemy.getRespawnX() + " respawn y" + enemy.getRespawnY());

            } else if (enemy.getHealth() <= 0) {

                createOrbs(enemy);

                respawnList.add(enemy);

                enemy.setRespawnTimer(Constants.respawnTimerEnemy);
                System.out.println("enemy died " + enemy.getCenterX());

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

        for (Orb orb : orbs) {
            orb.move(player);
        }

        for (Enemy enemy : respawnList) {
            System.out.println("enemy :" + enemy + "  has this num loops left: " + enemy.getRespawnTimer());

            if ((enemy.getRespawnTimer() == 0) && (((enemy.getRespawnX()) != 0 && (enemy.getRespawnY() != 0)))) {
                System.out.println("the enemy has passed and will be created");

                if (enemy instanceof Slime) {
                    enemy.setXSpeed(0);
                    enemy.setYSpeed(0);
                    enemy.setAbilityActive(false);
                    enemy.setImmunityTimer(10);
                    enemy.setHealth(Constants.slimeTotalHealth);
                    enemy.setLocation((int) enemy.getRespawnX(), (int) enemy.getRespawnY());

                } else if (enemy instanceof Mosquito) {
                    enemy.setXSpeed(0);
                    enemy.setYSpeed(0);
                    enemy.setAbilityActive(false);
                    enemy.setImmunityTimer(1);
                    enemy.setHealth(Constants.mosquitoTotalHealth);
                    //mosquito.setLocation((int) player.getCenterX(), (int) player.getCenterY() );
                    enemy.setLocation((int) enemy.getRespawnX(), (int) enemy.getRespawnY());


                } else if (enemy instanceof Jumper) {
                    enemy.setXSpeed(0);
                    enemy.setYSpeed(0);
                    enemy.setAbilityActive(false);
                    enemy.setImmunityTimer(10);
                    enemy.setHealth(Constants.jumperMaxHP);
                    enemy.setLocation((int) enemy.getRespawnX(), (int) enemy.getRespawnY());

                }

                enemies.add(enemy);

                System.out.println("respawned" + enemy + "    and respanw x and y are "  + enemy.getRespawnX() + "   " + enemy.getRespawnY());
                System.out.println(" player x and y " + player.getCenterX() + "   " + player.getCenterY());
            }

            System.out.println(enemy.getRespawnTimer());

            enemy.update();

        }


        respawnList.removeIf(enemy -> enemy.getRespawnTimer() < 0);
        respawnList.removeIf(enemy -> ((enemy.getRespawnX()) == 0 && (enemy.getRespawnY() == 0)) );

    }

    public void checkCollisions() {
        for (Wall object: surroundings) {
            if (object instanceof Crystal) {
                ((Crystal) object).crystalTick();
            }
            if (player.intersects(object)) {
                player.collide(object);
                player.setAbilityActive(false);
            }

            for (Enemy enemy: enemies) {
                if (enemy.intersects(object)) {
                    enemy.collision(object);
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

            for (Wall wall: surroundings) {
                if (wall instanceof Crystal) {
                    if (attack.intersects(wall)) {
                        ((Crystal) wall).takeHit(attack);
                        if (((Crystal) wall).getHealth() <= 0) {
                            createOrbs(wall);
                        }
                    }

                } else if (attack instanceof Projectile) {
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
        output.println((int) player.getX() + " " + (int) player.getY() + " " + (int) player.getWidth() + " " + (int) player.getHeight() + " " + (int) player.getHealth() + " " + (int) player.getMaxHealth());
        for (Wall wall: this.surroundings) {
            output.println(wall.getClass().getName() + " " + (int) wall.getX() + " " + (int) wall.getY() + " "  + (int) wall.getWidth() + " " + (int) wall.getHeight() + " " + (wall instanceof Crystal ? ((Crystal) wall).getBoostType() :  wall.isrespawnable()));
        }
        for (Enemy enemy: this.enemies) {
            output.println(enemy.getClass().getName() + " " + (int) enemy.getX() + " " + (int) enemy.getY() + " " + (int) enemy.getWidth() + " " + (int) enemy.getHeight() + " " +
                    (int) enemy.getHealth() + " " + (int) enemy.getMaxHealth() + " " + (int) enemy.getDamage() + " " + (int) enemy.getGoldReward() + " " + (int) enemy.getRespawnX() + " " + (int) enemy.getRespawnY());
        }
        output.close();
    }

    public void createOrbs(GameObject object) {
        if (object instanceof Enemy) {
            Enemy enemy = (Enemy) object;
            for (int i = (int) enemy.getGoldReward(); i > 0; i -= 10) {
                orbs.add(new Orb((int) enemy.getCenterX(), (int) enemy.getCenterY(), Constants.orbDimensions, Constants.orbDimensions, 10, "Gold"));
            }

            for (int i = 0; i < 3; i ++) {
                if (Math.random() < enemy.getGoldReward() / 300.0) {
                    orbs.add(new Orb((int) enemy.getCenterX(), (int) enemy.getCenterY(), Constants.orbDimensions, Constants.orbDimensions, 25, "Health"));
                } else if (Math.random() < enemy.getGoldReward() / 600.0) {
                    orbs.add(new Orb((int) enemy.getCenterX(), (int) enemy.getCenterY(), Constants.orbDimensions, Constants.orbDimensions, 20, "Energy"));
                }
            }
        } else {
            Crystal crystal = (Crystal) object;
            for (int i = crystal.getBoostValue(); i > 0; i = i - 10) {
                orbs.add(new Orb((int) crystal.getCenterX(), (int) crystal.getCenterY(), Constants.orbDimensions, Constants.orbDimensions, 10, crystal.getBoostType()));
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
            case "Energy":
                player.setEnergy(player.getEnergy() + orb.getBoostValue());
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
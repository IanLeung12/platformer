import java.awt.*;
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

    private int frameNum;

    boolean paused = false;

    boolean playing = true;

    private int refreshDelay;

    private ArrayList<Rectangle> obeliskHitboxes;

    private boolean inObelisk = false;

    private int currentObelisk = 0;

    private ArrayList<int[]> obeliskSpawns;

    private int requiredKills = 0;

    private boolean inShop = false;

    private ArrayList<ShopItem> shop;

    GameEngine() throws FileNotFoundException {
        Scanner input = new Scanner(new File("src/Save.txt"));
        this.player = new Player(input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextDouble(), input.nextDouble(), input.nextDouble(), input.nextDouble(), input.nextInt(), input.nextBoolean(), input.nextBoolean(), input.nextDouble());
        this.surroundings = new ArrayList<>();
        this.attacks = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.orbs = new ArrayList<>();
        this.respawnList = new ArrayList<>();
        this.obeliskHitboxes = new ArrayList<>();
        this.shop = new ArrayList<>();
        this.obeliskSpawns = new ArrayList<>();

        this.refreshDelay = 17;

        while (input.hasNext()) {
            String objectType = input.next();
            switch (objectType) {
                case "Wall":
                    surroundings.add(new Wall(input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextBoolean()));
                    break;
                case "Spike":
                    surroundings.add(new Spike(input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt()));
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
                case "Hitbox":
                    obeliskHitboxes.add(new Rectangle(input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt()));
                    break;
                case "Shop":
                    shop.add(new ShopItem(input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.next(), input.nextInt()));
                    break;
                case "Weapon":
                    player.getWeapons().add(input.next());
                    break;
            }
        }

        input.close();
    }


    public void moveAll() {

        if (inObelisk) {
            obeliskTick();
        }

        if (!paused) {
            player.move();
            player.immunityTick();
        }

        if (player.getHealth() <= 0) {
            if (inObelisk) {
                for (int i = enemies.size() - 1; i >= 0; i --) {
                    Enemy enemy = enemies.get(i);
                    if (enemy.getRespawnY() > 99999) {
                        respawnList.add(enemy);

                        enemy.setRespawnTimer(Constants.respawnTimerEnemy);

                        enemies.remove(i);
                    }
                }
                inObelisk = false;
            }
            player.respawn();
        }

        for (int i = enemies.size() - 1; i >= 0; i --) {

            Enemy enemy = enemies.get(i);

            if (enemy.getHealth() > 0) {

                enemy.move(player, surroundings);

            } else if (enemy.getHealth() <= 0) {

                if (enemy.isObeliskEnemy()) {
                    requiredKills --;
                }

                createOrbs(enemy);

                respawnList.add(enemy);

                enemy.setRespawnTimer(Constants.respawnTimerEnemy);

                enemies.remove(i);
            }
        }



        for (int i = attacks.size() - 1; i >= 0; i --) {
            Attack attack = attacks.get(i);

            if (attack.getAbilityDuration() > attack.getMaxAbilityDuration()) {
                if (attack instanceof Rocket) {
                    attacks.set(i, new Explosion((int) (attack.getCenterX()), (int) (attack.getCenterY()), true, 300, player.getDamageBoost()));
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

        for (int i = orbs.size() - 1; i >= 0; i --) {
            if (!orbs.get(i).move(player)) {
                orbs.remove(i);
            }
        }

        for (Enemy enemy : respawnList) {

            if ((enemy.getRespawnTimer() == 0) && (((enemy.getRespawnX()) != 0 && (enemy.getRespawnY() != 0)))) {

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

            }


            enemy.update();

        }


        respawnList.removeIf(enemy -> enemy.getRespawnTimer() < 0);
        respawnList.removeIf(enemy -> (enemy.getRespawnY() > 99999) );

    }

    public void checkCollisions() {
        for (Wall object: surroundings) {
            if (object instanceof Crystal) {
                ((Crystal) object).crystalTick();
                if (((Crystal) object).getRespawnTimer() == 0) {
                    if (player.intersects(object)) {
                        player.collide(object);
                        player.setAbilityActive(false);
                    }
                }
            } else if (player.intersects(object)) {
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
                        attacks.set(i, new Explosion((int) (attack.getCenterX()), (int) (attack.getCenterY()), true, 300, player.getDamageBoost()));
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
                            attacks.set(i, new Explosion((int) (attack.getCenterX()), (int) (attack.getCenterY()), true, 300, player.getDamageBoost()));
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

    public void createOrbs(GameObject object) {
        if (object instanceof Enemy) {
            Enemy enemy = (Enemy) object;
            for (int i = (int) enemy.getGoldReward(); i > 0; i -= 10) {
                orbs.add(new Orb((int) enemy.getCenterX(), (int) enemy.getCenterY(), Constants.orbDimensions, Constants.orbDimensions, 10, "Gold"));
            }

            for (int i = 0; i < 3; i ++) {
                if (Math.random() < enemy.getGoldReward() / 800.0) {
                    orbs.add(new Orb((int) enemy.getCenterX(), (int) enemy.getCenterY(), Constants.orbDimensions, Constants.orbDimensions, 25, "Health"));
                } else if (Math.random() < enemy.getGoldReward() / 1200.0) {
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

    public void createOrbs(int orbAmount, Rectangle hitbox, String orbType) {
        for (int i = orbAmount; i > 0; i -= 10) {
            orbs.add(new Orb((int) (hitbox.getX() + hitbox.getWidth() * Math.random()), (int) (hitbox.getY() + Math.random() * 200), Constants.orbDimensions, Constants.orbDimensions, 10, orbType));
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

    public void activateObelisk(int obeliskNum) {
        this.inObelisk = true;
        this.currentObelisk = obeliskNum;
        this.obeliskSpawns.clear();

        switch (obeliskNum) {
            case 0:
                this.obeliskSpawns.add(new int[] {25, 0, 0});
                requiredKills = 25;
                break;
            case 1:
                this.obeliskSpawns.add(new int[] {0, 5, 0});
                this.requiredKills = 5;
                this.obeliskSpawns.add(new int[] {5, 3, 0});
                this.obeliskSpawns.add(new int[] {0, 8, 0});
                this.obeliskSpawns.add(new int[] {10, 5, 0});
                break;
            case 2:
                this.obeliskSpawns.add(new int[] {0, 0, 4});
                this.requiredKills = 4;
                this.obeliskSpawns.add(new int[] {5, 3, 2});
                this.obeliskSpawns.add(new int[] {8, 2, 4});
                this.obeliskSpawns.add(new int[] {0, 5, 5});
                this.obeliskSpawns.add(new int[] {8, 10, 6});
                break;
        }
    }


    public void obeliskTick() {
        if (requiredKills <= 0) {
            requiredKills = 0;
            obeliskSpawns.remove(0);
            if (obeliskSpawns.size() == 0) {
                this.inObelisk = false;
                switch (currentObelisk) {
                    case 0:
                        if (player.getMaxJumps() < 2) {
                            player.setMaxJumps(2);
                        }
                        if (!player.isDashUnlocked()) {
                            player.setMaxHealth(player.getMaxHealth() + 100);
                            player.setDashUnlocked(true);
                        }

                        createOrbs(2000, obeliskHitboxes.get(0), "Gold");

                        break;
                    case 1:
                        if (!player.isBashUnlocked()) {
                            player.setMaxHealth(player.getMaxHealth() + 100);
                            player.setBashUnlocked(true);
                            createOrbs(4000, obeliskHitboxes.get(1), "Gold");
                        }
                        break;

                }
            } else {
                for (int i = 0; i < this.obeliskSpawns.get(0).length; i++) {
                    requiredKills += this.obeliskSpawns.get(0)[i];
                }
                createOrbs(30, obeliskHitboxes.get(currentObelisk), "Health");
                createOrbs(50, obeliskHitboxes.get(currentObelisk), "Energy");
            }


        } else {
            Rectangle obelisk = this.obeliskHitboxes.get(this.currentObelisk);
            for (int i = 0; i < obeliskSpawns.get(0).length; i ++) {
                if (obeliskSpawns.get(0)[i] > 0) {
                    if (Math.random() < 0.1) {
                        obeliskSpawns.get(0)[i] --;
                        switch (i) {
                            case 0:
                                enemies.add(new Slime((int) (obelisk.getX() - 100 + ((int) (Math.random() * 2)) * (obelisk.getWidth() + 200)), (int) (obelisk.getY() + obelisk.getHeight() - 100), 100, 100, 100, 100, 15, 0, 0, 150000, true));
                                break;
                            case 1:
                                enemies.add(new Mosquito((int) (obelisk.getX() + Math.random() * (obelisk.getWidth() - 50)), (int) (obelisk.getY() + Math.random() * (obelisk.getHeight() + 50)), 50, 50, 100, 100, 20, 0, 0, 150000, true));
                                break;
                            case 2:
                                enemies.add(new Jumper((int) (obelisk.getX() - 150 + Math.random() * (obelisk.getWidth() - 150)), (int) (obelisk.getY() + obelisk.getHeight() - 100), 150, 150, 150, 150, 40, 0, 0, 150000, true));
                                break;
                        }
                    }
                }
            }
        }
    }

    public void save() throws FileNotFoundException {

        PrintWriter output = new PrintWriter(new File("src/Save2.txt"));
        output.println(player.getRespawnPoint()[0] + " " + player.getRespawnPoint()[1] + " " + (int) player.getWidth() + " " + (int) player.getHeight() + " " +
                (int) player.getHealth() + " " + (int) player.getMaxHealth() + " " + (int) player.getEnergy() + " " + (int) player.getMaxEnergy() + " " +
                player.getMaxJumps() + " " + player.isDashUnlocked() + " " + player.isDashUnlocked() + " " + player.getDamageBoost());
        for (String weapon: player.getWeapons()) {
            output.println("Weapon " + weapon);
        }
        for (Wall wall: this.surroundings) {
            if (wall instanceof Wall) {
                output.println(wall.getClass().getName() + " " + (int) wall.getX() + " " + (int) wall.getY() + " "  + (int) wall.getWidth() + " " + (int) wall.getHeight());

            } else if (wall instanceof Spike){
                output.println(wall.getClass().getName() + " " + (int) wall.getX() + " " + (int) wall.getY() + " "  + (int) wall.getWidth() + " " + (int) wall.getHeight() + " " + (int) ((Spike) wall).getDamage());

            } else {
                output.println(wall.getClass().getName() + " " + (int) wall.getX() + " " + (int) wall.getY() + " "  + (int) wall.getWidth() + " " + (int) wall.getHeight() + " " + (wall instanceof Crystal ? ((Crystal) wall).getBoostType() :  wall.isrespawnable()));
            }
        }
        for (Enemy enemy: this.enemies) {
            output.println(enemy.getClass().getName() + " " + (int) enemy.getX() + " " + (int) enemy.getY() + " " + (int) enemy.getWidth() + " " + (int) enemy.getHeight() + " " +
                    (int) enemy.getHealth() + " " + (int) enemy.getMaxHealth() + " " + (int) enemy.getDamage() + " " + (int) enemy.getGoldReward() + " " + (int) enemy.getRespawnX() + " " + (int) enemy.getRespawnY());
        }
        for (ShopItem item: this.shop) {
            output.println("Shop " + item.getX() + " " + item.getY() + " " + item.getWidth() + " " + item.getHeight() + " " + item.getName() + " " + item.getPrice());
        }
        output.close();
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

    public ArrayList<Rectangle> getObeliskHitboxes() {
        return obeliskHitboxes;
    }

    public void setObeliskHitboxes(ArrayList<Rectangle> obeliskHitboxes) {
        this.obeliskHitboxes = obeliskHitboxes;
    }

    public boolean isInObelisk() {
        return inObelisk;
    }

    public void setInObelisk(boolean inObelisk) {
        this.inObelisk = inObelisk;
    }

    public boolean isInShop() {
        return inShop;
    }

    public void setInShop(boolean inShop) {
        this.inShop = inShop;
    }

    public ArrayList<ShopItem> getShop() {
        return shop;
    }

    public void setShop(ArrayList<ShopItem> shop) {
        this.shop = shop;
    }

    public int getRequiredKills() {
        return requiredKills;
    }
}
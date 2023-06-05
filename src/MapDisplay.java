/* UsingPictures
 * Desc: Demonstrates how to load from file and draw a picture
 * @author ICS3U
 * @version Dec 2017
 */
import javax.swing.*;
import java.awt.*;
// the following imports are needed for pictures
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class MapDisplay extends JFrame{
    // Game Window properties
    static GraphicsPanel canvas;
    static final int WIDTH = 1920;
    static final int HEIGHT = 1080;

    static int cameraX;

    static int cameraY;

    static int lastCamX;

    static int lastCamY;

    static GameEngine game;

    Player player;


    //------------------------------------------------------------------------------
    MapDisplay(GameEngine game){
        super("Game Window");
        this.setSize(WIDTH,HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cameraX = 0;
        cameraY = 0;

        MapDisplay.game = game;

        this.player = game.getPlayer();

        canvas = new GraphicsPanel();
        this.add(canvas);

        addKeyListener(new Keyboard());

        // load the picture from a file
        this.setVisible(true);

    } // main method end

    public void refresh() {
        this.repaint();
        cameraX = (int) player.getX() - 650;
        cameraY = (int) player.getY() - 500;

        int dX = lastCamX - cameraX;
        int dY = lastCamY - cameraY;

        if (Math.abs(dX) > 25) {
            cameraX = lastCamX - dX/Math.abs(dX) * 25;
        }

        if (Math.abs(dY) > 25) {
            cameraY = lastCamY - dY/Math.abs(dY) * 25;
        }

        for (GameObject surrounding: game.getSurroundings()) {
            surrounding.setLocation((int) (surrounding.getX() - cameraX + lastCamX), (int) (surrounding.getY() - cameraY + lastCamY));

        }

        for (Enemy enemy: game.getEnemies()) {
            enemy.setLocation((int) (enemy.getX() - cameraX + lastCamX), (int) (enemy.getY() - cameraY + lastCamY));

        }

        player.setLocation((int) (player.getX() - cameraX + lastCamX), (int) (player.getY() - cameraY + lastCamY));
    }

    static class GraphicsPanel extends JPanel{
        public GraphicsPanel(){
        }
        public void paintComponent(Graphics g){
            super.paintComponent(g); //required

            for (GameObject thing: game.getSurroundings()) {
                g.setColor(Color.darkGray);
                if (thing instanceof Spike) {
                    g.setColor(Color.red);
                }
                g.fillRect((int) thing.getX(), (int) thing.getY(), (int) thing.getWidth(), (int) thing.getHeight());

            }

            g.setColor(Color.orange);
            for (Attacks attack: game.getAttacks()) {
                g.drawRect((int) attack.getX(), (int) attack.getY(), (int) attack.getWidth(), (int) attack.getHeight());
            }

            for (Enemy enemy: game.getEnemies()) {
                g.drawRect((int) enemy.getX(), (int) enemy.getY(), (int) enemy.getWidth(), (int) enemy.getHeight());
                g.fillRect((int) enemy.getX(), (int) enemy.getY(), (int) enemy.getWidth(), (int) enemy.getHeight());
            }

            g.setColor(Color.gray);
            Player player = game.getPlayer();
            g.fillRect((int) player.getX(), (int) player.getY(), (int) player.getWidth(), (int) player.getHeight());
        } // paintComponent method end
    } // GraphicsPanel class end



    class Keyboard implements KeyListener {

        /**
         * Invoked when a key has been pressed.
         * See the class description for {@link KeyEvent} for a definition of
         * a key pressed event.
         *
         * @param e
         */
        public void keyPressed(KeyEvent e) {
            char key = Character.toLowerCase(e.getKeyChar());
            if (key == 'a') {
                player.setMovingLeft(true);
                player.setDirection(-1);
            } else if (key == 'd') {
                player.setMovingRight(true);
                player.setDirection(1);
            }

            if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                if (!(player.isAbilityActive() || player.isDashUsed())) {
                    player.setAbilityActive(true);

                    player.setAbilityDirection(Constants.getDashX() * player.getDirection(), 0);

                    player.movementAbility();
                }
            }



        }

        /**
         * Invoked when a key has been released.
         * See the class description for {@link KeyEvent} for a definition of
         * a key released event.
         *
         * @param e
         */
        public void keyReleased(KeyEvent e) {
            char key = Character.toLowerCase(e.getKeyChar());
            if (key == 'a') {
                player.setMovingLeft(false);
            } else if (key == 'd') {
                player.setMovingRight(false);
            }
        }

        /**
         * Invoked when a key has been typed.
         * See the class description for {@link KeyEvent} for a definition of
         * a key typed event.
         *
         * @param e
         */
        public void keyTyped(KeyEvent e) {
            char key = Character.toLowerCase(e.getKeyChar());
            if (key == ' ') {
                player.jump();
            }
            if (key == 'f') {
                if (player.getDirection() == 1) {
                    game.getAttacks().add(new Sword((int) (player.getX() + player.getWidth()), (int) (player.getY() - 50), 1, true));
                } else {
                    game.getAttacks().add(new Sword((int) (player.getX() - 150), (int) (player.getY() - 50), -1, true));
                }
            }
        }

    }
} // UsingPictures class
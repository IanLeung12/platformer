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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class MapDisplay extends JFrame{//
    // Game Window properties
    static GraphicsPanel canvas;
    static final int WIDTH = 1920;
    static final int HEIGHT = 1080;

    static int cameraX = 0;

    static int cameraY = 0;

    static int lastCamX;

    static int lastCamY;

    static GameEngine game;

    Player player;


    //------------------------------------------------------------------------------
    MapDisplay(GameEngine game){
        super("Game Window");
        this.setSize(WIDTH,HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MapDisplay.game = game;

        this.player = game.getPlayer();

        canvas = new GraphicsPanel();
        this.add(canvas);

        addKeyListener(new Keyboard());
        addMouseListener(new Mouse());

        // load the picture from a file
        this.setVisible(true);

    } // main method end

    public void refresh() {
        this.repaint();
        cameraX = (int) player.getX() - 900;
        cameraY = (int) player.getY() - 500;

        int dX = lastCamX - cameraX;
        int dY = lastCamY - cameraY;

        if (Math.abs(dX) > 26) {
            dX = dX/Math.abs(dX) * 26;
        }

        if (Math.abs(dY) > 26) {
            dY = dY/Math.abs(dY) * 26;
        }

        player.translate(dX, dY);

        for (GameObject surrounding: game.getSurroundings()) {
            surrounding.translate(dX, dY);

        }

        for (Enemy enemy: game.getEnemies()) {
            enemy.translate(dX, dY);

        }

        for (Attack attack: game.getAttacks()) {
            if (attack instanceof Arrow) {
                attack.translate(dX, dY);
            }
        }
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

            g.setColor(Color.GREEN);
            for (Attack attack: game.getAttacks()) {
                g.fillRect((int) attack.getX(), (int) attack.getY(), (int) attack.getWidth(), (int) attack.getHeight());
            }

            g.setColor(Color.yellow);
            for (Enemy enemy: game.getEnemies()) {
                g.drawRect((int) enemy.getX(), (int) enemy.getY(), (int) enemy.getWidth(), (int) enemy.getHeight());
                g.fillRect((int) enemy.getX(), (int) enemy.getY(), (int) enemy.getWidth(), (int) enemy.getHeight());
            }

            g.setColor(Color.gray);
            Player player = game.getPlayer();
            g.fillRect((int) player.getX(), (int) player.getY(), (int) player.getWidth(), (int) player.getHeight());
        } // paintComponent method end
    } // GraphicsPanel class end



    class Mouse implements MouseListener {

        /**
         * Invoked when the mouse button has been clicked (pressed
         * and released) on a component.
         *
         * @param e
         */
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        /**
         * Invoked when a mouse button has been pressed on a component.
         *
         * @param e
         */
        @Override
        public void mousePressed(MouseEvent e) {


        }

        /**
         * Invoked when a mouse button has been released on a component.
         *
         * @param e
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                int direction = e.getX() > player.getCenterX() ? 1 : -1;

                System.out.println(player.getCurrentWeapon());
                switch (player.getCurrentWeapon()) {
                    case "Sword":
                        game.getAttacks().add(new Sword((int) (player.getX() + (direction == 1 ? player.getWidth() : -150)),
                                (int) (player.getY() - 50), direction, true));
                        break;
                    case "Hammer":
                        game.getAttacks().add(new Hammer((int) (player.getX() + (direction == 1 ? player.getWidth() + 50 : -300)),
                                (int) (player.getY() - 50), 1, true));
                        break;
                    case "Bow":
                        game.getAttacks().add(new Arrow((int) player.getCenterX() - 50, (int) player.getCenterY() - 25,
                                e.getX() + cameraX, e.getY() + cameraY, direction, true));
                        break;
                    case "Rocket":
                        game.getAttacks().add(new Rocket((int) player.getCenterX() - 50, (int) player.getCenterY() - 25,
                                e.getX() + cameraX, e.getY() + cameraY, direction, true));
                        break;
                }

            } else if (e.getButton() == MouseEvent.BUTTON3) {
                // @Razor177 put bash here. TargetX = e.getX() etc.
            }
        }

        /**
         * Invoked when the mouse enters a component.
         *
         * @param e
         */
        @Override
        public void mouseEntered(MouseEvent e) {

        }

        /**
         * Invoked when the mouse exits a component.
         *
         * @param e
         */
        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

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
                player.setMovingRight(false);
                player.setDirection(-1);
            } else if (key == 'd') {
                player.setMovingRight(true);
                player.setMovingLeft(false);
                player.setDirection(1);
            }

            if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                if (!(player.isAbilityActive() || player.isDashUsed())) {
                    player.setAbilityActive(true);

                    player.setAbilityDirection(Constants.getDashX() * player.getDirection(), 0);

                    player.movementAbility();
                }
            }

            if (key == 'q') {
                game.paused = true;
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
            if (key == 'q') {
                game.paused = false;
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

            System.out.println(key);
            switch (key) {
                case '1':
                    player.setCurrentWeapon("Sword");
                    break;
                case '2':
                    player.setCurrentWeapon("Hammer");
                    break;
                case '3':
                    player.setCurrentWeapon("Bow");
                    break;
                case '4':
                    player.setCurrentWeapon("Rocket");
                    break;
            }
        }

    }
} // UsingPictures class
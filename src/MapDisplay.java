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
        this.cameraX = 0;
        this.cameraY = 0;

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

        for (GameObject surrounding: game.getSurroundings()) {
            surrounding.setLocation((int) (surrounding.getX() - cameraX + lastCamX), (int) (surrounding.getY() - cameraY + lastCamY));

        }

        player.setLocation((int) (player.getX() - cameraX + lastCamX), (int) (player.getY() - cameraY + lastCamY));
    }

    static class GraphicsPanel extends JPanel{
        public GraphicsPanel(){
        }
        public void paintComponent(Graphics g){
            super.paintComponent(g); //required

            g.setColor(Color.blue);
            for (GameObject thing: game.getSurroundings()) {
                g.drawRect((int) thing.getX(), (int) thing.getY(), (int) thing.getWidth(), (int) thing.getHeight());
            }

            g.setColor(Color.black);
            Player player = game.getPlayer();
            g.drawRect((int) player.getX(), (int) player.getY(), (int) player.getWidth(), (int) player.getHeight());
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
             if (e.getKeyChar() == 'a') {
                 player.setMovingLeft(true);
             } else if (e.getKeyChar() == 'd') {
                 player.setMovingRight(true);
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
            if (e.getKeyChar() == 'a') {
                player.setMovingLeft(false);
            } else if (e.getKeyChar() == 'd') {
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
            if (e.getKeyChar() == ' ') {
                player.jump();
            } else if (e.getKeyCode() == 16) {

            } else if (e.getKeyChar() == 'f') {

            }
            if (e.getKeyChar() == 'z') {
                player.setAbilityActive(true);
                if (player.isMovingRight()) {
                    player.setAbilityDirection(Constants.getDashX(), 0);
                } else {
                    player.setAbilityDirection((Constants.getDashX() * (-1)), 0);
                }
                player.movementAbility();
                System.out.println(Constants.getDashX());
            }
        }

    }
} // UsingPictures class
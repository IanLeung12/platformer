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
    static GameEngine game;

    //------------------------------------------------------------------------------
    MapDisplay(GameEngine game){
        super("Game Window");
        this.setSize(WIDTH,HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MapDisplay.game = game;

        canvas = new GraphicsPanel();
        this.add(canvas);

        addKeyListener(new Keyboard());
        
        // load the picture from a file
        this.setVisible(true);

    } // main method end

    public void refresh() {
        this.repaint();
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

        Player player = game.getPlayer();

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
            }
        }
    }
} // UsingPictures class
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        GameEngine map = new GameEngine();
        MapDisplay window = new MapDisplay(map);


        while (map.playing) {
            window.refresh();

            map.moveAll();
            map.checkCollisions();



            window.refresh();
            try {Thread.sleep(17);} catch(Exception e){}

            // Move the picture
        }

        System.out.println("end");

    }
}
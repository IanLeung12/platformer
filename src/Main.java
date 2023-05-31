// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        GameEngine map = new GameEngine();
        MapDisplay window = new MapDisplay(map);

        while (true) {
            window.refresh();
            map.moveAll();
            try  {Thread.sleep(20);} catch(Exception e){}

            // Move the picture
        }
    }
}
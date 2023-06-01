public class Wall extends GameObject{

    private boolean breakable;

    Wall(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.breakable = false;
    }

    Wall(int x, int y, int width, int height, boolean breakable) {
        super(x, y, width, height);
        this.breakable = breakable;
    }

    public boolean isBreakable() {
        return breakable;
    }

    public void setBreakable(boolean breakable) {
        this.breakable = breakable;
    }
}

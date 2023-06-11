public class Wall extends GameObject{

    private boolean respawnable;

    Wall(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.respawnable = false;
    }

    Wall(int x, int y, int width, int height, boolean respawnable) {
        super(x, y, width, height);
        this.respawnable = respawnable;
    }

    public boolean isrespawnable() {
        return respawnable;
    }

    public void setrespawnable(boolean respawnable) {
        this.respawnable = respawnable;
    }
}

abstract public class Interactable extends GameObject{


    private int interactTimer;
    private final int fullInteractTimer;

    Interactable(int x, int y, int width, int height, int interactTimer, int fullInteractTimer) {
        super(x, y, width, height);
        this.interactTimer = interactTimer;
        this.fullInteractTimer = fullInteractTimer;
    }



    public int getInteractTimer() {
        return interactTimer;
    }

    public void setInteractTimer(int interactTimer) {
        this.interactTimer = interactTimer;
    }

    public int getFullInteractTimer() {
        return fullInteractTimer;
    }
}

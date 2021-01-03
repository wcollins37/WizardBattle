public class Wizard {

    int x;
    int y;
    int prevX = -1;
    int prevY = -1;
    boolean isPlayerOne;

    public Wizard(int x, int y, boolean isPlayerOne) {
        this.x = x;
        this.y = y;
        this.isPlayerOne = isPlayerOne;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPrevX() {
        return this.prevX;
    }

    public void setPrevX() {
        this.prevX = this.x;
    }

    public int getPrevY() {
        return this.prevY;
    }

    public void setPrevY() {
        this.prevY = this.y;
    }

    public boolean getIsPlayerOne() {
        return this.isPlayerOne;
    }

    public void move(int x, int y) {
        if (x >= 0 && x < 5 && y >= 0 && y < 5) {
            setPrevX();
            setPrevY();
            setX(x);
            setY(y);
        }
    }
}

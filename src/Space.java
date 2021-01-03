public class Space {

    int x;
    int y;
    int value;
    boolean empty = true;

    public Space(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
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

    public int getValue() {
        return this.value;
    }

    public boolean incValue() {
        System.out.println("Old value: " + this.value);
        this.value++;
        System.out.println("Higher value: " + this.value);
        return true;
    }

    public boolean decValue() {
        System.out.println("Old value: " + this.value);
        if (this.value > 0) {
            this.value--;
            System.out.println("Lower value: " + this.value);
            return true;
        } else {
            return false;
        }
    }

    public boolean getEmpty() {
        return this.empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}

import java.util.Random;

public class Board {

    Space[][] spaces;
    Random rand = new Random();

    public Board() {
        this.spaces = generateBoard();
    }

    public Space[][] getSpaces() {
        return this.spaces;
    }

    public void setSpaces(Space[][] spaces) {
        if (spaces.length != 5) {
            System.out.println("Invalid board layout");
            return;
        } else {
            for (int i = 0; i < 5; i++) {
                if (spaces[i].length != 5) {
                    System.out.println("Invalid board layout");
                    return;
                }
            }

            for (int i2 = 0; i2 < 5; i2++) {
                for (int j = 0; j < 5; j++) {
                    this.spaces[i2][j] = spaces[i2][j];
                }
            }
        }
    }

    public Space[][] generateBoard() {
        Space[][] newSpaces = new Space[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                newSpaces[i][j] = new Space(i, j, rand.nextInt(3) + 1);
            }
        }
        return newSpaces;
    }

    public String toString() {
        String out = "";
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                out += (this.spaces[i][j].getValue() + " ");
            }
            out += "\n";
        }
        return out;
    }
}

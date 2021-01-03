import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Game {

    Board board = new Board();
    Wizard player1;
    Wizard player2;
    boolean player1Turn = true;
    Scanner scan = new Scanner(System.in);

    /*
    public Game() {
        this.board.spaces[player1.getY()][player1.getX()].setEmpty(false);
        this.board.spaces[player2.getY()][player2.getX()].setEmpty(false);
    }

     */

    public void changeTurn() {
        this.player1Turn = !this.player1Turn;
    }

    public String[] showMoves(Wizard player) {
        int playerX = player.getX();
        int playerY = player.getY();
        int spaceValue = this.board.spaces[playerY][playerX].getValue();
        // up, right, down, left
        int[] movesX = {playerX, playerX + spaceValue, playerX, playerX - spaceValue};
        int[] movesY = {playerY - spaceValue, playerY, playerY + spaceValue, playerY};
        String[] validDirections = {"UP", "RIGHT", "DOWN", "LEFT"};
        String[] out = new String[4];

        for (int i = 0; i < 4; i++) {
            boolean valid = true;
            // Check if move is out of bounds
            if (movesX[i] < 0 || movesX[i] > 4 || movesY[i] < 0 || movesY[i] > 4) {
                valid = false;
                // Check if move is in occupied space
            } else if (!this.board.spaces[movesY[i]][movesX[i]].getEmpty()) {
                valid = false;
                // Check if move is to previous space
            } else if (movesX[i] == player.getPrevX() && movesY[i] == player.getPrevY()) {
                valid = false;
            }

            if (valid) {
                out[i] = validDirections[i];
            }
        }

        ArrayList<String> outList = new ArrayList<String>();
        for (String d : out) {
            if (d != null) {
                outList.add(d);
            }
        }

        return outList.toArray(new String[outList.size()]);
    }

    public boolean castSpell(int x, int y, char spell, Wizard player) {
        if (x < 0 || x > 4 || y < 0 || y > 4) {
            System.out.println("Out of bounds");
            return false;
        } else if (!this.board.spaces[y][x].getEmpty()) {
            System.out.println("Space occupied");
            return false;
        } else if (x == player.getPrevX() && y == player.getPrevY()) {
            System.out.println("Cannot cast a spell on the space that you moved from");
            return false;
        }

        if (spell == '+') {
            this.board.spaces[y][x].incValue();
        } else {
            if (this.board.spaces[y][x].getValue() == 0) {
                System.out.println("Cannot have a negative space value");
                return false;
            } else {
                this.board.spaces[y][x].decValue();
            }
        }

        return true;
    }

    public boolean playerTurn() {
        Wizard current;
        if (this.player1Turn) {
            current = player1;
            System.out.println("Wizard A's turn!");
        } else {
            current = player2;
            System.out.println("Wizard B's turn!");
        }
        int currentX = current.getX();
        int currentY = current.getY();
        int currentSpaceValue = this.board.spaces[currentY][currentX].getValue();

        // Show board
        printGame();

        // Show available moves
        String[] valid = showMoves(current);
        if (valid.length == 0) {
            System.out.println("No valid moves.");
            if (player1Turn) {
                System.out.println("Wizard B wins!");
            } else {
                System.out.println("Wizard A wins!");
            }
            return false;
        }
        System.out.print("You can move " + currentSpaceValue + " space");
        if (currentSpaceValue != 1) {
            System.out.print("s");
        }
        System.out.print(": ");
        for (int i = 0; i < valid.length; i++) {
            System.out.print(valid[i] + " (" + (i + 1) + ")");
            if (i != valid.length - 1) {
                System.out.print(" , ");
            }
        }
        System.out.print("\n");

        // Move
        System.out.print("Enter move number: ");
        int moveNumber = scan.nextInt();

        while (moveNumber <= 0 || moveNumber > valid.length) {
            System.out.print("Input a valid move number: ");
            moveNumber = scan.nextInt();
        }

        String chosenMove = valid[moveNumber - 1];
        int newX = currentX;
        int newY = currentY;

        switch (chosenMove) {
            case "UP":
                newY -= currentSpaceValue;
                break;
            case "RIGHT":
                newX += currentSpaceValue;
                break;
            case "DOWN":
                newY += currentSpaceValue;
                break;
            case "LEFT":
                newX -= currentSpaceValue;
                break;
        }

        current.move(newX, newY);
        this.board.spaces[currentY][currentX].setEmpty(true);
        this.board.spaces[newY][newX].setEmpty(false);
        // Update board
        printGame();
        // Spell
        boolean validSpell = false;
        int targetX;
        int targetY;
        char spell;

        do {
            System.out.print("Select target row: (1 - 5) ");
            targetY = scan.nextInt();
            System.out.print("Select target column: (1 - 5) ");
            targetX = scan.nextInt();
            System.out.print("Select spell: (+, -) ");
            spell = scan.next().charAt(0);
            validSpell = castSpell(targetX - 1, targetY - 1, spell, current);
        } while (!validSpell);

        // Switch turns
        changeTurn();
        return true;
    }

    public boolean computerTurn() {
        Wizard current;
        Random rand = new Random();

        if (this.player1Turn) {
            current = player1;
            System.out.println("Wizard A's turn!");
        } else {
            current = player2;
            System.out.println("Wizard B's turn!");
        }

        printGame();

        int currentX = current.getX();
        int currentY = current.getY();
        int currentSpaceValue = this.board.spaces[currentY][currentX].getValue();

        String[] valid = showMoves(current);
        if (valid.length == 0) {
            System.out.println("No valid moves.");
            if (player1Turn) {
                System.out.println("Wizard B wins!");
            } else {
                System.out.println("Wizard A wins!");
            }
            return false;
        }

        String chosenMove = valid[rand.nextInt(valid.length)];
        System.out.println("Moved " + chosenMove);
        int newX = currentX;
        int newY = currentY;

        switch (chosenMove) {
            case "UP":
                newY -= currentSpaceValue;
                break;
            case "RIGHT":
                newX += currentSpaceValue;
                break;
            case "DOWN":
                newY += currentSpaceValue;
                break;
            case "LEFT":
                newX -= currentSpaceValue;
                break;
        }

        current.move(newX, newY);
        this.board.spaces[currentY][currentX].setEmpty(true);
        this.board.spaces[newY][newX].setEmpty(false);
        // Spell
        boolean validSpell = false;
        char[] spellList = new char[]{'+', '-'};
        int targetX;
        int targetY;
        char spell;

        do {
            targetY = rand.nextInt(5);
            targetX = rand.nextInt(5);
            spell = spellList[rand.nextInt(2)];
            validSpell = castSpell(targetX - 1, targetY - 1, spell, current);
        } while (!validSpell);

        System.out.println(spell + " spell on square (" + targetX + ", " + targetY + ")");

        changeTurn();

        return true;
    }

    public boolean gameLoop() {
        if (this.player1Turn) {
            if (this.player1.getIsHuman()) {
                return playerTurn();
            } else {
                return computerTurn();
            }
        } else {
            if (this.player2.getIsHuman()) {
                return playerTurn();
            } else {
                return computerTurn();
            }
        }
    }

    public void printGame() {
        System.out.println("----------------");
        // rows
        for (int i = 0; i < 5; i++) {
            // Wizard positions
            for (int j = 0; j < 5; j++) {
                if (this.board.spaces[i][j].getEmpty()) {
                    System.out.print("|  ");
                } else {
                    if (i == player1.getY() && j == player1.getX()) {
                        System.out.print("|A ");
                    } else {
                        System.out.print("|B ");
                    }
                }
            }
            System.out.print("|\n");

            // Space values
            for (int k = 0; k < 5; k++) {
                System.out.print("| " + this.board.spaces[i][k].getValue());
            }
            System.out.print("|\n");
            System.out.println("----------------");
        }
    }

    public static void main(String[] args) {
        Game testGame = new Game();
        Scanner gameScan = new Scanner(System.in);

        System.out.println("Welcome to Wizard Battle!");
        System.out.print("How many human players are there? (0 - 2) ");
        int playerCount = gameScan.nextInt();

        while (playerCount < 0 || playerCount > 2) {
            System.out.print("Please input a valid player count: ");
            playerCount = gameScan.nextInt();
        }

        switch (playerCount) {
            case 0:
                testGame.player1 = new Wizard(4, 0, true, false);
                testGame.player2 = new Wizard(0, 4, false, false);
                break;
            case 1:
                testGame.player1 = new Wizard(4, 0, true, true);
                testGame.player2 = new Wizard(0, 4, false, false);
                break;
            case 2:
                testGame.player1 = new Wizard(4, 0, true, true);
                testGame.player2 = new Wizard(0, 4, false, true);
                break;
        }

        testGame.board.spaces[4][0].setEmpty(false);
        testGame.board.spaces[0][4].setEmpty(false);

        boolean ongoing = true;
        while (ongoing) {
            ongoing = testGame.gameLoop();
        }
        // testGame.playerTurn(true);
    }
}

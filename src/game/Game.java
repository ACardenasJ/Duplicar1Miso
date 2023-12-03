package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Game {

    private static final String EMPTY_CELL = "⬜";
    private static final String OBSTACLE_CELL = "⬛";
    private static final String PLAYER_CAR = "🚗";
    private static final String ENEMY_CAR = "🚙";

    private String[][] board;
    private int playerColumn;

    public Game() {
        initializeBoard();
        playerColumn = 3; // Initial position of the player's car
    }

    private void initializeBoard() {
        board = new String[][]{
                {EMPTY_CELL, EMPTY_CELL, EMPTY_CELL, EMPTY_CELL, EMPTY_CELL, EMPTY_CELL, EMPTY_CELL, EMPTY_CELL},
                {EMPTY_CELL, EMPTY_CELL, EMPTY_CELL, OBSTACLE_CELL, OBSTACLE_CELL, OBSTACLE_CELL, EMPTY_CELL, EMPTY_CELL},
                {EMPTY_CELL, EMPTY_CELL, EMPTY_CELL, EMPTY_CELL, EMPTY_CELL, EMPTY_CELL, OBSTACLE_CELL, EMPTY_CELL},
                {EMPTY_CELL, EMPTY_CELL, EMPTY_CELL, EMPTY_CELL, EMPTY_CELL, EMPTY_CELL, OBSTACLE_CELL, EMPTY_CELL},
                {EMPTY_CELL, EMPTY_CELL, EMPTY_CELL, EMPTY_CELL, EMPTY_CELL, OBSTACLE_CELL, EMPTY_CELL, EMPTY_CELL},
                {EMPTY_CELL, EMPTY_CELL, EMPTY_CELL, EMPTY_CELL, EMPTY_CELL, EMPTY_CELL, OBSTACLE_CELL, EMPTY_CELL},
                {EMPTY_CELL, EMPTY_CELL, EMPTY_CELL, EMPTY_CELL, EMPTY_CELL, EMPTY_CELL, OBSTACLE_CELL, EMPTY_CELL},
                {EMPTY_CELL, EMPTY_CELL, EMPTY_CELL, OBSTACLE_CELL, OBSTACLE_CELL, OBSTACLE_CELL, EMPTY_CELL, EMPTY_CELL}
        };
    }

    private void printBoard() {
        for (String[] row : board) {
            for (String cell : row) {
                System.out.print(" " + cell + " ");
            }
            System.out.println();
        }
    }

    private void clearRow(int row) {
        for (int i = 0; i < board[row].length; i++) {
            board[row][i] = EMPTY_CELL;
        }
    }

    private void moveObstaclesDown() {
        for (int i = board.length - 1; i > 0; i--) {
            board[i] = board[i - 1].clone();
        }
        clearRow(0);
    }

    private void placeObstacles() {
        board[1][3] = OBSTACLE_CELL;
        board[1][4] = OBSTACLE_CELL;
        board[1][5] = OBSTACLE_CELL;
        board[2][2] = OBSTACLE_CELL;
        board[2][5] = OBSTACLE_CELL;
        board[3][6] = OBSTACLE_CELL;
        board[4][5] = OBSTACLE_CELL;
        board[5][4] = OBSTACLE_CELL;
        board[6][3] = OBSTACLE_CELL;
        board[7][2] = OBSTACLE_CELL;
        board[7][3] = OBSTACLE_CELL;
        board[7][4] = OBSTACLE_CELL;
        board[7][5] = OBSTACLE_CELL;
        board[7][6] = OBSTACLE_CELL;
    }

    public void play() {
        boolean playing = true;
        Random random = new Random();

        try {
            while (playing) {
                board[7][playerColumn] = EMPTY_CELL;
                int newCarColumn = random.nextInt(8);
                board[0][newCarColumn] = ENEMY_CAR;
                printBoard();
                String key = getUserInput();

                if (key.equals("q")) {
                    playing = false;
                    break;
                } else if (key.equals("a") && playerColumn > 0) {
                    playerColumn -= 1;
                } else if (key.equals("d") && playerColumn < 7) {
                    playerColumn += 1;
                }
                if (board[7][playerColumn].equals(ENEMY_CAR) || board[6][playerColumn].equals(ENEMY_CAR)) {
                    playing = false;
                    System.out.println("Perdiste!");
                    moveObstaclesDown();
                    clearRow(0);
                }
                Thread.sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getUserInput() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.placeObstacles();
        game.play();
    }
}
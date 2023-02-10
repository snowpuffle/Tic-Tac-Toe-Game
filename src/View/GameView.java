package View;

import java.util.Scanner;

import Controller.GameController;
import Model.GameBoard;
import Model.GameCell;
import Model.Player;

public class GameView {
    Scanner scanner;
    private GameController gameController;

    // Class Constructor
    public GameView() {
        this.scanner = new Scanner(System.in);
    }

    // Initialize Game View
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    // Print Game Board
    public void printGameBoard(GameCell[][] gameBoard) {
        System.out.println("\n~ Game Board ~ ");

        // Create the Top, Bottom, & Mid-Boundary
        StringBuilder topBottomBoundary = new StringBuilder("");
        for (int i = 0; i < gameBoard.length; ++i) {
            topBottomBoundary.append("+-----");
        }
        topBottomBoundary.append("+");

        // Set the Position Count
        int count = 1;

        // Loop through every Row in Game Board
        for (GameCell[] row : gameBoard) {

            // Display the Top and Bottom-Boundaries
            System.out.println(topBottomBoundary);

            // Loop through every Game Cell in Row
            for (GameCell cell : row) {

                // Display Position Number if no Checker is placed
                // Else, display the Checker Mark
                if (cell.getChecker() == null) {
                    System.out.print("|  " + count + "  ");
                } else {
                    System.out.print("|  " + cell.getChecker().toString() + "  ");
                }
                count++;
            }
            System.out.println("|");
        }

        // Display the Bottom Boundary
        System.out.println(topBottomBoundary);
    }

    // Display Game Introduction
    public void displayGameIntro() {
        // Display welcome introduction
        System.out.println("\n~ Welcome to Tic-Tac-Toe Game! ~\n");
    }

    // Create New Players and Game
    public String[] displayGetPlayers() {

        // Get the Player's names
        System.out.print("Enter Name of Player One: ");
        String playerOneName = scanner.nextLine();
        System.out.print("Enter Name of Player Two: ");
        String playerTwoName = scanner.nextLine();

        // Combine Player's names to return
        String[] namesOfPlayers = new String[] { playerOneName, playerTwoName };

        return namesOfPlayers;
    }

    // Display Get Play Turn
    public int displayGetPlayTurn(Player playerTurn, int boardSize) {
        try {
            System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~");
            System.out.print("It is " + playerTurn.getPlayerName() + "'s Turn. ");

            // Get row number from Player
            int boardMaxNum = boardSize * boardSize;
            System.out.print("Enter Position Number [1-" + boardMaxNum + "]: ");
            int position = scanner.nextInt();

            return position;

        } catch (Exception e) {
            System.out.println("ERROR: Invalid Input.");
        }
        return -1;
    }

    // Display Play Turn Response
    public void displayPlayTurnResponse(String response) {
        System.out.println("ERROR: " + response);
    }

    // Display Game Result
    public void displayGameResult(String response) {
        System.out.println("\n~ End of Game ~");
        System.out.println(response);
    }
}

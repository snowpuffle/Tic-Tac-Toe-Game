package View;

import java.util.Scanner;

import Controller.GameController;
import Model.Player;
import Model.PlayerType;

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

    // Create New Players and Game
    public Player[] displayCreatePlayers() {

        // Introduction
        System.out.println("~ Tic-Tac-Toe Game ~");

        // Get the Player's Names
        System.out.print("Name of Player One: ");
        String playerOneName = scanner.nextLine();
        System.out.print("Name of Player Two: ");
        String playerTwoName = scanner.nextLine();

        // Create the Players
        Player[] playerList = new Player[2];
        playerList[0] = new Player(playerOneName, PlayerType.PLAYER_ONE);
        playerList[1] = new Player(playerTwoName, PlayerType.PLAYER_TWO);

        return playerList;
    }
}

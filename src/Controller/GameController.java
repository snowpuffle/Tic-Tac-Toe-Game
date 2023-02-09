package Controller;

import Model.Checker;
import Model.GameBoard;
import Model.Player;
import Model.PlayerOneChecker;
import Model.PlayerTwoChecker;
import Model.PlayerType;
import View.GameView;

public class GameController {

    GameBoard gameBoard;
    GameView gameView;
    Player playerOne;
    Player playerTwo;
    boolean turn;
    int boardSize;

    public GameController(GameBoard gameBoard, GameView gameView) {
        this.gameBoard = gameBoard;
        this.gameView = gameView;
        this.boardSize = gameBoard.getBoardSize();
    }

    // 1. Prepare the Game
    public void prepareGame() {
        gameView.displayGameIntro();

        // Get names of the Players
        String[] namesOfPlayers = gameView.displayGetPlayers();

        // Set the Players
        this.playerOne = new Player(namesOfPlayers[0], PlayerType.PLAYER_ONE);
        this.playerTwo = new Player(namesOfPlayers[1], PlayerType.PLAYER_TWO);

        // Randomize the Player's turn
        randomizePlayers();
    }

    // 2. Play the Game
    public void playGame() {

        while (true) {
            gameView.printGameBoard(gameBoard.getGameBoard());

            playTurn();

            

        }
    }

    // Play Turn
    public void playTurn() {

        // Get Player's Turn
        Player playerTurn = getTurnPlayer();

        // Display Play Turn
        gameView.displayPlayTurn(playerTurn, boardSize);
    }

    public String playTurnHelper(Player playerTurn, int position) {
        // Check if the position is valid
        boolean isValidPosition = checkValidPosition(position);
        boolean isCheckerPlaced = false;

        if (!isValidPosition) {
            return "ERROR: Position is Invalid.";
        } else {
            // Place Checker based on Player type
            if (playerTurn.getPlayerType() == PlayerType.PLAYER_ONE) {
                PlayerOneChecker playerOneChecker = new PlayerOneChecker(playerTurn);
                isCheckerPlaced = placeChecker(playerOneChecker, position);
            } else {
                PlayerTwoChecker playerTwoChecker = new PlayerTwoChecker(playerTurn);
                isCheckerPlaced = placeChecker(playerTwoChecker, position);
            }

            if (isCheckerPlaced) {
                return "SUCCESS: Checker is Placed.";
            } else {
                return "ERROR: Checker Cannot be Placed.";
            }
        }
    }

    // Verify Position Number on Game Board
    public boolean checkValidPosition(int position) {
        // Check if Position is Valid
        if (position > 0 && position <= (boardSize * boardSize)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean placeChecker(Checker checker, int position) {
        int count = 1;
        boolean isCheckerPlaced = false;

        // Loop through every Row
        for (int i = 0; i < gameBoard.getGameBoard().length; ++i) {

            // Loop through every Column
            for (int j = 0; j < gameBoard.getGameBoard()[i].length; ++j) {

                // Set Checker if position matches count
                if (count == position) {
                    gameBoard.getGameBoard()[i][j].setChecker(checker);
                    isCheckerPlaced = true;
                    break;
                }
                ++count;
            }

            if (isCheckerPlaced) {
                break;
            }
        }

        return isCheckerPlaced;
    }

    // Randomize "Who Goes First?"
    public void randomizePlayers() {
        // Use Math Random
        double randomNumber = Math.random();
        Player tempPlayer;

        // Assign Who Goes First Based on Random Number
        if (randomNumber >= 0 && randomNumber < 0.5) {
            tempPlayer = playerOne;
            playerOne = playerTwo;
            playerTwo = tempPlayer;
        }
    }

    // End and Change Turn
    public void endTurn() {
        turn = !turn;
    }

    // Get Turn Player
    public Player getTurnPlayer() {
        if (turn) {
            return playerTwo;
        } else {
            return playerOne;
        }
    }

    public void checkForWin() {

    }
}
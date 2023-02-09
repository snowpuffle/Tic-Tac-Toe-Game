package Controller;

import Model.Checker;
import Model.GameBoard;
import Model.Player;
import Model.PlayerOneChecker;
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
        gameView.printGameBoard(gameBoard.getGameBoard());
    }

    // Play Turn
    public void playTurn() {

        // Get Player's Turn
        Player playerTurn = getTurnPlayer();

        // Display Play Turn
        gameView.displayPlayTurn(playerTurn, boardSize);
    }

    public String playTurnHelper(Player playerTurn, int position) {

        boolean isValidPosition = checkValidPosition(position);
        boolean isCheckerPlaced = false;

        if (!isValidPosition) {
            return "ERROR: Position is Invalid.";
        } else {
            if (playerTurn.getPlayerType() == PlayerType.PLAYER_ONE) {
                PlayerOneChecker playerOneChecker = new PlayerOneChecker(playerTurn);
                isCheckerPlaced = placeChecker(playerOneChecker, position);
            } else {

            }
        }

        return "";
    }

    public boolean checkValidPosition(int position) {
        if (position < 1 || position > (boardSize * boardSize)) {
            return false;
        }
        return true;
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
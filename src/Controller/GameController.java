package Controller;

import Model.Checker;
import Model.GameBoard;
import Model.GameCell;
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

    /* ~ Main Game Play Methods ~ */
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

        // Display the Player's Checkers
        
    }

    // 2. Play the Game
    public void playGame() {

        boolean isEndGame = false;

        while (true) {

            // Is the Game Over?
            if (isEndGame()) {
                isEndGame = true;
                break;
            }

            // Print the Game Board
            gameView.printGameBoard(gameBoard.getGameBoard());

            // Turn Player Plays Turn
            playTurn();
        }

        // End of Game
        if (isEndGame) {
            System.out.println("\nRESULT: It's a Tie!");
        } else {
            gameView.printGameBoard(gameBoard.getGameBoard());
            System.out.println("\nRESULT: " + getTurnPlayer().getPlayerName() + " Has Won!");
        }

    }

    /* ~ Game Play Helper Methods ~ */
    // Play Turn
    public void playTurn() {

        // Get Player's Turn
        Player playerTurn = getTurnPlayer();

        // Display Play Turn
        gameView.displayPlayTurn(playerTurn, boardSize);
    }

    // Play Turn Helper
    public String playTurnHelper(Player playerTurn, int position) {

        // Check if the Position is Valid
        boolean isValidPosition = checkValidPosition(position);
        boolean isCheckerPlaced = false;

        // Continue if Position is Valid
        if (!isValidPosition) {
            return "ERROR: Position is Invalid.";
        } else {
            // Place Checker based on Player Type
            if (playerTurn.getPlayerType() == PlayerType.PLAYER_ONE) {
                PlayerOneChecker playerOneChecker = new PlayerOneChecker(playerTurn);
                isCheckerPlaced = placeChecker(playerOneChecker, position);
            } else {
                PlayerTwoChecker playerTwoChecker = new PlayerTwoChecker(playerTurn);
                isCheckerPlaced = placeChecker(playerTwoChecker, position);
            }

            // Return Response
            if (isCheckerPlaced) {
                endTurn();
                return "SUCCESS: Checker is Placed.";
            } else {
                return "ERROR: Checker Cannot be Placed. Try Again!";
            }
        }
    }

    // Place Checker on the Game Board
    public boolean placeChecker(Checker checker, int position) {
        int count = 1;
        boolean isCheckerPlaced = false;

        // Loop through every Row
        for (int i = 0; i < gameBoard.getGameBoard().length; ++i) {

            // Loop through every Column
            for (int j = 0; j < gameBoard.getGameBoard()[i].length; ++j) {

                // Get the current Game Cell
                GameCell gameCell = gameBoard.getGameBoard()[i][j];

                // Set Checker if the Position matches the Count
                if (count == position && gameCell.getChecker() == null) {
                    // Set Checker if Position is Null
                    gameCell.setChecker(checker);
                    isCheckerPlaced = true;
                    break;
                }
                ++count;
            }

            // Break Loop if Checker is placed
            if (isCheckerPlaced) {
                break;
            }
        }

        return isCheckerPlaced;
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

    // Check if a Player Wins
    public void checkForWin() {

    }

    // Check if Game has Ended
    public boolean isEndGame() {

        boolean isGameBoardFull = true;

        // Loop through every Row
        for (int i = 0; i < gameBoard.getGameBoard().length; ++i) {

            // Loop through every Column
            for (int j = 0; j < gameBoard.getGameBoard()[i].length; ++j) {

                // Get the current Game Cell
                GameCell gameCell = gameBoard.getGameBoard()[i][j];

                // Check if Game Cell has a Checker
                if (gameCell.getChecker() == null) {
                    // Set Checker if Position is Null
                    isGameBoardFull = false;
                    break;
                }
            }

            // Break Loop if Checker is placed
            if (!isGameBoardFull) {
                break;
            }
        }
        return isGameBoardFull;
    }

    /* ~ Utility Methods ~ */
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

    // Verify Position Number on Game Board
    public boolean checkValidPosition(int position) {
        // Check if Position is Valid
        if (position > 0 && position <= (boardSize * boardSize)) {
            return true;
        } else {
            return false;
        }
    }

}
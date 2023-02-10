package Controller;

import Model.Checker;
import Model.GameBoard;
import Model.GameCell;
import Model.Player;
import Model.PlayerOneChecker;
import Model.PlayerTwoChecker;
import Model.PlayerType;
import View.GameView;

/* Game Controller Class: Main Controller of Game */
public class GameController {

    GameBoard gameBoard;
    GameView gameView;

    Player playerOne;
    Player playerTwo;
    Player winPlayer;

    boolean turn;
    int boardSize;
    int numberOfCheckersPlaced;

    // Class Constructor
    public GameController(GameBoard gameBoard, GameView gameView) {
        this.winPlayer = null;
        this.numberOfCheckersPlaced = 0;
        this.gameBoard = gameBoard;
        this.gameView = gameView;
        this.boardSize = gameBoard.getBoardSize();
    }

    /* ~ Main Game Play Methods ~ */
    // 1. Prepare the Game
    public void prepareGame() {

        // Display Game Introduction
        gameView.displayGameIntro();

        // Get Names of the Players
        String[] namesOfPlayers = gameView.displayGetPlayers();

        // Set the Players
        this.playerOne = new Player(namesOfPlayers[0], PlayerType.PLAYER_ONE);
        this.playerTwo = new Player(namesOfPlayers[1], PlayerType.PLAYER_TWO);

        // Randomize the Player's Turn
        randomizePlayers();
    }

    // 2. Play the Game
    public void playGame() {

        // Set Flag
        boolean isEndGame = false;

        // Loop until Game is Over
        while (true) {
            // Is the Game Over?
            if (isEndGame()) {
                isEndGame = true;
                break;
            }

            // Print the Game Board
            gameView.printGameBoard(gameBoard.getGameBoard());

            // Get Turn Player
            Player turnPlayer = getTurnPlayer();

            // Turn Player plays Turn
            boolean isCheckerPlaced = playTurn(turnPlayer);

            // Increment number of Checkers and end Turn if there is No Winner
            // Break and end Game if there is a Winner
            if (isCheckerPlaced && winPlayer == null) {
                numberOfCheckersPlaced++;
                endTurn();
            } else if (isCheckerPlaced && winPlayer != null) {
                break;
            }
        }

        // Display End of Game
        if (isEndGame) {
            gameView.displayGameResult("It's a Tie!");
        } else {
            gameView.printGameBoard(gameBoard.getGameBoard());
            gameView.displayGameResult(winPlayer.getPlayerName() + " Has Won!");
        }
    }

    /* ~ Game Play Helper Methods ~ */
    // Play Turn
    public boolean playTurn(Player turnPlayer) {

        // Display Play Turn & Get Position
        int position = gameView.displayGetPlayTurn(turnPlayer, boardSize);

        // Check if the Position is Valid
        boolean isValidPosition = checkValidPosition(position);
        int[] positionOfChecker = convertPosition(position);

        // Set Flag
        boolean isCheckerPlaced = false;

        // Continue if the Position is Valid
        if (!isValidPosition) {
            // Send Error Response to Game View
            gameView.displayPlayTurnResponse("Input Position is Invalid.");

        } else if (positionOfChecker[0] == -1 || positionOfChecker[1] == -1) {
            // Send Error Response to Game View
            gameView.displayPlayTurnResponse("Problem with Checker Position.");

        } else {
            // Create Checker based on Player Type
            Checker checker = createChecker(turnPlayer);

            // Place the Checker onto the GameBoard
            isCheckerPlaced = placeChecker(checker, positionOfChecker[0], positionOfChecker[1]);

            // Continue only if Checker is Placed
            if (!isCheckerPlaced) {
                gameView.displayPlayTurnResponse("Checker Cannot be Placed. Try Again!");
            } else {
                // Check if the Placed Checker is the Winning Move
                boolean isWinningChecker = checkForWin(positionOfChecker[0], positionOfChecker[1], turnPlayer);

                // Set Winning Player
                if (isWinningChecker) {
                    winPlayer = turnPlayer;
                } else {
                    winPlayer = null;
                }
            }
        }
        return isCheckerPlaced;
    }

    // Create and Return Checker based on the Turn Player
    public Checker createChecker(Player turnPlayer) {

        // Create and Return Checker based on the Turn Player
        if (turnPlayer.getPlayerType() == PlayerType.PLAYER_ONE) {
            PlayerOneChecker playerOneChecker = new PlayerOneChecker(turnPlayer);
            return playerOneChecker;
        } else {
            PlayerTwoChecker playerTwoChecker = new PlayerTwoChecker(turnPlayer);
            return playerTwoChecker;
        }
    }

    // Place Checker on the Game Board
    public boolean placeChecker(Checker checker, int rowNumber, int columnNumber) {

        // Set Flag
        boolean isCheckerPlaced = false;

        // Get Game Cell based on the Position
        GameCell gameCell = gameBoard.getGameBoard()[rowNumber][columnNumber];

        // Set Checker only if the Checker on Game Cell is Null
        if (gameCell.getChecker() == null) {
            // Set Checker if Position is Null
            gameCell.setChecker(checker);
            isCheckerPlaced = true;
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

    // Check if Board Game is Full
    public boolean isEndGame() {

        // Calculate the Maximum number of Checkers possible for the Game Board
        int maxNumOfCheckers = boardSize * boardSize;

        // End the Game if Game Board is Full
        if (numberOfCheckersPlaced >= maxNumOfCheckers) {
            return true;
        } else {
            return false;
        }
    }

    /* ~ Check for Win Methods */
    // Check for the Winning Move
    public boolean checkForWin(int rowNumber, int columnNumber, Player turnPlayer) {
        // True if a Win is Detected
        boolean win = false;

        // Check for a Win in 4 Different Directions
        if (checkWinVertical(rowNumber, columnNumber, turnPlayer)) {
            win = true;
        } else if (checkWinHorizontal(rowNumber, columnNumber, turnPlayer)) {
            win = true;
        } else if (checkWinLeftRightDiagonal(rowNumber, columnNumber, turnPlayer)) {
            win = true;
        } else if (checkWinRightLeftDiagonal(rowNumber, columnNumber, turnPlayer)) {
            win = true;
        }
        return win;
    }

    // Check for Win for Each Verticals
    public boolean checkWinVertical(int rowNumber, int columnNumber, Player turnPlayer) {
        // Player Wins if Count = boardSize
        int count = 0;

        // Top to Bottom: Increment Row #, Same Column #
        for (int i = 0; i < boardSize; ++i) {

            GameCell gameCell = gameBoard.getGameBoard()[i][columnNumber];

            // Break if there is a Space Between the Checkers
            if (gameCell == null) {
                break;
            }

            if (gameCell.getChecker() != null) {
                // Increment Count if the Checker Belongs to the Same Player
                if (gameCell.getChecker().getPlayer() == turnPlayer) {

                    if (count == boardSize - 1) {
                        return true;
                    }
                    count++;
                } else {
                    break;
                }
            }
        }

        return false;
    }

    // Check for Win for Each Horizontals
    public boolean checkWinHorizontal(int rowNumber, int columnNumber, Player turnPlayer) {
        // Player Wins if Count = boardSize
        int count = 0;

        // Right to Left: Same Row #, Decrement Column #
        for (int i = 0; i < boardSize; ++i) {

            GameCell gameCell = gameBoard.getGameBoard()[rowNumber][i];

            // Break if there is a Space Between the Checkers
            if (gameCell == null) {
                break;
            }

            // Increment Count if the Checker Belongs to the Same Player
            if (gameCell.getChecker() != null) {
                // Increment Count if the Checker Belongs to the Same Player
                if (gameCell.getChecker().getPlayer() == turnPlayer) {

                    if (count == boardSize - 1) {
                        System.out.println("WIN! " + turnPlayer.getPlayerName());
                        return true;
                    }
                    count++;
                } else {
                    break;
                }
            }
        }
        return false;
    }

    // Check for Win Left-to-Right Diagonal
    public boolean checkWinLeftRightDiagonal(int rowNumber, int columnNumber, Player turnPlayer) {
        // Player Wins if Count = boardSize
        int count = 0;

        // Top to Bottom Right: Increment Row #, Increment Column #
        for (int i = 0, j = 0; i < boardSize
                && j < boardSize; i++, j++) {

            GameCell gameCell = gameBoard.getGameBoard()[i][j];

            // Break if there is a Space Between the Checkers
            if (gameCell == null) {
                break;
            }

            // Increment Count if the Checker Belongs to the Same Player
            if (gameCell.getChecker() != null) {
                // Increment Count if the Checker Belongs to the Same Player
                if (gameCell.getChecker().getPlayer() == turnPlayer) {

                    if (count == boardSize - 1) {
                        return true;
                    }
                    count++;
                } else {
                    break;
                }
            }
        }
        return false;
    }

    // Check for Win Right-to-Left Diagonal
    public boolean checkWinRightLeftDiagonal(int rowNumber, int columnNumber, Player turnPlayer) {
        // Player Wins if Count = boardSize
        int count = 0;

        // Top to Bottom Left: Increment Row #, Decrement Column #
        for (int i = 0, j = boardSize - 1; i < boardSize && j >= 0; i++, j--) {

            GameCell gameCell = gameBoard.getGameBoard()[i][j];

            // Break if there is a Space Between the Checkers
            if (gameCell == null) {
                break;
            }

            // Increment Count if the Checker Belongs to the Same Player
            if (gameCell.getChecker() != null) {
                // Increment Count if the Checker Belongs to the Same Player
                if (gameCell.getChecker().getPlayer() == turnPlayer) {

                    if (count == boardSize - 1) {
                        return true;
                    }
                    count++;
                } else {
                    break;
                }
            }
        }
        return false;
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

    // Convert Position to Row & Column Numbers
    public int[] convertPosition(int position) {

        // Set Flag and initialize Checker Position
        int count = 1;
        int[] positionOfChecker = new int[] { -1, -1 };

        // Loop through every Row on Game Board
        for (int i = 0; i < gameBoard.getGameBoard().length; ++i) {

            // Loop through every Column on Game Board
            for (int j = 0; j < gameBoard.getGameBoard()[i].length; ++j) {

                // Set Position based on Count
                if (count == position) {
                    positionOfChecker[0] = i;
                    positionOfChecker[1] = j;
                }
                ++count;
            }
        }
        return positionOfChecker;
    }

}
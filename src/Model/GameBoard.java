package Model;

/* ~ Game Board Class */
public class GameBoard {
    GameCell[][] gameBoard;
    private final static int boardSize = 3;     // Board Size must be odd

    // Class Constructor; Initialize GameBoard
    public GameBoard() {
        this.gameBoard = new GameCell[boardSize][boardSize];

        // Initialize an empty game board
        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                gameBoard[i][j] = new GameCell();
            }
        }
    }

    /* ~ Getter & Setter Methods ~ */
    public GameCell[][] getGameBoard() {
        return gameBoard;
    }

    public int getBoardSize() {
        return boardSize;
    }
}

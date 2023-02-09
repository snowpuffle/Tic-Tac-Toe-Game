package Model;

/* ~ Game Board Class */
public class GameBoard {
    GameCell[][] gameBoard;
    int boardSize;

    public GameBoard(int boardSize) {
        this.boardSize = boardSize;
        initializeGameBoard();
    }

    public void initializeGameBoard() {
        this.gameBoard = new GameCell[boardSize][boardSize];

        // Initialize an empty game board
        for (int i = 0; i < gameBoard.length; ++i) {
            for (int j = 0; j < gameBoard[0].length; ++j) {
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

package Model;

/* ~ Game Board Class */
public class GameBoard {
    GameCell[][] gameBoard;
    Player playerOne;
    Player playerTwo;

    public GameBoard(int numberOfRows, int numberOfColumns) {
        // Create 3 x 3 Array of Game Cells
        this.gameBoard = new GameCell[numberOfRows][numberOfColumns];
    }

}

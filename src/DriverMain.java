import Controller.GameController;
import Model.GameBoard;
import Model.Player;
import View.GameView;

/* Driver Main Class */
public class DriverMain {
    public static void main(String[] args) {
        // Create & Initialize Game Board
        GameBoard gameBoard = new GameBoard(3, 3);

        // Create & Initialize View
        GameView gameView = new GameView();

        // Create Players from Game View
        Player[] playerList = gameView.displayCreatePlayers();

        // Create & Initialize Game Controller
        GameController gameController = new GameController(gameBoard, playerList[0], playerList[1]);
        gameView.setGameController(gameController);
    }
}

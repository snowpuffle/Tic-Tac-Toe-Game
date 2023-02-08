package Controller;

import Model.GameBoard;
import Model.Player;
import View.GameView;

public class GameController {

    GameBoard gameBoard;
    GameView gameView;
    Player playerOne;
    Player playerTwo;

    public GameController(GameBoard gameBoard, Player playerOne, Player playerTwo) {
        this.gameBoard = gameBoard;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    

}
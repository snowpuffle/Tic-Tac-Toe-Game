package Model;

/* Game Cell Class: A Unit Cell on GameBoard */
public class GameCell {
    private Checker checker;

    // Class Constructor
    public GameCell() {
        this.checker = null;
    }

    /* ~ Getter & Setter Methods ~ */
    public Checker getChecker() {
        return checker;
    }

    public void setChecker(Checker checker) {
        this.checker = checker;
    }
}

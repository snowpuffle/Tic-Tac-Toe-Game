package Model;

/* Game Cell Class: A Unit Cell on GameBoard */
public class GameCell {
    private Checker checker;

    // Class Constructor
    public GameCell(Checker checker) {
        this.setChecker(checker);
    }

    /* ~ Getter & Setter Methods ~ */
    public void setChecker(Checker checker) {
        this.checker = checker;
    }

    public char getChecker() {
        return checker.getCheckerSign();
    }

}

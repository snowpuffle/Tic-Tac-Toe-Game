package Model;

/* ~ Player One Checker Class: Subclass of Checker */
public class PlayerOneChecker extends Checker {

    public PlayerOneChecker(char checkerSign) {
        super(checkerSign);
    }

    public String toString() {
        return "O";
    }
}

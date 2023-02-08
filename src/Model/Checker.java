package Model;

/* ~ Checker Class: X's and O's ~ */
public class Checker {
    private char checkerSign;

    // Class Constructor
    public Checker(char checkerSign) {
        this.checkerSign = checkerSign;
    }

    /* ~ Getter & Setter Methods ~ */
    public void setCheckerSign(char checkerSign) {
        this.checkerSign = checkerSign;
    }

    public char getCheckerSign() {
        return checkerSign;
    }

}

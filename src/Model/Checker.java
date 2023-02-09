package Model;

/* ~ Checker Class: X's and O's ~ */
public class Checker {
    private Player player;

    // Class Constructor
    public Checker(Player player) {
        this.player = player;
    }

    /* ~ Getter & Setter Methods ~ */
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}

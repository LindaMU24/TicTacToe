import java.util.Random;

public class AI extends User {

    public AI() {
        super("Computer", 'O');
    }

    public int makeRandomMove() {
        Random rand = new Random();
        return rand.nextInt(9) + 1;
    }
}

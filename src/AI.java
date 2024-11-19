import java.util.Random;

public class AI extends User {

    public AI() {
        super("Computer", 'O');
    }

    //Computer random move
    public int makeRandomMove() {
        Random rand = new Random();
        return rand.nextInt(9) + 1;
    }
}

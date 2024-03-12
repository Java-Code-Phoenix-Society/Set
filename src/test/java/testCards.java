import org.jcps.SetDeck;

public class testCards {
    public static void main(String[] args) {
        final SetDeck setDeck = new SetDeck();
        setDeck.print();
        System.out.println("---------- Shuffling ---------------");
        setDeck.shuffle();
        setDeck.print();
    }
}

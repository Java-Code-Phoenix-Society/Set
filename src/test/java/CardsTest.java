import org.jcps.SetDeck;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardsTest {
    @Test
    public void cardTest() {
        final SetDeck setDeck = new SetDeck();
        setDeck.print();
        System.out.println("---------- Shuffling ---------------");
        setDeck.shuffle();
        setDeck.print();
        assertEquals(81, setDeck.remaining());
    }
}

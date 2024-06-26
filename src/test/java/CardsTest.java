import dev.jcps.*;
import org.junit.jupiter.api.Test;

import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.*;

public class CardsTest {
    @Test
    public void deckTest() {
        final SetDeck setDeck = new SetDeck();
        setDeck.print();
        System.out.println("----- Create deck and shuffle -----");
        setDeck.shuffle();
        setDeck.print();
        assertEquals(81, setDeck.remaining());
    }

    @Test
    public void boardTest() {
        final SetDeck setDeck = new SetDeck();
        System.out.println("----- Created deck and drawing 1 card -----");
        setDeck.deal();
        System.out.println("----- Drawing 3 cards -----");
        SetCard[] cards = setDeck.deal(3);
        for (SetCard card : cards) {
            card.print();
        }
        assertEquals(77, setDeck.remaining());
    }

    @Test
    public void setTest() {
        final SetDeck setDeck = new SetDeck();
        SetBoard setBoard = new SetBoard(setDeck);
        System.out.println("----- Created deck and drawing 3 cards -----");
        SetCard[] cards = setDeck.deal(12);
        System.out.println(cards[0].completes(cards[5], cards[8]));
        System.out.println("----- Checking for card set -----");
        setBoard.deal();
        setBoard.findSet();
        assertTrue(setBoard.containsSet());
    }

    @Test
    public void drainTest() {
        final SetDeck setDeck = new SetDeck();
        System.out.println("----- Drain deck -----");
        setDeck.deal(81);
        System.out.println("----- Try to draw a card -----");
        setDeck.deal(1);
        assertEquals(0, setDeck.remaining());
    }

    @Test
    public void insertTest() {
        final SetDeck setDeck = new SetDeck();
        System.out.println("----- Insert card into deck -----");
        SetCard card = setDeck.deal();
        setDeck.insert(card);
        setDeck.insert(card);
        assertEquals(81, setDeck.remaining());
    }

    @Test
    public void setGameTest() {
        SetGame setGame = new SetGame();
        setGame.init();
        setGame.updateUI();
        SetBoard board = setGame.getBoard();
        board.deal();
        board.deal(3);
        assertTrue(board.containsSet());
        SetGame.main(new String[]{"T"});
    }

    @Test
    public void setBoardTest() {
        SetBoard t = new SetBoard();
        SetBoard c = new SetBoard(t.getDeck());
        t.deal();
        assertNotEquals(c, t);
    }

    @Test
    public void setBoardTest2() {
        SetBoard t = new SetBoard(new SetDeck(), 78);
        t.reDeal();
        assertNotEquals(78, t.getDeck().remaining());
    }

    @Test
    public void setBoardSlotTest() {
        SetBoard t = new SetBoard(new SetDeck(), 78);
        t.reDeal();
        BoardSlot s = new BoardSlot(t);
        t.grid(0).changeCard(t.getDeck().deal());
        assertNotEquals(t.grid(0).card(), s.card());
    }

    @Test
    public void messageboxTestC() {
        System.out.println("--- Simple MessageBox Test ---");
        MessageBox messageBox = new MessageBox(null, "test", "text for test", 200);
        messageBox.actionPerformed(new ActionEvent(this, MessageBox.BTN_CLOSE, "test"));
        assertEquals("test", messageBox.getTitle());
        messageBox.dispose();
    }

    @Test
    public void messageboxCoverageTest() {
        System.out.println("MessageBox Coverage Tests...");
        MessageBox messageBox = new MessageBox(null, "test", "text for test", 200);
        messageBox.actionPerformed(new ActionEvent(this, 10, "string"));
        assertEquals("test", messageBox.getTitle());
        messageBox.dispose();
        messageBox = new MessageBox(null, "test", "text for test", 200, MessageBox.BTN_OKCANCEL);
        messageBox.actionPerformed(new ActionEvent(this, MessageBox.ID_OK, "string"));
        assertEquals("test", messageBox.getTitle());
        messageBox.dispose();
        messageBox = new MessageBox(null, "test", "text for test", 200, MessageBox.BTN_YESNO);
        messageBox.actionPerformed(new ActionEvent(this, MessageBox.ID_YES, "string"));
        assertEquals("test", messageBox.getTitle());
        messageBox.dispose();
        messageBox = new MessageBox(null, "test", "text for test", 200, MessageBox.BTN_YESNOCANCEL);
        messageBox.actionPerformed(new ActionEvent(this, MessageBox.ID_NO, "string"));
        assertEquals("test", messageBox.getTitle());
        messageBox.dispose();
        messageBox = new MessageBox(null, "test", "text for test", 200, MessageBox.BTN_OKCANCEL);
        messageBox.actionPerformed(new ActionEvent(this, MessageBox.ID_CANCEL, "string"));
        assertEquals("test", messageBox.getTitle());
        messageBox.dispose();
    }
}

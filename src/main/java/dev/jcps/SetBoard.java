package dev.jcps;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class SetBoard extends Container {
    public static int CARD_MIN;
    public static int CARD_MAX;
    public static int CARD_INCREMENT;

    static {
        SetBoard.CARD_MIN = 12;
        SetBoard.CARD_MAX = 15;
        SetBoard.CARD_INCREMENT = 3;
    }

    final private ArrayList<TriggerListener> listeners = new ArrayList<>();
    private final Vector<BoardSlot> grid;
    private final BoardSlot[] extras;
    SetDeck deck;
    Vector<SetCard> selections;
    private BoardSlot[] computerSet;

    public SetBoard() {
        this(4, 3, new SetDeck(), SetBoard.CARD_MAX);
    }

    public SetBoard(final SetDeck setDeck) {
        this(4, 3, setDeck, SetBoard.CARD_MAX);
    }

    public SetBoard(final SetDeck setDeck, final int deckSize) {
        this(4, 3, setDeck, deckSize);
    }

    public SetBoard(final int width, final int height, final SetDeck deck, final int deckSize) {
        this.selections = new Vector<>();
        this.deck = deck;
        if (deckSize >= width * height) {
            SetBoard.CARD_MAX = deckSize;
        }
        if (CARD_MAX < CARD_MIN) {
            CARD_MIN = CARD_MAX;
        }
        this.extras = new BoardSlot[SetBoard.CARD_MAX - SetBoard.CARD_MIN];
        this.computerSet = new BoardSlot[3];
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        this.setLayout(new GridBagLayout());
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        this.grid = new Vector<>();
        for (int i = 0; i < SetBoard.CARD_MIN; ++i) {
            this.grid.addElement(new BoardSlot(this));
            gridBagConstraints.gridx = i / height;
            gridBagConstraints.gridy = i % 3;
            this.add(this.grid(i), gridBagConstraints);
        }
        for (int j = 0; j < this.extras.length; ++j) {
            gridBagConstraints.gridx = (SetBoard.CARD_MIN + j) / height;
            gridBagConstraints.gridy = (SetBoard.CARD_MIN + j) % height;
            this.add(this.extras[j] = new BoardSlot(this), gridBagConstraints);
        }
    }

    public BoardSlot grid(final int index) {
        return this.grid.elementAt(index);
    }

    public void deal() {
        for (int i = 0; i < this.grid.size(); ++i) {
            if (this.grid(i).card == null && this.deck.remaining() > 0) {
                this.grid(i).changeCard(this.deck.deal());
            }
        }
        while (!this.containsSet() && this.deck.remaining() >= CARD_INCREMENT && this.grid.size() < CARD_MAX) {
            int size = this.grid.size();
            int end = size - CARD_MIN + CARD_INCREMENT;

            for (int n3 = size - CARD_MIN; n3 < end; n3++) {
                this.grid.addElement(this.extras[n3]);
                this.deal(CARD_MIN + n3);
            }
        }
        if (!this.containsSet()) {
            if (this.grid.size() >= SetBoard.CARD_MAX) {
                new MessageBox(new JFrame(), "Please deal again.",
                        "Maximum number of cards on board-- no set in " + this.grid.size() +
                                " cards!  Please deal again.", 170, MessageBox.BTN_CLOSE);
                //System.out.println("Maximum number of cards on board-- no set in " + this.grid.size() + " cards!  Please deal again.");
            } else {
                new MessageBox(new JFrame(), "Game Over", "Game over.  Please deal again.", 200);
                //System.out.println("Game over.  Please deal again.");
            }
        }
        this.computerSet = this.findSet();
        this.selectNone();
    }

    public void deal(final int n) {
        if (this.deck.remaining() > 0) {
            this.grid(n).changeCard(this.deck.deal());
        }
    }

    public void reDeal() {
        this.crop(SetBoard.CARD_MIN);
        for (int i = 0; i < this.grid.size(); ++i) {
            this.grid(i).changeCard(null);
        }
        this.deal();
    }

    public int countCards() {
        int n = 0;
        for (int i = 0; i < this.grid.size(); ++i) {
            if (this.grid(i).card != null) {
                ++n;
            }
        }
        return n;
    }

    public void consolidate() {
        if (this.grid.size() == this.countCards()) {
            return;
        }
        for (int i = 0; i < this.grid.size() - 1; ++i) {
            if (this.grid(i).card == null) {
                for (int j = this.grid.size() - 1; j > i; --j) {
                    if (this.grid(j).card != null) {
                        this.grid(i).changeCard(this.grid(j).card);
                        this.grid(j).changeCard(null);
                        break;
                    }
                }
            }
        }
    }

    public void crop(final int n) {
        for (int i = this.grid.size() - 1; i >= n; --i) {
            this.grid(i).changeCard(null);
            this.grid.removeElementAt(i);
        }
        this.repaint();
        fireEvent();
    }

    public boolean containsSet() {
        for (int i = 0; i < this.grid.size(); ++i) {
            for (int j = 0; j < this.grid.size(); ++j) {
                for (int k = 0; k < this.grid.size(); ++k) {
                    if (this.grid(i).card != null && this.grid(j).card != null && this.grid(k).card != null &&
                            this.grid(i) != this.grid(j) && this.grid(j) != this.grid(k) && this.grid(i) != this.grid(k) &&
                            this.grid(i).card.completes(this.grid(j).card, this.grid(k).card)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public BoardSlot[] findSet() {
        for (int i = 0; i < this.grid.size(); ++i) {
            for (int j = 0; j < this.grid.size(); ++j) {
                for (int k = 0; k < this.grid.size(); ++k) {
                    if (this.grid(i).card != null && this.grid(j).card != null && this.grid(k).card != null &&
                            this.grid(i) != this.grid(j) && this.grid(j) != this.grid(k) && this.grid(i) != this.grid(k) &&
                            this.grid(i).card.completes(this.grid(j).card, this.grid(k).card)) {
                        return new BoardSlot[]{this.grid(i), this.grid(j), this.grid(k)};
                    }
                }
            }
        }
        return null;
    }

    public int missing() {
        int count = 0;
        for (int i = 0; i < this.grid.size(); ++i) {
            if (this.grid(i) == null) {
                ++count;
            }
        }
        return count;
    }

    public void selectNone() {
        this.selections.removeAllElements();
        for (int i = 0; i < this.grid.size(); ++i) {
            if (this.grid(i).selected) {
                this.grid(i).select();
            }
        }
    }

    public SetCard selections(final int index) {
        return this.selections.elementAt(index);
    }

    public void addSelection(final SetCard obj) {
        this.selections.addElement(obj);
    }

    public void removeSelection(final SetCard obj) {
        this.selections.removeElement(obj);
    }

    public void testSelections() {
        if (this.selections.size() != 3) {
            System.out.println("Not 3 cards!");
            return;
        }
        if (this.selections(0).completes(this.selections(1), this.selections(2))) {
            //System.out.println("CORRECT!");
            SetGame.lbStatus.setText("CORRECT!");
            for (int i = 0; i < this.grid.size(); ++i) {
                if (this.contains(this.selections, this.grid(i).card)) {
                    this.grid(i).card = null;
                }
            }
            this.selectNone();
            this.resetComputer();
            this.reduce();
            this.deal();
            return;
        }
        //System.out.println("INCORRECT!");
        SetGame.lbStatus.setText("INCORRECT!");
        this.selectNone();
    }

    public boolean contains(final Vector<SetCard> vector, final Object o) {
        for (int i = 0; i < vector.size(); ++i) {
            if (vector.elementAt(i) != null && vector.elementAt(i) == o) {
                return true;
            }
        }
        return false;
    }

    public void resetComputer() {
        Arrays.fill(this.computerSet, null);
    }

    public void reduce() {
        if (this.grid.size() > SetBoard.CARD_MIN && this.containsSet()) {
            this.consolidate();
            this.crop(this.grid.size() - SetBoard.CARD_INCREMENT);
        }
        fireEvent();
    }

    private void fireEvent() {
        EventObject event = new EventObject(this);
        for (TriggerListener listener : listeners) {
            listener.onEventOccurred(event);
        }
    }

    public void revealSet() {
        if (this.computerSet == null) {
            return;
        }
        this.selectNone();
        for (int i = 0; i < this.computerSet.length - 1; ++i) {
            if (this.computerSet[i] != null) {
                this.computerSet[i].select();
            }
        }
    }

    public void update(final Graphics graphics) {
        this.paint(graphics);
    }

    public void paint(final Graphics graphics) {

    }

    /**
     * Adds an event listener to the list of listeners.
     *
     * @param listener The TriggerListener to be added.
     */
    public void addEventListener(TriggerListener listener) {
        listeners.add(listener);
    }

    public SetDeck getDeck() {
        return deck;
    }

    public interface TriggerListener extends EventListener {

        /**
         * Invoked upon the occurrence of a trigger event.
         *
         * @param event The TriggerEvent associated with the event in question.
         */
        void onEventOccurred(EventObject event);
    }
}

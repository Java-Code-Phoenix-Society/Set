package dev.jcps;

public class SetDeck {
    public static final int DEFAULT_NUM = 81;
    SetCard[] deck;
    private int card_index;

    public SetDeck() {
        this.deck = new SetCard[DEFAULT_NUM];
        int index = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                for (int k = -1; k < 2; k++) {
                    for (int l = -1; l < 2; l++) {
                        int[] array = {i, j, k, l};
                        this.deck[index] = new SetCard(array);
                        index++;
                    }
                }
            }
        }
        this.card_index = 0;
    }

    public SetCard[] deal(final int count) {
        final int min = Math.min(this.remaining(), count);
        if (min == 0) {
            return null;
        }
        final SetCard[] array = new SetCard[min];
        for (int i = 0; i < min; ++i, ++this.card_index) {
            array[i] = this.deck[this.card_index];
        }
        return array;
    }

    public SetCard deal() {
        if (this.remaining() == 0) {
            return null;
        }
        return this.deck[this.card_index++];
    }

    public int remaining() {
        return this.deck.length - this.card_index;
    }

    public void shuffle() {
        for (int i = 0; i < this.deck.length; ++i) {
            final int ranLen = (int) (Math.random() * this.deck.length);
            final SetCard setCard = this.deck[i];
            this.deck[i] = this.deck[ranLen];
            this.deck[ranLen] = setCard;
        }
        this.card_index = 0;
    }

    public boolean insert(final SetCard setCard) {
        if (this.card_index > 0) {
            final int ranCard = (int) (Math.random() * (this.deck.length - this.card_index)) + this.card_index;
            --this.card_index;
            this.deck[this.card_index] = this.deck[ranCard];
            this.deck[ranCard] = setCard;
            return true;
        }
        return false;
    }

    public void print() {
        for (SetCard setCard : this.deck) {
            setCard.print();
        }
    }
}

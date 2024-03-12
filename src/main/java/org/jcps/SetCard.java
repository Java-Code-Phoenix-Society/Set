package org.jcps;

import java.awt.*;

public class SetCard {
    int[] values;
    Image image;

    public SetCard(final int[] array) {
        this.values = new int[array.length];
        System.arraycopy(array, 0, this.values, 0, array.length);
    }

    public void print() {
        for (int value : this.values) {
            System.out.print(value);
        }
        System.out.print("\n");
    }

    public boolean completes(final SetCard setCard, final SetCard setCard2) {
        for (int i = 0; i < this.values.length; ++i) {
            final int value = setCard.values[i] + setCard2.values[i] + this.values[i];
            if (value != -3 && value != 3 && value != 0) {
                return false;
            }
        }
        return true;
    }
}

package dev.jcps;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public class BoardSlot extends Canvas implements MouseListener {
    private static final int ARC_WIDTH = 10;
    SetBoard board;
    SetCard card;
    boolean selected;

    public BoardSlot(final Dimension size, final SetCard card, final SetBoard board) {
        this.setSize(size);
        this.addMouseListener(this);
        this.card = card;
        this.board = board;
        this.selected = false;
    }

    public BoardSlot(final SetCard setCard, final SetBoard setBoard) {
        this(getSlotDimensions(), setCard, setBoard);
    }

    public BoardSlot(final SetBoard setBoard) {
        this(getSlotDimensions(), null, setBoard);
    }

    public BoardSlot(final Dimension dimension, final SetBoard setBoard) {
        this(dimension, null, setBoard);
    }

    private static Dimension getSlotDimensions() {
        return new Dimension(123 * SetGame.scaleCardsFactor, 80 * SetGame.scaleCardsFactor);
    }

    public static String valuesToName(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int j : array)
            sb.append(j);

        return sb.toString();
    }

    public void paint(final Graphics graphics) {
        this.drawCard(graphics);
        if (this.card != null) {
            this.drawSelected(graphics);
        }
    }

    public void drawCard(final Graphics graphics) {
        final Color color = graphics.getColor();
        if (this.card == null) {
            graphics.setColor(SetGame.background);
            graphics.fillRect(0, 0, this.getSize().width, this.getSize().height);
        } else {
            graphics.setColor(Color.white);
            graphics.fillRoundRect(0, 0, this.getSize().width, this.getSize().height, ARC_WIDTH, 10);
            graphics.drawImage(this.card.image, 5, 5, this.getSize().width - 5,
                    this.getSize().height - 5, 0, 0, this.card.image.getWidth(this),
                    this.card.image.getHeight(this), Color.white, this);
        }
        graphics.setColor(color);
    }

    public void drawSelected(final Graphics graphics) {
        final Color color = graphics.getColor();
        if (this.selected) {
            graphics.setColor(new Color(128, 233, 128));
        } else {
            graphics.setColor(Color.white);
        }
        for (int i = 0; i < 5; ++i) {
            graphics.drawRoundRect(i, i, this.getSize().width - 1 - 2 * i,
                    this.getSize().height - 1 - 2 * i, ARC_WIDTH, 10);
        }
        graphics.setColor(color);
    }

    public void update(final Graphics graphics) {
        this.paint(graphics);
    }

    public SetCard card() {
        return this.card;
    }

    public void changeCard(final SetCard card) {
        this.card = card;
        if (Objects.nonNull(card)) {
            this.setName(valuesToName(card.values));
        }
        this.repaint();
    }

    public void empty() {
        this.changeCard(null);
    }

    public void select() {
        if (this.card != null) {
            if (!this.selected) {
                if (this.board.selections.size() < 3) {
                    this.board.addSelection(this.card);
                    this.selected = true;
                }
                if (this.board.selections.size() == 3) {
                    this.board.testSelections();
                }
            } else {
                this.selected = false;
                this.board.removeSelection(this.card);
            }
        }
        this.repaint();
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void mousePressed(final MouseEvent mouseEvent) {
        this.select();
    }

    public void mouseReleased(final MouseEvent mouseEvent) {
    }

    public void mouseClicked(final MouseEvent mouseEvent) {
    }

    public void mouseEntered(final MouseEvent mouseEvent) {
    }

    public void mouseExited(final MouseEvent mouseEvent) {
    }
}

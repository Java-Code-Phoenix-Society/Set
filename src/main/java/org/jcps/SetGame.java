package org.jcps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

public class SetGame extends JPanel implements ActionListener, JavaAppletAdapter, SetBoard.TriggerListener {
    public static final String IMAGE_PATH = "images/";
    public static Color background;
    public static JLabel lbStatus;
    static JFrame frame;

    static {
        SetGame.background = Color.black;
    }

    SetBoard board;
    JButton reDeal;
    JButton cheat;

    public static void main(final String[] array) {
        SetGame sets = new SetGame();
        sets.paramMap.put("bgColor", "#830000");
        sets.init();
        frame = new JFrame("Set");
        frame.setBackground(SetGame.background);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(sets);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.requestFocus();
    }

    public void init() {
        this.loadParameters();
        this.setBackground(SetGame.background);
        this.board = new SetBoard(new SetDeck());
        this.board.addEventListener(this);
        this.reDeal = new JButton("Deal Again");
        this.cheat = new JButton("Reveal Set");
        lbStatus = new JLabel("Welcome to Set");
        lbStatus.setHorizontalAlignment(SwingConstants.CENTER);
        lbStatus.setVerticalAlignment(SwingConstants.CENTER);
        lbStatus.setFont(new Font("Arial", Font.BOLD, 16));
        lbStatus.setForeground(Color.BLACK);
        lbStatus.setBackground(Color.WHITE);
        lbStatus.setOpaque(true);
        lbStatus.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        final Panel comp = new Panel();
        final Panel comp2 = new Panel();
        final JPanel panel = new JPanel();
        panel.setSize(10, this.getSize().height);
        panel.setBackground(SetGame.background);
        comp.setBackground(SetGame.background);
        comp2.setBackground(SetGame.background);
        this.reDeal.setBackground(Color.white);
        this.cheat.setBackground(Color.white);
        comp2.add(this.reDeal);
        comp2.add(this.cheat);
        comp.add(lbStatus);
        this.setLayout(new BorderLayout());
        this.add(comp, "North");
        this.add(this.board, "Center");
        this.add(panel, "East");
        this.add(panel, "West");
        this.add(comp2, "South");
        this.reDeal.addActionListener(this);
        this.cheat.addActionListener(this);
        this.loadImages();
        this.board.deck.shuffle();
        this.board.deal();
    }

    public void loadParameters() {
        final String parameter = this.getParameter("bgColor");
        if (parameter != null) {
            try {
                SetGame.background = Color.decode(parameter);
            } catch (final NumberFormatException ignored) {
            }
        }
    }

    public void loadImages() {
        final MediaTracker mediaTracker = new MediaTracker(this);
        String str = this.getParameter("image_path");
        if (str == null) {
            str = IMAGE_PATH;
        } else if (str.lastIndexOf("/") != str.length() - 1) {
            str = str + "/";
        }
        System.out.println(str);
        for (int i = 0; i < this.board.deck.deck.length; ++i) {
            StringBuilder string = new StringBuilder();
            for (int j = 0; j < this.board.deck.deck[i].values.length; ++j) {
                string.append(this.board.deck.deck[i].values[j] + 1);
            }
            mediaTracker.addImage(this.board.deck.deck[i].image = this.getImage(
                    this.getDocumentBase(), str + string + ".gif"), i);
            try {
                this.showStatus("Loading cards: " + i + "/" + this.board.deck.deck.length +
                        " (" + str + string + ".gif" + ")");
                System.out.println("Loading cards: " + i + "/" + this.board.deck.deck.length +
                        " (" + str + string + ".gif" + ")");
                mediaTracker.waitForID(i);
            } catch (final InterruptedException ignored) {
            }
            if (mediaTracker.isErrorID(i)) {
                this.showStatus("Error loading " + str + string + ".gif");
                System.out.println("Error loading " + str + string + ".gif");
            }
        }
    }

    private void showStatus(String s) {
        System.out.println(s);
    }

    public void actionPerformed(final ActionEvent actionEvent) {
        if (actionEvent.getSource() == this.reDeal) {
            this.board.deck.shuffle();
            this.board.reDeal();
            lbStatus.setText("Welcome to Set");
            frame.repaint();
            return;
        }
        if (actionEvent.getSource() == this.cheat) {
            this.board.revealSet();
        }
    }

    public void update(final Graphics graphics) {
        this.paint(graphics);
    }

    public void paint(final Graphics graphics) {
    }

    @Override
    public void onEventOccurred(EventObject event) {
        frame.revalidate();
        frame.repaint();
    }
}

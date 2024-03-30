package org.jcps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.EventObject;

public class SetGame extends JPanel implements ActionListener, JavaAppletAdapter, SetBoard.TriggerListener {
    public static final String IMAGE_PATH = "images/";
    public static Color background;
    public static JLabel lbStatus;
    public static int scaleCardsFactor = 1;

    static {
        SetGame.background = Color.black;
    }

    public JFrame frame;
    SetBoard board;
    JButton reDeal;
    JButton cheat;

    public static void main(final String[] args) {
        SetGame sets = new SetGame();
        sets.paramMap.put("bgColor", "#830000");
        sets.init();
        sets.frame = new JFrame("Set");
        sets.frame.setBackground(SetGame.background);
        sets.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        sets.frame.add(sets);
        sets.frame.pack();
        sets.frame.setVisible(true);
        sets.frame.setResizable(false);
        sets.frame.setLocationRelativeTo(null);
        sets.frame.requestFocus();
        try {
            if (!args[0].isEmpty() && args[0].equals("T")) {
                sets.frame.dispose();
            }
        } catch (Exception e) {
            System.out.println("Playing the game");
        }
    }


    /**
     * Method to scale an image by a given factor
     *
     * @param originalImage Image to scale
     * @param scaleFactor   The factor to scale by
     * @return The scaled Image
     */
    public static Image scaleImage(BufferedImage originalImage, double scaleFactor) {
        if (originalImage != null) {
            // Calculate the new dimensions for the scaled image
            int newWidth = (int) (originalImage.getWidth() * scaleFactor);
            int newHeight = (int) (originalImage.getHeight() * scaleFactor);

            // Create a larger image with the new dimensions
            BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

            // Draw the original image onto the larger canvas with scaling
            Graphics2D g2d = scaledImage.createGraphics();
            g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
            g2d.dispose(); // Dispose of the graphics context

            return scaledImage;
        }
        return null;
    }

    public void init() {
        this.removeAll();
        this.loadParameters();
        this.setBackground(SetGame.background);
        this.board = new SetBoard(new SetDeck());
        this.board.addEventListener(this);
        this.reDeal = new JButton("Deal Again");
        this.cheat = new JButton("Reveal Set");
        this.reDeal.setName("reDeal");
        this.cheat.setName("cheat");
        lbStatus = new JLabel("Welcome to Set");
        lbStatus.setName("lbStatus");
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

    /**
     * Initializes the scaling of the Set game board.
     * If the scaling factor is not equal to 1, scales the card images by the factor.
     * Otherwise, loads the original images without scaling.
     */
    public void scaleInit() {
        // Remove the current board component from the container
        this.remove(this.board);

        // Create a status label indicating "Welcome to Set"
        lbStatus = new JLabel("Welcome to Set");

        // Check if the scaling factor is not equal to 1
        if (scaleCardsFactor != 1) {
            // Iterate over the deck of cards and scale each card image by the scaling factor
            for (int i = 0; i < this.board.deck.deck.length; ++i) {
                this.board.deck.deck[i].image = scaleImage((BufferedImage) this.board.deck.deck[i].image, scaleCardsFactor);
            }
        } else {
            // If the scaling factor is 1, load the original images without scaling
            loadImages();
        }

        // Create a new SetBoard instance with the original deck of cards
        this.board = new SetBoard(this.board.deck);

        // Add the new SetBoard component to the container at the center position
        this.add(this.board, "Center");

        // Shuffle the deck of cards
        this.board.deck.shuffle();

        // Deal the cards on the board
        this.board.deal();
    }

    /**
     * Loads parameters for the Set game.
     * Retrieves the value of the "bgColor" parameter and sets the background color accordingly.
     */
    public void loadParameters() {
        // Retrieve the value of the "bgColor" parameter from the applet's parameters
        final String parameter = this.getParameter("bgColor");

        // Check if the parameter value is not null
        if (parameter != null) {
            try {
                // Attempt to decode the parameter value into a Color object and set it as the background color
                SetGame.background = Color.decode(parameter);
            } catch (final NumberFormatException ignored) {
                // If the parameter value cannot be decoded as a color, ignore the exception
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
        System.out.println("Loading images from: " + str);
        for (int i = 0; i < this.board.deck.deck.length; ++i) {
            StringBuilder string = new StringBuilder();
            for (int j = 0; j < this.board.deck.deck[i].values.length; ++j) {
                string.append(this.board.deck.deck[i].values[j] + 1);
            }
            mediaTracker.addImage(
                    this.board.deck.deck[i].image =
                            scaleImage(
                                    (BufferedImage) this.getImage(this.getDocumentBase(),
                                            str + string + ".gif"), scaleCardsFactor), i
            );
            try {
                this.showStatus("Loading cards: " + (i + 1) + "/" + this.board.deck.deck.length +
                        " (" + str + string + ".gif" + ")");
                mediaTracker.waitForID(i);
            } catch (final InterruptedException ignored) {
            }
            if (mediaTracker.isErrorID(i)) {
                this.showStatus("Error loading " + str + string + ".gif");
            }
        }
    }

    private void showStatus(String s) {
        System.out.println(s);
    }

    public void actionPerformed(final ActionEvent actionEvent) {
        if (actionEvent.getSource() == this.reDeal) {
            if (actionEvent.getModifiers() == 17) {
                if (scaleCardsFactor == 1) {
                    scaleCardsFactor = 2; // If the scaling factor is 1, change it to 2
                } else {
                    scaleCardsFactor = 1; // If the scaling factor is 2, change it back to 1
                }
                scaleInit();
                frame.pack();
            }
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
        if (frame != null) {
            frame.revalidate();
            frame.repaint();
        }
    }

    public SetBoard getBoard() {
        return this.board;
    }
}

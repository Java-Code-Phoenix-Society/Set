package org.jcps;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

class MessageBox extends Dialog implements ActionListener, WindowListener {
    public static final int BTN_YESNOCANCEL = 11;
    public static final int BTN_YESNO = 14;
    public static final int BTN_OKCANCEL = 12;
    public static final int BTN_CLOSE = 13;
    public static final int IDOK = 1;
    public static final int IDCANCEL = 0;
    public static final int IDYES = 2;
    public static final int IDNO = 3;
    public static final int HEIGHT = 100;
    public static final int COORD_X = 200;
    public static final int COORD_Y = 200;
    public static int EVENT;
    private final Button close;
    private final Button ok;
    private final Button cancel;
    private final Button yes;
    private final Button no;

    public MessageBox(final Frame owner, final String title, final String text, final int width, final int type) {
        super(owner, title, true);
        this.setBounds(COORD_X, COORD_Y, width, HEIGHT);
        this.setResizable(false);
        this.addWindowListener(this);
        Label message = new Label(text, 1);
        this.close = new Button("Close");
        this.ok = new Button("Ok");
        this.cancel = new Button("Cancel");
        this.yes = new Button("Yes");
        this.no = new Button("No");
        this.close.addActionListener(this);
        this.ok.addActionListener(this);
        this.cancel.addActionListener(this);
        this.yes.addActionListener(this);
        this.no.addActionListener(this);
        Panel buttonPanel = new Panel();
        switch (type) {
            case BTN_YESNOCANCEL: {
                buttonPanel.add(this.yes);
                buttonPanel.add(this.no);
                buttonPanel.add(this.cancel);
                break;
            }
            case BTN_YESNO: {
                buttonPanel.add(this.yes);
                buttonPanel.add(this.no);
                break;
            }
            case BTN_OKCANCEL: {
                buttonPanel.add(this.ok);
                buttonPanel.add(this.cancel);
                break;
            }
            default: {
                buttonPanel.add(this.close);
                break;
            }
        }
        this.add(message, "Center");
        this.add(buttonPanel, "South");
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public MessageBox(final Frame frame, final String title, final String text, final int width) {
        this(frame, title, text, width, BTN_CLOSE);
    }

    public void actionPerformed(final ActionEvent actionEvent) {
        if (actionEvent.getSource() == this.close) {
            this.setVisible(false);
            return;
        }
        if (actionEvent.getSource() == this.ok) {
            MessageBox.EVENT = IDOK;
            this.setVisible(false);
            return;
        }
        if (actionEvent.getSource() == this.cancel) {
            MessageBox.EVENT = IDCANCEL;
            this.setVisible(false);
            return;
        }
        if (actionEvent.getSource() == this.yes) {
            MessageBox.EVENT = IDYES;
            this.setVisible(false);
            return;
        }
        if (actionEvent.getSource() == this.no) {
            MessageBox.EVENT = IDNO;
            this.setVisible(false);
        }
    }

    public void windowClosing(final WindowEvent windowEvent) {
    }

    public void windowClosed(final WindowEvent windowEvent) {
    }

    public void windowOpened(final WindowEvent windowEvent) {
    }

    public void windowIconified(final WindowEvent windowEvent) {
    }

    public void windowDeiconified(final WindowEvent windowEvent) {
    }

    public void windowActivated(final WindowEvent windowEvent) {
    }

    public void windowDeactivated(final WindowEvent windowEvent) {
    }
}

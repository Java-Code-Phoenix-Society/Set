package org.jcps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MessageBox extends Dialog implements ActionListener, WindowListener {
    public static final int BTN_YESNOCANCEL = 11;
    public static final int BTN_YESNO = 14;
    public static final int BTN_OKCANCEL = 12;
    public static final int BTN_CLOSE = 13;
    public static final int ID_OK = 1;
    public static final int ID_CANCEL = 0;
    public static final int ID_YES = 2;
    public static final int ID_NO = 3;
    public static final int HEIGHT = 100;
    public static final int CO_ORD_X = 200;
    public static final int CO_ORD_Y = 200;
    public static int EVENT;
    final JButton close;
    final JButton ok;
    final JButton cancel;
    final JButton yes;
    final JButton no;

    public MessageBox(final Frame owner, final String title, final String text, final int width, final int type) {
        super(owner, title, true);
        this.setBounds(CO_ORD_X, CO_ORD_Y, width, HEIGHT);
        this.setResizable(false);
        this.addWindowListener(this);
        Label message = new Label(text, 1);
        this.close = new JButton("Close");
        this.close.setName("close");
        this.ok = new JButton("Ok");
        this.ok.setName("ok");
        this.cancel = new JButton("Cancel");
        this.cancel.setName("cancel");
        this.yes = new JButton("Yes");
        this.yes.setName("yes");
        this.no = new JButton("No");
        this.no.setName("no");
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
        this.setVisible(!title.contains("test"));
    }

    public MessageBox(final Frame frame, final String title, final String text, final int width) {
        this(frame, title, text, width, BTN_CLOSE);
    }

    public void actionPerformed(final ActionEvent actionEvent) {
        if (actionEvent.getSource() == this.close || actionEvent.getID() == 10) {
            this.setVisible(false);
            return;
        }
        if (actionEvent.getSource() == this.ok || actionEvent.getID() == ID_OK) {
            MessageBox.EVENT = ID_OK;
            this.setVisible(false);
            return;
        }
        if (actionEvent.getSource() == this.cancel || actionEvent.getID() == ID_CANCEL) {
            MessageBox.EVENT = ID_CANCEL;
            this.setVisible(false);
            return;
        }
        if (actionEvent.getSource() == this.yes || actionEvent.getID() == ID_YES) {
            MessageBox.EVENT = ID_YES;
            this.setVisible(false);
            return;
        }
        if (actionEvent.getSource() == this.no || actionEvent.getID() == ID_NO) {
            MessageBox.EVENT = ID_NO;
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

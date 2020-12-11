package GUI.PopUps;

import GUI.*;
import GUI.Scenarios.*;
import Misc.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;


public abstract class PopUp extends JInternalFrame {
    protected GUI origin;
    protected User currentUser;
    protected Container contentPane;

    public PopUp(GUI origin, User currentUser) {
        super("", false, true);
        this.origin = origin;
        this.currentUser = currentUser;
        this.contentPane = this.getContentPane();
        setupWindowListener();
    }

    // This looks like shit, but it makes sure, that buttons are disabled when
    // the window is opened, and enabled when the window is closed.
    private void setupWindowListener() {
        this.addInternalFrameListener(new InternalFrameListener() {
            public void internalFrameClosed(InternalFrameEvent e) {
                if (origin instanceof Clickable) {
                    Clickable clickable = (Clickable) origin;
                    clickable.buttonsSetEnabled(true);
                }
            }
            public void internalFrameOpened(InternalFrameEvent e) {
                if (origin instanceof Clickable) {
                    Clickable sr = (Clickable) origin;
                    sr.buttonsSetEnabled(false);
                }
            }
            public void internalFrameClosing(InternalFrameEvent e) {}
            public void internalFrameIconified(InternalFrameEvent e) {}
            public void internalFrameDeiconified(InternalFrameEvent e) {}
            public void internalFrameActivated(InternalFrameEvent e) {}
            public void internalFrameDeactivated(InternalFrameEvent e) {}

        });
    }

    protected void clickOK(Scenario scenario) {
        origin.changeScenario(scenario);
        dispose();
    }
}

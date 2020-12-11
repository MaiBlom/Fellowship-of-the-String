package GUI.PopUps;

import GUI.*;
import GUI.Scenarios.*;
import Misc.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;


public abstract class PopUp extends JInternalFrame {
    protected GUI origin;
    protected Container contentPane;

    public PopUp() {
        super("", false, true);
        this.origin = GUI.getInstance();
        this.contentPane = this.getContentPane();
        setupWindowListener();
    }

    // This looks like shit, but it makes sure, that buttons are disabled when
    // the window is opened, and enabled when the window is closed.
    private void setupWindowListener() {
        this.addInternalFrameListener(new InternalFrameListener() {
            public void internalFrameClosed(InternalFrameEvent e) { origin.buttonsSetEnabled(true); }
            public void internalFrameOpened(InternalFrameEvent e) { origin.buttonsSetEnabled(false); }
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

package src.GUI.PopUps;

import javax.swing.*;
import java.awt.*;

import src.GUI.*;
import src.User;
import src.UserDB;

public class changeUserPopUp extends JInternalFrame {
    private static final long serialVersionUID = 1L;

    private User currentUser;

    private JPanel mainContainer;

    public changeUserPopUp(User currentUser) {
        this.currentUser = currentUser;
        setup();
    }

    private void setup() {
        mainContainer = new JPanel();
        mainContainer.setLayout(new BorderLayout());

        JLabel header = new JLabel("Choose account:");
        mainContainer.add(header, BorderLayout.NORTH);

        JPanel usersContainer = new JPanel();
        usersContainer.setLayout(new FlowLayout());
    }
}
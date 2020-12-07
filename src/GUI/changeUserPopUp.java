package src.GUI;

import javax.swing.*;
import java.awt.*;

import src.User;
import src.UserDB;

public class changeUserPopUp extends JInternalFrame {
    private static final long serialVersionUID = 1L;

    private UserDB userDB;
    private User currentUser;

    private Container mainContainer;

    public changeUserPopUp(User currentUser) {
        this.currentUser = currentUser;
        initUserDB();
        setup();
    }

    private void setup() {
        mainContainer = new Container();
        mainContainer.setLayout(new BorderLayout());

        JLabel header = new JLabel("Choose account:");
        mainContainer.add(header, BorderLayout.NORTH);

        Container usersContainer = new Container();
        usersContainer.setLayout(new FlowLayout());
    }

    private void initUserDB() {
        userDB = UserDB.getInstance();
    }
}
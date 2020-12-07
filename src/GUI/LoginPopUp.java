package src.GUI;

import src.*;
import src.Media.*;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.io.IOException;

public class LoginPopUp extends JInternalFrame {
    private Container origin;
    private UserDB db;
    
    private Container contentPane;
    private JPanel loginContainerPanel;
    private JLabel welcome;
    private JLabel info;
    private Container loginFields;
    private JTextField usernameField;
    private JTextField passwordField;
    
    private JPanel createUserContainerPanel;

    private final boolean EDITABLE = false;

    public LoginPopUp(Container origin) {
        this.origin = origin;
        this.db = UserDB.getInstance();
        this.setClosable(EDITABLE);
        this.setResizable(EDITABLE);

        // This looks like shit, but it makes sure, that buttons are disabled when
        // the window is opened, and enabled when the window is closed.
        this.addInternalFrameListener(new InternalFrameListener() {
            public void internalFrameClosed(InternalFrameEvent e) {}
            public void internalFrameOpened(InternalFrameEvent e) {} 
            public void internalFrameClosing(InternalFrameEvent e) {}
            public void internalFrameIconified(InternalFrameEvent e) {}
            public void internalFrameDeiconified(InternalFrameEvent e) {}
            public void internalFrameActivated(InternalFrameEvent e) {}
            public void internalFrameDeactivated(InternalFrameEvent e) {}
    
        });

        setup();
        setPreferredSize(new Dimension(600,400));
        pack();
        setVisible(true);
    }

    private void setup() {
        contentPane = this.getContentPane();

        loginContainerPanel = new JPanel();
        loginContainerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        loginContainerPanel.setLayout(new BorderLayout());
        contentPane.add(loginContainerPanel);

        setupInfoMessage();
        setupLoginFields();
        setupButton();
    }

    private void setupInfoMessage() {
        Container infoContainer = new Container();
        infoContainer.setLayout(new GridLayout(2,1));
        welcome = new JLabel("Welcome to the streaming service.");
        info = new JLabel(" ");
        infoContainer.add(welcome);
        infoContainer.add(info);
        loginContainerPanel.add(infoContainer, BorderLayout.NORTH);
    }

    private void setupLoginFields() {
        Container loginFieldsOuter = new Container();
        loginFieldsOuter.setLayout(new FlowLayout());

        loginFields = new Container();
        loginFields.setLayout(new GridLayout(2,2));
        loginFields.setPreferredSize(new Dimension(500,100));
        loginFieldsOuter.add(loginFields);

        JLabel usernameLabel = new JLabel("Username: ");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password: ");
        passwordField = new JTextField();

        loginFields.add(usernameLabel);
        loginFields.add(usernameField);
        loginFields.add(passwordLabel);
        loginFields.add(passwordField);

        loginContainerPanel.add(loginFieldsOuter, BorderLayout.CENTER);
    }

    private void setupButton() {
        Container buttons = new Container();
        buttons.setLayout(new GridLayout(1,2,2,2));

        JButton createUser = new JButton("Create new User");
        buttons.add(createUser);
        
        JButton login = new JButton("Login");
        login.addActionListener(l -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            try {
                if (db.login(username, password)) dispose();
            } catch (Exception e) {
                info.setText(e.getMessage());
            }
        });
        buttons.add(login);

        loginContainerPanel.add(buttons, BorderLayout.SOUTH);
    }
}

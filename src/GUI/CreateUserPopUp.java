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

public class CreateUserPopUp extends JInternalFrame {
    private Container origin;
    private UserDB db;
    
    private Container contentPane;
    private JPanel createUserContainerPanel;
    private JLabel createWelcome;
    private JLabel createInfo;
    private Container userFields;
    private JTextField newUsernameField;
    private JTextField newPasswordField;

    private final boolean EDITABLE = false;

    public CreateUserPopUp (Container origin) {
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

        createUserContainerPanel = new JPanel();
        createUserContainerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        createUserContainerPanel.setLayout(new BorderLayout());
        contentPane.add(createUserContainerPanel);

        setupCreateInfoMessage();
        setupCreateFields();
        setupCreateButton();
    }

    private void setupCreateInfoMessage() {
        Container infoContainer = new Container();
        infoContainer.setLayout(new GridLayout(2,1));
        createWelcome = new JLabel("Create a new user.");
        createInfo = new JLabel(" ");
        infoContainer.add(createWelcome);
        infoContainer.add(createInfo);
        createUserContainerPanel.add(infoContainer, BorderLayout.NORTH);
    }

    private void setupCreateFields() {
        Container userFieldsOuter = new Container();
        userFieldsOuter.setLayout(new FlowLayout());

        userFields = new Container();
        userFields.setLayout(new GridLayout(2,2));
        userFields.setPreferredSize(new Dimension(500,100));
        userFieldsOuter.add(userFields);

        JLabel usernameLabel = new JLabel("Username: ");
        newUsernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password: ");
        newPasswordField = new JTextField();

        userFields.add(usernameLabel);
        userFields.add(newUsernameField);
        userFields.add(passwordLabel);
        userFields.add(newPasswordField);

        createUserContainerPanel.add(userFieldsOuter, BorderLayout.CENTER);
    }

    private void setupCreateButton() {
        Container buttons = new Container();
        buttons.setLayout(new GridLayout(1,2,2,2));

        JButton cancel = new JButton("Return");
        cancel.addActionListener(l -> {
            dispose();
        });
        buttons.add(cancel);
        
        JButton create = new JButton("Create user");
        create.addActionListener(l -> {
            String username = newUsernameField.getText();
            String password = newPasswordField.getText();
            try {
                db.createUser(username, password);
                createInfo.setText("User created. Return to the login-screen to login.");
            } catch (Exception e) {
                createInfo.setText(e.getMessage());
            }
        });
        buttons.add(create);

        createUserContainerPanel.add(buttons, BorderLayout.SOUTH);
    }
}

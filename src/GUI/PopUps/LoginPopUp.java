package src.GUI.PopUps;

import src.*;
import src.GUI.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

public class LoginPopUp extends JInternalFrame {
    private static final long serialVersionUID = 1L;
    private GUI origin;
    private UserDB db;
    
    private Container contentPane;
    private JPanel loginContainerPanel;
    private JLabel loginWelcome;
    private JLabel loginInfo;
    private JPanel loginFields;
    private JTextField usernameField;
    private JPasswordField passwordField;

    private JPanel createUserContainerPanel;
    private JLabel createWelcome;
    private JLabel createInfo;
    private JPanel userFields;
    private JTextField newUsernameField;
    private JPasswordField newPasswordField;

    private final boolean EDITABLE = false;

    // Create a login popUp with the main GUI as the origin.
    public LoginPopUp(GUI origin) {
        this.origin = origin;
        this.db = UserDB.getInstance();
        this.setClosable(EDITABLE);
        this.setResizable(EDITABLE);

        setup();
        setPreferredSize(new Dimension(600,400));
        pack();
        setVisible(true);
    }

    // Setup of the contentPane. There are two states of the window. Either it is in login-mode, 
    // or else it's in create user-mode. 
    private void setup() {
        contentPane = this.getContentPane();

        loginContainerPanel = new JPanel();
        loginContainerPanel.setBounds(0,0,600,400);
        loginContainerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        loginContainerPanel.setLayout(new BorderLayout());
        contentPane.add(loginContainerPanel);

        createUserContainerPanel = new JPanel();
        createUserContainerPanel.setBounds(0,0,600,400);
        createUserContainerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        createUserContainerPanel.setLayout(new BorderLayout());

        setupWindowListener();
        setupLoginInfoMessage();
        setupLoginFields();
        setupLoginButton();
        setupCreateInfoMessage();
        setupCreateFields();
        setupCreateButton();
    }

    // This looks like shit, but it makes sure, that buttons are disabled when
    // the window is opened, and enabled when the window is closed.
    private void setupWindowListener() {
        this.addInternalFrameListener(new InternalFrameListener() {
            public void internalFrameClosed(InternalFrameEvent e) {
                if (origin instanceof Clickable) {
                    Clickable sr = (Clickable) origin;
                    sr.buttonsSetEnabled(true);
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

    // Setup of the login info message. It tells you if there are eny errors in the input you give.
    private void setupLoginInfoMessage() {
        JPanel infoContainer = new JPanel();
        infoContainer.setLayout(new GridLayout(2,1));
        loginWelcome = new JLabel("Welcome to the streaming service.");
        loginInfo = new JLabel(" ");
        infoContainer.add(loginWelcome);
        infoContainer.add(loginInfo);
        loginContainerPanel.add(infoContainer, BorderLayout.NORTH);
    }

    // Setup of the login fields, which are in the center of the window.
    private void setupLoginFields() {
        JPanel loginFieldsOuter = new JPanel();
        loginFieldsOuter.setLayout(new FlowLayout());

        loginFields = new JPanel();
        loginFields.setLayout(new GridLayout(2,2));
        loginFields.setPreferredSize(new Dimension(500,100));
        loginFieldsOuter.add(loginFields);

        JLabel usernameLabel = new JLabel("Username: ");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password: ");
        passwordField = new JPasswordField();

        loginFields.add(usernameLabel);
        loginFields.add(usernameField);
        loginFields.add(passwordLabel);
        loginFields.add(passwordField);

        JLabel help = new JLabel("Login with test / test to access the streaming service or create your own user.");
        loginFieldsOuter.add(help);

        loginContainerPanel.add(loginFieldsOuter, BorderLayout.CENTER);
    }

    // Setup of the login-buttons. The createUser button takes you to the create-user-mode. The Login button
    // checks whether or not your login credentials are valid and let's you into the streaming service if
    // they are.
    private void setupLoginButton() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,2,2,2));

        JButton createUser = new JButton("Create new user");
        createUser.addActionListener(l -> {
            contentPane.removeAll();
            contentPane.add(createUserContainerPanel);
            pack();
        });
        buttons.add(createUser);
        
        JButton login = new JButton("Login");
        login.addActionListener(l -> {
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();
            try {
                if (db.login(username, password)) {
                    origin.setCurrentUser(db.getUser(username));
                    dispose();
                }
            } catch (Exception e) {
                loginInfo.setText(e.getMessage());
            }
        });
        buttons.add(login);

        loginContainerPanel.add(buttons, BorderLayout.SOUTH);
    }

    // Setup of the create user info message. It tells you if there are eny errors in the input you give.
    private void setupCreateInfoMessage() {
        JPanel infoContainer = new JPanel();
        infoContainer.setLayout(new GridLayout(2,1));
        createWelcome = new JLabel("Create a new user.");
        createInfo = new JLabel(" ");
        infoContainer.add(createWelcome);
        infoContainer.add(createInfo);
        createUserContainerPanel.add(infoContainer, BorderLayout.NORTH);
    }

    // Setup of the login fields, which are in the center of the window.
    private void setupCreateFields() {
        JPanel userFieldsOuter = new JPanel();
        userFieldsOuter.setLayout(new FlowLayout());

        userFields = new JPanel();
        userFields.setLayout(new GridLayout(2,2));
        userFields.setPreferredSize(new Dimension(500,100));
        userFieldsOuter.add(userFields);

        JLabel usernameLabel = new JLabel("Username: ");
        newUsernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password: ");
        newPasswordField = new JPasswordField();

        userFields.add(usernameLabel);
        userFields.add(newUsernameField);
        userFields.add(passwordLabel);
        userFields.add(newPasswordField);

        createUserContainerPanel.add(userFieldsOuter, BorderLayout.CENTER);
    }

    // Setup of the create-buttons. The createUser button creates the user if the given username isn't already
    // taken. The cancel button takes you back to the login-screen.
    private void setupCreateButton() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,2,2,2));

        JButton cancel = new JButton("Return");
        cancel.addActionListener(l -> {
            contentPane.removeAll();
            contentPane.add(loginContainerPanel);
            pack();
        });
        buttons.add(cancel);
        
        JButton create = new JButton("Create user");
        create.addActionListener(l -> {
            String username = newUsernameField.getText();
            char[] password = newPasswordField.getPassword();
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

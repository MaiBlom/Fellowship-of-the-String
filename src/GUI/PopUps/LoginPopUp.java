package GUI.PopUps;

import GUI.*;
import GUI.Scenarios.*;
import Model.UserDB;

import javax.swing.*;
import java.awt.*;

public class LoginPopUp extends PopUp {
    private static final long serialVersionUID = 1L;

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

    // Create a login popUp with the main GUI as the origin.
    public LoginPopUp() {
        this.setClosable(false);
        this.setPreferredSize(new Dimension(600,400));

        setup();
        pack();
        setVisible(true);
    }

    // Setup of the contentPane. There are two states of the window. Either it is in login-mode, 
    // or else it's in create user-mode. 
    private void setup() {
        loginContainerPanel = new JPanel();
        loginContainerPanel.setBounds(0,0,600,400);
        loginContainerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        loginContainerPanel.setLayout(new BorderLayout());
        AssetDesigner.paintMainPanel(loginContainerPanel);
        contentPane.add(loginContainerPanel);

        createUserContainerPanel = new JPanel();
        createUserContainerPanel.setBounds(0,0,600,400);
        createUserContainerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        AssetDesigner.paintMainPanel(createUserContainerPanel);
        createUserContainerPanel.setLayout(new BorderLayout());

        setupLoginInfoMessage();
        setupLoginFields();
        setupLoginButton();
        setupCreateInfoMessage();
        setupCreateFields();
        setupCreateButton();
    }

    // Setup of the login info message. It tells you if there are eny errors in the input you give.
    private void setupLoginInfoMessage() {
        JPanel infoContainer = new JPanel();
        AssetDesigner.paintMainPanel(infoContainer);
        infoContainer.setLayout(new GridLayout(2,1));
        loginContainerPanel.add(infoContainer, BorderLayout.NORTH);

        loginWelcome = new JLabel("Welcome to Shire Streaming - hobbits only.");
        AssetDesigner.paintLoginScreenFont(loginWelcome);
        infoContainer.add(loginWelcome);

        loginInfo = new JLabel("Login to access the streaming service.");
        AssetDesigner.paintLoginScreenFont(loginInfo);
        infoContainer.add(loginInfo);
    }

    // Setup of the login fields, which are in the center of the window.
    private void setupLoginFields() {
        JPanel loginFieldsOuter = new JPanel();
        AssetDesigner.paintMainPanel(loginFieldsOuter);
        loginFieldsOuter.setLayout(new FlowLayout());
        loginContainerPanel.add(loginFieldsOuter, BorderLayout.CENTER);

        loginFields = new JPanel();
        AssetDesigner.paintMainPanel(loginFields);
        loginFields.setLayout(new GridLayout(2,2));
        loginFields.setPreferredSize(new Dimension(500,100));
        loginFieldsOuter.add(loginFields);

        JLabel usernameLabel = new JLabel("Username: ");
        AssetDesigner.paintLoginScreenFont(usernameLabel);
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password: ");
        AssetDesigner.paintLoginScreenFont(passwordLabel);
        passwordField = new JPasswordField();

        loginFields.add(usernameLabel);
        loginFields.add(usernameField);
        loginFields.add(passwordLabel);
        loginFields.add(passwordField);

        JLabel help = new JLabel("Login with test / test to access the streaming service or create your own user.");
        AssetDesigner.paintLoginScreenFont(help);
        loginFieldsOuter.add(help);
    }

    // Setup of the login-buttons. The createUser button takes you to the create-user-mode. The Login button
    // checks whether or not your login credentials are valid and let's you into the streaming service if
    // they are.
    private void setupLoginButton() {
        JPanel buttons = new JPanel();
        AssetDesigner.paintMainPanel(buttons);
        buttons.setLayout(new GridLayout(1,2,2,2));
        loginContainerPanel.add(buttons, BorderLayout.SOUTH);

        JButton createUser = new JButton("Create new user");
        AssetDesigner.paintClickableButton(createUser);
        createUser.addActionListener(l -> {
            contentPane.removeAll();
            contentPane.add(createUserContainerPanel);
            pack();
        });
        buttons.add(createUser);
        
        JButton login = new JButton("Login");
        AssetDesigner.paintClickableButton(login);
        login.addActionListener(l -> {
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();
            try {
                if (origin.getController().login(username, password)) {
                    origin.getController().setCurrentUser(origin.getController().getUser(username));
                    clickOK(Scenario.getMainMenu());
                }
            } catch (Exception e) {
                loginInfo.setText(e.getMessage());
            }
        });
        buttons.add(login);
    }

    // Setup of the create user info message. It tells you if there are eny errors in the input you give.
    private void setupCreateInfoMessage() {
        JPanel infoContainer = new JPanel();
        AssetDesigner.paintMainPanel(infoContainer);
        infoContainer.setLayout(new GridLayout(2,1));
        createUserContainerPanel.add(infoContainer, BorderLayout.NORTH);

        createWelcome = new JLabel("Create a new user.");
        AssetDesigner.paintLoginScreenFont(createWelcome);
        infoContainer.add(createWelcome);

        createInfo = new JLabel(" ");
        AssetDesigner.paintLoginScreenFont(createInfo);
        infoContainer.add(createInfo);
    }

    // Setup of the login fields, which are in the center of the window.
    private void setupCreateFields() {
        JPanel userFieldsOuter = new JPanel();
        AssetDesigner.paintMainPanel(userFieldsOuter);
        userFieldsOuter.setLayout(new FlowLayout());
        createUserContainerPanel.add(userFieldsOuter, BorderLayout.CENTER);

        userFields = new JPanel();
        AssetDesigner.paintMainPanel(userFields);
        userFields.setLayout(new GridLayout(2,2));
        userFields.setPreferredSize(new Dimension(500,100));
        userFieldsOuter.add(userFields);

        JLabel usernameLabel = new JLabel("Username: ");
        AssetDesigner.paintLoginScreenFont(usernameLabel);
        newUsernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password: ");
        AssetDesigner.paintLoginScreenFont(passwordLabel);
        newPasswordField = new JPasswordField();

        userFields.add(usernameLabel);
        userFields.add(newUsernameField);
        userFields.add(passwordLabel);
        userFields.add(newPasswordField);
    }

    // Setup of the create-buttons. The createUser button creates the user if the given username isn't already
    // taken. The cancel button takes you back to the login-screen.
    private void setupCreateButton() {
        JPanel buttons = new JPanel();
        AssetDesigner.paintMainPanel(buttons);
        buttons.setLayout(new GridLayout(1,2,2,2));
        createUserContainerPanel.add(buttons, BorderLayout.SOUTH);

        JButton cancel = new JButton("Return");
        AssetDesigner.paintClickableButton(cancel);
        cancel.addActionListener(l -> {
            contentPane.removeAll();
            contentPane.add(loginContainerPanel);
            pack();
        });
        buttons.add(cancel);
        
        JButton create = new JButton("Create user");
        AssetDesigner.paintClickableButton(create);
        create.addActionListener(l -> {
            String username = newUsernameField.getText();
            char[] password = newPasswordField.getPassword();
            try {
                origin.getController().createUser(username, password);
                createInfo.setText("User created. Return to the login-screen to login.");
            } catch (Exception e) {
                createInfo.setText(e.getMessage());
            }
        });
        buttons.add(create);
    }
}
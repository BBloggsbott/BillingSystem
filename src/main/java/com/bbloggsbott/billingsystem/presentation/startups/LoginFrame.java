package com.bbloggsbott.billingsystem.presentation.startups;

import com.bbloggsbott.billingsystem.exceptions.IncorrectPasswordException;
import com.bbloggsbott.billingsystem.exceptions.UserNotFoundException;
import com.bbloggsbott.billingsystem.integration.dbusersdao.User;
import com.bbloggsbott.billingsystem.presentation.springutils.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * The JFrame for the GUI of the Login Screen
 */
public class LoginFrame extends JFrame implements ActionListener {
    private JLabel username, password;
    private JTextField usernameText;
    private JPasswordField passwordText;
    private JButton login, exit;
    User user;

    /**
     * Constructor of the Login interface
     */
    public LoginFrame() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new SpringLayout());
        try {
            ImageIcon img = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource("billLogo.png")));
            setIconImage(img.getImage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        username = new JLabel("Username:");
        usernameText = new JTextField(15);
        password = new JLabel("Password");
        passwordText = new JPasswordField(15);
        login = new JButton("Login");
        exit = new JButton("Exit");

        panel.add(username);
        panel.add(usernameText);
        panel.add(password);
        panel.add(passwordText);
        panel.add(login);
        panel.add(exit);

        SpringUtilities.makeGrid(panel, 3, 2, 10, 50, 10, 80);
        add(panel, BorderLayout.CENTER);
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        titlePanel.add(new JLabel("\nBilling Login"));
        add(titlePanel, BorderLayout.NORTH);

        login.addActionListener(this);
        exit.addActionListener(this);

        setVisible(true);
        pack();
    }

    /**
     * Event Handler for the Action Event
     * 
     * @param e The Action Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
            if (usernameText.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Enter Username");
            } else if (Arrays.toString(passwordText.getPassword()).isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Enter Password");
            } else {
                user = new User(usernameText.getText(), new String(passwordText.getPassword()));
                try {
                    user.login();
                    setVisible(false);
                    new HomeFrame(user);

                } catch (UserNotFoundException e1) {
                    JOptionPane.showMessageDialog(this, "Username not Found");
                } catch (IncorrectPasswordException e1) {
                    JOptionPane.showMessageDialog(this, "Incorrect Password");
                }
            }
        } else if (e.getSource() == exit) {
            dispose();
        }
    }
}

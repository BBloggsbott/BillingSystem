package com.bbloggsbott.billingsystem.presentation.userhandling;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.bbloggsbott.billingsystem.integration.dbusersdao.User;
import com.bbloggsbott.billingsystem.presentation.springutils.SpringUtilities;
import com.bbloggsbott.billingsystem.service.userservice.UserLookup;

/**
 * The JFrame for the GUI of Updating a user in the database
 */
public class UpdateUserFrame extends JFrame implements ActionListener {
    JPanel header, content, footer;
    JLabel title, usernameLabel, nameLabel, passwordLabel;
    JTextField username, name, password;
    JButton updateButton, getUserButton;

    /**
     * Constructor of the Update user Interface
     */
    public UpdateUserFrame() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Update Password");
        try {
            ImageIcon img = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource("billLogo.png")));
            setIconImage(img.getImage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        header = new JPanel(new FlowLayout());
        content = new JPanel(new SpringLayout());
        footer = new JPanel(new FlowLayout());

        title = new JLabel("Update Password");
        usernameLabel = new JLabel("Username");
        nameLabel = new JLabel("Name");
        passwordLabel = new JLabel("Password");

        username = new JTextField();
        name = new JTextField();
        name.setEditable(false);
        password = new JTextField();

        updateButton = new JButton("Update");
        getUserButton = new JButton("Get User");

        content.add(usernameLabel);
        content.add(nameLabel);
        content.add(passwordLabel);
        content.add(username);
        content.add(name);
        content.add(password);
        SpringUtilities.makeGrid(content, 2, 3, 10, 20, 10, 10);

        header.add(title);

        footer.add(updateButton);
        footer.add(getUserButton);

        add(header, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        updateButton.addActionListener(this);
        getUserButton.addActionListener(this);

        setSize(600, 170);
        setVisible(true);
    }

    /**
     * Event Handler for the Action Event
     * 
     * @param e The Action Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateButton) {
            if (!username.getText().isEmpty()) {
                User user = UserLookup.lookupByUserName(username.getText()).get(0);
                if (user != null) {
                    if (user.updatePassword(password.getText())) {
                        JOptionPane.showMessageDialog(this, "User updated");
                        dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "User not Found");
                }
            }
        } else if (e.getSource() == getUserButton) {
            User user = UserLookup.lookupByUserName(username.getText()).get(0);
            if (user != null) {
                username.setText(user.getUserName());
                name.setText(user.getName());
                password.setText(user.getPassword());
            } else {
                JOptionPane.showMessageDialog(this, "User not Found");
            }
        }
    }
}
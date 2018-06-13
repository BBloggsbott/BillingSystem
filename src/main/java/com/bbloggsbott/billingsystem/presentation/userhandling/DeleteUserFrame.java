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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.bbloggsbott.billingsystem.exceptions.UserNotFoundException;
import com.bbloggsbott.billingsystem.integration.dbusersdao.User;
import com.bbloggsbott.billingsystem.presentation.springutils.SpringUtilities;
import com.bbloggsbott.billingsystem.service.userservice.UserLookup;

/**
 * The JFrame for the GUI of Deleting a user from the database
 */
public class DeleteUserFrame extends JFrame implements ActionListener {
    JLabel title, usernameLabel, nameLabel;
    JTextField username, name;
    JButton deleteButton, getUserButton;
    JPanel header, footer, content;

    /**
     * Constructor of the Delete user Interface
     */
    public DeleteUserFrame() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Delete User");
        try {
            ImageIcon img = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource("billLogo.png")));
            setIconImage(img.getImage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        title = new JLabel("Delete User");
        usernameLabel = new JLabel("Username");
        nameLabel = new JLabel("Name");
        username = new JTextField();
        name = new JTextField();
        name.setEditable(false);
        getUserButton = new JButton("Get User");
        deleteButton = new JButton("Delete");

        header = new JPanel(new FlowLayout());
        content = new JPanel(new SpringLayout());
        footer = new JPanel(new FlowLayout());

        header.add(title);
        content.add(usernameLabel);
        content.add(username);
        content.add(nameLabel);
        content.add(name);
        SpringUtilities.makeGrid(content, 2, 2, 10, 20, 10, 10);
        footer.add(getUserButton);
        footer.add(deleteButton);

        add(header, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        getUserButton.addActionListener(this);
        deleteButton.addActionListener(this);

        setSize(200, 200);
        setVisible(true);
    }

    /**
     * Event Handler for the Action Event
     * 
     * @param e The Action Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == getUserButton) {
            if (!username.getText().isEmpty()) {
                User user = UserLookup.lookupByUserName(username.getText()).get(0);
                if (user != null) {
                    name.setText(user.getName());
                }
            }
        } else if (e.getSource() == deleteButton) {
            if (!username.getText().isEmpty()) {
                User user = UserLookup.lookupByUserName(username.getText()).get(0);
                if (user != null) {
                    try {
                        if (user.deleteUser()) {
                            dispose();
                        }
                    } catch (UserNotFoundException unfe) {
                        unfe.printStackTrace();
                    }
                }
            }
        }
    }
}

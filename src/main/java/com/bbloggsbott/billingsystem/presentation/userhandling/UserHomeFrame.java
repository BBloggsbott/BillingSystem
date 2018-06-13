package com.bbloggsbott.billingsystem.presentation.userhandling;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The JFrame for the GUI of User Management
 */
public class UserHomeFrame extends JFrame implements ActionListener {
    JPanel header, content;
    JLabel title;
    JButton addUser, deleteUser, updateUser, searchUser;

    /**
     * Constructor of the User Handling Interface
     */
    public UserHomeFrame() {
        setTitle("User Management");
        try {
            ImageIcon img = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource("billLogo.png")));
            setIconImage(img.getImage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        content = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        addUser = new JButton("Add User");
        deleteUser = new JButton("Delete User");
        updateUser = new JButton("Update Password");
        searchUser = new JButton("Search Users");

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipady = 20;
        content.add(addUser, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        content.add(deleteUser, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        content.add(updateUser, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        content.add(searchUser, gbc);

        title = new JLabel("User Management");
        header = new JPanel(new FlowLayout());
        header.add(title);

        add(header, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);

        addUser.addActionListener(this);
        deleteUser.addActionListener(this);
        updateUser.addActionListener(this);
        searchUser.addActionListener(this);

        setSize(300, 200);
        setVisible(true);

    }

    /**
     * Event Handler for the Action Event
     * 
     * @param e The Action Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addUser) {
            AddUserFrame auf = new AddUserFrame();
        } else if (e.getSource() == deleteUser) {
            DeleteUserFrame duf = new DeleteUserFrame();
        } else if (e.getSource() == updateUser) {
            UpdateUserFrame uuf = new UpdateUserFrame();
        } else if (e.getSource() == searchUser) {
            SearchUserFrame suf = new SearchUserFrame();
        }
    }
}
package com.bbloggsbott.billingsystem.presentation.userhandling;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import com.bbloggsbott.billingsystem.exceptions.UserExistsException;
import com.bbloggsbott.billingsystem.integration.dbusersdao.User;
import com.bbloggsbott.billingsystem.presentation.springutils.SpringUtilities;

/**
 * The JFrame for the GUI of Adding a user to the database
 */
public class AddUserFrame extends JFrame implements ActionListener{
    JPanel header, content, footer;
    JLabel title, usernameLabel, nameLabel, passwordLabel, adminLabel;
    JTextField username, name, password;
    JCheckBox admin;
    JButton addButton, clearButton;

    /**
     * Constructor of the Add User Interface
     */
    public AddUserFrame(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Add User");
        try {
            ImageIcon img = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource("billLogo.png")));
            setIconImage(img.getImage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        header = new JPanel(new FlowLayout());
        content = new JPanel(new SpringLayout());
        footer = new JPanel(new FlowLayout());
        
        title = new JLabel("Add User");
        usernameLabel = new JLabel("Username");
        nameLabel = new JLabel("Name");
        passwordLabel = new JLabel("Password");
        adminLabel = new JLabel("Admin");

        username = new JTextField();
        name = new JTextField();
        password = new JTextField();
        admin = new JCheckBox("Yes");

        addButton = new JButton("Add");
        clearButton = new JButton("Clear");

        content.add(usernameLabel);
        content.add(nameLabel);
        content.add(passwordLabel);
        content.add(adminLabel);
        content.add(username);
        content.add(name);
        content.add(password);
        content.add(admin);
        SpringUtilities.makeGrid(content, 2, 4, 10, 20, 10, 10);

        header.add(title);

        footer.add(addButton);
        footer.add(clearButton);

        add(header, BorderLayout.NORTH);
        add(content,BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        addButton.addActionListener(this);
        clearButton.addActionListener(this);

        setSize(600,170);
        setVisible(true);
    }

    /**
     * Event Handler for the Action Event
     * 
     * @param e The Action Event
     */
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == addButton){
            User user = new User(username.getText(), name.getText(), password.getText(), admin.isSelected());
            try{
                if(user.insertUser()){
                    JOptionPane.showMessageDialog(this, "User created");    
                }
                username.setText("");
                name.setText("");
                password.setText("");
            }
            catch(UserExistsException uee){
                JOptionPane.showMessageDialog(this, "User Already Exists");
            }
        }
        else if(e.getSource() == clearButton){
            username.setText("");
            name.setText("");
            password.setText("");
        }
    }

}
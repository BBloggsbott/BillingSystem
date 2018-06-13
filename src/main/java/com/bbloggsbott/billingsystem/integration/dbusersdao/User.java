package com.bbloggsbott.billingsystem.integration.dbusersdao;

import com.bbloggsbott.billingsystem.exceptions.IncorrectPasswordException;
import com.bbloggsbott.billingsystem.exceptions.UserExistsException;
import com.bbloggsbott.billingsystem.exceptions.UserNotFoundException;
import com.bbloggsbott.billingsystem.service.connectionservice.ConnectionFactory;

import java.sql.*;

/**
 * The data access object for users
 */
public class User {
    private String userName;
    private String name;
    private String password;
    private boolean admin;

    /**
     * Constructor for the DAO
     * 
     * @param userName The username of the user
     * @param name     Name of the user
     * @param password Password of the user
     * @param admin    Admin status
     */
    public User(String userName, String name, String password, boolean admin) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.admin = admin;
    }

    /**
     * Constructor for the DAO
     * 
     * @param userName The username of the user
     * @param name     Name of the user
     * @param password Password of the user
     */
    public User(String userName, String name, String password) {
        this.userName = userName;
        this.password = password;
        this.name = name;
    }

    /**
     * Constructor for the DAO
     * 
     * @param userName The username of the user
     * @param password Password of the user
     */
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * Function to get the username of the user
     * 
     * @return String Username of the user
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Function to set the username of the user
     * 
     * @param userName new username of the user
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Function to get the name of the user
     * 
     * @return String Name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Function to set the name of the user
     * 
     * @param name New name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Function to get the password of the user
     * 
     * @return String Password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Function to set the password of the user
     * 
     * @param password New password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Function to get the admin status of the user
     * 
     * @return boolean Admin status of the user
     */
    public boolean getAdmin() {
        return admin;
    }

    /**
     * Function to set the password of the user
     * 
     * @param admin New password of the user
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    /**
     * Function to set the password of the user
     * 
     * @param admin New password of the user
     */
    public void setAdmin(String admin) {
        this.admin = admin.equalsIgnoreCase("Yes");
    }

    /**
     * Function to create the table for users
     * 
     * @return boolean Success of the task
     */
    public static boolean createTable() {
        Connection conn = ConnectionFactory.getConnection("Users");
        try {
            assert conn != null;
            Statement s = conn.createStatement();
            s.executeUpdate(
                    "create table users (username varchar(20) primary key, name varchar(50), password varchar(30), admin boolean)");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Function to login users
     * 
     * @throws UserNotFoundException      User was not found in the database
     * @throws IncorrectPasswordException Incorrect password
     */
    public void login() throws UserNotFoundException, IncorrectPasswordException {
        try {
            Connection conn = ConnectionFactory.getConnection("Users");
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement("select * from users where username = ? ");
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new UserNotFoundException(userName);
            }
            ps = conn.prepareStatement("select * from users where username = ? and password = ?");
            ps.setString(1, userName);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (!rs.next()) {
                throw new IncorrectPasswordException(userName);
            }
            setName(rs.getString("name"));
            setAdmin(rs.getBoolean("admin"));
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(userName);
        } catch (IncorrectPasswordException e) {
            throw new IncorrectPasswordException(userName);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    /**
     * Function to add users to the table
     * 
     * @throws UserExistsException
     * @return boolean Success of the task
     */
    public boolean insertUser() throws UserExistsException {
        try {
            Connection conn = ConnectionFactory.getConnection("Users");
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement("select * from users where username = ? ");
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                ps = conn.prepareStatement("insert into users values(?,?,?,?)");
                ps.setString(1, userName);
                ps.setString(2, name);
                ps.setString(3, password);
                ps.setBoolean(4, admin);
                ps.executeUpdate();
                return true;
            } else {
                throw new UserExistsException(userName);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (UserExistsException ue) {
            throw new UserExistsException(userName);
        }
        return false;
    }

    /**
     * Function to delete users from the table
     * 
     * @throws UserNotFoundException
     * @return boolean Success of the task
     */
    public boolean deleteUser() throws UserNotFoundException {
        try {
            Connection conn = ConnectionFactory.getConnection("Users");
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement("select * from users where username = ? ");
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ps = conn.prepareStatement("delete from users where username = ?");
                ps.setString(1, userName);
                ps.executeUpdate();
                return true;
            } else {
                throw new UserNotFoundException(userName);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (UserNotFoundException ue) {
            throw new UserNotFoundException(userName);
        }
        return false;
    }

    /**
     * Function to update passwords of user
     * 
     * @param newPassword The new password for the user
     * @return boolean Success of the task
     */
    public boolean updatePassword(String newPassword) {
        try {
            Connection conn = ConnectionFactory.getConnection("Users");
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement("select * from users where username = ? ");
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ps = conn.prepareStatement("update users set password = ? where username = ?");
                ps.setString(1, newPassword);
                ps.setString(2, userName);
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return false;
    }

    /**
     * Function to return user data as String array
     * 
     * @return Details as string array
     */
    public String[] toStringArray() {
        String[] toreturn = { userName, name, password, Boolean.toString(admin) };
        return toreturn;
    }
}

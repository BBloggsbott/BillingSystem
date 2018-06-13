package com.bbloggsbott.billingsystem.service.userservice;

import com.bbloggsbott.billingsystem.integration.dbusersdao.User;
import com.bbloggsbott.billingsystem.service.connectionservice.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;

/**
 * Provides method to look up users in the database
 */
public class UserLookup {
    private static Connection conn = ConnectionFactory.getConnection("Users");

    /**
     * Looks up users by username
     * 
     * @param username The username to look up for
     * @return The results
     */
    public static ArrayList<User> lookupByUserName(String username) {
        ArrayList<User> users = new ArrayList<User>();
        User u;
        try {
            PreparedStatement ps = conn.prepareStatement("select * from users where username = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                u = new User(rs.getString("username"), rs.getString("name"), rs.getString("password"));
                u.setAdmin(rs.getBoolean("admin"));
                users.add(u);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Looks up users by name
     * 
     * @param name The name to look up for
     * @return The results
     */
    public static ArrayList<User> lookupByName(String name) {
        ArrayList<User> users = new ArrayList<User>();
        User u;
        try {
            PreparedStatement ps = conn.prepareStatement("select * from users where name = ?");
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                u = new User(rs.getString("username"), rs.getString("name"), rs.getString("password"));
                u.setAdmin(rs.getBoolean("admin"));
                users.add(u);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Looks up users add admins
     * 
     * @return  The results
     */
    public static ArrayList<User> lookupAllAdmins() {
        ArrayList<User> users = new ArrayList<User>();
        User u;
        try {
            PreparedStatement ps = conn.prepareStatement("select * from users where admin = ?");
            ps.setBoolean(1, true);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                u = new User(rs.getString("username"), rs.getString("name"), rs.getString("password"));
                u.setAdmin(rs.getBoolean("admin"));
                users.add(u);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Looks up users all non admin users
     * 
     * @return The results
     */
    public static ArrayList<User> lookupAllNonAdmins() {
        ArrayList<User> users = new ArrayList<User>();
        User u;
        try {
            PreparedStatement ps = conn.prepareStatement("select * from users where admin = ?");
            ps.setBoolean(1, false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                u = new User(rs.getString("username"), rs.getString("name"), rs.getString("password"));
                u.setAdmin(rs.getBoolean("admin"));
                users.add(u);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get all users
     * 
     * @return The results
     */
    public static ArrayList<User> lookupAllUsers() {
        ArrayList<User> users = new ArrayList<User>();
        User u;
        try {
            Statement ps = conn.createStatement();
            ResultSet rs = ps.executeQuery("select * from users");
            while (rs.next()) {
                u = new User(rs.getString("username"), rs.getString("name"), rs.getString("password"));
                u.setAdmin(rs.getBoolean("admin"));
                users.add(u);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method to convert data
     * 
     * @param items The ArrayList to convert
     * @return The converted data
     */
    public static String[][] toTwoDArray(ArrayList<User> items) {
        String[][] toreturn = new String[items.size()][];
        for (int i = 0; i < items.size(); i++) {
            toreturn[i] = items.get(i).toStringArray();
        }
        return toreturn;
    }
}

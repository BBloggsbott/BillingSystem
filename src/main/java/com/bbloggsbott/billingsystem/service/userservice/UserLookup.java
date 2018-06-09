package com.bbloggsbott.billingsystem.service.userservice;

import com.bbloggsbott.billingsystem.integration.dbusersdao.User;
import com.bbloggsbott.billingsystem.service.connectionservice.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;

public class UserLookup {
    private static Connection conn = ConnectionFactory.getConnection("users");

    public static ArrayList lookupByUserName(String username){
        ArrayList<User> users = new ArrayList<User>();
        User u;
        try {
            PreparedStatement ps = conn.prepareStatement("select * from users where username = ?");
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                u = new User(rs.getString("username"),rs.getString("name"),rs.getString("password"));
                u.setAdmin(rs.getString("admin"));
                users.add(u);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList lookupByName(String name){
        ArrayList<User> users = new ArrayList<User>();
        User u;
        try {
            PreparedStatement ps = conn.prepareStatement("select * from users where name = ?");
            ps.setString(1,name);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                u = new User(rs.getString("username"),rs.getString("name"),rs.getString("password"));
                u.setAdmin(rs.getString("admin"));
                users.add(u);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList lookupAllAdmins(){
        ArrayList<User> users = new ArrayList<User>();
        User u;
        try {
            PreparedStatement ps = conn.prepareStatement("select * from users where admin = ?");
            ps.setString(1,"yes");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                u = new User(rs.getString("username"),rs.getString("name"),rs.getString("password"));
                u.setAdmin(rs.getString("admin"));
                users.add(u);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList lookupAllNonAdmins(){
        ArrayList<User> users = new ArrayList<User>();
        User u;
        try {
            PreparedStatement ps = conn.prepareStatement("select * from users where admin = ?");
            ps.setString(1,"no");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                u = new User(rs.getString("username"),rs.getString("name"),rs.getString("password"));
                u.setAdmin(rs.getString("admin"));
                users.add(u);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList lookupAllUsers(){
        ArrayList<User> users = new ArrayList<User>();
        User u;
        try {
            Statement ps = conn.createStatement();
            ResultSet rs = ps.executeQuery("select * from users");
            while(rs.next()){
                u = new User(rs.getString("username"),rs.getString("name"),rs.getString("password"));
                u.setAdmin(rs.getString("admin"));
                users.add(u);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}

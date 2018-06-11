package com.bbloggsbott.billingsystem.integration.dbusersdao;

import com.bbloggsbott.billingsystem.exceptions.IncorrectPasswordException;
import com.bbloggsbott.billingsystem.exceptions.UserExistsException;
import com.bbloggsbott.billingsystem.exceptions.UserNotFoundException;
import com.bbloggsbott.billingsystem.service.connectionservice.ConnectionFactory;

import java.sql.*;

public class User {
    private String userName;
    private String name;
    private String password;
    private boolean admin;

    public User(String userName, String name, String password, boolean admin){
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.admin = admin;
    }

    public User(String userName, String name, String password){
        this.userName = userName;
        this.password = password;
        this.name = name;
    }

    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName){   this.userName = userName;   }

    public String getName(){ return name; }

    public void setName(String name){   this.name = name;   }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean getAdmin(){ return admin;  }

    public void setAdmin(boolean admin){    this.admin = admin; }

    public void setAdmin(String admin){
        this.admin = admin.equalsIgnoreCase("Yes");
    }
    public static boolean createTable(){
        Connection conn = ConnectionFactory.getConnection("Users");
        try {
            assert conn != null;
            Statement s = conn.createStatement();
            s.executeUpdate("create table users (username varchar(20) primary key, name varchar(50), password varchar(30), admin boolean)");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void login() throws UserNotFoundException, IncorrectPasswordException {
        try{
            Connection conn = ConnectionFactory.getConnection("Users");
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement("select * from users where username = ? ");
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                throw new UserNotFoundException(userName);
            }
            ps = conn.prepareStatement("select * from users where username = ? and password = ?");
            ps.setString(1, userName);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if(!rs.next()){
                throw new IncorrectPasswordException(userName);
            }
            setName(rs.getString("name"));
            setAdmin(rs.getString("admin"));
        }
        catch (UserNotFoundException e){
            throw new UserNotFoundException(userName);
        }
        catch(IncorrectPasswordException e){
            throw new IncorrectPasswordException(userName);
        }
        catch(SQLException se) {
            se.printStackTrace();
        }
    }

    public boolean insertUser() throws UserExistsException{
        try{
            Connection conn = ConnectionFactory.getConnection("Users");
            assert conn != null:"Error Connecting to Database";
            PreparedStatement ps = conn.prepareStatement("select * from users where username = ? ");
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()) {
                ps = conn.prepareStatement("insert into users values(?,?,?,?)");
                ps.setString(1, userName);
                ps.setString(2, name);
                ps.setString(3, password);
                ps.setBoolean(4, admin);
                ps.executeUpdate();
                return true;
            }
            else{
                throw new UserExistsException(userName);
            }
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        catch(UserExistsException ue){
            throw new UserExistsException(userName);
        }
        return false;
    }

    public boolean deleteUser() throws UserNotFoundException{
        try{
            Connection conn = ConnectionFactory.getConnection("Users");
            assert conn != null:"Error Connecting to Database";
            PreparedStatement ps = conn.prepareStatement("select * from users where username = ? ");
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                ps = conn.prepareStatement("delete from users where username = ?");
                ps.setString(1, userName);
                ps.executeUpdate();
                return true;
            }
            else{
                throw new UserNotFoundException(userName);
            }
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        catch(UserNotFoundException ue){
            throw new UserNotFoundException(userName);
        }
        return false;
    }

    public boolean updatePassword() throws UserNotFoundException {
        try{
            Connection conn = ConnectionFactory.getConnection("Users");
            assert conn != null:"Error Connecting to Database";
            PreparedStatement ps = conn.prepareStatement("select * from users where username = ? ");
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                ps = conn.prepareStatement("update users set password = ? where userName = ?");
                ps.setString(1,password);
                ps.setString(2, userName);
                ps.executeUpdate();
                return true;
            }
            else{
                throw new UserNotFoundException(userName);
            }
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        catch(UserNotFoundException ue){
            throw new UserNotFoundException(userName);
        }
        return false;
    }
}

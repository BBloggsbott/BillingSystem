package com.bbloggsbott.billingsystem.service.connectionservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    static private Connection conn;
    static private String url;
    private String user;
    private String pass;
    public ConnectionFactory(String dbName, String user, String pass){
        url = "jdbc:derby:db/"+dbName+":create=true";
        this.user = user;
        this.pass = pass;
    }

    public ConnectionFactory(String dbName){
        url = "jdbc:derby:db/"+dbName+":create=true";
    }

    public Connection getConnectionWithCredentials(){

        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            conn = DriverManager.getConnection(url, user, pass);
            return conn;
        }
        catch(SQLException e){
            throw new RuntimeException("Error Connecting to Database",e);
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getConnection(String dbName){
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            conn = DriverManager.getConnection(url);
            return conn;
        }
        catch(SQLException e){
            throw new RuntimeException("Error Connecting to Database",e);
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

}

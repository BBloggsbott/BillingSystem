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
        url = "jdbc:derby:db/"+dbName+";create=true";
        this.user = user;
        this.pass = pass;
    }

    public ConnectionFactory(String dbName){
        url = "jdbc:derby:db/"+dbName+";create=true";
    }


    public static Connection getConnection(String dbName){
        url = "jdbc:derby:db/" + dbName + ";create=true";
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            conn = DriverManager.getConnection(url);
            return conn;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

}

package com.bbloggsbott.billingsystem.service.connectionservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Call to connect to databases
 */
public class ConnectionFactory {

    static private Connection conn;
    static private String url;
    private String user;
    private String pass;

    /**
     * Constructor
     * 
     * @param dbName Name of the database
     * @param user   Username to connect to the database
     * @param pass   Password to connect to the database
     */
    public ConnectionFactory(String dbName, String user, String pass) {
        url = "jdbc:derby:db/" + dbName + ";create=true";
        this.user = user;
        this.pass = pass;
    }

    /**
     * Constructor
     * 
     * @param dbName Name of the database
     */
    public ConnectionFactory(String dbName) {
        url = "jdbc:derby:db/" + dbName + ";create=true";
    }

    /**
     * Function to create and get the connection to the database
     * 
     * @param dbName Name of the database
     * @return Connection The connection to the database
     */
    public static Connection getConnection(String dbName) {
        url = "jdbc:derby:db/" + dbName + ";create=true";
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            conn = DriverManager.getConnection(url);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}

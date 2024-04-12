// package com.keyin.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/** Class that represents the connection between the program and the database */
public class DatabaseConnection {
    private static final String url = "jdbc:postgresql://localhost:5432/HealthMonitoring";
    private static final String user = "postgres";
    private static final String password = "password";

    public static Connection getCon(){
        Connection connection = null;
        try{
            Class.forName("org.postgresql.Driver");     // For Postgres
//            Class.forName("com.mysql.jdbc.Driver");  // For MySQL
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException  e) {
            e.printStackTrace();
        }
        return connection;

        
    }

    /**
     * Establishes a connection to the database using the provided URL, username, and password.
     * The connection is automatically closed after use.
     */
    public static void getConAndUse() {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            /* Connection in use here */
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /* Connection gets closed here */
    }


}

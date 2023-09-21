package com.example.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconnection {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/login";
        String username = "root";
        String password = "";

        try {
            // Step 1: Register the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 2: Establish the connection
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connected to the database!");

            // Step 3: Perform database operations here

            // Step 4: Close the connection
            connection.close();
            System.out.println("Connection closed.");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database connection error!");
            e.printStackTrace();
        }
    }
}

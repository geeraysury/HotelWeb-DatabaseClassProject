package com.ehotels;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final String DB_SERVER_PORT = "5432";
    private static final String DB_NAME = "HotelManagement";  // Change to match your database name
    private static final String DB_USERNAME = "postgres";  // Your PostgreSQL username
    private static final String DB_PASSWORD = "iLoveGyubee";  // Your PostgreSQL password

    private static Connection con = null;

    /**
     * Establishes and returns a connection with the database.
     * @return Connection object
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        if (con == null || con.isClosed()) {
            try {
                Class.forName("org.postgresql.Driver");
                String url = "jdbc:postgresql://" + IP_ADDRESS + ":" + DB_SERVER_PORT + "/" + DB_NAME;
                con = DriverManager.getConnection(url, DB_USERNAME, DB_PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("PostgreSQL Driver not found", e);
            }
        }
        return con;
    }

    /**
     * Closes the database connection.
     */
    public static void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


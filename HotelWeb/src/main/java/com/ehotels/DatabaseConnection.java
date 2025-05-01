package com.ehotels;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String IP_ADDRESS = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "db";
    private static final String DB_SERVER_PORT = System.getenv("POSTGRES_PORT") != null ? System.getenv("POSTGRES_PORT") : "5432";
    private static final String DB_NAME = System.getenv("POSTGRES_DB") != null ? System.getenv("POSTGRES_DB") : "ehotels";
    private static final String DB_USERNAME = System.getenv("POSTGRES_USER") != null ? System.getenv("POSTGRES_USER") : "postgres";
    private static final String DB_PASSWORD = System.getenv("POSTGRES_PASSWORD") != null ? System.getenv("POSTGRES_PASSWORD") : "iLoveGyubee";


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

                String host = System.getenv("DB_HOST");          
                String port = System.getenv("POSTGRES_PORT");   
                String db   = System.getenv("POSTGRES_DB");  
                String user = System.getenv("POSTGRES_USER");
                String pass = System.getenv("POSTGRES_PASSWORD");

                String url = "jdbc:postgresql://" + IP_ADDRESS + ":" + DB_SERVER_PORT + "/" + DB_NAME;

                con = DriverManager.getConnection(url, user, pass);
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


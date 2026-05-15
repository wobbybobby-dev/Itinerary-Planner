package com.travelapp.util;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class DatabaseUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/travel_itinerary";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Diya@2108";
 
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
 
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
 
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
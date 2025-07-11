package WCP;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:woodcutter.db";
    
    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            
            // Enable foreign key support
            stmt.execute("PRAGMA foreign_keys = ON;");
            
            // Create customers table (plural form)
            stmt.execute("CREATE TABLE IF NOT EXISTS customers (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                         "name TEXT NOT NULL, " +
                         "date TEXT DEFAULT CURRENT_TIMESTAMP);");
            
            // Create logs table with correct foreign key reference
            stmt.execute("CREATE TABLE IF NOT EXISTS logs (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                         "customer_id INTEGER, " +
                         "length REAL, " +
                         "roundness REAL, " +
                         "rate REAL, " +
                         "cft REAL, " +
                         "cost REAL, " +
                         "FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE);");
            
            System.out.println("Database tables created successfully");
            
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database initialization failed", e);
        }
    }
    
    public static int saveCustomerWithLogs(String customerName, List<WoodEntry> entries) {
        int customerId = -1;
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            conn.setAutoCommit(false);
            
            // 1. First insert the customer
            String insertCustomer = "INSERT INTO customers(name) VALUES(?);";
            try (PreparedStatement pstmt = conn.prepareStatement(insertCustomer, 
                  Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, customerName);
                int affectedRows = pstmt.executeUpdate();
                
                if (affectedRows == 0) {
                    throw new SQLException("Creating customer failed, no rows affected.");
                }
                
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        customerId = rs.getInt(1);
                    } else {
                        throw new SQLException("Creating customer failed, no ID obtained.");
                    }
                }
            }
            
            // 2. Then insert all logs for this customer
            String insertLog = "INSERT INTO logs(customer_id, length, roundness, rate, cft, cost) " +
                              "VALUES(?, ?, ?, ?, ?, ?);";
            try (PreparedStatement pstmt = conn.prepareStatement(insertLog)) {
                for (WoodEntry entry : entries) {
                    pstmt.setInt(1, customerId);
                    pstmt.setDouble(2, entry.getLength());
                    pstmt.setDouble(3, entry.getRoundness());
                    pstmt.setDouble(4, entry.getRate());
                    pstmt.setDouble(5, entry.getCFT());
                    pstmt.setDouble(6, entry.getCost());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }
            
            conn.commit();
            System.out.println("Saved customer with " + entries.size() + " logs successfully");
            
        } catch (SQLException e) {
            System.err.println("Error saving customer with logs: " + e.getMessage());
            e.printStackTrace();
        }
        return customerId;
    }
}
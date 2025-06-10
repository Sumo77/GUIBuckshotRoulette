/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BSRGameDatabase;

/**
 *
 * @author Prumm
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager { // Structure of this Class borrowed from our labs
    private static final String USER_NAME = "BuckShotRoulette"; // DB Username
    private static final String PASSWORD = "pdc"; // DB Password
    private static final String URL = "jdbc:derby:BSRWinsDatabase_Ebd; create=true";  // DB URL - Embedded Mode !
    
    Connection conn;

    public DatabaseManager() {
        establishConnection(); // Connect the database on class reference
    }

//    public static void main(String[] args) { // No longer needed since inital setup done ! Called from main logic class
//        DatabaseManager dbManager = new DatabaseManager();
//        System.out.println(dbManager.getConnection()); // Show Database connection status !
//    }

    public Connection getConnection() {
        return this.conn; // Get connection information/status
    }

    public void establishConnection() { // Create connection between code and database
        if (this.conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD); // Provide database connection details, attempt connection
                //System.out.println(URL + " Get Connected Successfully ....");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void closeConnections() { // Close / Stop the connection between database and code
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
}

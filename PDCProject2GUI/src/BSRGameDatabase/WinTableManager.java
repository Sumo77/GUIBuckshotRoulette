/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BSRGameDatabase;

/**
 *
 * @author Prumm
 */

import java.sql.*;
import java.util.ArrayList;

public class WinTableManager { // Manages the WinTable Reading / Writing to and from the Database
    
    private final Connection conn;
    public ArrayList<String> topPlayers = new ArrayList<>();// Database !
    
    public WinTableManager(DatabaseManager dbManager) { // pass database manager to scoretable (from player or main) throw error if not able to create statements
        this.conn = dbManager.getConnection(); // Connect to the DatabaseManager
        createWinTable(); // Check if table exists, else create ! (for first time run)
    }
    
    
    public void createWinTable() { // Create the winsTable if not yet already exists
        try {
            PreparedStatement prepStatement = conn.prepareStatement("SELECT TABLENAME FROM SYS.SYSTABLES WHERE TABLENAME = 'WINS_TABLE'");
            ResultSet result = prepStatement.executeQuery(); // Try to select the WinsTable from the systems tables
            
            if (result.next()) { // If can select the table,
                System.out.println("Table exists"); // Table exists !
            } else { // Nothing selected, table does not exist, so create !
                prepStatement = conn.prepareStatement("CREATE TABLE WINS_TABLE (Username VARCHAR(255) PRIMARY KEY, Wins INT)");
                prepStatement.executeUpdate(); // Create winsTable that stores username and number of wins
            }
            
            prepStatement.close(); // End statement nicely
            
        } catch (SQLException ex) { // Error if failed to create table or check if table already existed
             System.out.println("Failed to check for/create Wins_Table: " + ex.getMessage()); 
        }
    }
    
    public int retrievePlayerWins(String username) { // Retrieve from the winTable and return the player's score based on username
        try {
            PreparedStatement prepStatement = conn.prepareStatement("SELECT WINS FROM WINS_TABLE WHERE USERNAME = ?");
            prepStatement.setString(1, username); // insert username into SQL statement
            
            ResultSet result = prepStatement.executeQuery(); // Select wins from the winsTable based on username provided
            
            if (result.next()) { // If was able to select username (there is a row),
                int wins = result.getInt("WINS"); // Get the wins of the row
                
                result.close(); // End statements nicely (before return ends method)
                prepStatement.close();
                
                return wins; // Return number of wins for user !
            }
            
            result.close(); // End statements nicely
            prepStatement.close();
                
        } catch (SQLException ex) { 
            System.out.println("Failed to create retrieve wins from the Wins_Table: " + ex.getMessage());
        }
        
        return 0; // No existing wins or user found, return 0! - This is fallback return, should always be a 0
    }
    
    public void updatePlayerWins(String username, int wins) { // Update the players wins in winTable based on username
        try {
            PreparedStatement prepStatement = conn.prepareStatement("UPDATE WINS_TABLE SET WINS = ? WHERE USERNAME = ?");
            prepStatement.setInt(1, wins); // Number of users new wins for SQL
            prepStatement.setString(2, username); // Username for SQL
            
            prepStatement.executeUpdate(); // Update the wins of a specific user with provided values
            
            prepStatement.close(); // End statement nicely
            
        } catch (SQLException ex) {
            System.out.println("Failed to update player wins in the Wins_Table: " + ex.getMessage());
        }
                
    }
    
    public boolean checkPlayerExists(String username) { // Check if player exists in winTable already based on username
        try {
            PreparedStatement prepStatement = conn.prepareStatement("SELECT USERNAME FROM WINS_TABLE WHERE USERNAME = ?");
            prepStatement.setString(1, username); // Username for SQL
            
            ResultSet result = prepStatement.executeQuery(); // Try to find username in winsTable, provided username
            
            if (result.next()) { // Check if there is a row returned to select/move to,
                result.close(); // End statements nicely (before return)
                prepStatement.close();
                return true; // If there is, Player found !
            } else {
                result.close(); // End statements nicely (before return)
                prepStatement.close();
                return false; // If not, Player not found !
            }
            
        } catch (SQLException ex) {
            System.out.println("Failed to check player wins from the Wins_Table: " + ex.getMessage());
        }

        return false; // Nothing found or error!
        
    }
    
    public void addNewPlayer(String username) { // Add new player to winTable
        try {
            PreparedStatement prepStatement = conn.prepareStatement("INSERT INTO WINS_TABLE (USERNAME, WINS) VALUES (?, ?)");
            prepStatement.setString(1, username); // Username to give to SQL
            prepStatement.setInt(2, 0); // 0 wins as is new player! give to SQL
            
            prepStatement.executeUpdate(); // Insert new users values (username + 0 wins) into the winsTable to keep record

            prepStatement.close(); // End statement nicely
            
        } catch (SQLException ex) {
            System.out.println("Failed to add New Player to the Wins_Table: " + ex.getMessage());
        }
    }
    
    public ArrayList<String> displayWinsTable() { 
        System.out.print(topPlayers);
        if (!topPlayers.isEmpty()) {
            topPlayers.clear(); // Clear the list for fresh data
        }
        try {
            PreparedStatement prepStatement = conn.prepareStatement(
                "SELECT USERNAME, WINS FROM WINS_TABLE ORDER BY WINS DESC FETCH FIRST 10 ROWS ONLY");
            ResultSet result = prepStatement.executeQuery();

            int rank = 1;
            
            while (result.next()) { 
                String username = result.getString("USERNAME");
                int wins = result.getInt("WINS");
                topPlayers.add(rank + ": " + username + " | Wins: " + wins); // Add to array
                rank++;
            }

            result.close();
            prepStatement.close();
        } catch (SQLException ex) {
            System.out.println("Failed to retrieve wins from the Wins_Table: " + ex.getMessage());
        }

        return topPlayers; // Return the ArrayList
    }
}

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

public class WinTableManager { // Manages the WinTable Reading / Writing to and from the Database
    // Migrate each method
    
    private final Connection conn; // Database !
    
    public WinTableManager(DatabaseManager dbManager) { // pass database manager to scoretable (from player or main) throw error if not able to create statements
        this.conn = dbManager.getConnection(); // Connect to the DatabaseManager
        createWinTable(); // Check if table exists, else create ! (for first time run)
    }
    
    
    public void createWinTable() { // Create the winsTable if not yet already exists
        try {
            PreparedStatement prepStatement = conn.prepareStatement("SELECT TABLENAME FROM SYS.SYSTABLES WHERE TABLENAME = 'WINS_TABLE'");
            ResultSet result = prepStatement.executeQuery();
            
            if (result.next()) {
                System.out.println("Table exists");
            } else {
                prepStatement = conn.prepareStatement("CREATE TABLE WINS_TABLE (Username VARCHAR(255) PRIMARY KEY, Wins INT)");
                prepStatement.executeUpdate(); // Create winsTable that stores username and number of wins
            }
            
            prepStatement.close(); // End statement nicely
            
        } catch (SQLException ex) { // Error if failed to create table
             System.out.println("Failed to create Wins_Table: " + ex.getMessage()); 
        }
    }
    
    public int retrievePlayerWins(String username) { // Retrieve from the winTable and return the player's score based on username
        try {
            PreparedStatement prepStatement = conn.prepareStatement("SELECT WINS FROM WINS_TABLE WHERE USERNAME = ?");
            prepStatement.setString(1, username);
            
            ResultSet result = prepStatement.executeQuery();
            
            if (result.next()) {
                int wins = result.getInt("WINS");
                
                result.close();
                prepStatement.close();
                
                return wins;
            }
            
            result.close();
            prepStatement.close();
                
        } catch (SQLException ex) { 
            System.out.println("Failed to create retrieve wins from the Wins_Table: " + ex.getMessage());
        }
        
        return 0;
    }
    
    public void updatePlayerWins(String username, int wins) { // Update the players wins in winTable based on username
        try {
            PreparedStatement prepStatement = conn.prepareStatement("UPDATE WINS_TABLE SET WINS = ? WHERE USERNAME = ?");
            prepStatement.setInt(1, wins);
            prepStatement.setString(2, username);
            
            prepStatement.executeUpdate();
            
            prepStatement.close();
            
        } catch (SQLException ex) {
            System.out.println("Failed to update player wins in the Wins_Table: " + ex.getMessage());
        }
                
    }
    
    public boolean checkPlayerExists(String username) { // Check if player exists in winTable already based on username
        try {
            PreparedStatement prepStatement = conn.prepareStatement("SELECT USERNAME FROM WINS_TABLE WHERE USERNAME = ?");
            prepStatement.setString(1, username);
            
            ResultSet result = prepStatement.executeQuery();
            
            if (result.next()) { // Check if there is a row returned/to select/move to
                result.close();
                prepStatement.close();
                return true; // If there is, Player found !
            } else {
                result.close();
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
            prepStatement.setString(1, username); // Use / save under entered username
            prepStatement.setInt(2, 0); // 0 wins as is new player!
            
            prepStatement.executeUpdate();

            prepStatement.close();
            
        } catch (SQLException ex) {
            System.out.println("Failed to add New Player to the Wins_Table: " + ex.getMessage());
        }
    }
    
    public void displayWinsTable() { // Display the top 10 (or under) winners of the game in the winTable
        try {
            PreparedStatement prepStatement = conn.prepareStatement("SELECT USERNAME, WINS FROM WINS_TABLE ORDER BY WINS DESC FETCH FIRST 10 ROWS ONLY");
            
            ResultSet result = prepStatement.executeQuery();
            
            int numRank = 1;
            
            while(result.next()) {
                String username = result.getString("USERNAME");
                int wins = result.getInt("WINS");
                
                System.out.println(numRank + ": " + username + " | " + wins);
                numRank++;
            }

            prepStatement.close();
            
        } catch (SQLException ex) {
            System.out.println("Failed to add New Player to the Wins_Table: " + ex.getMessage());
        }
    }
    
}

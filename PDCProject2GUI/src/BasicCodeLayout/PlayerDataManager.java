/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BasicCodeLayout;

import java.io.*;
import java.util.*;

/**
 * @author Summer Harris
 * @author Davor Georgiev
 */

public class PlayerDataManager { // Controls all Read / Write functions of the Player to the winFile
    
    private static final String winsFile = "./resources/winsFile.txt"; // File that holds all of the Player's wins, stored by "username win"
    
    public static int retrievePlayerWins(String username) { // Retrieve from the winFile and return the player's score based on username
        try { BufferedReader reader = new BufferedReader(new FileReader(winsFile)); // Start reader
            String line = reader.readLine();
            
            while (line != null) { // Until the end of the file
                String[] userData = line.split("\\|"); // Split username and win
                if (userData.length == 2 && userData[0].trim().equals(username.trim())) { // If there are 2 values and given username matches the username in the winFile
                    return Integer.parseInt(userData[1].trim()); // Return the win
                }
                line = reader.readLine(); // Read next line
            }
            reader.close(); // Once finsihed, stop reader
        } catch (IOException ex) {
            System.out.println("An error occured: " + ex.getMessage()); // Catch any reader errors
        }
        
        return 0;
    }
    
    public static void updatePlayerWins(String username, int wins) { // Update the players wins in winFile based on username
        List<String> tempWinsHolder = new ArrayList<>(); // Hold wins temporairly in order to edit
        
        try {BufferedReader reader = new BufferedReader(new FileReader(winsFile)); // Start reader
            String line = reader.readLine();
            
            while (line != null) {
                String[] userData = line.split("\\|"); // Split into username and wins
                if (userData.length == 2 && userData[0].trim().equals(username.trim())) { // If username found
                    tempWinsHolder.add(username.trim() + " | " + wins); // Override old winFile line by adding line with same username and new wins to List
                } else {
                    tempWinsHolder.add(line); // Add to list (not the line looking for, no need to update)
                }
                line = reader.readLine(); // Read next line
            }
            reader.close(); // Stop reader
            
        } catch (IOException ex) {
            System.out.println("An error occured: " + ex.getMessage()); // Catches reader errors
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(winsFile))) { // Re-print newly changed List wins back into file
            for (String updatedLine : tempWinsHolder) {
                writer.println(updatedLine);
            }
            writer.close(); // Stop writer
        } catch (IOException ex) {
            System.out.println("An error occurred while writing to the file: " + ex.getMessage()); // Catch writer errors
        }
    }
    
    public static boolean checkPlayerExists(String username) { // Check if player exists in winFile already based on username
        try { BufferedReader reader = new BufferedReader(new FileReader(winsFile));
            String line = reader.readLine();
            
            while (line != null) {
                String[] userData = line.split("\\|");
                if (userData.length == 2 && userData[0].trim().equals(username.trim())) { // If user is found in scorefile, return true, as in found.
                    return true;
                }
                line = reader.readLine(); // Read next line
            }
            reader.close(); // Stop reader
            
        } catch (IOException ex) {
            System.out.println("An error occured: " + ex.getMessage()); // Catch errors
        }
        // Else return false, as in username not found in scorefile.
        return false;
    }
    
    public static void addNewPlayer(String username) { // Add new player to winFile
        try { PrintWriter writer = new PrintWriter(new FileWriter(winsFile, true));
            writer.println(username.trim() + " | " + 0); // Write new line in winFile with username and a score of 0
            writer.close(); // Stop writer
        } catch (IOException ex) {
            System.out.println("An error occured: " + ex.getMessage()); // Ctach writer errors
        }
    }
    
    public static void displayWinsFile() { // Display the top 10 (or under) winners of the game in the winFile
        ArrayList<String> tempWinHolder = new ArrayList<>(); // List to temporairly hold winFile
        
        try { BufferedReader reader = new BufferedReader(new FileReader(winsFile));
            String line = reader.readLine();
            while (line != null) { // Read all of winFile to the list
                String[] parts = line.trim().split("\\|"); // Split line into 2 parts, username and wins
                if (parts.length == 2) { // Only add line to list if it has the 2 parts
                    tempWinHolder.add(line);
                }
                line = reader.readLine(); // Read next line
            }
            
            tempWinHolder.sort((a, b) -> { // Split and sort the players into decending rank in the list
                int winA = Integer.parseInt(a.split("\\|")[1].trim()); // Take one win from the list (int)
                int winB = Integer.parseInt(b.split("\\|")[1].trim()); // Take another win from the list (int)
                return Integer.compare(winB, winA); // Compare B and A (decends) and return order in which one is bigger than the other
            });
            
            for (int winners = 0; winners < Math.min(10, tempWinHolder.size()); winners++) { // Loop through 10 times (Top 10 !)
                System.out.println((winners + 1) + ": " + tempWinHolder.get(winners)); // Print Top 10
            }
            
        } catch (IOException ex) {
            System.out.println("An error occured: " + ex.getMessage()); // Ctach reader errors
        }
    }
    
}

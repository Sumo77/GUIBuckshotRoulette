/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BasicCodeLayout;

import java.util.ArrayList;
/**
 * @author Summer Harris
 * @author Davor Georgiev
 */
public class Player { // Controls all Player related mechanics, events and information

    // Initialise Constants and Variables
    public int MAX_HEALTH = 4;
    private final String playerName;
    private int health;
    private int wins;
    private ArrayList<String> powerUps;
    public boolean doubleDamage = false;
    
    
    public Player (String playerName) { // Player constructor, gives values to select variables above
        this.playerName = playerName;
        this.health = MAX_HEALTH;
        this.wins = 0; // Associated with player in the Wins file ! New player is added to file with 0 wins.
        this.powerUps = new ArrayList<>(); // Players powerups
    }
    
    public String getUsername() { // Get the username of the player
        return playerName;
    }
    
    public void addPowerUp(String powerUp) { // Give a powerup to the player
        powerUps.add(powerUp);
    }
    
    public void usePowerUp(String powerUp) { // Use up (remove) the players powerup
        powerUps.remove(powerUp);
    }
    
    public ArrayList<String> getPowerUps() { // Get the list of the players powerups
        return powerUps;
    }
    
    public boolean playerExists() { // Check if player exists in the winFile by username
        return PlayerDataManager.checkPlayerExists(playerName);
    }
    
    public void addNewPlayerToWins() { // Add player to the winFile by username
        PlayerDataManager.addNewPlayer(playerName);
    }
    
    public int getWins() { // Get the player's wins
        return wins;
    }
    
    public void retrievePlayerWins() { // Retrieve the players wins from the winFile by username
        this.wins = PlayerDataManager.retrievePlayerWins(playerName);
    }
    
    public void updateWins(int win) { // Update the players wins in the winFile
        PlayerDataManager.updatePlayerWins(playerName, wins + win);
    }
    
    public void removeHealth(int dmg) { // Remove health from player based on given damage
        this.health -= dmg;
    }
    
    public int checkHealth() { // Get player health
        return health;
    }
    
    public void addHealth(int hp) { // Add health to player based on given HP
        this.health += hp;
    }
}

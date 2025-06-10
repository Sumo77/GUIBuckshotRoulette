/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BSRCodeLogic;


import BSRCodeLogic.PowerUps.BlindReloadGun;
import BSRCodeLogic.PowerUps.DoubleDamage;
import BSRCodeLogic.PowerUps.Cigarette;
import BSRCodeLogic.PowerUps.PowerUp;
import BSRCodeLogic.PowerUps.ReverseOrder;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;

/**
 * @author Davor Georgiev
 * @author Summer Harris
 */

public final class PowerUpManager { // Controller of all of the PowerUp classes
    
    // Initliase Constants and Variables
        public static final int MAXPOWERUPS = 3;
    public LinkedHashSet<String> currPowerUps;
    
    public PowerUpManager() { // Constructor
        currPowerUps = generatePowerUps(); // Assign immediately!
    }
    
    public LinkedHashSet<String> generatePowerUps() { // Creates Power-Ups hashset with all the current powerup options
        LinkedHashSet<String> powerUpSet = new LinkedHashSet<>();
        powerUpSet.add("Cigarette");
        powerUpSet.add("Reverse Order");
        powerUpSet.add("Double Damage");
        powerUpSet.add("Blind Reload Gun");
        return powerUpSet;
    }
    
    public ArrayList<String> recievePowerUps() { // Randomly generates 3 Power-Ups from the hashset and adds them to a set player 'consumable' list
        ArrayList<String> playerPowerUps = new ArrayList<>();
        ArrayList<String> powerUpList = new ArrayList<>(currPowerUps);
        Random random = new Random();
        for (int i = 0; i < MAXPOWERUPS; i++) {
            int random_powerup_allocation = random.nextInt(currPowerUps.size());
            playerPowerUps.add(powerUpList.get(random_powerup_allocation));
        }
        return playerPowerUps;
    }
    
    public void checkRestock(ArrayList alivePlayerList) { // Checks if powerups for each player needs to be restocked
        for (int i = 0; i < alivePlayerList.size(); i++) { // Loop through every player and check
            Player currPlayer = (Player) alivePlayerList.get(i);
            int playerPowerUpSize = currPlayer.getPowerUps().size();
            if (playerPowerUpSize < MAXPOWERUPS) { // If player has less Power-Ups than the max size, perform restock.
                restockPowerUps(currPlayer, playerPowerUpSize);
            }
        }
    }
    
    public void restockPowerUps(Player player, int powerUpAmount) { // restock powerups for all
        ArrayList<String> newPowerUps = recievePowerUps();
        int missingPowerUps = MAXPOWERUPS - powerUpAmount; // Calculate missing powerups (how many need to be assigned)
        
        for (int powerUp = 0; powerUp < missingPowerUps && powerUp < newPowerUps.size(); powerUp++) {
            player.addPowerUp(newPowerUps.get(powerUp)); // Loop and assign powerup from powerUp list for number of times of missing Power-Ups for the player
        }
        
        //displayPowerUps(player); // Display new power-ups to keep the player informed
    }
    
//    public void displayPowerUps(Player player) { // Displays the power-ups of the player
//        System.out.println(player.getUsername() + "'s Power-Ups are: " + player.getPowerUps());
//    }
    
    public String usePowerUp(Player player, String powerUpName, Round round, ArrayList<Player> alivePlayerList) {
        PowerUp powerUp = null;
        
        switch(powerUpName) { // Get the selected PowerUp and tigger the correct event
            case "Cigarette":
                powerUp = new Cigarette(); // Cigarette - heals player
                break;
            case "Reverse Order":
                powerUp = new ReverseOrder(); // Reverse Order
                break;
            case "Double Damage":
                powerUp = new DoubleDamage(); // Double Damage - For one of players shots
                break;
            case "Blind Reload Gun":
                powerUp = new BlindReloadGun(); // Blind Reload Gun - Resets/Reloads all bullets and blanks in the gun randomly
                break;
            default:
//                System.out.println("You don't have the " + powerUpName + " Power Up!");
                return "You don't have the " + powerUpName + " Power Up!";
                
        }
        
        String message = powerUp.usePowerUp(player, round, alivePlayerList); // Trigger powerUp
        return message;
    }
}

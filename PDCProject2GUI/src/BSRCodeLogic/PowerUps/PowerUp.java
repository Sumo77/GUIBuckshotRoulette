/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BSRCodeLogic.PowerUps;

import BSRCodeLogic.Player;
import BSRCodeLogic.Round;
import java.util.ArrayList;

/**
 * @author Davor Georgiev
 * @author Summer Harris
 */

public abstract class PowerUp { // Template Class for all of the powerups - Parent Class
    // Initliase Variable
    private String name;
    
    public PowerUp(String name) { // Constructor for PowerUp class
        this.name = name; // Assign name of Powerup
    }
    
    public String getName() { // Get name of PowerUp
        return name;
    }
    
    // Method to be overrided - The PowerUp's ability
    public abstract String usePowerUp(Player player, Round round, ArrayList<Player> alivePlayerList);
    
}

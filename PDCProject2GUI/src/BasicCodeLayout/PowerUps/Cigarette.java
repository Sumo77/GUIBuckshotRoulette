/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BasicCodeLayout.PowerUps;

import BasicCodeLayout.*;
import java.util.ArrayList;

/**
 * @author Davor Georgiev
 * @author Summer Harris
 */

public class Cigarette extends PowerUp { // Cigarette Powerup - Child Class
    
    public Cigarette() { // Constructor
        super("Cigarette");
    }

    @Override
    public void usePowerUp(Player player, Round round, ArrayList<Player> alivePlayers) { // Heal the player by the designated amount
        if (player.checkHealth() < player.MAX_HEALTH) { // Unless the player is at max health
            player.addHealth(1); // Heal by 1 point
            System.out.println("Cigarette used, " + player.getUsername() + " has been healed by 1!");
        } else {
            System.out.println("Cigarette used, but " + player.getUsername() + " is already at max health!");
        }
    }
}

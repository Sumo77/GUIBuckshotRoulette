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

public class BlindReloadGun extends PowerUp { // Blind Reload Gun Powerup - Child Class
    
    public BlindReloadGun() { // Constructor
        super("BlindReloadGun");
    }

    @Override
    public void usePowerUp(Player player, Round round, ArrayList<Player> alivePlayers) { // Reload the Gun with a new random set of rounds, and display
        //System.out.println(round.currRoundList);
        round.currRoundList.clear(); // Erase old list to ensure overrides correctly
        round.generateRounds(); // Generate a new set of rounds
        System.out.println("-------------------------------------");
        System.out.println("The Gun has been reloaded and re-organized!"); // Reflect change to player
        round.displayRounds(); // Display new set of rounds to player
        System.out.println("-------------------------------------");
        //System.out.println(round.currRoundList);
    }
}

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

public class BlindReloadGun extends PowerUp { // Blind Reload Gun Powerup - Child Class
    
    public BlindReloadGun() { // Constructor
        super("BlindReloadGun");
    }

    @Override
    public String usePowerUp(Player player, Round round, ArrayList<Player> alivePlayers) { // Reload the Gun with a new random set of rounds, and display
        //System.out.println(round.currRoundList);
        round.currRoundList.clear(); // Erase old list to ensure overrides correctly
        round.generateRounds(); // Generate a new set of rounds
        return "The Gun has been reloaded and re-organized!"; // Return completion ! (no display rounds - blind !)
    }
}

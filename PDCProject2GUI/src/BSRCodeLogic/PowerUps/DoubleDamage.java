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

public class DoubleDamage extends PowerUp { // Double Damage PowerUp - Child Class
    
    public DoubleDamage() { // Constructor
        super("DoubleDamage");
    }

    @Override
    public String usePowerUp(Player player, Round round, ArrayList<Player> alivePlayers) {
        player.doubleDamage = true; // Activate double damage for the player
        return "player.getUsername() + ' now has double damage!'"; // Return completion (player double damsge on!)
    }
}

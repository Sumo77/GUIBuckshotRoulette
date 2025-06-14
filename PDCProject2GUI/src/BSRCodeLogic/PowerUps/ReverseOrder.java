/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BSRCodeLogic.PowerUps;

import BSRCodeLogic.Player;
import BSRCodeLogic.Round;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Davor Georgiev
 * @author Summer Harris
 */

public class ReverseOrder extends PowerUp { // Reverse Order Powerup - Child Class
    
    public ReverseOrder() {
        super("ReverseOrder");
    }

    @Override
    public String usePowerUp(Player player, Round round, ArrayList<Player> alivePlayers) { // Reverse the order of the players playing
        Collections.reverse(alivePlayers); // Reverse the player list order
        return "The order of players has been reversed!"; // Return completion ! (order reversed!)
    }
}

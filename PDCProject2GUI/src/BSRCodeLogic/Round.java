/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BSRCodeLogic;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Davor Georgiev
 * @author Summer Harris
 */

public final class Round { // Controls all of the Bullet / Blanks mechanics, events and information
    
    // Initalise Constants and Variables
    public static final int NORMAL_BULLET_DMG = 1;
    public static final int MAXROUNDS = 5;
    public ArrayList currRoundList;
    final char liveRound = 'x';
    final char blankRound = 'o';
    
    
    public void generateRounds() { // Generates certain number of bullets and blanks in a random order and is saved
        ArrayList roundsList = new ArrayList();
        Random random = new Random();
        for (int i = 0; i < MAXROUNDS; i++) {
            int random_round_allocation = random.nextInt(1, 10);
            if (random_round_allocation <= 5) { // if random number less than or equal to 5, add live round to array
                roundsList.add(liveRound);
            }
            else { // if random number more than 5, add blank round to array
                roundsList.add(blankRound);
            }
        }
        this.currRoundList = roundsList;
    }
    
    public void removeBlankOrBullet() { // Remove round from round list
        currRoundList.remove(0);
    }
    
    public boolean checkReload() {
        if (currRoundList.isEmpty()) { // Check if the gun is empty
            reloadGun(); // Reload gun, if it is empty
            return true;
        }
        return false;
    }
    
    public String reloadGun() { // Reload Gun with a new set of Rounds
        if (currRoundList.isEmpty()) { // Check again if round size is 0 / empty (Gun is empty)
            generateRounds(); // Generate a new set of rounds
            return "All loaded rounds in the Gun depleted; The Gun has been reloaded"; // Display message and rounds
        }
        return null; // No need to reload ! - check not passed
    }
    
    public void bulletShot(Player currentPlayer, Player target) { // Performs the sequenece of events that triggers if bullet is shot from Gun
        if (currentPlayer.doubleDamage == true) { // If player has doubleDamage,
            target.removeHealth(NORMAL_BULLET_DMG * 2); // Double the damage the target takes
            currentPlayer.doubleDamage = false;
            removeBlankOrBullet(); // Remove bullet from the Gun (it has been shot)
            //return target.getUsername() + " takes " + NORMAL_BULLET_DMG * 2 + " damage!"; // Print result of damage !
        } else { // Normal damage
            target.removeHealth(NORMAL_BULLET_DMG); // Make the target sustain normal damage
            removeBlankOrBullet(); // Remove bullet from the Gun (it has been shot)
            //return target.getUsername() + " takes " + NORMAL_BULLET_DMG + " damage!"; // Print result of damage !
        }
    }
    
    public String checkCurrentRound(int round, ArrayList roundList) { // Check whetehr the current round is a bullet or a blank
        char curRound = (char) roundList.get(round);
        if (curRound == liveRound) {
            return "bullet";
        } else if (curRound == blankRound) {
            return "blank";
        }
        return null;
    }
    
    public ArrayList organiseRounds() { // Organise them into a new array, that displays them to be bullets, then blanks, order is unknown
        ArrayList currRounds = new ArrayList();
        
        for (int r = 0; r < currRoundList.size(); r++) {
            String curRound = checkCurrentRound(r, currRoundList);
            if ("bullet".equals(curRound)) { // If the round is a bullet, show
                currRounds.add(liveRound);
            }
        }
        for (int r = 0; r < currRoundList.size(); r++) {
            String curRound = checkCurrentRound(r, currRoundList);
            if ("blank".equals(curRound)) { // If the round is a bullet, show
                currRounds.add(blankRound);
            }
        }
        return currRounds;
    }
}

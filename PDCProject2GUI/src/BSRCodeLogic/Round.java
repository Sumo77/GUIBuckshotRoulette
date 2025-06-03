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
    
//    public void displayRounds() { // Display the information of the current rounds (bullets / blanks) to the players
//        //System.out.println(currRoundList); // For testing purposes
//        int num_bullets = 0;
//        int num_blanks = 0;
//        for (int round = 0; round < currRoundList.size(); round++) {
//            String curRound = checkCurrentRound(round);
//            if ("bullet".equals(curRound)) { // If the round is a bullet, count bullet
//                num_bullets += 1;
//            } else if ("blank".equals(curRound)) { // If the round is a blank, count blank
//                num_blanks += 1;
//            }
//        }
//        if (num_bullets == 1) { // Print out statement of info about rounds - Difference in grammar for printing out each statement (bullets/bullet)
//            System.out.println("The Gun is loaded with: " + num_bullets + " bullet and " + num_blanks + " blanks"); 
//        } else if (num_blanks == 1) {
//            System.out.println("The Gun is loaded with: " + num_bullets + " bullets and " + num_blanks + " blank"); 
//        } else {
//            System.out.println("The Gun is loaded with: " + num_bullets + " bullets and " + num_blanks + " blanks");   
//        }
//    }
    
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
        if (currRoundList.size() <= 0) { // Check again if round size is 0 (Gun is empty)
//            System.out.println("-------------------------------------");
            generateRounds(); // Generate a new set of rounds
//            System.out.println("All loaded rounds in the Gun depleted; The Gun has been reloaded"); // Print reult of reload
//            displayRounds(); // Display updated info on new set of rounds
        }
        return "Complete";
    }
    
    public void bulletShot(Player currentPlayer, Player target) { // Performs the sequenece of events that triggers if bullet is shot from Gun
        if (currentPlayer.doubleDamage == true) { // If player has doubleDamage,
            target.removeHealth(NORMAL_BULLET_DMG * 2); // Double the damage the target takes
            System.out.println(target.getUsername() + " takes " + NORMAL_BULLET_DMG * 2 + " damage!"); // Print result of damage !
            currentPlayer.doubleDamage = false;
        } else { // Normal damage
            target.removeHealth(NORMAL_BULLET_DMG); // Make the target sustain normal damage
            System.out.println(target.getUsername() + " takes " + NORMAL_BULLET_DMG + " damage!"); // Print result of damage !
        }
        removeBlankOrBullet(); // Remove bullet from the Gun (it has been shot)
    }
    
    public String checkCurrentRound(int round) { // Check whetehr the current round is a bullet or a blank
        char curRound = (char) currRoundList.get(round);
        if (curRound == liveRound) {
            return "bullet";
        } else if (curRound == blankRound) {
            return "blank";
        }
        return null;
    }

    
}

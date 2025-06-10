package BSRCodeLogic;

import java.util.ArrayList;

import BSRGameDatabase.DatabaseManager;
import BSRGameDatabase.WinTableManager;

public class GameLogic { // Main Game Logic - Pulls all together

    public static final int MAX_PLAYER_AMOUNT = 4;
    public static final int MAX_ACTION_NUM = 3;
    
    public ArrayList<Player> alivePlayerList = new ArrayList<>(); // player list - only contains alive / active 
    public int numPlayers;
    public int currPlayerNum;
    
    public boolean winner;
    
    public static DatabaseManager dbManager = new DatabaseManager(); // Create connection
    public static WinTableManager winManager = new WinTableManager(dbManager); // Connect to winsTable


//    private static int setupPlayers(Scanner scan, PowerUpManager powerUps) { // Sets up and displays all player information
//        numPlayers = getPlayerCount(scan); // Get and check number of players
//        return numPlayers; // return player number
//    }

//    private static int getPlayerCount(Scanner scan) { // Retrieve and check for valid number of players
//        numPlayers = 0;
//        boolean validPlayerAmount = false; 
//        
//        while (!validPlayerAmount) {
//            
//            if (scan.hasNextInt()) {
//                numPlayers = scan.nextInt();
//                scan.nextLine();
//                
//                if (numPlayers >= 2 && numPlayers <= MAX_PLAYER_AMOUNT) {
//                    validPlayerAmount = true;
//                } else {
//                    System.out.println("Invalid number. Please enter a player amount from 2 players minimum to " + MAX_PLAYER_AMOUNT + " players maximum:");
//                }
//                
//            } else {
//                System.out.println("Invalid input. Please enter a player amount from 2 players minimum to " + MAX_PLAYER_AMOUNT + " players maximum:");
//                scan.nextLine();
//            }
//        }
//        return numPlayers; // Return num players
//    }
    
    public void createPlayer(String username, PowerUpManager powerUps) {
        Player player = new Player(username, winManager);
        alivePlayerList.add(player);
        
        if (player.playerExists()) { // Check if player exists in winsFile, if it does, retrieve wins, else create player in file
                player.retrievePlayerWins();
            } else {
                player.addNewPlayerToWins();
            }
        assignPlayerPowerUps(player, powerUps);
    }
    
    public boolean isUniqueUsername(String username) { // Return condition for a player to not have the same username as another player
        for (Player player : alivePlayerList) {
            if (player.getUsername().equalsIgnoreCase(username)) {
                return false; // Can't have that username, it has been taken (even if it is in lower caps)
            }
        }
        return true; // That username is not taken, all good.
    }

    public ArrayList<String> assignPlayerPowerUps(Player player, PowerUpManager powerUps) {  // Assign a set of powerups to each player 
        ArrayList<String> playerPowerUps = powerUps.recievePowerUps();
        
        for (String powerUp : playerPowerUps) {
            player.addPowerUp(powerUp);
        }
        return playerPowerUps; // Display powerups
        
    }

    public boolean isWinner() { // Return condition for a player to classify as a winner
        if (alivePlayerList.size() == 1) {
            return true; // One left, winner !
        } else {
            return false; // Still more than one person left, no winner
        }
    }
    
    public Player announceWinner() { // Print out the winner of the match
        Player winner = alivePlayerList.get(0);
        winner.updateWins(1);
        return winner;
    }
    
    public ArrayList displayWinners() {
        return winManager.displayWinsTable();
        
    }
    
    public ArrayList<Player> getAlivePlayers() {
        return alivePlayerList;
    }

    public ShootResult shootAction(Player currentPlayer, Player targetPlayer, Round round) { // Check whether player shoots a bullet or a blank and proceed accordingly
        String bulletOrBlank = round.checkCurrentRound(0, round.currRoundList); // check round type
        String shot;
        boolean deathResult = false;
        boolean reloadResult;
        
        if ("bullet".equals(bulletOrBlank)) { // if it is a bullet
            round.bulletShot(currentPlayer, targetPlayer);
            
            reloadResult = checkReloadAll(round, new PowerUpManager());
            
            deathResult = deathCheck(targetPlayer);
            
            shot = "bullet";
            
        } else { // if it is a blank
            round.removeBlankOrBullet();
            
            reloadResult = checkReloadAll(round, new PowerUpManager());
            
            if (currentPlayer.doubleDamage) {
                currentPlayer.doubleDamage = false;
            }
            
            if (targetPlayer == currentPlayer) {
                shot = "blank-self";
                
            } else {
                shot = "blank-other";
            }
        }
        
        return new ShootResult(shot, deathResult, reloadResult); // return all logic !
    }

    public boolean checkReloadAll(Round round, PowerUpManager powerUps) { // Reload gun and powerups
        if (round.checkReload()) { // If gun was reloaded, reload powerups
            powerUps.checkRestock(alivePlayerList);
            return true;
        }
        return false;
    }
    
    public boolean deathCheck(Player curTargetPlayer) { // Check if player's health is at 0 (dead)
        if (curTargetPlayer.checkHealth() <= 0) {
            alivePlayerList.remove(curTargetPlayer);
            return true;
        }
        return false;
    }
    
    public String powerUpAction(Player currentPlayer, String chosenPower, PowerUpManager powerUps, Round round) {
        String powerUpResult = powerUps.usePowerUp(currentPlayer, chosenPower, round, alivePlayerList);
        currentPlayer.usePowerUp(chosenPower);
        return powerUpResult;
    }
    
}

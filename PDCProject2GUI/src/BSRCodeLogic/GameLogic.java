/*



convert this game class to obly logic
link it to apropriate gui



*/

package BSRCodeLogic;

import java.util.Scanner;
import java.util.ArrayList;

import BSRGameDatabase.DatabaseManager;
import BSRGameDatabase.WinTableManager;

public class GameLogic { // Main Game Logic - Pulls all together

    public static final int MAX_PLAYER_AMOUNT = 4;
    public static final int MAX_ACTION_NUM = 3;
    
    public static ArrayList<Player> alivePlayerList = new ArrayList<>(); // player list - only contains alive / active 
    public static int numPlayers;
    public int currPlayerNum;
    
    public boolean winner;
    
    public static DatabaseManager dbManager = new DatabaseManager(); // Create connection
    public static WinTableManager winManager = new WinTableManager(dbManager); // Connect to winsTable
    
    public static void main(String[] args) {
        
        // Initialise Classes and Imports
        Scanner scan = new Scanner(System.in);
        Round round = new Round();
        PowerUpManager powerUps = new PowerUpManager();
        
        System.out.println("Welcome to Buckshot Roulette");
        
        boolean gameLoop = true;
        
        while (gameLoop) { // Start the main game loop
            numPlayers = setupPlayers(scan, powerUps); // Setup Players

            round.generateRounds(); // Generate Gun Round
            System.out.println("-------------------------------------");
            //round.displayRounds(); // Display Gun Round

            playGame(round, powerUps); // Start Game

            // gameLoop = commenceGameEnd(scan); // Will end the main game loop on commence of method
        }
    }
    
    public void makePlayersTest() { // TEST GAME ONLY - REMOVE LATER
        PowerUpManager powerUps = new PowerUpManager();
        
        Player player1 = new Player("Davor", winManager);
        alivePlayerList.add(player1);
        assignPlayerPowerUps(player1, powerUps);
        
        Player player2 = new Player("Seva", winManager);
        alivePlayerList.add(player2);
        assignPlayerPowerUps(player2, powerUps);
        
        Player player3 = new Player("Cody", winManager);
        alivePlayerList.add(player3);
        assignPlayerPowerUps(player3, powerUps);
        
        Player player4 = new Player("Summa", winManager);
        alivePlayerList.add(player4);
        assignPlayerPowerUps(player4, powerUps);
    }
    
    
    public ArrayList<Player> getAlivePlayers() {
        return alivePlayerList;
    }
    
    public ArrayList<Player> makeAlivePlayers() {
        makePlayersTest();
        return alivePlayerList;
    }

    private static int setupPlayers(Scanner scan, PowerUpManager powerUps) { // Sets up and displays all player information
        // System.out.println("How many players are playing? (Player amount must be from 2 minimum to " + MAX_PLAYER_AMOUNT + " maximum):");

        numPlayers = getPlayerCount(scan); // Get and check number of players
        // initializePlayers(scan, powerUps, numPlayers); // Create players

        return numPlayers; // return player number
    }

    private static int getPlayerCount(Scanner scan) { // Retrieve and check for valid number of players
        numPlayers = 0;
        boolean validPlayerAmount = false; 
        
        while (!validPlayerAmount) {
            
            if (scan.hasNextInt()) {
                numPlayers = scan.nextInt();
                scan.nextLine();
                
                if (numPlayers >= 2 && numPlayers <= MAX_PLAYER_AMOUNT) {
                    validPlayerAmount = true;
                } else {
                    System.out.println("Invalid number. Please enter a player amount from 2 players minimum to " + MAX_PLAYER_AMOUNT + " players maximum:");
                }
                
            } else {
                System.out.println("Invalid input. Please enter a player amount from 2 players minimum to " + MAX_PLAYER_AMOUNT + " players maximum:");
                scan.nextLine();
            }
        }
        return numPlayers; // Return num players
    }

//    public static void initializePlayers(Scanner scan, PowerUpManager powerUps, int numPlayers) { // Create and initialize a list of players 
//        for (int playerNum = 0; playerNum < numPlayers; playerNum++) {
//            
//            String userName = getUniqueUsername(scan, playerNum); // get and check for unique username (not currently being used)
//            
//            Player player = new Player(userName, winManager); // Create player
//            alivePlayerList.add(player); // Add player to alive player list
//            
//            if (player.playerExists()) { // Check if player exists in winsFile, if it does, retrieve wins, else create player in file
//                player.retrievePlayerWins();
//            } else {
//                player.addNewPlayerToWins();
//            }
//            assignPlayerPowerUps(player, powerUps); // Assign powerups to player
//        }
//    }
    
    public static void createPlayer(String username, PowerUpManager powerUps) {
        Player player = new Player(username, winManager);
        alivePlayerList.add(player);
        
        if (player.playerExists()) { // Check if player exists in winsFile, if it does, retrieve wins, else create player in file
                player.retrievePlayerWins();
            } else {
                player.addNewPlayerToWins();
            }
        assignPlayerPowerUps(player, powerUps);
    }

//    private static String getUniqueUsername(Scanner scan, int playerIndex) { // Ask for a username and check if there is another player with the same 
//        String userName = null;
//        
//        boolean validUsername = false;
//        
//        while (!validUsername) {
//            System.out.println("Please enter a username for Player " + (playerIndex + 1) + ":");
//            userName = scan.nextLine();
//            
//            if (isUniqueUsername(userName)) {
//                validUsername = true;
//            } else {
//                System.out.println("Someone is using this username. Please choose another");
//                scan.nextLine();
//            }
//        }
//        return userName;
//    }
    
    public static boolean isUniqueUsername(String username) { // Return condition for a player to not have the same username as another player
        for (Player player : alivePlayerList) {
            if (player.getUsername().equalsIgnoreCase(username)) {
                return false; // Can't have that username, it has been taken (even if it is in lower caps)
            }
        }
        return true; // That username is not taken, all good.
    }

    private static ArrayList<String> assignPlayerPowerUps(Player player, PowerUpManager powerUps) {  // Assign a set of powerups to each player 
        ArrayList<String> playerPowerUps = powerUps.recievePowerUps();
        
        for (String powerUp : playerPowerUps) {
            player.addPowerUp(powerUp);
        }
        return playerPowerUps; // Display powerups
        
    }

    private static void playGame(Round round, PowerUpManager powerUps) { // Control player turn loop and determine a winner
        while (!isWinner()) {
            int currPlayer = 0;
            
            while (alivePlayerList.size() > 1 && currPlayer < alivePlayerList.size()) { // Loop for each players turn
                
                Player currentPlayer = alivePlayerList.get(currPlayer);

                currPlayer = alivePlayerList.indexOf(currentPlayer) + 1; // Update player order before continuing to next player (incase reversed or changed)
            }
        }
        
        announceWinner();
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
    

    public static boolean isWinner() { // Return condition for a player to classify as a winner
        if (alivePlayerList.size() == 1) {
            return true; // One left, winner !
        } else {
            return false; // Still more than one person left, no winner
        }
    }
    
    private static void announceWinner() { // Print out the winner of the match
        Player winner = alivePlayerList.get(0);
        winner.updateWins(1);
        winManager.displayWinsTable();
    }
    
}

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
        Player player1 = new Player("Player 1", winManager);
        alivePlayerList.add(player1);
        Player player2 = new Player("Player 2", winManager);
        alivePlayerList.add(player2);
        Player player3 = new Player("Player 3", winManager);
        alivePlayerList.add(player3);
        Player player4 = new Player("Player 4", winManager);
        alivePlayerList.add(player4);
    }
    
    
    public ArrayList<Player> getAlivePlayers() {
        makePlayersTest(); // Remove Later
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
                
//                System.out.println("-------------------------------------");
//                printHealth();
//                System.out.println("-------------------------------------");
//
//                System.out.println(currentPlayer.getUsername() + "'s Turn!");
//                System.out.println("-------------------------------------");
//                
//                playerTurn(currentPlayer, round, powerUps); // Start player turn

                currPlayer = alivePlayerList.indexOf(currentPlayer) + 1; // Update player order before continuing to next player (incase reversed or changed)
            }
        }
        
        announceWinner();
    }
    
//    public static void printHealth() { // Print health of alive players
//        for (Player player : alivePlayerList) {
//            System.out.println(player.getUsername() + " || Health : " + player.checkHealth());
//        }
//    }
    
//    private static void playerTurn(Player currentPlayer, Round round, PowerUpManager powerUps) { // Commence players action of choice for their turn
//        Scanner scan = new Scanner(System.in);
//        boolean turnComplete = false;
//
//        while (!turnComplete) {
//            System.out.println("Choose an action");
//            System.out.println("-------------------------------------");
//            System.out.println("Type the corresponding number to: (1) Shoot, (2) Power-Up");
//            System.out.println("-------------------------------------");
//
//            int action = getPlayerAction(scan, MAX_ACTION_NUM); // Check valid action (input)
//            turnComplete = performPlayerAction(currentPlayer, action, round, powerUps); // Commence action
//        }
//    }

//    private static int getPlayerAction(Scanner scan, int actionNum) { // Return the action a player chose with error handling
//        int action = 0;
//        
//        boolean validAction = false;
//        
//        while (!validAction) {
//            if (scan.hasNextInt()) {
//                action = scan.nextInt();
//                scan.nextLine();
//                
//                if (action >= 1 && action <= actionNum) {
//                    validAction = true;
//                } else {
//                    System.out.println("Invalid number. Please enter a number corresponding to the action you wish to choose.");
//                }
//            } else {
//                System.out.println("Invalid input. Please enter a number corresponding to the action you wish to choose.");
//                scan.nextLine();
//            }
//        }
//        
//        return action;
//    }

//    private static boolean performPlayerAction(Player currentPlayer, int action, Round round, PowerUpManager powerUps) { // Commence either shooting or powerUp action
//        switch (action) {
//            case 1:
//                return shootAction(currentPlayer, round);
//            case 2:
//                powerUpAction(currentPlayer, powerUps, round);
//                return false;
//            default:
//                return false;
//        }
//    }

    private static boolean shootAction(Player currentPlayer, Player targetPlayer, Round round) { // Check whether player shoots a bullet or a blank and proceed accordingly
        // System.out.println("Please type the corresponding number of the target you wish to shoot: ");
        // printTargetOptions(currentPlayer);

        
        String bulletOrBlank = round.checkCurrentRound(0, round.currRoundList); // check round type

        if ("bullet".equals(bulletOrBlank)) { // if it is a bullet
            // System.out.println("-------------------------------------");
            // System.out.println("BANG! The Gun has fired a bullet at " + targetPlayer.getUsername());
            round.bulletShot(currentPlayer, targetPlayer);
            checkReloadAll(round, new PowerUpManager());
            deathCheck(targetPlayer);
            return true;
            
        } else { // if it is a blank
            // System.out.println("CHK-- The Gun fired a blank round at " + targetPlayer.getUsername());
            round.removeBlankOrBullet();
            checkReloadAll(round, new PowerUpManager());
            if (currentPlayer.doubleDamage) {
                currentPlayer.doubleDamage = false;
            }
            if (targetPlayer == currentPlayer) {
                // System.out.println("As you have have successfully fired a blank at yourself, you may continue your turn:");
            }
            return !targetPlayer.equals(currentPlayer);
        }
    }
    
//    public static void printTargetOptions(Player currentPlayer) { // Display players able to be shot/targetted
//        for (int alivePlayerNum = 0; alivePlayerNum < alivePlayerList.size(); alivePlayerNum++) {
//            Player curAlivePlayer = alivePlayerList.get(alivePlayerNum);
//            System.out.println((alivePlayerNum + 1) + ": " + curAlivePlayer.getUsername() + (currentPlayer.equals(curAlivePlayer) ? " (Yourself)" : ""));
//        }
//    }
    
    public static Player getTarget() { // Target a player to shoot
        Scanner scan = new Scanner(System.in);
        int targetPlayerNum = 0;
        
        boolean validTarget = false;
        
        while (!validTarget) {
            
            if (scan.hasNextInt()) {
                targetPlayerNum = scan.nextInt();
                scan.nextLine();
                
                if (targetPlayerNum > 0 && targetPlayerNum <= alivePlayerList.size()) {
                    validTarget = true;
                } else {
                    System.out.println("Invalid input. Please enter a valid number corresponding to the player you wish to target, as displayed above.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number corresponding to the player you wish to target, as displayed above.");
                scan.nextLine();
            }
        }
        return alivePlayerList.get(targetPlayerNum - 1); // List Index is 1 back from the choice of the player
    }
    
    public static void checkReloadAll(Round round, PowerUpManager powerUps) { // Reload gun and powerups
        if (round.checkReload()) { // If gun was reloaded, reload powerups
            System.out.println("-------------------------------------");
            System.out.println("All players Power-Ups are stocked to full.");
            powerUps.checkRestock(alivePlayerList);
        }
    }
    
    public static void deathCheck(Player curTargetPlayer) { // Check if player's health is at 0 (dead)
        if (curTargetPlayer.checkHealth() <= 0) {
            alivePlayerList.remove(curTargetPlayer);
            System.out.println("-------------------------------------");
            System.out.println(curTargetPlayer.getUsername() + " has died!");
        }
    }

//    private String powerUpAction(Player currentPlayer, PowerUpManager powerUps, Round round) { // Take player input on which power up to use, check if player has powerups left and error handle spelling
//        ArrayList<String> currPowerUps = currentPlayer.getPowerUps();
//        if (currPowerUps.isEmpty()) { // No powerups to use !
//            //System.out.println("You have no Power-Ups left!");
//        } else { // Give option to choose and use powerup
//            //powerUps.displayPowerUps(currentPlayer);
//            System.out.println("Which do you wish to use? (Please type the full name of the Power-Up as displayed above):");
//            Scanner scan = new Scanner(System.in);
//            String chosenPower = scan.nextLine();
//
//            if (currentPlayer.getPowerUps().contains(chosenPower)) { // if power exists, use it
//                powerUps.usePowerUp(currentPlayer, chosenPower, round, alivePlayerList);
//                currentPlayer.usePowerUp(chosenPower);
//            } else { // No exist :(
//                //System.out.println("Input does not match an existing Power-Up. Please ensure you type the full name of the Power-Up as displayed above.");
//            }
//        }
//    }

    public static boolean isWinner() { // Return condition for a player to classify as a winner
        if (alivePlayerList.size() == 1) {
            return true; // One left, winner !
        } else {
            return false; // Still more than one person left, no winner
        }
    }
    
    private static void announceWinner() { // Print out the winner of the match
        Player winner = alivePlayerList.get(0);
//        System.out.println("+-----------------------------------+");
//        System.out.println(winner.getUsername() + " is the (sole) winner!");
//        winner.updateWins(1);
//        System.out.println("+-----------------------------------+");
//        System.out.println("Winner's Leaderboard:");
          winManager.displayWinsTable();
//        System.out.println("+-----------------------------------+");
    }
}

//    private static boolean commenceGameEnd(Scanner scan) { // Ask for a restart or exit program, reload gun and powerups if restart and delete previous
//        System.out.println("Would you like to reset the table for a different match? (Play again?)");
//        System.out.println("Type the corresponding number to: (1) Exit, (2) Play again");
//
//        int choice = getPlayerAction(scan, 2);
//        if (choice == 1) {
//            // System.out.println("Thank you for playing Buckshot Roulette - The gun has been holstered, for now..");
//            return false;
//        } else {
//            // System.out.println("Reloading Gun and setting up table for a new round..");
//            alivePlayerList.clear();
//            return true;
//        }
//    }
//}
//

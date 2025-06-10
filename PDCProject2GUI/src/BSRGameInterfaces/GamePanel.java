
package BSRGameInterfaces;

import BSRCodeLogic.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;

/**
 * @author Davor Georgiev
 * @author Summer Harris
 */

public class GamePanel extends JPanel {
    // Main Imports
    public BuckshotRouletteGUI mainGUI; // Import main gui
    public GameLogic game; // Import game logic
    public Round round; // Import round logic
    public PowerUpManager powerUps; // Import powerUp logic

    // Main Vars
    public ArrayList<Player> alivePlayers; // Holds each of the remaining alive players !
    public int numPlayers; // Number of alive players
    
    public int currentPlayerNum; // Current player index
    public Player currentPlayer; // Current Player
    public boolean targettingPlayer; // Boolean to see if in 'shoot' mode
    
    private final HashMap<Player, PlayerPosition> playerPositions = new HashMap<>(); // Holds the Player and their GUI position around the table (permanent!)
    public enum PlayerPosition { // All positions around table
        LEFT_POS, RIGHT_POS, BOTTOM_POS, TOP_POS
    }
    public final HashMap<Player, JButton> playerButtons = new HashMap<>(); // Player's GUI - Holds Player reference and their GUI button
    
    public final HashMap<Player, JButton> playerPowerUps = new HashMap<>(); // Player's GUI PowerUps - Holds Player reference and their GUI powerUp button
    
    public boolean seeRounds; // Display the rounds ! (True = paint, False = no paint)
    public boolean gameLoop; // Keep the game running !
    public boolean turnComplete = false; // Control whether a turn is 'complete' or not (when shoot blank at another player or bullet)
    public boolean prep = true; // Paint once at start of game!
    
    
    // Labels / Text Field / Buttons - GUIS!
    int TABLE_DIAM = 250; // Everything is centred around the size of the table
    public JLabel mainInfoLabel; // The main instruction/description of events label at top of game
    public JButton shootButton; // Click this button to target a player to shoot
    public JLabel winnerLabel; // Announce winner at end of game !
    public JButton exitButton; // Exit the game at any stage
    
    public boolean explodeImageOn = false; // Temporary explosion by gun
    public boolean smokeImageOn = false; // Temporary smoke image by gun
    
    
    public GamePanel(BuckshotRouletteGUI mainGUI, GameLogic game) { // GamePanel Constructor
        this.mainGUI = mainGUI;
        this.game = game;
        prepGame(); // Setup game components to play !
    }

    private void prepGame() { // Setup components to play!
        round = new Round(); // Create new round object
        powerUps = new PowerUpManager(); // Create new powerUPManager object
        
        round.generateRounds(); // Generate rounds (bullet/blanks)
        
        alivePlayers = game.getAlivePlayers(); // Update alivePlayerList with gameLogic info
        numPlayers = alivePlayers.size(); // Get the number of players playing from ^
        
        assignPositionsToPlayers();  // Assign positions of each player around the table
        
        currentPlayerNum = 0; // Set the first player !
        currentPlayer = alivePlayers.get(currentPlayerNum); // Get the first player !
        
        targettingPlayer = false; // Establish and ensure 'shoot' mode is not enabled
        gameLoop = true; // Start gameLoop !
        seeRounds = true; // Display the rounds !
        
        this.setLayout(null); // Ensure absolute pos for all GUI components
    }
    
    private void assignPositionsToPlayers() { // Assign position to each player around the table
        playerPositions.clear(); // Clear old mappings, just in case

        PlayerPosition[] positions = PlayerPosition.values(); // {LEFT_POS, RIGHT_POS, BOTTOM_POS, TOP_POS} - set position options to be assigned

        for (int i = 0; i < alivePlayers.size(); i++) { // Loop though each player and assign position around the table until no more players or no more positions
            if (i < positions.length) {
                Player player = alivePlayers.get(i); // Get player
                playerPositions.put(player, positions[i]); // Assign position (send player and pos to hashmap)
            }
        }
    }
    
    /*----------------------------------SETUP / PAINT------------------------------------*/
    
    public void setupTable(Graphics g) { // Setup/Draw Table
        int tableX = (getWidth() - TABLE_DIAM) / 2; // Table width
        int tableY = (getHeight() - TABLE_DIAM) / 2; // Table height
        Image table = new ImageIcon("./resources/pumpkinTable.png").getImage(); // Table IMG
        g.drawImage(table, tableX, tableY, this); // Draw table
    }
    
    public void setupPlayers() { // Setup/Draw each Player (button)
        playerButtons.clear(); // Clear mapping, just in case
        alivePlayers = game.getAlivePlayers(); // Refresh players
        
        int playerSize = 60;
        int middleX = getWidth() / 2; // of window
        int middleY = getHeight() / 2;// of window
        int tableDist = TABLE_DIAM - playerSize; // Each Player distance from table

        for (Player player : alivePlayers) {
            PlayerPosition playerPos = playerPositions.get(player); // Get player position (left, top, right, bottom)
            JButton playerButton = new JButton(); // make button for player

            int x = 0;
            int y = 0;
            
            
            switch (playerPos) {
                case LEFT_POS: {
                        // Player 1 - Left
                        x = middleX - tableDist - playerSize / 2;
                        y = middleY - playerSize / 2;
                        
                        ImageIcon orangePlayer = new ImageIcon("./resources/OrangePlayerLEFT.png"); // Orange IMG
                        playerButton.setIcon(orangePlayer); // Set player look
                        
                        break;
                    }
                    
                case RIGHT_POS: {
                        // Player 2 - Right
                        x = middleX + tableDist - playerSize / 2;
                        y = middleY - playerSize / 2;
                        
                        ImageIcon pearPlayer = new ImageIcon("./resources/PearPlayerRIGHT.png"); // Pear IMG
                        playerButton.setIcon(pearPlayer); // Set player look
                        
                        break;
                    }
                    
                case BOTTOM_POS: {
                        // Player 3 - Bottom
                        x = middleX - playerSize / 2;
                        y = middleY + tableDist - playerSize / 2;
                        
                        ImageIcon grapePlayer = new ImageIcon("./resources/GrapePlayerBOTTOM.png"); // Grape IMG
                        playerButton.setIcon(grapePlayer); // Set player look
                        
                        break;
                    }
                    
                case TOP_POS: {
                        // Player 4 - Top
                        x = middleX - playerSize / 2;
                        y = middleY - tableDist - playerSize / 2;
                        
                        ImageIcon applePlayer = new ImageIcon("./resources/ApplePlayerTOP.png"); // Apple IMG
                        playerButton.setIcon(applePlayer); // Set player look
                        
                        break;
                    }
            }
            
            playerButton.setBounds(x, y, playerSize, playerSize); // Set player postion / size

            playerButton.addActionListener(e -> { // Event listener for if player clicked
                if (targettingPlayer) { // If player is going to be selected ! Targetting mode
                    shootPlayer(player); // Trigger player shot !
                }
            });

            hideButtonFeatures(playerButton); // Make it not look like a button lol
            this.add(playerButton); // Add player button to panel
            playerButtons.put(player, playerButton); // Add player and player button to hashtable to save references
            
        }
    }
    
        public void displayPlayerInfo(Graphics g) { // Display hearts and usernames of each player
        alivePlayers = game.getAlivePlayers(); // Refresh alive players
        
        int playerSize = 60;
        int tableX = getWidth() / 2; // of window
        int tableY = getHeight() / 2;// of window
        int tableDist = TABLE_DIAM - playerSize; // Palyer dist from table

        for (Player player : alivePlayers) {
            PlayerPosition playerPos = playerPositions.get(player); // Get player pos
            String username = player.getUsername(); // Get player username
            
            int x = 0;
            int y = 0;
            
            switch (playerPos) {
                case LEFT_POS: {
                        // Player 1 - Left
                        x = tableX - tableDist - playerSize / 2;
                        y = tableY - playerSize / 2;
                        
                        break;
                    }
                    
                case RIGHT_POS: {
                        // Player 2 - Right
                        x = tableX + tableDist - playerSize / 2;
                        y = tableY - playerSize / 2;
                        
                        break;
                    }
                    
                case BOTTOM_POS: {
                        // Player 3 - Bottom
                        x = tableX - playerSize / 2;
                        y = tableY + tableDist - playerSize / 2;
                        
                        break;
                    }
                    
                case TOP_POS: {
                        // Player 4 - Top
                        x = tableX - playerSize / 2;
                        y = tableY - tableDist - playerSize / 2;
                        
                        break;
                    }  
            }
            
            if (player == currentPlayer) { // Colour indicate which player has their turn
                g.setColor(Color.RED); // Current players turn !
            } else {
                g.setColor(Color.BLACK); // Waiting for turn / normal
            }
            
            g.setFont(new Font("Arial", Font.BOLD, 20)); // Set name font
            g.drawString(username, x, y - 10); // Draw name
            displayHearts(player, playerSize, x, y, g); // Display the health of the player (hearts)
        }
    }
        
    public void displayHearts(Player player, int playerSize, int x, int y, Graphics g) { // Display player health (hearts)
        int playerHealth = player.checkHealth(); // Check the health of the player !
        int heartSpacing = 25; // Spacing between each heart
        
        ImageIcon heartIcon = new ImageIcon("./resources/heart.png"); // Heart IMG
        Image scaledHeart = heartIcon.getImage();
        
        for (int i = 0; i < playerHealth; i++) { // For each health, draw heart ! (spaced)
            g.drawImage(scaledHeart, (x - 18) + i * heartSpacing, y + playerSize + 5, this);
        }
    }
    
    public boolean shootPlayer(Player targetPlayer) { // Action of shooting a player
        ShootResult resultShot = game.shootAction(currentPlayer, targetPlayer, round); // Get the result of the the shot of the player
        
        if ("bullet".equals(resultShot.shot)) { // If bullet was shot, state what happened and show explosion, then turn is over
            mainInfoLabel.setText("BANG! The Banana Gun has fired a bullet at " + targetPlayer.getUsername());
            showExplosion();
            turnComplete = true;
            
        } else if ("blank-other".equals(resultShot.shot)) { // If blank was shot at another player, state what happened, show smoke, then turn over
            mainInfoLabel.setText("CHK-- The Banana Gun fired a blank round at " + targetPlayer.getUsername());
            showSmoke();
            turnComplete = true;
            
        } else if ("blank-self".equals(resultShot.shot)) { // If blank was shot at self, state what happened and show smoke, then turn is over
            mainInfoLabel.setText("CHK-- The Banana Gun fired a blank round at.. yourself?! You may continue your turn:");
            showSmoke();
            turnComplete = false;
        }
        
        targettingPlayer = false; // Stop targetting player after shot
        removeActionButtons(); // Remove ability to do anything temp.
        
        repaint();
        
        if (resultShot.playerDied) { // If player dies:
            mainInfoLabel.setText(targetPlayer.getUsername() + " has died!"); // Announce death

            if (playerButtons.get(targetPlayer) != null) { // Remove player GUI
                this.remove(playerButtons.get(targetPlayer)); // Remove from panel
            }
            playerButtons.remove(targetPlayer); // Remove from hash

            if (playerPowerUps.get(targetPlayer) != null) { // Remove player powerUps GUI
                this.remove(playerPowerUps.get(targetPlayer)); // Remove from panel
            }
            playerPowerUps.remove(targetPlayer); // Remove from hash

            //System.out.println(game.getAlivePlayers()); // Testing proper removal

            alivePlayers = game.getAlivePlayers(); // Refresh alive players

            repaint();
            
            isWinner(); // Check for winner !
        }
        
        if (resultShot.roundReloaded) { // If shot was reloaded:
            
            Timer reloadedShot = new Timer(2000, e -> { // Announce gun reload, and display rounds for 2 seconds
                    mainInfoLabel.setText("The Banana Gun has been reloaded and PowerUps for each Player have been restocked!");
                    seeRounds = true;
                    repaint();
                    
                    Timer delayNextTurn = new Timer(2000, ev -> { // Delay switching to next turn for 2 seconds, allows proper refresh and display of rounds
                        playTurn();
                    });
                    
                    delayNextTurn.setRepeats(false);
                    delayNextTurn.start();
                    
            });
            
            reloadedShot.setRepeats(false);
            reloadedShot.start();
            
        } else {
            Timer shootAction = new Timer(2000, e -> { // If no reload, after 2 seconds, play turn !
                playTurn();
            });
            
            shootAction.setRepeats(false);
            shootAction.start();
        }
        
        return turnComplete; // Return whetehr turn was deemed 'complete' or not (determines whether to swap to next player)
    }
    
    public void setupPowerUps() { // Setup the powerups of the Player
        playerPowerUps.clear(); // clear buttons cleaner !
        alivePlayers = game.getAlivePlayers(); // Refresh players
        
        int playerSize = 20; // Size and Positions:
        int middleX = getWidth() / 2;
        int middleY = getHeight() / 2;
        int tableDist = TABLE_DIAM - playerSize; // Player dist from table
        int spacing = 50; // Spacing of basket powerup from player

        for (Player player : alivePlayers) {
            PlayerPosition playerPos = playerPositions.get(player); // Get player pos
            JButton playerPUPButton = new JButton(); // Make player Powerup Button

            int x = 0;
            int y = 0;
            
            
            switch (playerPos) {
                case LEFT_POS: {
                        // Player 1 - Left
                        x = middleX - tableDist - playerSize / 2 - spacing;
                        y = middleY - playerSize / 2;
                        
                        break;
                    }
                    
                case RIGHT_POS: {
                        // Player 2 - Right
                        x = middleX + tableDist - playerSize / 2 + spacing;
                        y = middleY - playerSize / 2;
                        
                        break;
                    }
                    
                case BOTTOM_POS: {
                        // Player 3 - Bottom
                        x = middleX - playerSize / 2 - (spacing + spacing / 2);
                        y = middleY + tableDist - playerSize / 2 - spacing;

                        break;
                    }
                    
                case TOP_POS: {
                        // Player 4 - Top
                        x = middleX - playerSize / 2 + (spacing + spacing / 2);
                        y = middleY - tableDist - playerSize / 2 + spacing;
                        
                        break;
                    }
            }

            playerPUPButton.addActionListener(e -> { // Event listener for if player clicks powerUp basket
                targettingPlayer = false; // Stop targetting players if shooting was previously selected
                
                if (player == currentPlayer) { // Lock the baskets from other players, only if currentPlayer is player, can they access
                    if (player.getPowerUps().isEmpty()) { // If powerups are empty, announce
                        mainInfoLabel.setText("You have no more powerups, " + currentPlayer.getUsername() + "! - You must wait until they refresh on next reload. Click the Banana Gun to shoot!");
                    } else { // Announce and display player's powerups
                        mainInfoLabel.setText("Here are your PowerUps, " + currentPlayer.getUsername() + "!");
                        displayPowerUps(playerPUPButton.getX(), playerPUPButton.getY());
                    }
                } else { // If not players powerup basket (clicked another players), tell them why access didn't work
                    mainInfoLabel.setText("You may only access your own powerups ! - Click the Gun to shoot or your Bag for powerups");
                }
            });
            
            playerPUPButton.setBounds(x, y, 50, 60); // Set pos
            
            ImageIcon pupIcon = new ImageIcon("./resources/basketPUP.png"); // basket IMG
            playerPUPButton.setIcon(pupIcon); // Set look
            
            hideButtonFeatures(playerPUPButton); // Make it not look like a button
            
            this.add(playerPUPButton); // Add to panel
            playerPowerUps.put(player, playerPUPButton); // Add to hash, player and playerPowerUp
            repaint();
        }
    }
    
    public void displayPowerUps(int x, int y) { // Display powerUps
        JPopupMenu powerUpMenu = new JPopupMenu(); // Create popUp interactable menu
        
        for (String powerUp : currentPlayer.getPowerUps()) { // For each powerup the player has, add to the menu
            JMenuItem chosenPower = new JMenuItem(powerUp); // Get powerup / create menu item using powerup
            
            chosenPower.addActionListener(e -> { // Event Listener, If the powerup was chosen/used by player, return and display feedback of powerup
                String powerUpResult = game.powerUpAction(currentPlayer, powerUp, powerUps, round);
                mainInfoLabel.setText(powerUpResult);
                repaint();
                
                Timer powerUpTimer = new Timer(3000, e2 -> { // Wait 3 seconds, announce players turn again !
                    mainInfoLabel.setText("It's still " + currentPlayer.getUsername() + "'s turn! Click the Banana Gun to Shoot or Click your Basket to use a Powerup:");
                    repaint();
                });
                powerUpTimer.setRepeats(false);
                powerUpTimer.start();
            });
            
            powerUpMenu.add(chosenPower); // Add powerup to menu
        }
        
        powerUpMenu.show(this, x, y); // Display menu ontop of player basket
    }
    
    public void setupInfoLabel() { // Setup main label
        mainInfoLabel = new JLabel(); // Create
        
        mainInfoLabel.setBounds(0, 10, getWidth(), 50); // Set pos
        mainInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainInfoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Set boundary
        
        mainInfoLabel.setFont(new Font("Arial", Font.BOLD, 15)); // Set font
        mainInfoLabel.setBackground(Color.WHITE); // Set background colour
        mainInfoLabel.setOpaque(true); // Set background
        // Set first msg
        mainInfoLabel.setText("The Banana Gun has been re-loaded with these unordered rounds: (Red = Bullet), (Blue = Blank)");
        
        this.add(mainInfoLabel); // Add to panel
    }
    
    public void setupWinnerLabel() { // Setup Winner Label do announce Winner at the end
        winnerLabel = new JLabel(); // Create
        
        winnerLabel.setBounds(0, getHeight() / 2 + 200, getWidth(), 50); // Set pos
        winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        winnerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Set boundary
        
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 15)); // Set font
        winnerLabel.setBackground(Color.WHITE); // Set background colour
        winnerLabel.setOpaque(true); // Set background
        
        this.add(winnerLabel); // Add to panel
        winnerLabel.setVisible(false); // Set visability to false
    }
    
    public void setupShootButton() { // Setup shoot button
        shootButton = new JButton(); // Create
        
        shootButton.setSize(100, 40); // Set size of button
        
        int x = (getWidth() - shootButton.getWidth()) / 2; // Set pos
        int y = (getHeight() - shootButton.getHeight()) / 2;
        
        shootButton.setLocation(x, y);
        
        ImageIcon gunIcon = new ImageIcon("./resources/banana.png"); // Banana IMG
        shootButton.setIcon(gunIcon); // Change look to banana!
        
        shootButton.addActionListener(e -> { // Event listener for if button pressed, initiate 'shoot' mode
            mainInfoLabel.setText("Click a player to shoot, " + currentPlayer.getUsername() + "!");
            targettingPlayer = true; // Start targeting player, when player clicked now, they are shot at
        });
        
        hideButtonFeatures(shootButton); // Make it not look like button
        
        this.add(shootButton); // Add to panel
    }
    
    public void hideButtonFeatures(JButton button) { // If all images, revoce if statement later !
        button.setOpaque(false);  // Remove background
        button.setContentAreaFilled(false);
        button.setBorderPainted(false); // Remove border
        button.setFocusPainted(false); // Remove hover over border
    }
    
    public void displayRounds(Graphics g) { // Display the bullet/blanks
        removeActionButtons(); // Remove buttons for now, so no actions can be perfomed whiel reloading.
        
        ArrayList organisedRounds = round.organiseRounds(); // Order the rounds bullents then blanks so they don't give away randomised shooting order
        
        int roundSize = 50; // Round sizes and positions
        int roundSpacing = 7;
        int roundWidth = organisedRounds.size() * (roundSize + roundSpacing);
        int roundX = (getWidth() - roundWidth) / 2;
        int roundY = getHeight() / 2 - roundSize - 50;
        
        // Display casing that holds bullets !
        g.setColor(Color.GRAY);
        g.fillRect(roundX - 10, roundY - 10, roundWidth + 10, roundSize + 20);
        
        // Display Rounds !
        for (int r = 0; r < organisedRounds.size(); r++) {
            String curRound = round.checkCurrentRound(r, organisedRounds); // Get round type
            
            if ("bullet".equals(curRound)) { // If the round is a bullet, show
                g.setColor(Color.RED); // Set colour to red (rep. bullet)
                
            } else if ("blank".equals(curRound)) { // If the round is a blank, show
                g.setColor(Color.BLUE); // Set colour to blue (rep. blank)
            }
            g.fillRect(roundX + r * (roundSize + roundSpacing), roundY, roundSize, roundSize); // Draw round and repeat !
        }
        
        Timer displayTimer = new Timer(5000, e -> { // Display the rounds for 5 seconds, then hide.
            seeRounds = false;
            
            repaint();
            
            playTurn(); // Continue turns !
        });
        displayTimer.setRepeats(false);
        displayTimer.start();
        
    }
    
    public void showSmoke() { // Show smoke image/graphic
        smokeImageOn = true; // Show image
        repaint();
        
        Timer smokeTimer = new Timer(1000, e -> { // Wait 1 sec, hide image
            smokeImageOn = false;
            repaint();
        });
        smokeTimer.setRepeats(false);
        smokeTimer.start();
    }

    
    public void showExplosion() { // Show explosion image/graphic
        explodeImageOn = true; // Show image
        repaint();
        
        Timer explodeTimer = new Timer(1000, e -> { // Wait 1 sec, hide image
            explodeImageOn = false;
            repaint();
        });
        explodeTimer.setRepeats(false);
        explodeTimer.start();
    }

    @Override
    public void paintComponent(Graphics g) { // Paint all components
        super.paintComponent(g); // Clear canvas
        
        Image background = new ImageIcon("./resources/picnicBG.png").getImage(); // Picnic IMG
        g.drawImage(background, 0, 0, this); // Paint background
        
        if (prep) { // Only paitn/ Setup these once per playthrough ! :D
            setupInfoLabel(); // Main label
            setupShootButton(); // Shoot Button / Banana Gun
            setupPlayers(); // Players characters (buttons)
            setupPowerUps(); // Players baskets (buttons)
            exitGameButton(); // Exit button
            setupWinnerLabel(); // Winner label
            prep = false;
        }
        
        setupTable(g); // Setup table !
        displayPlayerInfo(g); // Setup players hearts / strings !
        
        if (seeRounds) {
            displayRounds(g); // Display the rounds, at beginning and when reloading phase only.
        }
        
        if (smokeImageOn) { // Draw smoke image by gun to indicate blank shot
            Image smokeImage = new ImageIcon("./resources/smoke.png").getImage();
            int x = (getWidth() - shootButton.getWidth()) / 2 + 50;
            int y = (getHeight() - shootButton.getHeight()) / 2 - 50;
            g.drawImage(smokeImage, x, y, this);
        }
        if (explodeImageOn) { // Draw explode image by gun to indicate bullet shot
            Image explodeImage = new ImageIcon("./resources/explosion.png").getImage();
            int x = (getWidth() - shootButton.getWidth()) / 2 + 50;
            int y = (getHeight() - shootButton.getHeight()) / 2 - 50;
            g.drawImage(explodeImage, x, y, this);
        }
    }
    
    /*----------------------------------TURNS / ACTION GUI------------------------------------*/
    
    public void playTurn() { // Play a players turn
        
        if (!gameLoop) { // If game finished,
            return; // Cancel play loop, exit !
        }
        
        if (!turnComplete) { // If turn is not over, announce and unlock action buttons to allow turn to commence
            mainInfoLabel.setText("It's " + currentPlayer.getUsername() + "'s turn! Click the Banana Gun to Shoot or Click your Basket to use a Powerup:");
            showActionButtons();
            
        } else { // If turn is complete, move to next player
            nextPlayerTurn();
        }
        
        repaint();
    }
    
    public void showActionButtons() { // Enable the banana gun (shoot button) and powerup baskets
        if (shootButton != null) {
            shootButton.setEnabled(true);
        }
        if (playerPowerUps != null) {
            for (JButton playerPUPButton : playerPowerUps.values()) { // Loop through and enable each basket
                playerPUPButton.setEnabled(true);
            }
        }
    }
    
    public void removeActionButtons() { // Disable the banana gun (shoot button) and powerup baskets
        if (shootButton != null) {
            shootButton.setEnabled(false);
        }
        if (playerPowerUps != null) {
            for (JButton playerPUPButton : playerPowerUps.values()) { // Loop through and disable each basket
                playerPUPButton.setEnabled(false);
            }
        }
    }
    
    public void nextPlayerTurn() { // Move onto the next players turn
        alivePlayers = game.getAlivePlayers(); // Refresh players
        numPlayers = alivePlayers.size(); // Refresh player amount

        currentPlayerNum = (currentPlayerNum + 1) % numPlayers; // Change to next player based on player amount left (indexs)!
        currentPlayer = alivePlayers.get(currentPlayerNum); // Change current players to the new/next player !
        
        turnComplete = false; // Reset turn complete for new player
        
        playTurn(); // Play new/next players turn
    }
    
    public void isWinner() { // Check for winner and commence game end if winner found
        boolean checkWinner = game.isWinner(); // Check for winner, return result of check
        
        if (checkWinner) { // If winner,
            gameLoop = false; // Stop gameLoop
            
            Player winner = game.announceWinner(); // Get who the winner is (last remaining player)
            
            winnerLabel.setText("The Winner is: " + winner.getUsername() + "!"); // Announce winner
            winnerLabel.setVisible(true); // Show winner banner ! :D
            repaint();
            
            Timer winnerTimer = new Timer(3000, e -> { // After 3 seconds, move to the end game panel
                mainGUI.endGame();
            });
            winnerTimer.setRepeats(false);
            winnerTimer.start();
        }
    }
    
    public void exitGameButton() { // Exit (at any time in the game) button !
        exitButton = new JButton("EXIT"); // Create
        
        exitButton.setBounds(getWidth() - 100, getHeight() - 40, 100, 40); // Size and pos
        
        ImageIcon exit = new ImageIcon("./resources/signTemplate.png"); // sign IMG
        exitButton.setIcon(exit); // Set background to sign
        
        exitButton.setFont(new Font("Arial", Font.BOLD, 15)); // Set font
        
        exitButton.setHorizontalTextPosition(SwingConstants.CENTER); // Set font over sign image
        exitButton.setVerticalTextPosition(SwingConstants.CENTER);
        
        exitButton.addActionListener((ActionEvent e) -> { // Event listener, if button pressed, exit !
            System.exit(0);
        });
        
        this.add(exitButton); // Add button to panel
    }
    
}
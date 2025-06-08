/*
    // game.player Action - check if game over, then !gameloop ends ! or if exit button pressed, game ends !
    // COntroller game method needed ^ - pulls together game logic - split into prep, action etc - make sure repaint() at end
    // Status label - can be the label up top middle, text can be updated ! Such as welcome, next player is, etc.
 */
package BSRGameInterfaces;

import BSRCodeLogic.ShootResult;
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
    // Imports
    public BuckshotRouletteGUI mainGUI; // Import main gui
    public GameLogic game; // Import game logic
    public Round round; // Import round logic
    public PowerUpManager powerUps; // Import powerUp logic

    // Vars
    public ArrayList<Player> alivePlayers; // alivePlayer list, what players are left !
    public int numPlayers; // Get from game logic
    
    public int currentPlayerNum;
    public Player currentPlayer;
    public boolean targettingPlayer;
    
    private final HashMap<Player, PlayerPosition> playerPositions = new HashMap<>();
    public enum PlayerPosition {
        LEFT_POS, RIGHT_POS, BOTTOM_POS, TOP_POS
    }
    private final HashMap<Player, JButton> playerButtons = new HashMap<>();
    private final HashMap<Player, JButton> playerPowerUps = new HashMap<>();
    
    public boolean seeRounds;
    public boolean gameLoop;
    public boolean turnComplete = false;
    public boolean prep = true;
    
    
    // Labels / Text Field / Buttons - GUIS!
    int TABLE_DIAM = 250; // Everything is centred around the size of the table
    public JLabel mainInfoLabel; // call infoLabel.setText ...
    public JPanel buttonLayout;
    public JButton shootButton; // shootButton
    public JLabel winnerLabel;
    public JButton exitButton;
    
    public boolean explodeImageOn = false;
    public boolean smokeImageOn = false;
    
    
    public GamePanel(BuckshotRouletteGUI mainGUI, GameLogic game) {
        this.mainGUI = mainGUI;
        this.game = game;
        prepGame();
    }
    
/*----------------------------------PREP GAME------------------------------------*/
    
    private void assignPositionsToPlayers() {
        alivePlayers = game.getAlivePlayers(); // get current alive players
        playerPositions.clear(); // clear old mappings just in case

        PlayerPosition[] positions = PlayerPosition.values(); // {LEFT_POS, RIGHT_POS, BOTTOM_POS, TOP_POS}

        for (int i = 0; i < alivePlayers.size(); i++) {
            if (i < positions.length) {
                Player player = alivePlayers.get(i);
                playerPositions.put(player, positions[i]);
            }
        }
    }

    private void prepGame() {
        round = new Round();
        round.generateRounds(); // Perhaps in own method ?
        powerUps = new PowerUpManager();
        
        alivePlayers = game.getAlivePlayers(); // Able to get all players and usernames from this !
        numPlayers = alivePlayers.size();
        
        assignPositionsToPlayers();  // Assign positions once here
        
        // Get current player
        currentPlayerNum = 0; // Start with first player !
        currentPlayer = alivePlayers.get(currentPlayerNum); // Get the first player !
        targettingPlayer = false;
        
        gameLoop = true; // Reset gameloop to true !
        
        seeRounds = true;
        this.setLayout(null); // Absolute pos for all !
    }
    
    /*----------------------------------SETUP / PAINT------------------------------------*/
    
    public void setupTable(Graphics g) { // Setup Table
        //g.setColor(Color.ORANGE);
        //g.fillOval((getWidth() - TABLE_DIAM) / 2, (getHeight() - TABLE_DIAM) / 2, TABLE_DIAM, TABLE_DIAM);
        int tableX = (getWidth() - TABLE_DIAM) / 2;
        int tableY = (getHeight() - TABLE_DIAM) / 2;
        Image table = new ImageIcon("./resources/pumpkinTable.png").getImage();
        g.drawImage(table, tableX, tableY, this);
    }
    
    public void setupPlayers() {
        playerButtons.clear(); // clear buttons cleaner !
        alivePlayers = game.getAlivePlayers();
        
        int playerSize = 60;
        int tableX = getWidth() / 2; // of window
        int tableY = getHeight() / 2;// of window
        int tableDist = TABLE_DIAM - playerSize; // Palyer dist from table

        for (Player player : alivePlayers) {
            PlayerPosition playerPos = playerPositions.get(player);
            JButton playerButton = new JButton();

            int x = 0;
            int y = 0;
            
            
            switch (playerPos) {
                case LEFT_POS: {
                        // Player 1 - Left
                        x = tableX - tableDist - playerSize / 2;
                        y = tableY - playerSize / 2;
                        
                        ImageIcon orangePlayer = new ImageIcon("./resources/OrangePlayerLEFT.png");
                        playerButton.setIcon(orangePlayer);
                        
                        break;
                    }
                    
                case RIGHT_POS: {
                        // Player 2 - Right
                        x = tableX + tableDist - playerSize / 2;
                        y = tableY - playerSize / 2;
                        
                        ImageIcon pearPlayer = new ImageIcon("./resources/PearPlayerRIGHT.png");
                        playerButton.setIcon(pearPlayer);
                        
                        break;
                    }
                    
                case BOTTOM_POS: {
                        // Player 3 - Bottom
                        x = tableX - playerSize / 2;
                        y = tableY + tableDist - playerSize / 2;
                        
                        ImageIcon grapePlayer = new ImageIcon("./resources/GrapePlayerBOTTOM.png");
                        playerButton.setIcon(grapePlayer);
                        
                        break;
                    }
                    
                case TOP_POS: {
                        // Player 4 - Top
                        x = tableX - playerSize / 2;
                        y = tableY - tableDist - playerSize / 2;
                        
                        ImageIcon applePlayer = new ImageIcon("./resources/ApplePlayerTOP.png");
                        playerButton.setIcon(applePlayer);
                        
                        break;
                    }
            }
            
            playerButton.setBounds(x, y, playerSize, playerSize);

            playerButton.addActionListener(e -> {
                if (targettingPlayer) { // If player is going to be selected ! Targetting mode
                    mainInfoLabel.setText(currentPlayer.getUsername() + ", click a player to shoot !");
                    shootPlayer(player);
                }
            });

            hideButtonFeatures(playerButton);
            this.add(playerButton);
            playerButtons.put(player, playerButton);
            
        }
    }
    
        public void displayPlayerInfo(Graphics g) {
        alivePlayers = game.getAlivePlayers();
        
        int playerSize = 60;
        int tableX = getWidth() / 2; // of window
        int tableY = getHeight() / 2;// of window
        int tableDist = TABLE_DIAM - playerSize; // Palyer dist from table

        for (Player player : alivePlayers) {
            PlayerPosition playerPos = playerPositions.get(player);
            String username = player.getUsername();
            
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
            
            if (player == currentPlayer) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.BLACK);
            }
            
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString(username, x, y - 10);
            displayHearts(player, playerSize, x, y, g);
        }
    }
    
    public void setupInfoLabel() {
        mainInfoLabel = new JLabel();
        mainInfoLabel.setBounds(0, 10, getWidth(), 50);
        mainInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainInfoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainInfoLabel.setFont(new Font("Arial", Font.BOLD, 15));
        mainInfoLabel.setBackground(Color.WHITE);
        mainInfoLabel.setOpaque(true);
        mainInfoLabel.setText("The Banana Gun has been re-loaded with these unordered rounds: (Red = Bullet), (Blue = Blank)");
        this.add(mainInfoLabel);
    }
    
    public void setupWinnerLabel() {
        winnerLabel = new JLabel();
        winnerLabel.setBounds(0, getHeight() / 2, getWidth() - 100, 50);
        winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        winnerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 15));
        winnerLabel.setBackground(Color.WHITE);
        winnerLabel.setOpaque(true);
        this.add(winnerLabel);
        winnerLabel.setVisible(false);
    }
    
    public void setupShootButton() {
        shootButton = new JButton();
        
        shootButton.setSize(100, 40);
        
        int x = (getWidth() - shootButton.getWidth()) / 2;
        int y = (getHeight() - shootButton.getHeight()) / 2;
        
        shootButton.setLocation(x, y);
        
        hideButtonFeatures(shootButton);
        
        ImageIcon gunIcon = new ImageIcon("./resources/banana.png");
        shootButton.setIcon(gunIcon);
        
        shootButton.addActionListener(e -> {
            mainInfoLabel.setText("Click a player to shoot " + currentPlayer.getUsername() + "!");
            targettingPlayer = true;
        });
        
        this.add(shootButton);
    }
    
    public void hideButtonFeatures(JButton button) { // If all images, revoce if statement later !
        button.setOpaque(false); 
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
    }
    
    public void displayHearts(Player player, int playerSize, int x, int y, Graphics g) {
        int playerHealth = player.checkHealth();
        int heartSpacing = 25;
        
        ImageIcon heartIcon = new ImageIcon("./resources/heart.png");
        Image scaledHeart = heartIcon.getImage();
        
        for (int i = 0; i < playerHealth; i++) {
            g.drawImage(scaledHeart, (x - 18) + i * heartSpacing, y + playerSize + 5, this);
        }
    }
    
    public void displayRounds(Graphics g) {
        removeActionButtons(); // Remove buttons for now to target player !
        
        ArrayList organisedRounds = round.organiseRounds();
        
        int roundSize = 50;
        int roundSpacing = 7;
        int roundWidth = organisedRounds.size() * (roundSize + roundSpacing);
        int roundX = (getWidth() - roundWidth) / 2;
        int roundY = getHeight() / 2 - roundSize - 50;
        
        // display casing !
        g.setColor(Color.GRAY);
        g.fillRect(roundX - 10, roundY - 10, roundWidth + 10, roundSize + 20);
        
        // display rounds !
        for (int r = 0; r < organisedRounds.size(); r++) {
            String curRound = round.checkCurrentRound(r, organisedRounds);
            
            if ("bullet".equals(curRound)) { // If the round is a bullet, show
                g.setColor(Color.RED);
                
            } else if ("blank".equals(curRound)) { // If the round is a blank, show
                g.setColor(Color.BLUE);
            }
            g.fillRect(roundX + r * (roundSize + roundSpacing), roundY, roundSize, roundSize);
        }
        
        Timer displayTimer = new Timer(5000, e -> {
            seeRounds = false;
            
            repaint();
            
            playTurn();
        });
        displayTimer.setRepeats(false);
        displayTimer.start();
        
    }
    
    public void showSmoke() {
        smokeImageOn = true;
        repaint();
        
        Timer smokeTimer = new Timer(1000, e -> {
            smokeImageOn = false;
            repaint();
        });
        smokeTimer.setRepeats(false);
        smokeTimer.start();
    }

    
    public void showExplosion() {
        explodeImageOn = true;
        repaint();
        
        Timer explodeTimer = new Timer(1000, e -> {
            explodeImageOn = false;
            repaint();
        });
        explodeTimer.setRepeats(false);
        explodeTimer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Clear
        
        Image background = new ImageIcon("./resources/picnicBG.png").getImage();
        g.drawImage(background, 0, 0, this);
        
        if (prep) { // Only paitn/ Setup these once per playthrough ! :D
            setupInfoLabel();
            setupShootButton();
            setupPlayers();
            setupPowerUps();
            exitGameButton();
            setupWinnerLabel();
            prep = false;
        }
        
        setupTable(g); // Setup table !
        displayPlayerInfo(g); // Setup players !
        
        if (seeRounds) {
            displayRounds(g); // Display the rounds - only when nessesary ! toggle ?
        }
        
        if (smokeImageOn) {
            Image smokeImage = new ImageIcon("./resources/smoke.png").getImage();
            int x = (getWidth() - shootButton.getWidth()) / 2 + 50;
            int y = (getHeight() - shootButton.getHeight()) / 2 - 50;
            g.drawImage(smokeImage, x, y, this);
        }
        if (explodeImageOn) {
            Image explodeImage = new ImageIcon("./resources/explosion.png").getImage();
            int x = (getWidth() - shootButton.getWidth()) / 2 + 50;
            int y = (getHeight() - shootButton.getHeight()) / 2 - 50;
            g.drawImage(explodeImage, x, y, this);
        }
    }
    
    /*----------------------------------LITTLE LOGIC------------------------------------*/
    
    public void playTurn() {
        if (!gameLoop) {
            return; // end game ~!
        }
        
        if (!turnComplete) {
            mainInfoLabel.setText("It's " + currentPlayer.getUsername() + "'s turn! Click the Banana Gun to Shoot or Click your Basket to use a Powerup:");
            showActionButtons();
            
        } else {
            // end turn ! next turn !
            turnComplete = false;
            nextPlayerTurn();
        }
        
        repaint();
    }
    
    public void showActionButtons() {
        if (shootButton != null) {
            shootButton.setEnabled(true);
        }
        if (playerPowerUps != null) {
            for (JButton playerPUPButton : playerPowerUps.values()) {
                playerPUPButton.setEnabled(true);
            }
        }
    }
    
    public void removeActionButtons() {
        if (shootButton != null) {
            shootButton.setEnabled(false);
        }
        if (playerPowerUps != null) {
            for (JButton playerPUPButton : playerPowerUps.values()) {
                playerPUPButton.setEnabled(false);
            }
        }
    }
    
    
public boolean shootPlayer(Player targetPlayer) { // pass from click to the logic/gui of the shot
        ShootResult resultShot = game.shootAction(currentPlayer, targetPlayer, round);
        
        if ("bullet".equals(resultShot.shot)) {
            mainInfoLabel.setText("BANG! The Banana Gun has fired a bullet at " + targetPlayer.getUsername());
            showExplosion();
            turnComplete = true;
            
        } else if ("blank-other".equals(resultShot.shot)) {
            mainInfoLabel.setText("CHK-- The Banana Gun fired a blank round at " + targetPlayer.getUsername());
            showSmoke();
            turnComplete = true;
            
        } else if ("blank-self".equals(resultShot.shot)) {
            mainInfoLabel.setText("CHK-- The Banana Gun fired a blank round at.. yourself?! You may continue your turn:");
            showSmoke();
            turnComplete = false;
        }
        
        targettingPlayer = false;
        removeActionButtons();
        
        repaint();
        
        if (resultShot.playerDied) {
            mainInfoLabel.setText(targetPlayer.getUsername() + " has died!");

            if (playerButtons.get(targetPlayer) != null) {
                this.remove(playerButtons.get(targetPlayer));
            }
            playerButtons.remove(targetPlayer);

            if (playerPowerUps.get(targetPlayer) != null) {
                this.remove(playerPowerUps.get(targetPlayer));
            }
            playerPowerUps.remove(targetPlayer);

            System.out.println(game.getAlivePlayers());

            alivePlayers = game.getAlivePlayers();

            repaint();
            
            // winner check !
            isWinner();
        }
        
        if (resultShot.roundReloaded) {
            
            Timer reloadedShot = new Timer(2000, e -> {
                    mainInfoLabel.setText("The Banana Gun has been reloaded and PowerUps for each Player have been restocked!");
                    seeRounds = true;
                    repaint();
                    
                    Timer delayNextTurn = new Timer(2000, ev -> {
                        playTurn();
                    });
                    
                    delayNextTurn.setRepeats(false);
                    delayNextTurn.start();
                    
            });
            
            reloadedShot.setRepeats(false);
            reloadedShot.start();
            
        } else {
            Timer shootAction = new Timer(2000, e -> {
                playTurn();
            });
            
            shootAction.setRepeats(false);
            shootAction.start();
        }
        
        return turnComplete;
    }
    
    public void setupPowerUps() {
        playerPowerUps.clear(); // clear buttons cleaner !
        alivePlayers = game.getAlivePlayers();
        
        int playerSize = 20;
        int tableX = getWidth() / 2; // of window
        int tableY = getHeight() / 2;// of window
        int tableDist = TABLE_DIAM - playerSize; // Player dist from table
        int spacing = 50;

        for (Player player : alivePlayers) {
            PlayerPosition playerPos = playerPositions.get(player);
            JButton playerPUPButton = new JButton();

            int x = 0;
            int y = 0;
            
            
            switch (playerPos) {
                case LEFT_POS: {
                        // Player 1 - Left
                        x = tableX - tableDist - playerSize / 2 - spacing;
                        y = tableY - playerSize / 2;
                        
                        playerPUPButton.setBackground(Color.RED);
                        //System.out.println("player1PUPprinted");
                        
                        break;
                    }
                    
                case RIGHT_POS: {
                        // Player 2 - Right
                        x = tableX + tableDist - playerSize / 2 + spacing;
                        y = tableY - playerSize / 2;
                        
                        playerPUPButton.setBackground(Color.BLUE);
                        //System.out.println("player2PUPprinted");
                        
                        break;
                    }
                    
                case BOTTOM_POS: {
                        // Player 3 - Bottom
                        x = tableX - playerSize / 2 - (spacing + spacing / 2);
                        y = tableY + tableDist - playerSize / 2 - spacing;
                        
                        playerPUPButton.setBackground(Color.GRAY);
                        //System.out.println("player3PUPprinted");
                        break;
                    }
                    
                case TOP_POS: {
                        // Player 4 - Top
                        x = tableX - playerSize / 2 + (spacing + spacing / 2);
                        y = tableY - tableDist - playerSize / 2 + spacing;
                        
                        playerPUPButton.setBackground(Color.GREEN);
                        //System.out.println("player4PUPprinted");
                        break;
                    }
            }

            playerPUPButton.addActionListener(e -> {
                targettingPlayer = false;
                //System.out.println("CLICK!");
                if (player == currentPlayer) {
                    if (player.getPowerUps().isEmpty()) {
                        mainInfoLabel.setText("You have no more powerups, " + currentPlayer.getUsername() + "! - Click the Banana Gun to shoot!");
                    } else {
                        mainInfoLabel.setText("Here are your PowerUps, " + currentPlayer.getUsername() + "!");
                        displayPowerUps(playerPUPButton.getX(), playerPUPButton.getY());
                    }
                } else {
                    mainInfoLabel.setText("You may only access your own powerups ! - Click the Gun to shoot or your Bag for powerups");
                }
            });
            
            playerPUPButton.setBounds(x, y, 50, 60);
            
            hideButtonFeatures(playerPUPButton);
            ImageIcon pupIcon = new ImageIcon("./resources/basketPUP.png");
            playerPUPButton.setIcon(pupIcon);
            this.add(playerPUPButton);
            playerPowerUps.put(player, playerPUPButton);
            repaint();
        }
    }
    
    public void displayPowerUps(int x, int y) {
        JPopupMenu powerUpMenu = new JPopupMenu();
        
        for (String powerUp : currentPlayer.getPowerUps()) {
            JMenuItem chosenPower = new JMenuItem(powerUp);
            chosenPower.addActionListener(e -> {
                String powerUpResult = game.powerUpAction(currentPlayer, powerUp, powerUps, round);
                mainInfoLabel.setText(powerUpResult);
                repaint();
                
                Timer powerUpTimer = new Timer(3000, e2 -> {
                    mainInfoLabel.setText("It's still " + currentPlayer.getUsername() + "'s turn! Click the Banana Gun to Shoot or Click your Basket to use a Powerup:");
                    repaint();
                });
                powerUpTimer.setRepeats(false);
                powerUpTimer.start();
            });
            powerUpMenu.add(chosenPower);
        }
        
        powerUpMenu.show(this, x, y);
        
    }
    
    public void nextPlayerTurn() {
        alivePlayers = game.getAlivePlayers(); // Check dead
        numPlayers = alivePlayers.size(); // Quickly refresh player amount

        currentPlayerNum = (currentPlayerNum + 1) % numPlayers; // Change to next player !
        currentPlayer = alivePlayers.get(currentPlayerNum);
        
        turnComplete = false;
        playTurn();
    }
    
    public void isWinner() {
        boolean checkWinner = game.isWinner();
        if (checkWinner) {
            gameLoop = false;
            Player winner = game.announceWinner();
            winnerLabel.setText("The Winner is: " + winner.getUsername() + "!");
            winnerLabel.setVisible(true);
            repaint();
            
            Timer winnerTimer = new Timer(3000, e -> {
                mainGUI.endGame();
            });
            winnerTimer.setRepeats(false);
            winnerTimer.start();
        }
    }
    
    public void exitGameButton() { // Exit button !
        exitButton = new JButton("EXIT");
        exitButton.setBounds(getWidth() - 100, getHeight() - 40, 100, 40);
        ImageIcon exit = new ImageIcon("./resources/signTemplate.png");
        exitButton.setIcon(exit);
        
        exitButton.setFont(new Font("Arial", Font.BOLD, 15));
        exitButton.setHorizontalTextPosition(SwingConstants.CENTER);
        exitButton.setVerticalTextPosition(SwingConstants.CENTER);
        
        exitButton.addActionListener((ActionEvent e) -> {
            System.exit(0); // Exit the application
        });
        this.add(exitButton);
    }
    
}
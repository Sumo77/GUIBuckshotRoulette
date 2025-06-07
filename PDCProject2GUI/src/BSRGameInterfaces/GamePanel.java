/*
    // game.player Action - check if game over, then !gameloop ends ! or if exit button pressed, game ends !
    // COntroller game method needed ^ - pulls together game logic - split into prep, action etc - make sure repaint() at end
    // Status label - can be the label up top middle, text can be updated ! Such as welcome, next player is, etc.
 */
package BSRGameInterfaces;

import BSRCodeLogic.ShootResult;
import BSRCodeLogic.*;
import java.awt.*;
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
    private HashMap<Player, JButton> playerButtons = new HashMap<>();
    private HashMap<Player, JButton> playerPowerUps = new HashMap<>();
    
    public boolean seeRounds;
    public boolean gameLoop;
    public boolean turnComplete = false;
    public boolean prep = true;
    
    
    // Labels / Text Field / Buttons - GUIS!
    int TABLE_DIAM = 250; // Everything is centred around the size of the table
    public JLabel mainInfoLabel; // call infoLabel.setText ...
    public JPanel buttonLayout;
    public JButton shootButton; // shootButton
    
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
        
        alivePlayers = game.makeAlivePlayers(); // Able to get all players and usernames from this !
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
        g.setColor(Color.ORANGE);
        g.fillOval((getWidth() - TABLE_DIAM) / 2, (getHeight() - TABLE_DIAM) / 2, TABLE_DIAM, TABLE_DIAM);
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
                        
                        playerButton.setBackground(Color.RED);
                        
                        break;
                    }
                    
                case RIGHT_POS: {
                        // Player 2 - Right
                        x = tableX + tableDist - playerSize / 2;
                        y = tableY - playerSize / 2;
                        
                        playerButton.setBackground(Color.BLUE);
                        
                        break;
                    }
                    
                case BOTTOM_POS: {
                        // Player 3 - Bottom
                        x = tableX - playerSize / 2;
                        y = tableY + tableDist - playerSize / 2;
                        
                        playerButton.setBackground(Color.GRAY);
                        
                        break;
                    }
                    
                case TOP_POS: {
                        // Player 4 - Top
                        x = tableX - playerSize / 2;
                        y = tableY - tableDist - playerSize / 2;
                        
                        playerButton.setBackground(Color.GREEN);
                        
                        break;
                    }
            }
            
            playerButton.setBounds(x, y, playerSize, playerSize);

            playerButton.addActionListener(e -> {
                if (targettingPlayer) { // If player is going to be selected ! Targetting mode
                    mainInfoLabel.setText("Select a player to shoot !");
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
                        
                        g.setColor(Color.RED);
                        
                        break;
                    }
                    
                case RIGHT_POS: {
                        // Player 2 - Right
                        x = tableX + tableDist - playerSize / 2;
                        y = tableY - playerSize / 2;
                        
                        g.setColor(Color.BLUE);
                        
                        break;
                    }
                    
                case BOTTOM_POS: {
                        // Player 3 - Bottom
                        x = tableX - playerSize / 2;
                        y = tableY + tableDist - playerSize / 2;
                        
                        g.setColor(Color.GRAY);
                        
                        break;
                    }
                    
                case TOP_POS: {
                        // Player 4 - Top
                        x = tableX - playerSize / 2;
                        y = tableY - tableDist - playerSize / 2;
                        
                        g.setColor(Color.GREEN);
                        
                        break;
                    }  
            }
                                    
            g.drawString(username, x, y - 10);
            displayHearts(player, playerSize, x, y, g);
        }
    }
    
    public void setupInfoLabel() {
        mainInfoLabel = new JLabel();
        mainInfoLabel.setBounds(0,0, getWidth(), 50);
        mainInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainInfoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainInfoLabel.setBackground(Color.GRAY);
        this.add(mainInfoLabel);
        mainInfoLabel.setText("The Gun has been re-loaded with these unordered rounds: (Red = Bullet), (Blue = Blank)");
    }
    
    public void setupShootButton() {
        shootButton = new JButton();
        
        shootButton.setSize(100, 40);
        
        int x = (getWidth() - shootButton.getWidth()) / 2;
        int y = (getHeight() - shootButton.getHeight()) / 2;
        
        shootButton.setLocation(x, y);
        
        hideButtonFeatures(shootButton);
        
        ImageIcon gunIcon = new ImageIcon("./resources/pixelGun.png");
        shootButton.setIcon(gunIcon);
        
        shootButton.addActionListener(e -> {
            mainInfoLabel.setText("Select a player to shoot!");
            targettingPlayer = true;
        });
        
        this.add(shootButton);
    }
    
    public void hideButtonFeatures(JButton button) { // If all images, revoce if statement later !
        if (button.equals(shootButton)) {
            button.setOpaque(false); 
            button.setContentAreaFilled(false);
        }
        button.setBorderPainted(false);
        button.setFocusPainted(false);
    }
    
    public void displayHearts(Player player, int playerSize, int x, int y, Graphics g) {
        int playerHealth = player.checkHealth();
        int heartSpacing = 25;
        
        g.setColor(Color.RED);
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
        
        if (prep) { // Only paitn/ Setup these once per playthrough ! :D
            setupInfoLabel();
            setupShootButton();
            setupPlayers();
            setupPowerUps();
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
            mainInfoLabel.setText("It's " + currentPlayer.getUsername() + "'s turn! Choose to Shoot or use a Powerup:");
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
            mainInfoLabel.setText("BANG! The Gun has fired a bullet at " + targetPlayer.getUsername());
            showExplosion();
            turnComplete = true;
            
        } else if ("blank-other".equals(resultShot.shot)) {
            mainInfoLabel.setText("CHK-- The Gun fired a blank round at " + targetPlayer.getUsername());
            showSmoke();
            turnComplete = true;
            
        } else if ("blank-self".equals(resultShot.shot)) {
            mainInfoLabel.setText("CHK-- The Gun fired a blank round at.. yourself?! You may continue your turn:");
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
        }
        
        if (resultShot.roundReloaded) {
            
            Timer reloadedShot = new Timer(2000, e -> {
                    mainInfoLabel.setText("The Gun has been reloaded and PowerUps for each player have been restocked");
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
        int tableDist = TABLE_DIAM - playerSize; // Palyer dist from table
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
                        
                        break;
                    }
                    
                case RIGHT_POS: {
                        // Player 2 - Right
                        x = tableX + tableDist - playerSize / 2 + spacing;
                        y = tableY - playerSize / 2;
                        
                        playerPUPButton.setBackground(Color.BLUE);
                        
                        break;
                    }
                    
                case BOTTOM_POS: {
                        // Player 3 - Bottom
                        x = tableX - playerSize / 2 - spacing;
                        y = tableY + tableDist - playerSize / 2;
                        
                        playerPUPButton.setBackground(Color.GRAY);
                        
                        break;
                    }
                    
                case TOP_POS: {
                        // Player 4 - Top
                        x = tableX - playerSize / 2 + spacing;
                        y = tableY - tableDist - playerSize / 2;
                        
                        playerPUPButton.setBackground(Color.GREEN);
                        
                        break;
                    }
            }
            
            playerPUPButton.setBounds(x, y, playerSize, playerSize);

            playerPUPButton.addActionListener(e -> {
                targettingPlayer = false;
                //System.out.println("CLICK!");
                if (player == currentPlayer) {
                    if (player.getPowerUps().isEmpty()) {
                        mainInfoLabel.setText("You have no more powerups! - Choose someone to shoot!");
                    } else {
                        mainInfoLabel.setText("Here are your PowerUps!");
                        displayPowerUps();
                    }
                } else {
                    mainInfoLabel.setText("You may only access your own powerups ! - Click the Gun to shoot or your Bag for powerups");
                }
            });

            hideButtonFeatures(playerPUPButton);
            this.add(playerPUPButton);
            playerPowerUps.put(player, playerPUPButton);
        }
    }
    
    public void displayPowerUps() {
        JPopupMenu powerUpMenu = new JPopupMenu();
        
        for (String powerUp : currentPlayer.getPowerUps()) {
            JMenuItem chosenPower = new JMenuItem(powerUp);
            chosenPower.addActionListener(e -> {
                String powerUpResult = game.powerUpAction(currentPlayer, powerUp, powerUps, round);
                mainInfoLabel.setText(powerUpResult);
            });
            powerUpMenu.add(chosenPower);
        }
        
        powerUpMenu.show(this, 0, this.getHeight());
        
    }
    
    public void nextPlayerTurn() {
        alivePlayers = game.getAlivePlayers(); // Check dead
        numPlayers = alivePlayers.size(); // Quickly refresh player amount
        
        boolean checkWinner = game.isWinner();
        if (checkWinner) {
            gameLoop = false;
            return; // or go to end game screen !
        }
        
        currentPlayerNum = (currentPlayerNum + 1) % numPlayers; // Change to next player !
        currentPlayer = alivePlayers.get(currentPlayerNum);
        
        turnComplete = false;
        playTurn();
    }
    
    public void exitGameButton() { // Exit button !
        
    }
    
}
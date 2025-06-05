/*
    // game.player Action - check if game over, then !gameloop ends ! or if exit button pressed, game ends !
    // COntroller game method needed ^ - pulls together game logic - split into prep, action etc - make sure repaint() at end
    // Status label - can be the label up top middle, text can be updated ! Such as welcome, next player is, etc.
 */
package BSRGameInterfaces;

import BSRCodeLogic.*;
import java.awt.*;
import java.util.ArrayList;
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
    
    public boolean seeRounds;
    
    public boolean gameLoop;
    
    // Labels / Text Field / Buttons - GUIS!
    int TABLE_DIAM = 250; // Everything is centred around the size of the table
    public JLabel mainInfoLabel; // call infoLabel.setText ...
    
    public GamePanel(BuckshotRouletteGUI mainGUI, GameLogic game) {
        this.mainGUI = mainGUI;
        this.game = game;
        prepGame();
    }
    
/*----------------------------------PREP GAME------------------------------------*/
    
    private void prepGame() {
        round = new Round();
        round.generateRounds(); // Perhaps in own method ?
        powerUps = new PowerUpManager();
        
        alivePlayers = game.getAlivePlayers(); // Able to get all players and usernames from this !
        numPlayers = alivePlayers.size();
        
        // Get current player
        currentPlayerNum = 0; // Start with first player !
        currentPlayer = alivePlayers.get(currentPlayerNum); // Get the first player !
        
        gameLoop = true; // Reset gameloop to true !
        
        seeRounds = true;
        round.generateRounds();
        
        setupInfoLabel();
        
        repaint();
    }
    
    /*----------------------------------SETUP / PAINT------------------------------------*/
    
    public void setupTable(Graphics g) { // Setup Table
        // Create Table
        g.setColor(Color.ORANGE);
        g.fillOval((getWidth() - TABLE_DIAM) / 2, (getHeight() - TABLE_DIAM) / 2, TABLE_DIAM, TABLE_DIAM);
        
        // Create Gun ! - change orientation based on player turn
    }
    
    public void setupPlayers(Graphics g) {
        int playerSize = 60;
        int tableX = getWidth() / 2;
        int tableY = getHeight() / 2;
        int tableDist = 175; // Distance from table

        for (int i = 0; i < numPlayers; i++) {
            Player player = alivePlayers.get(i);
            String username = player.getUsername();

            int x = 0, y = 0;

            if (i == 0) { // Player 1 - Left
                x = tableX - tableDist - playerSize / 2;
                y = tableY - playerSize / 2;

                g.setColor(Color.RED);
                g.fillOval(x, y, playerSize, playerSize);
                g.drawString(username, x + 10, y - 20);
                displayHearts(player, x, y, g);
                
            } else if (i == 1) { // Player 2 - Right
                x = tableX + tableDist - playerSize / 2;
                y = tableY - playerSize / 2;

                g.setColor(Color.BLUE);
                g.fillOval(x, y, playerSize, playerSize);
                g.drawString(username, x + 10, y - 20);
                displayHearts(player, x, y, g);
                
            } else if (i == 2) { // Player 3 - Bottom
                x = tableX - playerSize / 2;
                y = tableY + tableDist - playerSize / 2;

                g.setColor(Color.GRAY);
                g.fillOval(x, y, playerSize, playerSize);
                g.drawString(username, x + 10, y + playerSize + 20);
                displayHearts(player, x, y, g);
                
            } else if (i == 3) { // Player 4 - Top
                x = tableX - playerSize / 2;
                y = tableY - tableDist - playerSize / 2;

                g.setColor(Color.GREEN);
                g.fillOval(x, y, playerSize, playerSize);
                g.drawString(username, x + 10, y - 20);
                displayHearts(player, x, y, g);
            }
        }
    }
    
    public void setupInfoLabel() {
        mainInfoLabel = new JLabel();
        mainInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainInfoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainInfoLabel.setOpaque(true);
        mainInfoLabel.setBackground(Color.GRAY);
        this.add(mainInfoLabel);
    }
    
    public void displayHearts(Player player, int x, int y, Graphics g) {
        int playerHealth = player.checkHealth();
        int heartSize = 10;
        int heartSpacing = 7;
        
        g.setColor(Color.RED);
        for (int i = 0; i < playerHealth; i++) {
            g.fillOval(x + i * (heartSize + heartSpacing), y - heartSize - 2, heartSize, heartSize);
        }
    }
    
    public void displayRounds(Graphics g) {
        mainInfoLabel.setText("The Gun has been loaded with the following UNORDERED rounds: (Red = Bullet), (Blue = Blank)");
        ArrayList organisedRounds = round.organiseRounds();
        
        int roundSize = 50;
        int roundSpacing = 7;
        int roundWidth = organisedRounds.size() * (roundSize + roundSpacing);
        int roundX = (getWidth() - roundWidth) / 2;
        int roundY = getHeight() / 2 - roundSize / 2;
        
        // display casing !
        g.setColor(Color.GRAY);
        g.fillRect(roundX - 10, roundY - 10, roundWidth + 10, roundSize + 20);
        
        // display rounds !
        for (int r = 0; r < organisedRounds.size(); r++) {
            String curRound = round.checkCurrentRound(r, organisedRounds);
            
            if ("bullet".equals(curRound)) { // If the round is a bullet, show
                //System.out.println("bullet");
                g.setColor(Color.RED);
                
            } else if ("blank".equals(curRound)) { // If the round is a blank, show
                //System.out.println("blank");
                g.setColor(Color.BLUE);
            }
            g.fillRect(roundX + r * (roundSize + roundSpacing), roundY, roundSize, roundSize);
        }
        Timer timer = new Timer(5000, e -> {
            seeRounds = false;
            repaint(); // update display
            
            playTurn(); // continue turns !
        });
        timer.start();
        
    }

    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Clear
        
        setupTable(g); // Setup table !
        setupPlayers(g); // Setup players !
        
        if (seeRounds) {
            displayRounds(g); // Display the rounds - only when nessesary ! toggle ?
        }
    }
    
    /*----------------------------------LITTLE LOGIC------------------------------------*/
    
    public void playTurn() {
        if (!gameLoop) {
            return; // end game ~!
        }
        
        boolean checkWinner = game.isWinner();
        if (checkWinner) {
            gameLoop = false;
            return; // end game !
        }

        mainInfoLabel.setText("It's " + currentPlayer.getUsername() + "'s turn! Taking action...");
        
        repaint();
        
        Timer actionTimer = new Timer(1500, e -> { // Quick process pause
            playerAction(); // Shoot or Action

            nextPlayerTurn(); // Move to next player
        });
        actionTimer.setRepeats(false);
        actionTimer.start();
    }
    
    public void nextPlayerTurn() {
        if (!gameLoop) {
            return; // Commence end ?
        }
        numPlayers = alivePlayers.size(); // Quickly refresh player amount
        currentPlayerNum = (currentPlayerNum + 1) % numPlayers; // Change to next player !
    }
    
    private void playerAction() {
        if (!gameLoop) {
            return; // commence end ?
        }

        // Do shooting logic or powerup Logic
        // Perform game logic - make choice
    }
    
    public void exitGame() { // Exit button !
        
    }
    
}

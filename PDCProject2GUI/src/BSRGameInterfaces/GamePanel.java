/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BSRGameInterfaces;

import BSRCodeLogic.*;
import java.awt.*;
import java.util.*;
import javax.swing.JPanel;

/**
 * @author Davor Georgiev
 * @author Summer Harris
 */

public class GamePanel extends JPanel {
    public BuckshotRouletteGUI mainGUI; // Import main gui
    public GameLogic game; // Import game logic
    public Round round; // Import round logic
    public PowerUpManager powerUps; // Import powerUp logic
    
    //define all interactable buttons / textfields / drawings (start button or textfield for usernames)
    public ArrayList<Player> alivePlayers; // alivePlayer list, what players are left !
    public int numPlayers; // Get from game logic
    public boolean gameLoop;
    
    public GamePanel(BuckshotRouletteGUI mainGUI, GameLogic game) {
        this.mainGUI = mainGUI;
        this.game = game;
    }
    
    private void prepGame() {
        round = new Round();
        round.generateRounds(); // Perhaps in own method ?
        powerUps = new PowerUpManager();
        alivePlayers = new ArrayList<>(); // Able to get all players and usernames from this !
        numPlayers = alivePlayers.size();
        gameLoop = true; // Reset gameloop to true !
        
        // Get current player
        // Setup table / players (perhaps seperate method)
        //if (!gameLoop) return; // Stop if game is already over - 
        // game.player Action - check if game over, then !gameloop ends ! or if exit button pressed, game ends !
        // COntroller game method needed ^ - pulls together game logic - split into prep, action etc - make sure repaint() at end
        // Status label - can be the label up top middle, text can be updated ! Such as welcome, next player is, etc.
    }
    
    public void paint(Graphics g) {
        g.drawString("Game Test", 40, 50);
        repaint();
    }
}

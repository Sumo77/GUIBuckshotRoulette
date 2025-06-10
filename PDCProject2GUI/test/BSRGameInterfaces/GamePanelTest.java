/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package BSRGameInterfaces;

import BSRCodeLogic.*;
import java.util.ArrayList;
import javax.swing.JButton;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Prumm
 */
public class GamePanelTest {
    
    public GamePanelTest() {

    }

    /**
     * Test of shootPlayer method, of class GamePanel. -----------------------------------------------------------
     */
    @Test
    public void testShootPlayerSurvive() {
        System.out.println("shootPlayer - Player survives");

        // Setup imports:
        BuckshotRouletteGUI mainGUI = new BuckshotRouletteGUI();
        PowerUpManager powerUps = new PowerUpManager();
        GameLogic testGame = new GameLogic();

        // Create target and shooter players for testing using Game Logic
        testGame.createPlayer("testTarget", powerUps);
        testGame.createPlayer("testShooter", powerUps);

        // Get alive players, should now contain players created above ^
        ArrayList<Player> alivePlayers = testGame.getAlivePlayers();

        // Save players locally here
        Player testTarget = alivePlayers.get(0);
        Player testShooter = alivePlayers.get(1);

        // Create GUI panel and generate new round
        GamePanel testInstance = new GamePanel(mainGUI, testGame);
        
        testInstance.round = new Round();
        testInstance.round.generateRounds();
        testInstance.round.currRoundList.clear();
        testInstance.round.currRoundList.add('x');
        
        testInstance.setupInfoLabel();

        // Manually place both players in hashmaps (skipping some setup to manually test shoot)
        testInstance.playerButtons.put(testTarget, new JButton());
        testInstance.playerPowerUps.put(testTarget, new JButton());
        
        testInstance.playerButtons.put(testShooter, new JButton());
        testInstance.playerPowerUps.put(testShooter, new JButton());

        // Manually set current player to the test shooter
        testInstance.currentPlayer = testShooter;

        // Testing Results - Shoot player - Player Survives
        
        boolean result = testInstance.shootPlayer(testTarget); // Call shoot player !
        assertTrue(result); // Check for if turn complete, should be true as bullet shot
        
        assertTrue(testInstance.mainInfoLabel.getText().contains("BANG")); // Check for if there is a BANG message, means bullet was shot, should be true.
        
        assertTrue(testGame.getAlivePlayers().contains(testTarget)); // Check if player exists in alive players list, should be true
        
        assertTrue(testInstance.playerButtons.containsKey(testTarget)); // Check for if player still exists in both hashmaps, it should be there and return true, since they havent died
        assertTrue(testInstance.playerPowerUps.containsKey(testTarget));
    }
    
    /**
     * Test of shootPlayer method, of class GamePanel.-----------------------------------------------------------
     */
    @Test
    public void testShootPlayerDies() {
        System.out.println("shootPlayer - Player dies");

        // Setup imports:
        BuckshotRouletteGUI mainGUI = new BuckshotRouletteGUI();
        PowerUpManager powerUps = new PowerUpManager();
        GameLogic testGame = new GameLogic();

        // Create target and shooter players for testing using Game Logic
        testGame.createPlayer("testTarget", powerUps);
        testGame.createPlayer("testShooter", powerUps);

        // Get alive players, should now contain players created above ^
        ArrayList<Player> alivePlayers = testGame.getAlivePlayers();

        // Save players locally here
        Player testTarget = alivePlayers.get(0);
        testTarget.removeHealth(4); // so dies is true !
        Player testShooter = alivePlayers.get(1);

        // Create GUI panel and generate new round
        GamePanel testInstance = new GamePanel(mainGUI, testGame);
        
        testInstance.round = new Round();
        testInstance.round.generateRounds();
        testInstance.round.currRoundList.clear();
        testInstance.round.currRoundList.add('x');
        
        testInstance.setupInfoLabel();
        testInstance.setupWinnerLabel();

        // Manually place both players in hashmaps (skipping some setup to manually test shoot)
        testInstance.playerButtons.put(testTarget, new JButton());
        testInstance.playerPowerUps.put(testTarget, new JButton());
        
        testInstance.playerButtons.put(testShooter, new JButton());
        testInstance.playerPowerUps.put(testShooter, new JButton());

        // Manually set current player to the test shooter
        testInstance.currentPlayer = testShooter;

        // Testing Results - Shoot player - Player dies

        boolean result = testInstance.shootPlayer(testTarget); // Call shoot player !
        assertTrue(result); // Check for if turn complete, should be true as bullet shot
        assertTrue(testInstance.mainInfoLabel.getText().contains(testTarget.getUsername() + " has died!")); // Check for if there is a person died message, means bullet was shot, should be true.

        assertFalse(testGame.getAlivePlayers().contains(testTarget)); // Check if player exists in alive players list, should be false, player dead
        
        assertFalse(testInstance.playerButtons.containsKey(testTarget)); // Check for if player still exists in both hashmaps, it should be removed and return false as dead!
        assertFalse(testInstance.playerPowerUps.containsKey(testTarget));
    }

    /**
     * Test of shootPlayer method, of class GamePanel. -----------------------------------------------------------
     */
    @Test
    public void testShootSelfBullet() {
        System.out.println("shootPlayer - Bullet at self");
        
        // Setup imports:
        BuckshotRouletteGUI mainGUI = new BuckshotRouletteGUI();
        PowerUpManager powerUps = new PowerUpManager();
        GameLogic testGame = new GameLogic();

        // Create target and shooter players for testing using Game Logic
        testGame.createPlayer("testTarget", powerUps);

        // Get alive players, should now contain players created above ^
        ArrayList<Player> alivePlayers = testGame.getAlivePlayers();

        // Save players locally here
        Player testTarget = alivePlayers.get(0);

        // Create GUI panel and generate new round
        GamePanel testInstance = new GamePanel(mainGUI, testGame);
        
        testInstance.round = new Round();
        testInstance.round.generateRounds();
        testInstance.round.currRoundList.clear();
        testInstance.round.currRoundList.add('x');
        
        testInstance.setupInfoLabel();

        // Manually place both players in hashmaps (skipping some setup to manually test shoot)
        testInstance.playerButtons.put(testTarget, new JButton());
        testInstance.playerPowerUps.put(testTarget, new JButton());

        // Manually set current player to the test shooter
        testInstance.currentPlayer = testTarget;

        
        // Testing Results - Shoot player - Player shoots bullet

        boolean result = testInstance.shootPlayer(testTarget); // Call shoot player !
        
        assertTrue(result); // Check for if turn complete, since bullet shot at self, should be true

        assertTrue(testInstance.mainInfoLabel.getText().contains("BANG")); // Check for if there is a BANG message, means bullet was shot, should be true.
        
        assertTrue(testGame.getAlivePlayers().contains(testTarget)); // Check if player exists in alive players list, should be true
        
        assertTrue(testInstance.playerButtons.containsKey(testTarget)); // Check for if player still exists in both hashmaps, it should be there and return true;
        assertTrue(testInstance.playerPowerUps.containsKey(testTarget));
    }
    
    /**
     * Test of shootPlayer method, of class GamePanel. -----------------------------------------------------------
     */
    @Test
    public void testShootPlayerBlank() {
        System.out.println("shootPlayer - Blank at other player");

        // Setup imports:
        BuckshotRouletteGUI mainGUI = new BuckshotRouletteGUI();
        PowerUpManager powerUps = new PowerUpManager();
        GameLogic testGame = new GameLogic();

        // Create target and shooter players for testing using Game Logic
        testGame.createPlayer("testTarget", powerUps);
        testGame.createPlayer("testShooter", powerUps);

        // Get alive players, should now contain players created above ^
        ArrayList<Player> alivePlayers = testGame.getAlivePlayers();

        // Save players locally here
        Player testTarget = alivePlayers.get(0);
        Player testShooter = alivePlayers.get(1);

        // Create GUI panel and generate new round
        GamePanel testInstance = new GamePanel(mainGUI, testGame);
        
        testInstance.round = new Round();
        testInstance.round.generateRounds();
        testInstance.round.currRoundList.clear();
        testInstance.round.currRoundList.add('o');
        
        testInstance.setupInfoLabel();

        // Manually place both players in hashmaps (skipping some setup to manually test shoot)
        testInstance.playerButtons.put(testTarget, new JButton());
        testInstance.playerPowerUps.put(testTarget, new JButton());
        
        testInstance.playerButtons.put(testShooter, new JButton());
        testInstance.playerPowerUps.put(testShooter, new JButton());

        // Manually set current player to the test shooter
        testInstance.currentPlayer = testShooter;
        
        // Testing Results - Shoot player - Blank at another player

        boolean result = testInstance.shootPlayer(testTarget); // Call shoot player !
        assertTrue(result); // Check for if turn complete, since blank shot at another player, should be true.
        
        assertTrue(testInstance.mainInfoLabel.getText().contains("CHK-")); // Check for if blank shot
        
        assertTrue(testInstance.playerButtons.containsKey(testTarget)); // Check for if player still exists in both hashmaps, it shouldn't be removed and should return true
        assertTrue(testInstance.playerPowerUps.containsKey(testTarget));
    }
    
    /**
     * Test of shootPlayer method, of class GamePanel. -----------------------------------------------------------
     */
    @Test
    public void testShootSelfBlank() {
        System.out.println("shootPlayer - Blank at self");
        
        // Setup imports:
        BuckshotRouletteGUI mainGUI = new BuckshotRouletteGUI();
        PowerUpManager powerUps = new PowerUpManager();
        GameLogic testGame = new GameLogic();

        // Create target and shooter players for testing using Game Logic
        testGame.createPlayer("testTarget", powerUps);

        // Get alive players, should now contain players created above ^
        ArrayList<Player> alivePlayers = testGame.getAlivePlayers();

        // Save players locally here
        Player testTarget = alivePlayers.get(0);

        // Create GUI panel and generate new round
        GamePanel testInstance = new GamePanel(mainGUI, testGame);
        
        testInstance.round = new Round();
        testInstance.round.generateRounds();
        testInstance.round.currRoundList.clear();
        testInstance.round.currRoundList.add('o');
        
        testInstance.setupInfoLabel();

        // Manually place both players in hashmaps (skipping some setup to manually test shoot)
        testInstance.playerButtons.put(testTarget, new JButton());
        testInstance.playerPowerUps.put(testTarget, new JButton());

        // Manually set current player to the test shooter
        testInstance.currentPlayer = testTarget;

        
        // Testing Results - Shoot player - Player dies

        boolean result = testInstance.shootPlayer(testTarget); // Call shoot player !
        
        assertFalse(result); // Check for if turn complete, since blank shot at self, should be false

        assertTrue(testInstance.mainInfoLabel.getText().contains("continue your turn:")); // Check for if message says to continue turn, should be true
        
        assertTrue(testInstance.playerButtons.containsKey(testTarget)); // Check for if player still exists in both hashmaps, it should be there and return true;
        assertTrue(testInstance.playerPowerUps.containsKey(testTarget));
    }

    /**
     * Test of nextPlayerTurn method, of class GamePanel. -----------------------------------------------------------
     */
    @Test
    public void testNextPlayerTurn() {
        System.out.println("nextPlayerTurn");
        // Setup imports:
        BuckshotRouletteGUI mainGUI = new BuckshotRouletteGUI();
        PowerUpManager powerUps = new PowerUpManager();
        GameLogic testGame = new GameLogic();

        // Create target and shooter players for testing using Game Logic
        testGame.alivePlayerList.clear();
        testGame.createPlayer("testP1", powerUps);
        testGame.createPlayer("testP2", powerUps);

        // Get alive players, should now contain players created above ^
        ArrayList<Player> alivePlayers = testGame.getAlivePlayers();

        // Save players locally here
        Player testP1 = alivePlayers.get(0);
        Player testP2 = alivePlayers.get(1);
        
        // Create GUI panel and generate new round
        GamePanel testInstance = new GamePanel(mainGUI, testGame);
        
        testInstance.setupInfoLabel();
        
        testInstance.alivePlayers = alivePlayers;
        testInstance.numPlayers = testInstance.alivePlayers.size();

        // Manually place both players in hashmaps (skipping some setup to manually test shoot)
        testInstance.playerButtons.put(testP1, new JButton());
        testInstance.playerPowerUps.put(testP1, new JButton());
        
        testInstance.playerButtons.put(testP2, new JButton());
        testInstance.playerPowerUps.put(testP2, new JButton());

        // Manually set current player to the test shooter
        testInstance.currentPlayer = testP1;
        testInstance.currentPlayerNum = 0;
        
        // Testing Results - Shoot player - Blank at another player

        //System.out.println(testInstance.currentPlayer.getUsername());
        
        testInstance.nextPlayerTurn(); // Call next player !
        assertTrue(testInstance.currentPlayer == testP2); // Check if moved onto next player !
        
        //System.out.println(testInstance.currentPlayer.getUsername());

        testInstance.nextPlayerTurn(); // Call next player !
        assertTrue(testInstance.currentPlayer == testP1); // Check if moved onto next player !
        //System.out.println(testInstance.currentPlayer.getUsername());
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package BSRGameInterfaces;

import BSRCodeLogic.*;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
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
     * Test of shootPlayer method, of class GamePanel.
     */
    @Test
    public void testShootPlayerSurvive() {
        System.out.println("shootPlayer - Player survives");

        BuckshotRouletteGUI mainGUI = new BuckshotRouletteGUI();
        PowerUpManager powerUps = new PowerUpManager();

        GameLogic testGame = new GameLogic() {
            @Override
            public ShootResult shootAction(Player current, Player target, Round round) {
                return new ShootResult("bullet", false, false); // shoot bullet, no death, no reload
            }
        };

        testGame.createPlayer("testTarget", powerUps);
        testGame.createPlayer("testShooter", powerUps);

        ArrayList<Player> alivePlayers = testGame.getAlivePlayers();

        Player testTarget = alivePlayers.get(0);
        Player testShooter = alivePlayers.get(1);

        GamePanel testInstance = new GamePanel(mainGUI, testGame);

        testInstance.round = new Round();
        testInstance.mainInfoLabel = new JLabel();
        testInstance.alivePlayers = new ArrayList<>();

        testInstance.playerButtons.put(testTarget, new JButton());
        testInstance.playerPowerUps.put(testTarget, new JButton());

        testInstance.currentPlayer = testShooter;

        boolean result = testInstance.shootPlayer(testTarget);

        assertTrue(result); // Check for if turn complete, should be true as bullet shot
        assertTrue(testInstance.mainInfoLabel.getText().contains("BANG")); // Check for if there is a BANG message, means bullet was shot, should be true.
        
        assertTrue(testGame.getAlivePlayers().get(0) == testTarget); // Check if player exists in alive players list, should be true
        assertTrue(testTarget.checkHealth() == 3); // Check if player's health has been removed by 1, should be true
        
        assertTrue(testInstance.playerButtons.containsKey(testTarget)); // Check for if player still exists in both hashmaps, it should be there and return true, since they havent died
        assertTrue(testInstance.playerPowerUps.containsKey(testTarget));
    }
    
    /**
     * Test of shootPlayer method, of class GamePanel.
     */
    @Test
    public void testShootPlayerDie() {
        System.out.println("shootPlayer - Player dies");

        BuckshotRouletteGUI mainGUI = new BuckshotRouletteGUI();
        PowerUpManager powerUps = new PowerUpManager();

        GameLogic testGame = new GameLogic() {
            @Override
            public ShootResult shootAction(Player current, Player target, Round round) {
                return new ShootResult("bullet", true, false); // shoot bullet, yes death, no reload
            }
        };

        testGame.createPlayer("testTarget", powerUps);
        testGame.createPlayer("testShooter", powerUps);

        ArrayList<Player> alivePlayers = testGame.getAlivePlayers();

        Player testTarget = alivePlayers.get(0);
        Player testShooter = alivePlayers.get(1);

        GamePanel testInstance = new GamePanel(mainGUI, testGame);

        testInstance.round = new Round();
        testInstance.mainInfoLabel = new JLabel();
        testInstance.alivePlayers = new ArrayList<>();

        testInstance.playerButtons.put(testTarget, new JButton());
        testInstance.playerPowerUps.put(testTarget, new JButton());

        testInstance.currentPlayer = testShooter;

        boolean result = testInstance.shootPlayer(testTarget);

        assertTrue(result); // Check for if turn complete, should be true as bullet shot
        assertTrue(testInstance.mainInfoLabel.getText().contains(testTarget.getUsername() + " has died!")); // Check for if there is a person died message, means bullet was shot, should be true.
        
        assertTrue(result); // Check for if turn complete, should be true as bullet shot
        assertTrue(testInstance.mainInfoLabel.getText().contains("BANG")); // Check for if there is a BANG message, means bullet was shot, should be true.
        
        assertTrue(testTarget.checkHealth() == 0); // Check if player's health has been removed by 1, should be true
        assertTrue(testGame.getAlivePlayers().get(0) == testTarget); // Check if player exists in alive players list, should be true
        
        assertFalse(testInstance.playerButtons.containsKey(testTarget)); // Check for if player still exists in both hashmaps, it should be removed and return false.
        assertFalse(testInstance.playerPowerUps.containsKey(testTarget));
    }

    
    /**
     * Test of shootPlayer method, of class GamePanel.
     */
    @Test
    public void testShootPlayerBlank() {
        System.out.println("shootPlayer - Blank at other player");

        BuckshotRouletteGUI mainGUI = new BuckshotRouletteGUI();
        PowerUpManager powerUps = new PowerUpManager();

        GameLogic testGame = new GameLogic() {
            @Override
            public ShootResult shootAction(Player current, Player target, Round round) {
                return new ShootResult("blank", false, false); // shoot blank, no death, no reload
            }
        };

        testGame.createPlayer("testTarget", powerUps);
        testGame.createPlayer("testShooter", powerUps);

        ArrayList<Player> alivePlayers = testGame.getAlivePlayers();

        Player testTarget = alivePlayers.get(0);
        Player testShooter = alivePlayers.get(1);

        GamePanel testInstance = new GamePanel(mainGUI, testGame);

        testInstance.round = new Round();
        testInstance.mainInfoLabel = new JLabel();
        testInstance.alivePlayers = new ArrayList<>();

        testInstance.playerButtons.put(testTarget, new JButton());
        testInstance.playerPowerUps.put(testTarget, new JButton());

        testInstance.currentPlayer = testShooter;

        boolean result = testInstance.shootPlayer(testTarget);

        assertTrue(result); // Check for if turn complete, since blank shot at another player, should be true.
        assertTrue(testInstance.mainInfoLabel.getText().contains("CHK-")); // Check for if blank shot
        assertTrue(testInstance.smokeImageOn); // check if the smoke image is displayed !
        
        assertTrue(testInstance.playerButtons.containsKey(testTarget)); // Check for if player still exists in both hashmaps, it shouldn't be removed and should return true
        assertTrue(testInstance.playerPowerUps.containsKey(testTarget));
    }
    
    /**
     * Test of shootPlayer method, of class GamePanel.
     */
    @Test
    public void testShootSelfBlank() {
        System.out.println("shootPlayer - Blank at self");

        BuckshotRouletteGUI mainGUI = new BuckshotRouletteGUI();
        PowerUpManager powerUps = new PowerUpManager();

        GameLogic testGame = new GameLogic() {
            @Override
            public ShootResult shootAction(Player current, Player target, Round round) {
                return new ShootResult("blank", false, false);
            }
        };

        testGame.createPlayer("testTarget", powerUps);
        testGame.createPlayer("testShooter", powerUps);

        ArrayList<Player> alivePlayers = testGame.getAlivePlayers();

        Player testTarget = alivePlayers.get(0);
        Player testShooter = alivePlayers.get(1);

        GamePanel testInstance = new GamePanel(mainGUI, testGame);

        testInstance.round = new Round();
        testInstance.mainInfoLabel = new JLabel();
        testInstance.alivePlayers = new ArrayList<>();

        testInstance.playerButtons.put(testTarget, new JButton());
        testInstance.playerPowerUps.put(testTarget, new JButton());

        testInstance.currentPlayer = testShooter;

        boolean result = testInstance.shootPlayer(testTarget);
        
        assertFalse(result); // Check for if turn complete, since blank shot at self, should be false

        //assertTrue(labelText.contains("continue your turn:")); // Check for if message says to continue turn, should be true
        
        assertFalse(testInstance.playerButtons.containsKey(testTarget)); // Check for if player still exists in both hashmaps, it should be removed and return false.
        assertFalse(testInstance.playerPowerUps.containsKey(testTarget));
    }

    /**
     * Test of setupPowerUps method, of class GamePanel.
     */
    @Test
    public void testSetupPowerUps() {
        System.out.println("setupPowerUps");
        GamePanel instance = null;
        instance.setupPowerUps();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of playTurn method, of class GamePanel.
     */
    @Test
    public void testPlayTurn() {
        System.out.println("playTurn");
        GamePanel instance = null;
        instance.playTurn();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of nextPlayerTurn method, of class GamePanel.
     */
    @Test
    public void testNextPlayerTurn() {
        System.out.println("nextPlayerTurn");
        GamePanel instance = null;
        instance.nextPlayerTurn();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isWinner method, of class GamePanel.
     */
    @Test
    public void testIsWinner() {
        System.out.println("isWinner");
        GamePanel instance = null;
        instance.isWinner();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

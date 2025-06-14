/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BSRGameInterfaces;

import BSRCodeLogic.GameLogic;
import javax.swing.*;
import java.awt.*;

/**
 * @author Davor Georgiev
 * @author Summer Harris
 */

public class BuckshotRouletteGUI extends JFrame {
    
    private final GameLogic game = new GameLogic(); //game starts here
    public final CardLayout panelLayout;//controls panel order
    public final JPanel allPanels;//holds all panels
    
    public BuckshotRouletteGUI() {
        JFrame frame = new JFrame("Buckshot Roulette");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 750);//game size
        
        panelLayout = new CardLayout();
        allPanels = new JPanel(panelLayout);
        
        frame.add(allPanels);//add panels to game frame window
        frame.setVisible(true);
        
        startMenu();
        
    }
    
    public void showPanel(String panelName) {
        panelLayout.show(allPanels, panelName);
    }
    
    public void startMenu() {
        StartMenuPanel startPanel = new StartMenuPanel(this, game);//start menu panel
        allPanels.add(startPanel, "Start Menu");
        panelLayout.show(allPanels, "Start Menu");
    }
    
    public void playGame() {
        GamePanel gamePanel = new GamePanel(this, game);//game menu panel
        allPanels.add(gamePanel, "Game");
        panelLayout.show(allPanels, "Game");
    }
    
    public void endGame() {
        EndGamePanel endPanel = new EndGamePanel(this, game);//end game menu panel
        allPanels.add(endPanel, "End Game");
        panelLayout.show(allPanels, "End Game");
    }

    
    public static void main(String[] args) { //main run class
        SwingUtilities.invokeLater(() -> new BuckshotRouletteGUI());
    }
}

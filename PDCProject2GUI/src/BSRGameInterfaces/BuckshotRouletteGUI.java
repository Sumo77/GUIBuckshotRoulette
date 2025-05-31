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
    private final CardLayout panelLayout;//controls panel order
    private final JPanel allPanels;//holds all panels
    
    public BuckshotRouletteGUI() {
        JFrame frame = new JFrame("Buckshot Roulette");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);//game size
        
        panelLayout = new CardLayout();
        allPanels = new JPanel(panelLayout);
        
        StartMenuPanel startPanel = new StartMenuPanel(this, game);//start menu panel
        allPanels.add(startPanel, "Start Menu");
        GamePanel gamePanel = new GamePanel(this, game);//game menu panel
        allPanels.add(gamePanel, "Game");
        EndGamePanel endPanel = new EndGamePanel(this, game);//end game menu panel
        allPanels.add(endPanel, "End Game");
        frame.add(allPanels);//add panels to game frame window
        
        frame.setVisible(true);
        
        panelLayout.show(allPanels, "Game"); //change which panel showing
    }
    
    public static void main(String[] args) { //main run class
        SwingUtilities.invokeLater(() -> new BuckshotRouletteGUI());
    }
}

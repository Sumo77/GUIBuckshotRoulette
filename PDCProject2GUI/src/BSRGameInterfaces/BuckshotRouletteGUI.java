/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BSRGameInterfaces;

import BSRCodeLogic.GameModified;
import javax.swing.*;
import java.awt.*;

/**
 * @author Davor Georgiev
 * @author Summer Harris
 */

public class BuckshotRouletteGUI extends JFrame {
    
    private GameModified game;
    private CardLayout panelLayout;
    private JPanel allPanels;
    
    public BuckshotRouletteGUI() {
        JFrame frame = new JFrame("Buckshot Roulette");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        
        panelLayout = new CardLayout();
        allPanels = new JPanel(panelLayout);
        
        StartMenuPanel startPanel = new StartMenuPanel(this, game);
        allPanels.add(startPanel, "Start Menu");
        GamePanel gamePanel = new GamePanel(this, game);
        allPanels.add(gamePanel, "Game");
        EndGamePanel endPanel = new EndGamePanel(this, game);
        allPanels.add(endPanel, "End Game");
        frame.add(allPanels);
        
        frame.setVisible(true);
        
        panelLayout.show(allPanels, "Start Menu"); //change which panel showing
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BuckshotRouletteGUI());
    }
}

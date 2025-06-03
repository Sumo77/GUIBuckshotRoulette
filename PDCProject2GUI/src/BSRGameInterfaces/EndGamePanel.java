/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BSRGameInterfaces;

import BSRCodeLogic.GameLogic;
import java.awt.*;
import javax.swing.JPanel;

/**
 * @author Davor Georgiev
 * @author Summer Harris
 */

public class EndGamePanel extends JPanel {
    private BuckshotRouletteGUI mainGUI; // Import main gui
    private GameLogic game; // Import game logic
    //define all interactable buttons / textfields / drawings (start button or textfield for usernames)
    
    public EndGamePanel(BuckshotRouletteGUI mainGUI, GameLogic game) {
        //gui logic
    }
    
    public void paint(Graphics g) {
        g.drawString("End Test", 40, 50);
        repaint();
    }
}

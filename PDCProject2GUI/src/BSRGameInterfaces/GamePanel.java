/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BSRGameInterfaces;

import BSRCodeLogic.GameModified;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * @author Davor Georgiev
 * @author Summer Harris
 */

public class GamePanel extends JPanel {
    private BuckshotRouletteGUI mainGUI; // Import main gui
    private GameModified game; // Import game logic
    //define all interactable buttons / textfields / drawings (start button or textfield for usernames)
    
    public GamePanel(BuckshotRouletteGUI mainGUI, GameModified game) {
        //gui logic
    }
    
    public void paint(Graphics g) {
        g.drawString("Game Test", 40, 50);
        repaint();
    }
}

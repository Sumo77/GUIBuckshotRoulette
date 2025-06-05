/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BSRGameInterfaces;

import BSRCodeLogic.GameLogic;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Davor Georgiev
 * @author Summer Harris
 */

public class StartMenuPanel extends JPanel {
    private BuckshotRouletteGUI mainGUI; // Import main gui
    private GameLogic game; // Import game logic
    //define all interactable buttons / textfields / drawings (start button or textfield for usernames)
    private JButton startButton;
    private JButton exitButton;
    
    public StartMenuPanel(BuckshotRouletteGUI mainGUI, GameLogic game) {
        //gui logic
        setLayout(null);
        
        startButton = new JButton("Start");
        startButton.setBounds(375, 300, 100, 40);
        
        
        
        exitButton = new JButton("Exit");
        exitButton.setBounds(375, 400, 100, 40);
        exitButton.addActionListener((ActionEvent e) -> {
            System.exit(0); // Exit the application
        });
        

        // Add button to panel
        add(startButton);
        add(exitButton);
    }
    
    
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Display title
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Buckshot Roulette", 200, 100);
        // Display header
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Welcome to Buckshot Roulette!", 250, 200);
        // Display creators
        g.setFont(new Font("Arial", Font.PLAIN, 15));
        g.drawString("Created by Summer Harris and Davor Georgiev", 250, 700);
    }
}

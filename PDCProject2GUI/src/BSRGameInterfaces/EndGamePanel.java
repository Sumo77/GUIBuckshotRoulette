/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BSRGameInterfaces;

import BSRCodeLogic.GameLogic;
import BSRGameDatabase.WinTableManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;

/**
 * @author Davor Georgiev
 * @author Summer Harris
 */

public class EndGamePanel extends JPanel {
    //define all interactable buttons / textfields / drawings (start button or textfield for usernames)
    private JButton playAgainButton;
    private JButton exitButton;
    private JTextArea scoreArea;
    
    public EndGamePanel(BuckshotRouletteGUI mainGUI, GameLogic game) {
        //gui logic
        ArrayList scoreboard = game.displayWinners();
        setLayout(null);
        
        playAgainButton = new JButton("Play Again?");
        playAgainButton.setBounds(375, 300, 100, 40);
        playAgainButton.addActionListener(e -> { 
            mainGUI.showPanel("Start Menu");
        });
        
        exitButton = new JButton("Exit");
        exitButton.setBounds(375, 400, 100, 40);
        exitButton.addActionListener((ActionEvent e) -> {
            System.exit(0); // Exit the application
        });
        
        // Scoreboard Area
        scoreArea = new JTextArea();
        scoreArea.setBounds(200, 150, 400, 120); // Set position and size
        scoreArea.setEditable(false); // Make it read-only
        scoreArea.setFont(new Font("Monospaced", Font.PLAIN, 14)); // Use monospaced font for alignment

        // Populate the scoreboard
        StringBuilder scoreText = new StringBuilder("Player Scores:\n");
        for (Object entry : scoreboard) {
            scoreText.append((String) entry).append("\n");
        }
        scoreArea.setText(scoreText.toString());
        
        
        add(playAgainButton);
        add(exitButton);
        add(scoreArea);
    }
    
    
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Display title
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Game Over", 290, 100);
        // Display header
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Thank you for playing!", 293, 200);
    }
}

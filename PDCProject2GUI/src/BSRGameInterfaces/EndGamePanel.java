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
    private final JButton playAgainButton;
    private final JButton exitButton;
    private final JTextArea scoreArea;
    
    public EndGamePanel(BuckshotRouletteGUI mainGUI, GameLogic game) {
        //gui logic
        ArrayList scoreboard = game.displayWinners();
        setLayout(null);
        
        // Set up play again button 
        playAgainButton = new JButton("Play Again?");
        playAgainButton.setBounds(320, 300, 200, 80);
        ImageIcon signBackground = new ImageIcon("./resources/signTemplate.png");
        playAgainButton.setIcon(signBackground);
        
        playAgainButton.setFont(new Font("Arial", Font.BOLD, 20));
        playAgainButton.setHorizontalTextPosition(SwingConstants.CENTER);
        playAgainButton.setVerticalTextPosition(SwingConstants.CENTER);
        playAgainButton.addActionListener(e -> { 
            mainGUI.showPanel("Start Menu"); // Move back to start menu when play again button is pressed
        });
        // Set up exit button 
        exitButton = new JButton("Exit");
        exitButton.setBounds(320, 400, 200, 80);
        exitButton.setIcon(signBackground);
        exitButton.setFont(new Font("Arial", Font.BOLD, 20));
        exitButton.setHorizontalTextPosition(SwingConstants.CENTER);
        exitButton.setVerticalTextPosition(SwingConstants.CENTER);
        exitButton.addActionListener((ActionEvent e) -> {
            System.exit(0); // Exit the application when exit button is pressed
        });
        
        // Leaderboard Area
        scoreArea = new JTextArea();
        scoreArea.setBounds(100, 300, 400, 300); // Set position and size
        scoreArea.setEditable(false); // Make it read-only
        scoreArea.setFont(new Font("Arial", Font.BOLD, 14)); 
        scoreArea.setOpaque(false);

        // Fill in the scoreboard
        StringBuilder scoreText = new StringBuilder("Player Win Leaderboard:\n");
        for (Object entry : scoreboard) {
            scoreText.append((String) entry).append("\n");
        }
        scoreArea.setText(scoreText.toString());
        
        // Display buttons and leaderboard on the panel
        add(playAgainButton);
        add(exitButton);
        add(scoreArea);
    }
    
    
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Display picnic background
        Image background = new ImageIcon("./resources/picnicBG.png").getImage();
        g.drawImage(background, 0, 0, this);
        // Display title
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.setColor(Color.WHITE);
        g.drawString("Game Over", 290, 135);
        // Display header
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.setColor(Color.BLACK);
        g.drawString("Thank you for playing!", 293, 200);
        // Display banana gun
        Image gun = new ImageIcon("./resources/banana.png").getImage();
        g.drawImage(gun, 100, 130, this);
        // Display decorative Orange
        Image orange = new ImageIcon("./resources/OrangePlayerLEFT.png").getImage();
        g.drawImage(orange, 500, 130, this);
        // Display decorative Orange
        Image apple = new ImageIcon("./resources/ApplePlayerTOP.png").getImage();
        g.drawImage(apple, 600, 480, this);
        // Display decorative Apple
        Image pear = new ImageIcon("./resources/PearPlayerRIGHT.png").getImage();
        g.drawImage(pear, 200, 390, this);
        // Display decorative Pear
        Image grape = new ImageIcon("./resources/GrapePlayerBOTTOM.png").getImage();
        g.drawImage(grape, 100, 530, this);
        // Display decorative 
        Image pumpkinTable = new ImageIcon("./resources/pumpkinTable.png").getImage();
        g.drawImage(pumpkinTable, 630, -50, this);
        
    }
}

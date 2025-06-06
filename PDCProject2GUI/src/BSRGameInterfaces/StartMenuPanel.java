/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BSRGameInterfaces;

import BSRCodeLogic.GameLogic;
import static BSRCodeLogic.GameLogic.alivePlayerList;
import static BSRCodeLogic.GameLogic.winManager;
import BSRCodeLogic.Player;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import BSRCodeLogic.PowerUpManager;
import javax.swing.JOptionPane;


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
    private JTextField playerCountField;
    private JTextField[] playerNameFields;
    private JButton twoPlayersButton;
    private JButton threePlayersButton;
    private JButton fourPlayersButton;
    
    
    public StartMenuPanel(BuckshotRouletteGUI mainGUI, GameLogic game) {
        //gui logic
        this.mainGUI = mainGUI;
        setLayout(null);
        
        
        startButton = new JButton("Start");
        startButton.setBounds(375, 300, 100, 40);
        startButton.addActionListener((ActionEvent e) -> showPlayerNumber());
        
        
        exitButton = new JButton("Exit");
        exitButton.setBounds(375, 400, 100, 40);
        exitButton.addActionListener((ActionEvent e) -> {
            System.exit(0); // Exit the application
        });
        

        // Add button to panel
        add(startButton);
        add(exitButton);
    }
    
    private void showPlayerNumber() {
        // Remove the Start button
        remove(startButton);
        repaint();
        
        JLabel promptLabel = new JLabel("How many players are playing?");
        promptLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        promptLabel.setHorizontalAlignment(SwingConstants.CENTER);
        promptLabel.setBounds(220, 200, 400, 40);
        add(promptLabel);

        // Add player count buttons
        twoPlayersButton = new JButton("2 Players");
        threePlayersButton = new JButton("3 Players");
        fourPlayersButton = new JButton("4 Players");

        // Set button bounds
        twoPlayersButton.setBounds(235, 300, 120, 40);
        threePlayersButton.setBounds(365, 300, 120, 40);
        fourPlayersButton.setBounds(495, 300, 120, 40);

        // Add action listeners
        twoPlayersButton.addActionListener(e -> {
            initializePlayers(2);
            mainGUI.showPanel("Game");
        });
        threePlayersButton.addActionListener(e -> {
            initializePlayers(3);
            mainGUI.showPanel("Game");
        });
        fourPlayersButton.addActionListener(e -> {
            initializePlayers(4);
            mainGUI.showPanel("Game");
        });


        // Add new buttons to the panel
        add(twoPlayersButton);
        add(threePlayersButton);
        add(fourPlayersButton);

        revalidate();
        repaint();
    }
    
    private void initializePlayers(int numPlayers) {
        alivePlayerList.clear(); // Clear the list of alive players if it contains any players
        PowerUpManager powerUps = new PowerUpManager(); // Example manager for power-ups

        for (int playerNum = 0; playerNum < numPlayers; playerNum++) {
            String userName = getUniqueUsername(playerNum); // Get a unique username from the user

            Player player = new Player(userName, winManager); // Create a new Player instance
            alivePlayerList.add(player); // Add player to the alive players list

            if (player.playerExists()) { // Check if the player exists in the wins file
                player.retrievePlayerWins(); // Retrieve player's win count
            } else {
                player.addNewPlayerToWins(); // Add new player to wins file
            }

            assignPlayerPowerUps(player, powerUps); // Assign power-ups to the player
        }
    }

    private String getUniqueUsername(int playerNum) {
        String userName;
        do {
            userName = JOptionPane.showInputDialog(this, 
                "Enter a unique username for Player " + (playerNum + 1) + ":", 
                "Player Username", 
                JOptionPane.QUESTION_MESSAGE);
        } while (userName == null || userName.trim().isEmpty() || usernameExists(userName));
        return userName.trim();
    }

    private boolean usernameExists(String userName) {
        for (Player player : alivePlayerList) {
            if (player.getUsername().equalsIgnoreCase(userName)) {
                return true;
            }
        }
        return false;
    }


    private void assignPlayerPowerUps(Player player, PowerUpManager powerUps) {
        // Logic for assigning power-ups
        game.assignPlayerPowerUps(player, powerUps);
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

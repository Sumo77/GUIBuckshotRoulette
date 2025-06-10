/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BSRGameInterfaces;

import BSRCodeLogic.*;
import static BSRCodeLogic.GameLogic.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Random;


/**
 * @author Davor Georgiev
 * @author Summer Harris
 */

public class StartMenuPanel extends JPanel {
    private final BuckshotRouletteGUI mainGUI; // Import main gui
    private GameLogic game; // Import game logic
    //define all interactable buttons / textfields / drawings (start button or textfield for usernames)
    private final JButton startButton;
    private final JButton exitButton;
    private JButton twoPlayersButton;
    private JButton threePlayersButton;
    private JButton fourPlayersButton;
    private JLabel hintLabel;
    
    
    public StartMenuPanel(BuckshotRouletteGUI mainGUI, GameLogic game) {
        //gui logic
        this.mainGUI = mainGUI;
        setLayout(null);
        
        
        startButton = new JButton("Start");
        startButton.setBounds(320, 300, 200, 80);
        ImageIcon signBackground = new ImageIcon("./resources/signTemplate.png");
        startButton.setIcon(signBackground);
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setHorizontalTextPosition(SwingConstants.CENTER);
        startButton.setVerticalTextPosition(SwingConstants.CENTER);
        startButton.addActionListener((ActionEvent e) -> showPlayerNumber());
        
        
        exitButton = new JButton("Exit");
        exitButton.setBounds(320, 400, 200, 80);
        exitButton.setIcon(signBackground);
        exitButton.setFont(new Font("Arial", Font.BOLD, 20));
        exitButton.setHorizontalTextPosition(SwingConstants.CENTER);
        exitButton.setVerticalTextPosition(SwingConstants.CENTER);
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
        
        hintLabel = new JLabel();
        hintLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        hintLabel.setBounds(220, 600, 400, 40);
        randomHintArray();

        // Add player count buttons
        twoPlayersButton = new JButton("2 Players");
        threePlayersButton = new JButton("3 Players");
        fourPlayersButton = new JButton("4 Players");

        // Set button bounds
        twoPlayersButton.setBounds(105, 300, 200, 80);
        threePlayersButton.setBounds(320, 300, 200, 80);
        fourPlayersButton.setBounds(535, 300, 200, 80);
        
        ImageIcon signBackground = new ImageIcon("./resources/signTemplate.png");
        twoPlayersButton.setIcon(signBackground);
        twoPlayersButton.setFont(new Font("Arial", Font.BOLD, 20));
        twoPlayersButton.setHorizontalTextPosition(SwingConstants.CENTER);
        twoPlayersButton.setVerticalTextPosition(SwingConstants.CENTER);
        
        threePlayersButton.setIcon(signBackground);
        threePlayersButton.setFont(new Font("Arial", Font.BOLD, 20));
        threePlayersButton.setHorizontalTextPosition(SwingConstants.CENTER);
        threePlayersButton.setVerticalTextPosition(SwingConstants.CENTER);
        
        fourPlayersButton.setIcon(signBackground);
        fourPlayersButton.setFont(new Font("Arial", Font.BOLD, 20));
        fourPlayersButton.setHorizontalTextPosition(SwingConstants.CENTER);
        fourPlayersButton.setVerticalTextPosition(SwingConstants.CENTER);

        // Add action listeners
        twoPlayersButton.addActionListener(e -> {
            initializePlayers(2);
            mainGUI.startGame();
        });
        threePlayersButton.addActionListener(e -> {
            initializePlayers(3);
            mainGUI.startGame();
        });
        fourPlayersButton.addActionListener(e -> {
            initializePlayers(4);
            mainGUI.startGame();
        });


        // Add new buttons to the panel
        add(twoPlayersButton);
        add(threePlayersButton);
        add(fourPlayersButton);
        add(hintLabel);

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

    while (true) {
        userName = JOptionPane.showInputDialog(this, 
            "Enter a unique username for Player " + (playerNum + 1) + ":", 
            "Player Username", 
            JOptionPane.QUESTION_MESSAGE);

        // Handle cancel or close dialog
        if (userName == null) {
            JOptionPane.showMessageDialog(this, 
                "Player setup canceled. Exiting the game.", 
                "Setup Canceled", 
                JOptionPane.INFORMATION_MESSAGE);
            System.exit(0); // Exit the application
        }

        userName = userName.trim();

        if (!userName.isEmpty() && !usernameExists(userName)) {
            return userName; // Return the valid username
        }

        // Notify the user if the username is invalid
        JOptionPane.showMessageDialog(this, 
            "Invalid or duplicate username. Please try again.", 
            "Invalid Username", 
            JOptionPane.ERROR_MESSAGE);
    }
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
    
    public void randomHintArray() {
        Random random = new Random();
        int randomHint = random.nextInt(5);
        
        switch(randomHint) {
            case 0:
                hintLabel.setText("Hint: Click the Banana to shoot !");
                break;
            case 1:
                hintLabel.setText("Hint: Click the Basket to select a powerups !");
                break;
            case 2:
                hintLabel.setText("Hint: Cigarettes heal you by 1 health!");
                break;
            case 3:
                hintLabel.setText("Hint: Reverse Order reverses the order in which you play!");
                break;
            case 4:
                hintLabel.setText("Hint: Blind Reload Gun reloads and reorganises the banana gun");
                break;
            case 5:
                hintLabel.setText("Hint: Double Damage upgrades your shot to 2 damage! You lose it if you shoot a blank though..");
                break;
        }
        
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Display background
        Image background = new ImageIcon("./resources/picnicBG.png").getImage();
        g.drawImage(background, 0, 0, this);
        // Display title
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.setColor(Color.WHITE);
        g.drawString("Buckshot Roulette", 200, 135);
        // Display header
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.setColor(Color.BLACK);
        g.drawString("Welcome to Buckshot Roulette!", 250, 200);
        // Display creators
        g.setFont(new Font("Arial", Font.PLAIN, 15));
        g.drawString("Created by Summer Harris and Davor Georgiev", 250, 700);
        // Display decorative Gun
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

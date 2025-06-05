/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BSRGameInterfaces;

import BSRCodeLogic.GameLogic;
import java.awt.*;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

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
    private JLabel title;
    private JLabel hintLabel;
    
    public StartMenuPanel(BuckshotRouletteGUI mainGUI, GameLogic game) {
        //gui logic
        setLayout(new BorderLayout());
        
        title = new JLabel("Buckshot Roulette", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);
        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        startButton = new JButton("Start");
        exitButton = new JButton("Exit");
        
        buttonPanel.add(startButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.CENTER);
        
        hintLabel = new JLabel("Created by Davor Georgiev & Summer Harris", SwingConstants.CENTER);
        hintLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        add(hintLabel, BorderLayout.SOUTH);
        
        startButton.addActionListener(e -> System.out.println("Game Started!"));
        exitButton.addActionListener(e -> System.exit(0));
        
    }
    
    public void paint(Graphics g) {
        super.paintComponent(g);
    }
}

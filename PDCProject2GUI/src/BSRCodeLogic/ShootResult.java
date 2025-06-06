package BSRCodeLogic;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Prumm
 */
    public class ShootResult { // Holds the returns of all the shoot logic ! In order to keep logic and gui seperate haha
        public String shot;
        public boolean playerDied;
        public boolean roundReloaded;

        public ShootResult(String shot, boolean playerDied, boolean roundReloaded) {
            this.shot = shot;
            this.playerDied = playerDied;
            this.roundReloaded = roundReloaded;
        }
    }

package cz.cvut.fel.pjv;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        int boardWidth = 640;
        int boardHeight = 480;

        JFrame frame = new JFrame("Walking char game");
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null); // center the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close the program when the window is closed

        walkingGame walkingGame = new walkingGame(boardWidth, boardHeight);
        frame.add(walkingGame);
        frame.pack(); // fit the window to the preferred size of its subcomponents
        walkingGame.requestFocus(); // request focus for the game panel
    }
}
package main;

import javax.swing.JFrame;

/*
 * Author:			Jacob Stewart
 * Project:			Pacman in Java
 * Date Started:	March 11, 2024
 * Description: 	This project is a recreation of the original release of pacman in 1980 that was dispatched to
 * 					arcade machines, art and sound is created using a mini arcade machine i own with the original pacman on it.
 * 
 * Class Description: This class serves as the entry point into the application, window setup and the game loop will be called here.
 */

public class Main {

	public static void main(String[] args) {

		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Pacman In Java");

		// Adding JPanel to JFrame
		Engine engine = new Engine();
		window.add(engine);
		window.setIconImage(engine.iconImage);
		// Forcing JFrame to fit the size of its subcomponent (JPanel)
		window.pack();

		window.setLocationRelativeTo(null);
		window.setVisible(true);

		// Setup game and start thread
		engine.startThread();
		engine.setupGame();
	}

}

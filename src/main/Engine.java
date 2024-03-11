package main;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/*
 * Author:			Jacob Stewart
 * Project:			Pacman in Java
 * Date Started:	March 11, 2024
 * Class Description: 
 * 		This class will act as the Game Engine, it is responsible for supplying the JFrame
 * 		and controlling the update and render methods.
 */


@SuppressWarnings("serial")
public class Engine extends JPanel implements Runnable {

	// TODO JPanel Settings
	
	// TODO Game States
	
	public Engine() {
		
	}
	
	public void startThread() {
		
	}
	
	public void setupGame() {
		
	}

	@Override
	public void run() {
		// TODO Create Game Loop at 60 FPS CAP.
	}
	
	public void update() {
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Converting to Graphics2D which includes more functionality then Graphics.
		Graphics2D g2 = (Graphics2D) g;
		
		// TODO Depending on Game State render Actors and UI.
	}
	
	
	// AUDIO CONTROL
	public void playMusic(int i) {
		
	}
	
	public void stopMusic() {
		
	}
	
	public void playSoundEffect(int i) {
		
	}
	
	public void stopSoundEffect() {
		
	}
	// AUDIO CONTROL
	
	
	
}

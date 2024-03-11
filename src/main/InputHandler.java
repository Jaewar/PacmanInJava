package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * Author:			Jacob Stewart
 * Project:			Pacman in Java
 * Date Started:	March 11, 2024
 * Class Description: 
 * 		This class is responsible for taking in key presses and updating boolean values
 * 		to control UI navigation and Pacman movement.
 */

public class InputHandler implements KeyListener {

	private Engine engine;
	
	// TODO Boolean keypresses
	
	public InputHandler(Engine e) {
		this.engine = e;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// NOT USED
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// NOT USED
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Depending on game state create statements to control key input.
		int keyCode = e.getKeyCode();
		
		if (keyCode == KeyEvent.VK_W) {
		}
		
	}
	
	// In event that key presses need to be reset to default false value.
	public void resetKeys() {
		
	}

	
	
}

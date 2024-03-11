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

	// Boolean keypresses
	public boolean upPressed, downPressed, leftPressed, rightPressed;

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

		// TITLE STATE
		if (engine.gameState == engine.titleState) {
			if (keyCode == KeyEvent.VK_ENTER) {
				engine.gameState = engine.playState;
			}
		}

		if (engine.gameState == engine.playState) {
			if (keyCode == KeyEvent.VK_W) {
				resetKeys();
				upPressed = true;
			}
			if (keyCode == KeyEvent.VK_S) {
				resetKeys();
				downPressed = true;
			}
			if (keyCode == KeyEvent.VK_A) {
				resetKeys();
				leftPressed = true;
			}
			if (keyCode == KeyEvent.VK_D) {
				resetKeys();
				rightPressed = true;
			}
		}

		// DEBUG GAME STATE SWAPPING
		if (keyCode == KeyEvent.VK_NUMPAD1) {
			engine.gameState = 1;
		}
		if (keyCode == KeyEvent.VK_NUMPAD2) {
			engine.gameState = 2;
		}
		if (keyCode == KeyEvent.VK_NUMPAD3) {
			engine.gameState = 3;
		}
		if (keyCode == KeyEvent.VK_NUMPAD4) {
			engine.gameState = 4;
		}

	}

	// In event that key presses need to be reset to default false value.
	public void resetKeys() {
		upPressed = false;
		downPressed = false;
		leftPressed = false;
		rightPressed = false;
	}

}

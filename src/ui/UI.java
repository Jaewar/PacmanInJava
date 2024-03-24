package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.Engine;

/*
 * Author:			Jacob Stewart
 * Project:			Pacman in Java
 * Date Started:	March 20, 2024
 * Class Description: 
 * 		This class will act as the updater and renderer for all UI elements. Including timers
 * 		and text and images.
 */

public class UI {
	
	private Engine engine;
	private Graphics2D g2;
	private Font arial_40;
	
	// timers for intro sequence
	private int introTimer = 0, counter = 0;
	
	// timers for start countdown
	private int startTimer = 4, startCounter = 0;
	
	// pacman and ghost starting positions for run across animations.
	private int pManX = 600, rX = 650, bX = 700, pX = 750, oX = 800;

	public boolean runIntro = true;
	
	public UI(Engine e) {
		this.engine = e;
		arial_40 = new Font("Arial", Font.PLAIN, 40);
	}
	
	public void resetUI() {
		introTimer = 0;
		counter = 0;
		pManX = 600;
		rX = 650;
		bX = 700;
		pX = 750;
		oX = 800;
	}
	
	public void update() {
		if (introTimer < 14 && runIntro) {
			counter++;
			if (counter >= 60) {
				introTimer += 1;
				counter = 0;
			}
		} else {
			// resetting timers and turning runIntro to false, intro is only displayed once
			// at launch of application.
			counter = 0;
			introTimer = 0;
			runIntro = false;
		}
		if (engine.gameState == engine.playState) {
			if (engine.startCountdown = true) {
				if (startTimer > 0) {
					startCounter++;
					if (startCounter >= 60) {
					startTimer--;
					startCounter = 0;
					}
				}
				if (startTimer == 0) {
					engine.startCountdown = false;
				}
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		// TITLE STATE
		if (engine.gameState == engine.titleState) {
			drawTitleScreen();
		}
		
		// PLAY STATE
		if (engine.gameState == engine.playState) {
			// drawing score, highscore and player lives.
			g2.setFont(arial_40);
			g2.setColor(Color.white);
			
			g2.drawString("Score: " + engine.score, 25, 50);
			g2.drawString("HighScore: " + engine.highScore, 300, 50);
			
			// player lives
			int xOffset = 25;
			for (int i = 0; i < engine.pacman.lives; i++) {
				g2.drawImage(engine.pacman.left1, xOffset, 800, null);
				xOffset += 50;
			}
			if (engine.startCountdown == true) {
				if (startTimer == 1) {
					g2.drawString("GO!", engine.tileSize * 8, engine.tileSize * 15);
				} else {
				g2.drawString("" + startTimer, engine.tileSize * 9, engine.tileSize * 15);
				}
			}
		}
		
		// PAUSE STATE (DEBUG)
		if (engine.gameState == engine.pauseState) {
			drawPauseScreen();
		}
	}
	
	public void restartStartTimer() {
		engine.startCountdown = true;
		this.startTimer = 4;
		this.startCounter = 0;
	}

	public void drawTitleScreen() {
		// Larger than normal render size
		int ghostSize = 48; 

		g2.setFont(arial_40.deriveFont(Font.BOLD, 30));
		g2.setColor(Color.white);

		if (introTimer < 14 && runIntro) {
			// TOP SCREEN
			g2.drawString("1UP", 100, 30);
			g2.drawString("00", 140, 55);
			g2.drawString("HIGH SCORE", 220, 30);
			g2.drawString("" + engine.highScore, 280, 60);
			g2.drawString("2UP", 450, 30);

			// CHAR / NICKNAME (WHITE)
			if (introTimer >= 1) {
				g2.drawString("CHARACTER  /  NICKNAME", 170, 150);
			}

			// SHADOW / BLINKY (RED)
			if (introTimer > 2) {
				g2.setColor(Color.red);
				g2.drawImage(engine.rGhost.right1, 100, 180, ghostSize, ghostSize, null);
				g2.drawString("- SHADOW", 170, 210);
				g2.drawString("\"BLINKY\"", 400, 210);
			}

			if (introTimer > 3) {
				// SPEEDY / PINKY (PINK)
				g2.setColor(Color.pink);
				//g2.drawImage(engine.pGhost.right1, 100, 280, ghostSize, ghostSize, null);
				g2.drawString("- SPEEDY", 170, 310);
				g2.drawString("\"PINKY\"", 400, 310);
			}

			if (introTimer > 4) {
				// BASHFUL / INKY (CYAN/Light Blue)
				g2.setColor(Color.cyan);
				//g2.drawImage(engine.bGhost.right1, 100, 380, ghostSize, ghostSize, null);
				g2.drawString("- BASHFUL", 170, 410);
				g2.drawString("\"INKY\"", 400, 410);
			}

			if (introTimer > 5) {
				// POKEY / CLYDE (Orange (lighter))
				g2.setColor(Color.orange);
				//g2.drawImage(engine.oGhost.right1, 100, 480, ghostSize, ghostSize, null);
				g2.drawString("- POKEY", 170, 510);
				g2.drawString("\"CLYDE\"", 400, 510);
			}
			if (introTimer > 6) {
				// Pacman and ghosts			
				g2.drawImage(engine.rGhost.left1, rX, 560, 32, 32, null);
				//g2.drawImage(engine.pGhost.left1, pX, 560, 32, 32, null);
				//g2.drawImage(engine.bGhost.left1, bX, 560, 32, 32, null);
				//g2.drawImage(engine.oGhost.left1, oX, 560, 32, 32, null);
				//g2.drawImage(engine.player.left1, pManX, 560, null);

				pManX -= 2;
				pX -= 2;
				bX -= 2;
				oX -= 2;
				rX -= 2;
			}

			// point values.
			g2.setColor(Color.white);
			// small orb
			if (engine.obj[0] == null) {
				
			} else {
			g2.drawImage(engine.obj[0].image, 250, 650, null);
			g2.drawString("10 PTS", 300, 665);

			// big orb
			g2.drawImage(engine.obj[0].image, 242, 700, 32, 32, null);
			g2.drawString("50 PTS", 300, 725);
			}
			// copyright message
			g2.drawString("\u00A9 1980 MIDWAY MFG. CO. (CLONE)", 50, 800);
			// CREDITS
			g2.drawString("CREDIT  0", 50, 860);
			g2.dispose();
		} else {
			// TOP SCREEN
			g2.drawString("1UP", 100, 30);
			g2.drawString("00", 140, 55);
			g2.drawString("HIGH SCORE", 220, 30);
			g2.drawString("" + engine.highScore, 280, 60);
			g2.drawString("2UP", 450, 30);

			g2.drawString("PRESS ENTER BUTTON", 140, 400);

			g2.setColor(Color.cyan);
			g2.drawString("1 PLAYER ONLY", 185, 475);

			g2.setColor(Color.white);
			g2.drawString("BONUS PAC-MAN FOR 10000 PTS", 70, 550);
			
			g2.drawString("\u00A9 1980 MIDWAY MFG. CO. (CLONE)", 50, 700);
			g2.drawString("CREDIT  1", 50, 860);
		}
	}
	
	public void drawPauseScreen() {
		String text = "PAUSED";

		g2.drawString(text, engine.tileSize * 8, engine.tileSize * 15);
	}
}

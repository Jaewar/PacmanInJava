package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import actors.Pacman;
import actors.RedGhost;
import collision.CollisionManager;
import objects.ObjectManager;
import objects.SuperObject;
import tiles.TileManager;
import ui.UI;

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
	public final int tileSize = 32;
	
	// Max Tiles needed to support UI and Map.
	public final int maxScreenCol = 19; 
	public final int maxScreenRow = 24;
	
	// 608x868 pixels
	private final int screenWidth = 608; // tileSize * maxScreenCol
	private final int screenHeight = 868; // tileSize * maxScreenRow
	
	private final int FPS = 60;
	
	// Game States
	public int gameState;
	public final int titleState = 1;
	public final int playState = 2;
	public final int pauseState = 3;
	public final int deathState = 4;
	
	// Scoring
	public int score;
	public int highScore;
	public int pelletsRemaining;
	
	// INSTANCES
	public InputHandler inputH = new InputHandler(this);
	public Pacman pacman = new Pacman(this, inputH);
	public RedGhost rGhost = new RedGhost(this);
	public TileManager tileM = new TileManager(this);
	public CollisionManager cManager = new CollisionManager(this);
	public ObjectManager oManager = new ObjectManager(this);
	public UI ui = new UI(this);
	
	public SuperObject obj[] = new SuperObject[200];
	
	private Thread gameThread;
	
	public BufferedImage iconImage;
	
	public Engine() {
		// JPANEL SETTINGS
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		// Improved rendering time
		this.setDoubleBuffered(true);
		
		this.addKeyListener(inputH);
		this.setFocusable(true);
		
		// setting icon image
		try {
			iconImage = ImageIO.read(getClass().getResource("/player/" + "pacmanLeft1" + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startThread() {
		gameThread = new Thread(this);
		gameThread.start(); // calls run() method
	}
	
	public void setupGame() {
		gameState = titleState;
		
		score = 0;
		
		ui.resetUI();
		
		oManager.setObject();
	}

	@Override
	public void run() {
		// Using Delta/Accumulator Method to control game loop.
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		// FPS Check
		long timer = 0;
		int drawCount = 0;

		// game loop
		while (gameThread != null) {

			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1) {
				// UPDATE: update information such as positioning
				update();
				// DRAW: draw to screen with updated information.
				repaint(); // standard way to call paintComponent method.

				delta--;
				drawCount++;
			}

			// checking that game loop is running at the proper frames (60 fps)
			if (timer >= 1000000000) {
				// System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}

		}
		System.out.println("Thread closed");
		gameThread = null;
	}
	
	public void update() {
		switch(gameState) {
		case titleState:
			ui.update();
			break;
		case playState:
			pacman.update();
			rGhost.update();
			break;
		case pauseState:
			//System.out.println("Pause State 2");
			break;
		case deathState:
			//System.out.println("Death State 4");
			break;
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Converting to Graphics2D which includes more functionality then Graphics.
		Graphics2D g2 = (Graphics2D) g;
		
		// TODO Depending on Game State render Actors and UI.
		// TITLE SCREEN
		if (gameState == titleState) {
			ui.draw(g2);
		} else {
		// TILES
		tileM.draw(g2);
		
		// OBJECTS
		for (int i = 0; i < obj.length; i++) {
			if (obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}
		
		// ACTORS
		pacman.draw(g2);
		rGhost.draw(g2);
		
		// UI
		ui.draw(g2);
		}
		// release unnecessary memory after rendering.
		g2.dispose();
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

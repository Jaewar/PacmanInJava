package actors;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Engine;

/*
 * Author:			Jacob Stewart
 * Project:			Pacman in Java
 * Date Started:	March 11, 2024
 * Class Description: 
 * 		This class is responsible for rendering, updating and controlling the red ghost
 * 		and his unique chase/scatter states.
 */

public class RedGhost extends Actor {
	
	private Engine engine;
	
	public int state = 0;
	public int chaseState = 1;
	public int runState = 2;
	
	public int counter = 0;
	public int runTimer = 0;
	
	public RedGhost(Engine e) {
		this.engine = e;
		
		// hitbox is 1 pixel smaller (AI should be able to perform perfect movement)
		hitbox = new Rectangle(1,1,31,31);
		
		// recording default position for when changes are made for collision prediction.
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
	}
	
	public void setDefaultValues() {
		// 9 * 10 (default ghost starting position outside of box).
		x = engine.tileSize * 9;
		y = engine.tileSize * 10;
		speed = 1;
		state = chaseState;
		direction = "left";
	}
	
	public void getRedGhostImage() {
		try {
			idle1 = ImageIO.read(getClass().getResource("/ghost/redghost/redGhostStill.png"));
			up1 = ImageIO.read(getClass().getResource("/ghost/redghost/redGhostUp1.png"));
			up2 = ImageIO.read(getClass().getResource("/ghost/redghost/redGhostUp2.png"));
			down1 = ImageIO.read(getClass().getResource("/ghost/redghost/redGhostDown1.png"));
			down2 = ImageIO.read(getClass().getResource("/ghost/redghost/redGhostDown2.png"));
			left1 = ImageIO.read(getClass().getResource("/ghost/redghost/redGhostLeft1.png"));
			left2 = ImageIO.read(getClass().getResource("/ghost/redghost/redGhostLeft2.png"));
			right1 = ImageIO.read(getClass().getResource("/ghost/redghost/redGhostRight1.png"));
			right2 = ImageIO.read(getClass().getResource("/ghost/redghost/redGhostRight2.png"));
			run1 = ImageIO.read(getClass().getResource("/ghost/run1.png"));
			run2 = ImageIO.read(getClass().getResource("/ghost/run2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		// TODO Add AI and Animation
	}
	
	public void draw(Graphics2D g2) {
		
	}

}

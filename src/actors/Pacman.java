package actors;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Engine;
import main.InputHandler;
import tiles.Tile;
import utils.ImageScaler;

/*
 * Author:			Jacob Stewart
 * Project:			Pacman in Java
 * Date Started:	March 11, 2024
 * Class Description: 
 * 		This class is responsible for rendering, updating and providing controls to react
 * 		to the users input.
 */

public class Pacman extends Actor {

	private Engine engine;
	private InputHandler inputH;
	public int lives;

	public Pacman(Engine e, InputHandler inputH) {
		this.engine = e;
		this.inputH = inputH;

		hitbox = new Rectangle(8,8,8,8);

		// recording default hitbox location
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;

		setDefaultValues();
		getPlayerImage();
		
		lives = 3;

	}

	public void setDefaultValues() {
		// X and Y are calculated by multiplying the tileSize by the tile position
		// where you need pacman to start I.E 9 tiles right 18 tiles down.
		x = engine.tileSize * 9;
		y = engine.tileSize * 18;
		speed = 2;
		direction = "left";
	}

	public void getPlayerImage() {
		idle1 = setup("pacmanStill");
		up1 = setup("pacmanUp2");
		up2 = setup("pacmanUp1");
		down1 = setup("pacmanDown2");
		down2 = setup("pacmanDown1");
		left1 = setup("pacmanLeft1");
		left2 = setup("pacmanLeft2");
		right1 = setup("pacmanRight1");
		right2 = setup("pacmanRight2");
	}

	public BufferedImage setup(String imageName) {
		ImageScaler iScaler = new ImageScaler();
		BufferedImage image = null;

		try {
			image = ImageIO.read(getClass().getResource("/player/" + imageName + ".png"));
			image = iScaler.scaleImage(image, engine.tileSize, engine.tileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;
	}

	public void update() {
		if (inputH.upPressed == true || inputH.downPressed == true || inputH.leftPressed == true
				|| inputH.rightPressed == true) {
			// player position and movement
			if (inputH.upPressed == true) {
				direction = "up";
			} else if (inputH.downPressed == true) {
				direction = "down";
			} else if (inputH.leftPressed == true) {
				direction = "left";
			} else if (inputH.rightPressed == true) {
				direction = "right";
			}

			colliding = false;
			engine.cManager.checkTiles(this);
			
			int objIndex = engine.cManager.checkObject(this, true);
			pickUpObject(objIndex);
			
			// Adjusting x and y pos based on speed.
			// @formatter:off
			if (colliding == false) {
				switch (direction) {
				case "up": y -= speed; break;
				case "down": y += speed; break;
				case "left": x -= speed; break;
				case "right": x += speed; break;
				}
			}
			// @formatter:on
		}
		// swapping spriteNum, triggering a different image to render (animating)
		spriteCounter++;
		if (spriteCounter > 8) {
			if (spriteNum == 1) {
				spriteNum = 2;
			} else if (spriteNum == 2) {
				spriteNum = 3;
			} else if (spriteNum == 3) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	}
	
	private void pickUpObject(int i) {
		if (i != 999) {

			String objectName = engine.obj[i].name;

			switch (objectName) {
			case "Pellet":
				//engine.stopSE();
				//engine.playSE(1);
				engine.score += 10;
				engine.obj[i] = null;
				engine.pelletsRemaining -= 1;
				break;
			case "LargePellet":
				engine.score += 15;
				engine.obj[i] = null;
				engine.rGhost.state = engine.rGhost.runState;
				engine.pelletsRemaining -= 1;
				break;
			case "TPRight":
				this.x = engine.tileSize * -1;
				this.y = engine.tileSize * 12;
				break;
			case "TPLeft":
				this.x = engine.tileSize * 19;
				this.y = engine.tileSize * 12;
				break;
			}

		}
		
		  //Possible code to reset objects and player position. 
		int count = 0; 
		for (int j= 0; j < engine.obj.length; j++) { 
			if (engine.obj[j] != null) { 
				count++; 
			} 
		} 
		// ghost release conditions
		if (count < 150) {

		}
		if (count < 120) {

		} 
		if (count < 170) {

		}
		System.out.println(engine.pelletsRemaining);
		if (engine.pelletsRemaining < 1) {
			engine.oManager.setObject();
			
			engine.rGhost.setDefaultValues();
			
			// TODO reset pacman on map completion
			//resetValuesOnCompletion();
			
			// TODO highscore manager needs implementing.
			//engine.hsm.writeScore();
		}
		
		// TODO HANDLE LIFEUP after 10000 score.
		/*
		 * if (engine.score >= lifeUpScore) { lifeUpScore += 10000; lives++; }
		 */
	}

	public void draw(Graphics2D g2) {

		BufferedImage image = null;
		
		// @formatter:off
		// Swapping Pacman image based on current sprite num.
		switch (direction) {
		case "up":
			if (spriteNum == 1) image = up1;
			if (spriteNum == 2) image = up2;
			if (spriteNum == 3) image = idle1;break;
		case "down":
			if (spriteNum == 1) image = down1;
			if (spriteNum == 2) image = down2;
			if (spriteNum == 3) image = idle1; break;
		case "left":
			if (spriteNum == 1) image = left1;
			if (spriteNum == 2) image = left2;
			if (spriteNum == 3) image = idle1; break;
		case "right":
			if (spriteNum == 1) image = right1;
			if (spriteNum == 2) image = right2;
			if (spriteNum == 3) image = idle1; break;
		}
		// @formatter:on

		g2.drawImage(image, x, y, null);

		g2.setColor(Color.red);
		g2.drawRect(x + hitbox.x, y + hitbox.y, hitbox.width, hitbox.height);

	}

}

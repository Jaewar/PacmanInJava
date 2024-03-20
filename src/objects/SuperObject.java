package objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Engine;

/*
 * Author:			Jacob Stewart
 * Project:			Pacman in Java
 * Date Started:	March 11, 2024
 * Class Description: 
 * 		This super class represents data shared between each object in the game.
 */

public class SuperObject {

	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int x,y;
	public Rectangle hitbox = new Rectangle(0,0,32,32);
	public int hitboxDefaultX = 0;
	public int hitboxDefaultY = 0;
	
	public void draw(Graphics2D g2, Engine e) {
		g2.drawImage(image, x, y, e.tileSize, e.tileSize, null);
	}

}

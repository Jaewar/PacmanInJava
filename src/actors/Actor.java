package actors;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/*
 * Author:			Jacob Stewart
 * Project:			Pacman in Java
 * Date Started:	March 11, 2024
 * Class Description: 
 * 		This super class provides the shared methods and variables between all actors.
 */

public abstract class Actor {

	public int x, y;

	public int speed;

	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, idle1, run1, run2;
	public String direction;

	public int spriteCounter = 0;
	public int spriteNum = 1;

	public Rectangle hitbox;

	public int hitboxDefaultX, hitboxDefaultY;

	public boolean colliding = false;

}

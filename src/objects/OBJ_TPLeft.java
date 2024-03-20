package objects;

import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * Author:			Jacob Stewart
 * Project:			Pacman in Java
 * Date Started:	March 20, 2024
 * Class Description: 
 * 		This class is used to create and instance of a empty object to act as a teleportation square
 * 		for pacman when he enters the hallway (LEFT SIDE).
 */

public class OBJ_TPLeft extends SuperObject{

	public OBJ_TPLeft() {
		name = "TPLeft";
		
		try {
			image = ImageIO.read(getClass().getResource("/tiles/wall.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		hitbox.width = 1;
	}
	
}

package objects;

import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * Author:			Jacob Stewart
 * Project:			Pacman in Java
 * Date Started:	March 11, 2024
 * Class Description: 
 * 		This class is used to create and instance of the large pellet and is responsible for
 * 		its own image and unique pickup action.
 */

public class OBJ_Large_Pellet extends SuperObject {

	public OBJ_Large_Pellet() {
		name = "LargePellet";
		
		try {
			image = ImageIO.read(getClass().getResource("/objects/largePellet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

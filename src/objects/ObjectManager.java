package objects;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import main.Engine;

/*
 * Author:			Jacob Stewart
 * Project:			Pacman in Java
 * Date Started:	March 12, 2024
 * Class Description: 
 * 		This class is responsible for creating objects and adding them to the engines object
 * 		array for rendering.
 */

public class ObjectManager {

	private Engine engine;
	
	public ObjectManager(Engine e) {
		this.engine = e;
	}
	
	public void setObject() {
		// Clearing object list (Reset for new levels)
		engine.pelletsRemaining = 0;
		
		for (int i = 0; i < engine.obj.length; i++) {
			engine.obj[i] = null;
		}
		
		int i = 0;
		try {
			InputStream is = getClass().getResourceAsStream("/maps/map01.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			// variables for tile/map layout
			int col = 0, row = 0, x = 0, y = 0;
			
			// looping through map file
			while (col < engine.maxScreenCol && row < engine.maxScreenRow) {
				String line = br.readLine();
				
				while (col < engine.maxScreenCol) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					
					// 10 refers to floor tile that should have a pellet
					if (num == 10 && i < engine.obj.length) {
						// checking that bounds stay within the map (excluding blank space)
						if (row > 2 && row < 23) {
							createObject(i, new OBJ_Small_Pellet(), x, y);
							engine.pelletsRemaining += 1;
							i++;
						}
					}
					col++;
					x++;
				}
				if (col == engine.maxScreenCol) {
					col = 0;
					x = 0;
					row++;
					y++;
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Placing 4 large pellets and teleport objects (manually increasing orbs remaining by 4).
		createObject(198, new OBJ_TPRight(), 20, 12);
		createObject(199, new OBJ_TPLeft(), -1, 12);
		
		createObject(197, new OBJ_Large_Pellet(), 1, 18);
		createObject(196, new OBJ_Large_Pellet(), 17, 18);
		createObject(195, new OBJ_Large_Pellet(), 17, 4);
		createObject(194, new OBJ_Large_Pellet(), 1, 4);
		engine.pelletsRemaining += 4;
	}
	
	public void createObject(int index, SuperObject obj, int x, int y) {
		// creating the object at selected index, and setting its tile position.
		engine.obj[index] = obj;
		engine.obj[index].x = engine.tileSize * x;
		engine.obj[index].y = engine.tileSize * y;
	}

}

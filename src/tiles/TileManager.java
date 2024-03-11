package tiles;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.Engine;
import utils.ImageScaler;

/*
 * Author:			Jacob Stewart
 * Project:			Pacman in Java
 * Date Started:	March 11, 2024
 * Class Description: 
 * 		This class is responsible for setting up tiles image and collision,
 * 		reading data from a text file to an Tile array before rendering the map.
 */

public class TileManager {

	private Engine engine;
	public Tile[] tile;
	public int mapTileNum[][];
	
	public TileManager(Engine e) {
		this.engine = e;
		
		tile = new Tile[30];
		mapTileNum = new int[e.maxScreenCol][e.maxScreenRow];
		
		// load tile images and load map data.
		getTileImages();
		loadMap("/maps/map01.txt");
	}
	
	public void getTileImages() {
		// tile data starts at an integer of 10 to keep txt file data readable
		setupTile(10, "floor", false);
		setupTile(11, "wall", true);
		setupTile(12, "wallSide", true);
		setupTile(13, "wallEndDown", true);
		setupTile(14, "wallEndLeft", true);
		setupTile(15, "wallEndRight", true);
		setupTile(16, "wallEndUp", true);
		setupTile(17, "wallLeftDown", true);
		setupTile(18, "wallLeftUp", true);
		setupTile(19, "wallRightDown", true);
		setupTile(20, "wallRightUp", true);
		setupTile(21, "wallTDown", true);
		setupTile(22, "wallTUp", true);
		setupTile(23, "wallTLeft", true);
		setupTile(24, "wallTRight", true);
		
		
	}
	
	public void setupTile(int index, String imagePath, boolean collision) {
		ImageScaler iScale = new ImageScaler();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath + ".png"));
			tile[index].image = iScale.scaleImage(tile[index].image, engine.tileSize, engine.tileSize);
			tile[index].collision = collision;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			int col = 0;
			int row = 0;

			while (col < engine.maxScreenCol && row < engine.maxScreenRow) {

				String line = br.readLine();
				
				while (col < engine.maxScreenCol) {
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					col++;
				}
				if (col == engine.maxScreenCol) {
					col = 0;
					row++;
				}

			}
			br.close();
			
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public void draw(Graphics2D g2) {

		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;

		while (col < engine.maxScreenCol && row < engine.maxScreenRow) {
			
			int tileNum = mapTileNum[col][row];
			
			g2.drawImage(tile[tileNum].image, x, y, null);
			col++;
			x += engine.tileSize;

			if (col == engine.maxScreenCol) {
				col = 0;
				x = 0;
				row++;
				y += engine.tileSize;
			}
		}

	}
	
}

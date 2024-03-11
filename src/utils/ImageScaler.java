package utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/*
 * Author:			Jacob Stewart
 * Project:			Pacman in Java
 * Date Started:	March 11, 2024
 * Class Description: 
 * 		This class takes a buffered image and scales it to the appropriate size in the event
 * 		image files used are smaller or larger than 32x32.
 */

public class ImageScaler {

	public BufferedImage scaleImage(BufferedImage original, int width, int height) {
		BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(original, 0, 0, width, height, null);
		g2.dispose();
		
		return scaledImage;
	}
	
}

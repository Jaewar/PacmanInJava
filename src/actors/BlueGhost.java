package actors;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Engine;

/*
 * Author:			Jacob Stewart
 * Project:			Pacman in Java
 * Date Started:	March 11, 2024
 * Class Description: 
 * 		This class is responsible for rendering, updating and controlling the blue ghost
 * 		and his unique chase/scatter states.
 */

public class BlueGhost extends Actor {

	private Engine engine;
	public boolean onPath = false;

	public int state = 0;
	public int chaseState = 1;
	public int runState = 2;

	public int counter = 0;
	public int runTimer = 0;

	// Variables for releasing ghost at specific timing
	public boolean isActive = false;
	public boolean canActivate = true;
	
	public BlueGhost(Engine e) {
		this.engine = e;
		// hitbox is 1 pixel smaller (AI should be able to perform perfect movement)
		hitbox = new Rectangle(1, 1, 31, 31);

		// recording default position for when changes are made for collision
		// prediction.
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;

		setDefaultValues();
		getBlueGhostImage();
	}

	public void setDefaultValues() {
		// 9 * 10 (default ghost starting position outside of box).
		if (isActive) {
		x = engine.tileSize * 9;
		y = engine.tileSize * 10;
		speed = 1;
		state = chaseState;
		direction = "left";
		} else {
			x = engine.tileSize * 8;
			y = engine.tileSize * 12;
			speed = 0;
			state = chaseState;
			direction = "left";
		}
	}

	public void getBlueGhostImage() {
		try {
			idle1 = ImageIO.read(getClass().getResource("/ghost/blueghost/blueGhostStill.png"));
			up1 = ImageIO.read(getClass().getResource("/ghost/blueghost/blueGhostUp1.png"));
			up2 = ImageIO.read(getClass().getResource("/ghost/blueghost/blueGhostUp2.png"));
			down1 = ImageIO.read(getClass().getResource("/ghost/blueghost/blueGhostDown1.png"));
			down2 = ImageIO.read(getClass().getResource("/ghost/blueghost/blueGhostDown2.png"));
			left1 = ImageIO.read(getClass().getResource("/ghost/blueghost/blueGhostLeft1.png"));
			left2 = ImageIO.read(getClass().getResource("/ghost/blueghost/blueGhostLeft2.png"));
			right1 = ImageIO.read(getClass().getResource("/ghost/blueghost/blueGhostRight1.png"));
			right2 = ImageIO.read(getClass().getResource("/ghost/blueghost/blueGhostRight2.png"));
			run1 = ImageIO.read(getClass().getResource("/ghost/run1.png"));
			run2 = ImageIO.read(getClass().getResource("/ghost/run2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {

		onPath = true;

		setAction(state);
		checkCollision();

		// IF COLLISION IS FALSE, PLAYER CAN MOVE
		if (colliding == false) {
			//@formatter:off
			switch (direction) {
			case "up": y -= speed; break;
			case "down": y += speed; break;
			case "left": x -= speed; break;
			case "right": x += speed; break;
			}
			//@formatter:on
		}

		if (runTimer < 5 && state == runState) {
			counter++;
			if (counter >= 60) {
				runTimer += 1;
				counter = 0;
			}
		} else {
			counter = 0;
			runTimer = 0;
			state = chaseState;
		}

		// check tile collision
		colliding = false;
		engine.cManager.checkTiles(this);

		spriteCounter++;
		if (spriteCounter > 8) {
			if (spriteNum == 1) {
				spriteNum = 2;
			} else if (spriteNum == 2) {
				spriteNum = 1;
			} else if (spriteNum == 3) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}

	}

	public void setAction(int state) {
		if (state == chaseState) {
			if (onPath == true) {
				// search direction to walk
				searchPath(getGoalCol(engine.pacman), getGoalRow(engine.pacman));
			}
		}
		if (state == runState) {
			if (onPath == true) {
				// BOTTOM RIGHT
				searchPath((engine.tileSize * 17) / engine.tileSize, (engine.tileSize * 22) / engine.tileSize);
			}
		}
	}

	public int getGoalCol(Actor target) {
		int goalCol = (target.x + target.hitbox.x) / engine.tileSize;
		return goalCol;
	}

	public int getGoalRow(Actor target) {
		int goalRow = ((target.y + target.hitbox.y) / engine.tileSize) -1;
		return goalRow;
	}

	public void searchPath(int goalCol, int goalRow) {
		int startCol = (x + hitbox.x) / engine.tileSize;
		int startRow = (y + hitbox.y) / engine.tileSize;

		try {
			engine.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
		} catch (ArrayIndexOutOfBoundsException e) {

		}

		if (engine.pFinder.search() == true) {
			// next worldX, worldY
			int nextX = engine.pFinder.pathList.get(0).col * engine.tileSize;
			int nextY = engine.pFinder.pathList.get(0).row * engine.tileSize;
			// entity solidArea pos
			int enLeftX = x + hitbox.x;
			int enRightX = x + hitbox.x + hitbox.width;
			int enTopY = y + hitbox.y;
			int enBottomY = y + hitbox.y + hitbox.height;

			if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + engine.tileSize) {
				direction = "up";
			} else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + engine.tileSize) {
				direction = "down";
			} else if (enTopY >= nextY && enBottomY < nextY + engine.tileSize) {
				// left or right
				if (enLeftX > nextX) {
					direction = "left";
				}
				if (enLeftX < nextX) {
					direction = "right";
				}
			} else if (enTopY > nextY && enLeftX > nextX) {
				// up or left
				direction = "up";
				checkCollision();
				if (colliding == true) {
					direction = "left";
				}
			} else if (enTopY > nextY && enLeftX < nextX) {
				// up or right
				direction = "up";
				checkCollision();
				if (colliding == true) {
					direction = "right";
				}
			} else if (enTopY < nextY && enLeftX > nextX) {
				// down or left
				direction = "down";
				checkCollision();
				if (colliding == true) {
					direction = "left";
				}
			} else if (enTopY < nextY && enLeftX < nextX) {
				// down or right
				direction = "down";
				checkCollision();
				if (colliding == true) {
					direction = "right";
				}
			}
		}
	}

	public void checkCollision() {
		colliding = false;
		engine.cManager.checkTiles(this);

		boolean contactPlayer = engine.cManager.checkPacman(this);

		if (contactPlayer == true && state == chaseState) {
			engine.pacman.isDead = true;
		}
		if (contactPlayer == true && state == runState) {
			setDefaultValues();
			engine.score += 100;
		}
	}

	public void draw(Graphics2D g2) {
		BufferedImage image = null;

		if (state == chaseState) {
			// chaseState
			switch (direction) {
			case "up":
				if (spriteNum == 1) {
					image = up1;
				}
				if (spriteNum == 2) {
					image = up2;
				}
				break;
			case "down":
				if (spriteNum == 1) {
					image = down1;
				}
				if (spriteNum == 2) {
					image = down2;
				}
				break;
			case "left":
				if (spriteNum == 1) {
					image = left1;
				}
				if (spriteNum == 2) {
					image = left2;
				}
				break;
			case "right":
				if (spriteNum == 1) {
					image = right1;
				}
				if (spriteNum == 2) {
					image = right2;
				}
				break;
			}
		}
		if (state == runState) {
			// runState
			switch (direction) {
			case "up":
				if (spriteNum == 1) {
					image = run1;
				}
				if (spriteNum == 2) {
					image = run2;
				}
				break;
			case "down":
				if (spriteNum == 1) {
					image = run1;
				}
				if (spriteNum == 2) {
					image = run2;
				}
				break;
			case "left":
				if (spriteNum == 1) {
					image = run1;
				}
				if (spriteNum == 2) {
					image = run2;
				}
				break;
			case "right":
				if (spriteNum == 1) {
					image = run1;
				}
				if (spriteNum == 2) {
					image = run2;
				}
				break;
			}
		}

		g2.drawImage(image, x, y, engine.tileSize, engine.tileSize, null);
		g2.drawRect(x + hitbox.x, y + hitbox.y, hitbox.width, hitbox.height);
	}
	
}

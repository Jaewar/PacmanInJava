package collision;

import actors.Actor;
import main.Engine;

/*
 * Author:			Jacob Stewart
 * Project:			Pacman in Java
 * Date Started:	March 11, 2024
 * Class Description: 
 * 		This class provides the methods required for each actor, object and tile to control and
 * 		check its collision in the game space.
 */

public class CollisionManager {

	private Engine engine;

	public CollisionManager(Engine e) {
		this.engine = e;
	}

	public void checkTiles(Actor actor) {
		// Method used to check tile collision for all actors.

		// Getting coordinates of the hitbox bounds.
		int actorLeftX = actor.x + actor.hitbox.x; // hitbox left position on x axis
		int actorRightX = actor.x + actor.hitbox.x + actor.hitbox.width; // hitbox right position on x
		int actorTopY = actor.y + actor.hitbox.y; // hitbox top position y axis
		int actorBottomY = actor.y + actor.hitbox.y + actor.hitbox.height; // hitbox bottom position

		// based on coordinates above, finding the hitbox column and row values.
		int actorLeftCol = actorLeftX / engine.tileSize;
		int actorRightCol = actorRightX / engine.tileSize;
		int actorTopRow = actorTopY / engine.tileSize;
		int actorBottomRow = actorBottomY / engine.tileSize;

		// only needs to check for 2 tiles per direction.
		int tileNum1, tileNum2;
		try {
			// case "up" commented, each case is similar with the exception of speed and tile numbers.
			switch (actor.direction) {
			case "up":
				// predicting upcoming collision adjusting for speed value
				actorTopRow = (actorTopY - actor.speed) / engine.tileSize;
				// checking that actor is within map bounds.
				if (actorTopRow < 24 && actorTopRow >= 0) {
					// getting the tile position that the actor is attempting to move into.
					tileNum1 = engine.tileM.mapTileNum[actorLeftCol][actorTopRow];
					tileNum2 = engine.tileM.mapTileNum[actorRightCol][actorTopRow];
					// checking if either tile has collision enabled.
					if (engine.tileM.tile[tileNum1].collision == true
							|| engine.tileM.tile[tileNum2].collision == true) {
						// if collision on tile is found, actor is colliding.
						actor.colliding = true;
					}
				}
				break;
			case "down":
				actorBottomRow = (actorBottomY + actor.speed) / engine.tileSize;
				if (actorBottomRow < 24 && actorBottomRow >= 0) {
					tileNum1 = engine.tileM.mapTileNum[actorLeftCol][actorBottomRow];
					tileNum2 = engine.tileM.mapTileNum[actorRightCol][actorBottomRow];
					if (engine.tileM.tile[tileNum1].collision == true
							|| engine.tileM.tile[tileNum2].collision == true) {
						actor.colliding = true;
					}
				}
				break;
			case "left":
				actorLeftCol = (actorLeftX - actor.speed) / engine.tileSize;
				if (actorLeftCol >= 0 && actorLeftCol < 19) {
					tileNum1 = engine.tileM.mapTileNum[actorLeftCol][actorTopRow];
					tileNum2 = engine.tileM.mapTileNum[actorLeftCol][actorBottomRow];
					if (engine.tileM.tile[tileNum1].collision == true
							|| engine.tileM.tile[tileNum2].collision == true) {
						actor.colliding = true;
					}
				}
				break;
			case "right":
				actorRightCol = (actorRightX + actor.speed) / engine.tileSize;
				if (actorRightCol < 19 && actorRightCol >= 0) {
					tileNum1 = engine.tileM.mapTileNum[actorRightCol][actorTopRow];
					tileNum2 = engine.tileM.mapTileNum[actorRightCol][actorBottomRow];
					if (engine.tileM.tile[tileNum1].collision == true
							|| engine.tileM.tile[tileNum2].collision == true) {
						actor.colliding = true;
					}
				}
				break;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// Actor somehow left the map and collision failed, resseting position.
			actor.x = 9 * engine.tileSize;
			actor.y = 10 * engine.tileSize;
		}
	}
	
	// PACMAN ACTOR SHOULD NEVER CALL THIS METHOD
	public boolean checkPacman(Actor actor) {
		boolean contactPacman = false;
		
		// getting actors hitbox position
		actor.hitbox.x = actor.x + actor.hitbox.x;
		actor.hitbox.y = actor.y + actor.hitbox.y;
		
		// getting pacmans current hitbox position
		engine.pacman.hitbox.x = engine.pacman.x + engine.pacman.hitbox.x;
		engine.pacman.hitbox.y = engine.pacman.y + engine.pacman.hitbox.y;
		
		//@formatter:off
		// moving the entities hitbox with its current movement
		switch (actor.direction) {
			case "up": actor.hitbox.y -= actor.speed; break;
			case "down": actor.hitbox.y += actor.speed; break;
			case "left": actor.hitbox.x -= actor.speed; break;
			case "right": actor.hitbox.x += actor.speed; break;
		}
		//@formatter:on
		
		// checking if ghost hitbox intersects with pacmans hitbox
		if (actor.hitbox.intersects(engine.pacman.hitbox)) {
			actor.colliding = true;
			contactPacman = true;
		}
		
		// resetting hitboxes to default values
		actor.hitbox.x = actor.hitboxDefaultX;
		actor.hitbox.y = actor.hitboxDefaultY;
		engine.pacman.hitbox.x = engine.pacman.hitboxDefaultX;
		engine.pacman.hitbox.y = engine.pacman.hitboxDefaultY;
		
		return contactPacman;
	}
	
	/*
	 * public int checkObject(Actor actor, boolean player) { // index = 999, could
	 * be any number however object array should never be greater than 900. int
	 * index = 999;
	 * 
	 * for (int i = 0; i < engine.obj.length; i++) {
	 * 
	 * }
	 * 
	 * // If index is anything other than 999, an object was collided with. return
	 * index; }
	 */
}

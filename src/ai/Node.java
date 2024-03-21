package ai;

/*
 * Author:			Jacob Stewart
 * Project:			Pacman in Java
 * Date Started:	March 21, 2024
 * Class Description: 
 * 		This class represents a single node, tracking its individual cost, position, and collision status.
 * 		The Node tracks if it was previously checked and is used in the the Pathfinder A* algorithm.
 */

public class Node {

	Node parent;
	
	public int col, row;
	
	public int gCost, hCost, fCost;
	
	public boolean solid, open, checked;
	
	public Node(int col, int row) {
		this.col = col;
		this.row = row;
	}

}

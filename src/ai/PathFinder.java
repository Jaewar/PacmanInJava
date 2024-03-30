package ai;

import java.util.ArrayList;

import main.Engine;

/*
 * Author:			Jacob Stewart
 * Project:			Pacman in Java
 * Date Started:	March 21, 2024
 * Class Description: 
 * 		This class handles pathfinding. It is responsible for setting nodes, finding the cost and
 * 		searching the best path to follow towards a target destination.
 */

public class PathFinder {
	
	Engine gp;
	Node[][] node;
	ArrayList<Node> openList = new ArrayList<>();
	public ArrayList<Node> pathList = new ArrayList<>();
	Node startNode, goalNode, currentNode;
	boolean goalReached = false;
	int step = 0;

	public PathFinder(Engine gp) {
		this.gp = gp;
		instantiateNodes();
	}

	public void instantiateNodes() {
		node = new Node[gp.maxScreenCol][gp.maxScreenRow];

		int col = 0;
		int row = 0;

		while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
			node[col][row] = new Node(col, row);

			col++;
			if (col == gp.maxScreenCol) {
				col = 0;
				row++;
			}
		}
	}

	public void resetNodes() {
		int col = 0;
		int row = 0;

		while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
			// reset open, checked and solid state
			node[col][row].open = false;
			node[col][row].checked = false;
			node[col][row].solid = false;

			col++;
			if (col == gp.maxScreenCol) {
				col = 0;
				row++;
			}
		}

		// reset other settings
		openList.clear();
		pathList.clear();
		goalReached = false;
		step = 0;
	}

	public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
		resetNodes();

		// set start and goal
		startNode = node[startCol][startRow];
		currentNode = startNode;
		goalNode = node[goalCol][goalRow];
		openList.add(currentNode);

		int col = 0;
		int row = 0;

		while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
			// SET SOLID NODE
			// CHECK TILES
			int tileNum = gp.tileM.mapTileNum[col][row];
			if (gp.tileM.tile[tileNum].collision == true) {
				node[col][row].solid = true;
			}
			// set cost
			getCost(node[col][row]);

			col++;
			if (col == gp.maxScreenCol) {
				col = 0;
				row++;
			}
		}

	}

	public void getCost(Node node) {
		// g cost
		int xDistance = Math.abs(node.col - startNode.col);
		int yDistance = Math.abs(node.row - startNode.row);
		node.gCost = xDistance + yDistance;
		// h cost
		xDistance = Math.abs(node.col - goalNode.col);
		yDistance = Math.abs(node.row - goalNode.row);
		node.hCost = xDistance + yDistance;
		// f cost
		node.fCost = node.gCost + node.hCost;
	}

	public boolean search() {
		while (goalReached == false && step < 500) {

			int col = currentNode.col;
			int row = currentNode.row;

			// CHECK THE CURR NODE
			currentNode.checked = true;
			openList.remove(currentNode);

			// open up node
			if (row - 1 >= 0) {
				openNode(node[col][row - 1]);
			}
			// left node
			if (col - 1 >= 0) {
				openNode(node[col - 1][row]);
			}
			// down node
			if (row + 1 < gp.maxScreenRow) {
				openNode(node[col][row + 1]);
			}

			// right node
			if (col + 1 < gp.maxScreenCol) {
				openNode(node[col + 1][row]);
			}

			// find best node
			int bestNodeIndex = 0;
			int bestNodefCost = 999;

			for (int i = 0; i < openList.size(); i++) {
				// check is nodes f cost is better
				if (openList.get(i).fCost < bestNodefCost) {
					bestNodeIndex = i;
					bestNodefCost = openList.get(i).fCost;
				}
				// if f cost is equal check g cost
				else if (openList.get(i).fCost == bestNodefCost) {
					if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
					}
				}
			}

			// if there is no node in openList, end the loop
			if (openList.size() == 0) {
				break;
			}

			// after loop, openList[bestNodeIndex] is the next step (= currentNode)
			currentNode = openList.get(bestNodeIndex);

			if (currentNode == goalNode) {
				goalReached = true;
				trackThePath();
			}
			step++;

		}

		return goalReached;
	}

	public void openNode(Node node) {
		if (node.open == false && node.checked == false && node.solid == false) {
			node.open = true;
			node.parent = currentNode;
			openList.add(node);
		}
	}

	public void trackThePath() {
		Node current = goalNode;
		while (current != startNode) {
			// always add to first slot so the last added node is in [0];
			pathList.add(0, current);
			current = current.parent;
		}
	}

}
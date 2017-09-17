package org.gameofthelife.entity;

import org.gameofthelife.GameOfLife;

/**
 * @author jguyet
 * particl Object
 */
public class Particl {
	
	private int x;
	private int y;
	
	/**
	 * Constructor
	 * @param x
	 * @param y
	 */
	public Particl(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * get x position
	 * @return
	 */
	public int x() {
		return (x);
	}
	
	/**
	 * get y position
	 * @return
	 */
	public int y() {
		return (y);
	}
	
	public String toString() {
		return (this.x + "" + this.y);
	}
	
	/**
	 * STATIC Method for get count 8opposed case alive
	 * @param game
	 * @param x
	 * @param y
	 * @return
	 */
	public static int checkHasOppositeParticls(GameOfLife game, int x, int y) {
		int oppositeParticls = 0;
		int[][] map = game.getMap();
		
		if (!game.isOutofMap(x, y + 1) && map[y + 1][x] == 1)//south
			oppositeParticls++;
		if (!game.isOutofMap(x, y - 1) && map[y - 1][x] == 1)//north
			oppositeParticls++;
		if (!game.isOutofMap(x + 1, y) && map[y][x + 1] == 1)//est
			oppositeParticls++;
		if (!game.isOutofMap(x - 1, y) && map[y][x - 1] == 1)//west
			oppositeParticls++;
		if (!game.isOutofMap(x - 1, y - 1) && map[y - 1][x - 1] == 1)//north west
			oppositeParticls++;
		if (!game.isOutofMap(x + 1, y - 1) && map[y - 1][x + 1] == 1)//north est
			oppositeParticls++;
		if (!game.isOutofMap(x - 1, y + 1) && map[y + 1][x - 1] == 1)//south west
			oppositeParticls++;
		if (!game.isOutofMap(x + 1, y + 1) && map[y + 1][x + 1] == 1)//south est
			oppositeParticls++;
		
		return (oppositeParticls);
	}
}

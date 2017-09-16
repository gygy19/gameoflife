package org.gameofthelife.entity;

import org.gameofthelife.Game;

public class Particl {
	
	private int x;
	private int y;
	
	public Particl(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int x() {
		return (x);
	}
	
	public int y() {
		return (y);
	}
	
	public String toString() {
		return (this.x + "" + this.y);
	}
	
	public static int checkHasOppositeParticls(Game game, int x, int y) {
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

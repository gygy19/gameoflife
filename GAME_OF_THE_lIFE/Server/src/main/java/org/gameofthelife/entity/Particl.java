package org.gameofthelife.entity;

import java.util.Map;

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
	
	public static int checkHasOppositeParticls(Map<String, Particl> map, int x, int y) {
		int oppositeParticls = 0;
		
		if (map.containsKey((x) + "" + (y + 1)))//south
			oppositeParticls++;
		if (map.containsKey((x) + "" + (y - 1)))//north
			oppositeParticls++;
		if (map.containsKey((x + 1) + "" + (y)))//est
			oppositeParticls++;
		if (map.containsKey((x - 1) + "" + (y)))//west
			oppositeParticls++;
		if (map.containsKey((x - 1) + "" + (y - 1)))//north west
			oppositeParticls++;
		if (map.containsKey((x + 1) + "" + (y - 1)))//north est
			oppositeParticls++;
		if (map.containsKey((x - 1) + "" + (y + 1)))//south west
			oppositeParticls++;
		if (map.containsKey((x + 1) + "" + (y + 1)))//south est
			oppositeParticls++;
		
		return (oppositeParticls);
	}
}

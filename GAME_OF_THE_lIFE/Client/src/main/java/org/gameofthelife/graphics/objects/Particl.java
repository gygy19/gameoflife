package org.gameofthelife.graphics.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;


public class Particl{

	private static Map<String, Particl> particlsMap = new HashMap<String, Particl>();
	
	private int x;
	private int y;
	
	public Particl(int x, int y) {
		this.x = x;
		this.y = y;
		particlsMap.put(this.toString(), this);
	}
	
	
	protected void paint(Graphics2D g, int size)
	{
		g.setColor(Color.RED);
        g.fillRect(this.x * 2, this.y * 2, 2, 2);
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
	
}

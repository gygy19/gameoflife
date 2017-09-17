package org.gameofthelife.client.entity;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * @author jguyet
 * Class pixel 2d particl
 */
public class Particl{

	private int x;
	private int y;
	
	/**
	 * Particl contructor
	 * @param x
	 * @param y
	 */
	public Particl(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * paint this particl to Graphics2D with zoom configuration
	 * @param g
	 * @param zoom
	 */
	public void paint(Graphics2D g, int zoom)
	{
		g.setColor(Color.DARK_GRAY);
        g.fillRect(this.x * zoom, this.y * zoom, 1 * zoom, 1 * zoom);
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
	
}

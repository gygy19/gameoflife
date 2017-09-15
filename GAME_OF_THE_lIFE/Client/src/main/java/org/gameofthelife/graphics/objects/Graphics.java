package org.gameofthelife.graphics.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Graphics extends JPanel{
	
	private int sizeX;
	private int sizeY;
	private int particl_size;
	private Window win;
	private int maxCaseIdX;
	private int maxCaseIdY;
	
	private ArrayList<Particl> particls = new ArrayList<Particl>();
	
	public Graphics(int sizeX, int sizeY, int particl_size) {
		super();
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.particl_size = particl_size;
		this.maxCaseIdX = this.sizeX / particl_size;
		this.maxCaseIdX = this.sizeY / particl_size;
		this.win = new Window(sizeX, sizeY);
		this.win.add(this);
		this.win.setVisible(true);
	}
	
	public int getsizeX() {
		return (this.sizeX);
	}
	
	public int getsizeY() {
		return (this.sizeY);
	}
	
	public int getMaxCaseIdX() {
		return (this.maxCaseIdX);
	}
	
	public int getMaxCaseIdY() {
		return (this.maxCaseIdY);
	}
	
	public void refresh() {
		this.win.repaint();
	}
	
	protected void paintComponent(java.awt.Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		//loadGrid(g2);
		
		for (Particl p : this.particls) {
			p.paint(g2, this.particl_size);
		}
	}
	
	private void loadGrid(Graphics2D g2) {
		g2.setColor(Color.BLACK);
		boolean black = false;
		for (int y = 0; y < this.sizeY; y++) {
			for (int x = 0; x < this.sizeX; x++) {
				if (black) {
					g2.setColor(Color.BLACK);
					black = false;
				}
				else {
					g2.setColor(Color.WHITE);
					black = true;
				}
		        Line2D lin = new Line2D.Float(x, y, x, y);
		        g2.draw(lin);
	        }
		}
	}
	
	public void addParticls(ArrayList<Particl> p) {
	this.particls = p;
	}
	
	public void resetParticls() {
		this.particls.clear();
	}

}

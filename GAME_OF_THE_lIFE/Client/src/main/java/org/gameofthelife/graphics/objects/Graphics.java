package org.gameofthelife.graphics.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

import org.gameofthelife.client.Gameofthelife;
import org.gameofthelife.client.Main;
import org.gameofthelife.client.network.messages.AddOneParticlMessage;
import org.gameofthelife.graphics.objects.interfaces.PaintingInterface;

public class Graphics extends JPanel implements MouseListener, KeyListener {
	
	private int sizeX;
	private int sizeY;
	private Window win;
	private int zoom;
	private PaintingInterface paintClass;
	private MenuBar menu;
	
	public Graphics(int sizeX, int sizeY, int zoom, PaintingInterface paintClass) {
		super();
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.zoom = zoom;
		this.paintClass = paintClass;
		this.menu = new MenuBar();
		this.win = new Window(sizeX, sizeY);
		this.win.add(this);
		this.win.setJMenuBar(this.menu);
		this.addMouseListener(this);
		this.win.addKeyListener(this);
	}
	
	public int getsizeX() {
		return (this.sizeX);
	}
	
	public int getsizeY() {
		return (this.sizeY);
	}
	
	public void refresh() {
		this.win.repaint();
	}
	
	public int getZoom() {
		return (this.zoom);
	}
	
	public void display(boolean visible) {
		this.setVisible(visible);
	}
	
	protected void paintComponent(java.awt.Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		paintClass.paintLoop(g2);
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
	
	public void setgenerationCount(int gcount) {
		menu.setgenerationCount(gcount);
	}
	
	public void setparticlsCount(int count) {
		menu.setparticlsCount(count);
	}
	
	public void close() {
		this.display(false);
		this.win.setVisible(false);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			int x = (e.getX() / Main.game.getZoom());
			int y = (e.getY() / Main.game.getZoom());
			
			Particl p = new Particl(x, y);
			//System.out.println("x:" + x + " y:" + y);
			Main.sockClient.sendMessage(new AddOneParticlMessage(p));
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			Main.game.setPause();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

}

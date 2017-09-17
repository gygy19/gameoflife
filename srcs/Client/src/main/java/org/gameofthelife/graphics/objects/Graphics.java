package org.gameofthelife.graphics.objects;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import org.gameofthelife.client.Main;
import org.gameofthelife.client.entity.Particl;
import org.gameofthelife.client.network.messages.AddOneParticlMessage;
import org.gameofthelife.graphics.objects.interfaces.PaintingInterface;

/**
 * @author jguyet
 * Window grid
 */
public class Graphics extends JPanel implements MouseListener, KeyListener {
	
	/**
	 * VERSION
	 */
	private static final long serialVersionUID = 1L;
	
	private int					sizeX;
	private int					sizeY;
	private Window				win;
	private int					zoom;
	private PaintingInterface	paintClass;
	private MenuBar				menu;
	
	/**
	 * Graphics Constructor
	 * @param sizeX
	 * @param sizeY
	 * @param zoom
	 * @param paintClass
	 */
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
	
	/**
	 * get sizeX
	 * @return integer
	 */
	public int getsizeX() {
		return (this.sizeX);
	}
	
	/**
	 * get sizeY
	 * @return
	 */
	public int getsizeY() {
		return (this.sizeY);
	}
	
	/**
	 * get current zoom of particls
	 * @return
	 */
	public int getZoom() {
		return (this.zoom);
	}
	
	/**
	 * change visiblity of the window
	 * @param visible
	 */
	public void display(boolean visible) {
		this.setVisible(visible);
	}
	
	public void setSize(int width, int height) {
		this.win.setSize(width, height);
	}
	
	/**
	 * child method painting
	 */
	protected void paintComponent(java.awt.Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		paintClass.paintLoop(g2);
	}
	
	/**
	 * change generation output
	 * @param gcount
	 */
	public void setgenerationCount(int gcount) {
		menu.setgenerationCount(gcount);
	}
	
	/**
	 * change particlsCount output
	 * @param count
	 */
	public void setparticlsCount(int count) {
		menu.setparticlsCount(count);
	}
	
	/**
	 * close this and the window
	 */
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
		//hook key space
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			Main.game.setPause();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

}

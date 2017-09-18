package org.gameofthelife.client;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;

import org.gameofthelife.client.entity.Particl;
import org.gameofthelife.client.network.messages.PauseMessage;
import org.gameofthelife.graphics.objects.Graphics;
import org.gameofthelife.graphics.objects.interfaces.PaintingInterface;

/**
 * 
 * @author jguyet
 * Game grid class
 */
public class GameOfLife implements PaintingInterface {
	
	private int				sizeX;
	private int				sizeY;
	private Graphics		window;
	private int				generationN = 0;
	private boolean			onPause = false;
	private int				zoom = 1;
	
	private ArrayList<Particl> particls = new ArrayList<Particl>();
	
	public GameOfLife(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		intitialize_zoom();
		initializeWindow();
	}
	
	/**
	 * Set zoom by map size
	 * TODO: a refaire
	 */
	private void intitialize_zoom() {
		zoom = 1;
		if (sizeX < 1000 && sizeY < 1000) {
			zoom = 2;
		}
		if (sizeX < 401 && sizeY < 401) {
			zoom = 3;
		}
		if (sizeX < 201 && sizeY < 201) {
			zoom = 7;
		}
		if (sizeX < 100 && sizeY < 100) {
			zoom = 12;
		}
		if (sizeX < 50 && sizeY < 50) {
			zoom = 30;
		}
	}
	
	/**
	 * intializer of window
	 */
	private void initializeWindow() {
		window = new Graphics(sizeX * zoom, sizeY * zoom, zoom, this);
		window.getWindow().setSize(sizeX * zoom, sizeY * zoom);
		window.display(true);
	}
	
	/**
	 * get zoom
	 * @return
	 */
	public int getZoom() {
		return (this.zoom);
	}
	
	/**
	 * Reset Game
	 * @param sizeX
	 * @param sizeY
	 */
	public void reset(int sizeX, int sizeY) {
		if (sizeX != this.sizeX || sizeY != this.sizeY) {
			this.sizeX = sizeX;
			this.sizeY = sizeY;
			intitialize_zoom();
			this.window.setZoom(zoom);
			this.window.getWindow().setSize(sizeX * zoom, sizeY * zoom);
			this.window.setSize(sizeX * zoom, sizeY * zoom);
		}
		particls.clear();
		this.generationN = 0;
		this.window.display(true);
	}
	
	/**
	 * replace particls collection to p
	 * @param p
	 */
	public void setParticls(Collection<Particl> p) {
		this.particls.addAll(p);
	}
	
	/**
	 * clear particls collection
	 */
	public void resetParticls() {
		this.particls.clear();
	}
	
	@Override
	public void paintLoop(Graphics2D g) {
		
		try
		{
			for (Particl p : this.particls) {
				p.paint(g, zoom);
			}
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * Update current generation of particls and display
	 * @param p
	 */
	public void updateGeneration(Collection<Particl> p) {
		resetParticls();
		//replace all particls
		setParticls(p);
		//refresh map
		this.window.setgenerationCount(this.generationN);
		this.window.setparticlsCount(this.particls.size());
		this.window.display(false);
		this.window.display(true);
		generationN++;
	}
	
	/**
	 * add one particl
	 * @param p
	 */
	public void addOneParticl(Particl p) {
		this.particls.add(p);
		this.window.setparticlsCount(this.particls.size());
		this.window.display(false);
		this.window.display(true);
	}
	
	/**
	 * set on pause
	 */
	public void setPause() {
		if (onPause == false) {
			onPause = true;
		} else {
			onPause = false;
		}
		Main.sockClient.sendMessage(new PauseMessage(onPause));
	}
	
	/**
	 * Close this window and clear all particls
	 */
	public void close() {
		this.window.close();
		this.window = null;
		this.particls.clear();
	}
}

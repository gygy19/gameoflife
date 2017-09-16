package org.gameofthelife.client;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;

import org.gameofthelife.client.network.messages.PauseMessage;
import org.gameofthelife.graphics.objects.Graphics;
import org.gameofthelife.graphics.objects.Particl;
import org.gameofthelife.graphics.objects.interfaces.PaintingInterface;

public class Gameofthelife implements PaintingInterface {

	public static final int DEFAULT_ZOOM = 15;
	private int sizeX;
	private int sizeY;
	private Graphics window;
	private int generationN = 0;
	private boolean onPause = false;
	private int zoom = 1;
	
	private ArrayList<Particl> particls = new ArrayList<Particl>();
	
	public Gameofthelife(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		intitialize_zoom();
		initializeWindow();
	}
	
	public void intitialize_zoom() {
		if (sizeX < 1000 && sizeY < 1000) {
			zoom = 2;
		}
		if (sizeX < 800 && sizeY < 800) {
			zoom = 4;
		}
		if (sizeX < 600 && sizeY < 600) {
			zoom = 6;
		}
		if (sizeX < 400 && sizeY < 400) {
			zoom = 8;
		}
		if (sizeX < 200 && sizeY < 200) {
			zoom = 10;
		}
		if (sizeX < 100 && sizeY < 100) {
			zoom = 20;
		}
		if (sizeX < 50 && sizeY < 50) {
			zoom = 40;
		}
		System.out.println(zoom);
	}
	
	public int getZoom() {
		return (this.zoom);
	}
	
	public void initializeWindow() {
		window = new Graphics(sizeX * zoom, sizeY * zoom, zoom, this);
		window.display(true);
	}
	
	public void reset(int sizeX, int sizeY) {
		window.setSize(sizeX * zoom, sizeY * zoom);
		particls.clear();
		this.generationN = 0;
		this.window.display(true);
	}
	
	public void setParticls(Collection<Particl> p) {
		this.particls.addAll(p);
	}
	
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
	
	public void updateGame(Collection<Particl> p) {
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
	
	public void addOneParticl(Particl p) {
		this.particls.add(p);
		this.window.setparticlsCount(this.particls.size());
		this.window.display(false);
		this.window.display(true);
	}
	
	public void setPause() {
		if (onPause == false) {
			onPause = true;
		} else {
			onPause = false;
		}
		Main.sockClient.sendMessage(new PauseMessage(onPause));
	}
	
	public void close() {
		this.window.close();
		this.window = null;
		this.particls.clear();
	}
}

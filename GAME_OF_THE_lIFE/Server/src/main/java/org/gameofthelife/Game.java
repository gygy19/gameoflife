package org.gameofthelife;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.gameofthelife.entity.Particl;
import org.gameofthelife.server.network.client.TcpClient;
import org.gameofthelife.server.network.messages.AddOneParticlMessage;
import org.gameofthelife.server.network.messages.NewMapMessage;
import org.gameofthelife.server.network.messages.ParticlPositionMessage;
import org.gameofthelife.server.network.messages.PauseMessage;
import org.gameofthelife.server.network.messages.SetSettingsMessage;

public class Game implements Runnable{
	
	private static final int DEFAULT_MIN_MAP_X = 0;
	private static final int DEFAULT_MIN_MAP_Y = 0;
	private static final int DEFAULT_MAP_X = 1000;
	private static final int DEFAULT_MAP_Y = 1000;
	private static final int DEFAULT_REFRESH_TIME = 1000;
	private static final int DEFAULT_INTERVAL_PARTICL_LIFE = 3;
	private static final int DEFAULT_MIN_PARTICL_POPULATION = 2;
	
	private static final boolean RANDOM_MODE = true; 
	
	private static Game 		partagedMap = null;
	
	private Thread					_t;
	private SetSettingsMessage		settings;
	private ArrayList<TcpClient> 	clients = new ArrayList<TcpClient>();
	private boolean					finished = false;
	private int[][]					map;
	private boolean					onPause = false;
	

	public Game(SetSettingsMessage settings) {
		this.settings = settings;
		initialize();
	}
	
	public Game() {
		this.settings = new SetSettingsMessage(DEFAULT_MAP_X, DEFAULT_MAP_Y, DEFAULT_REFRESH_TIME, DEFAULT_INTERVAL_PARTICL_LIFE);
		Game.partagedMap = this;
		initialize();
	}
	
	public static Game getpartagedMap() {
		return (Game.partagedMap);
	}
	
	private void initialize() {
		this._t = new Thread(this);
		this._t.start();
	}
	
	public void addTcpClient(TcpClient client) throws IOException {
		clients.add(client);
		client.setGame(this);
		client.sendMessage(new NewMapMessage(this.settings.sizeMapX, this.settings.sizeMapY));
	}
	
	public void removeTcpClient(TcpClient client) {
		if (this.clients.contains(client)) {
			this.clients.remove(client);
		}
		if (this.clients.size() == 0) {
			this.finished = true;
		}
		client.setGame(null);
	}

	@Override
	public void run() {
		this.map = getcleanMap();
		load_gameMap();
		while (finished == false) {
			if (onPause == false) {
				sendMapInformations();
				update_game();
			}
			try { Thread.sleep(this.settings.refreshTime); } catch (InterruptedException e) {}
		}
	}
	
	private void load_gameMap() {
		if (RANDOM_MODE) {
			load_random_map();
		}
	}
	
	private int[][] getcleanMap() {
		int[][] newMap = new int[this.settings.sizeMapY + 1][this.settings.sizeMapX + 1];
		
		for (int y = DEFAULT_MIN_MAP_Y; y < this.settings.sizeMapY + 1; y++) {
			for (int x = DEFAULT_MIN_MAP_X; x < this.settings.sizeMapX + 1; x++) {
				newMap[y][x] = 0;
			}
		}
		return (newMap);
	}
	
	private void load_random_map() {
		int numberOfParticl = (this.settings.sizeMapX * this.settings.sizeMapY) / 5;
		
		for (int i = 0; i < numberOfParticl; i++) {
		
			int x = getRandomValue(DEFAULT_MIN_MAP_X, this.settings.sizeMapX - 1);
			int y = getRandomValue(DEFAULT_MIN_MAP_Y, this.settings.sizeMapY - 1);
			
    		if (this.map[y][x] != 0) {
    			i--;
    			continue ;
    		}
    		this.map[y][x] = 1;
		}
	}
	
	public void update_game() {
		int[][] newMap = getcleanMap();
		
		for (int y = DEFAULT_MIN_MAP_Y; y < this.settings.sizeMapY; y++) {
			for (int x = DEFAULT_MIN_MAP_X; x < this.settings.sizeMapX; x++) {
				newMap[y][x] = 0;
				int oppositeParticls = 0;
				boolean hasParticl = false;
				
				if (this.map[y][x] == 1) {
					hasParticl = true;
				}
				
				oppositeParticls = Particl.checkHasOppositeParticls(this, x, y);
				
				if (hasParticl && oppositeParticls >= DEFAULT_MIN_PARTICL_POPULATION && oppositeParticls <= this.settings.interval_life) {
					newMap[y][x] = 1;
				} else if (hasParticl == false && oppositeParticls == 3) {
					newMap[y][x] = 1;
				}
				//died
			}
		}
		this.map = newMap;
	}
	
	public Collection<Particl> getParticls() {
		ArrayList<Particl> particls = new ArrayList<Particl>();
		
		for (int y = DEFAULT_MIN_MAP_Y; y < this.settings.sizeMapY; y++) {
			for (int x = DEFAULT_MIN_MAP_X; x < this.settings.sizeMapX; x++) {
				if (this.map[y][x] == 1)
					particls.add(new Particl(x, y));
			}
		}
		return (particls);
	}
	
	public int[][] getMap() {
		return (this.map);
	}
	
	public boolean isOutofMap(int x, int y) {
		if (y < 0 || y > this.settings.sizeMapY)
			return true;
		if (x < 0 || x > this.settings.sizeMapX)
			return true;
		return false;
	}
	
	public void sendMapInformations() {
		
		for (TcpClient client : this.clients) {
			
			ParticlPositionMessage message = new ParticlPositionMessage(this.getParticls());
			
			client.sendMessage(message);
		}
	}
	
	public void addOneParticl(Particl p) {
		if (this.isOutofMap(p.x(), p.y()))
			return ;
		if (this.map[p.y()][p.x()] == 1)
			return ;
		this.map[p.y()][p.x()] = 1;
		for (TcpClient client : this.clients) {
			
			AddOneParticlMessage message = new AddOneParticlMessage(p);
			
			client.sendMessage(message);
		}
	}
	
	public void setPause(PauseMessage pause) {
		this.onPause = pause.onPause;
	}
	
	public static int getRandomValue(int i1, int i2)
	{
		if (i2 < i1)
			return 0;
		Random rand = new Random();
		return (rand.nextInt((i2 - i1) + 1)) + i1;
	}
	
	public static boolean getRandomBool()
	{
		return (getRandomValue(1, 2) == 1);
	}
}

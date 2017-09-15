package org.gameofthelife;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.gameofthelife.entity.Particl;
import org.gameofthelife.server.network.client.TcpClient;
import org.gameofthelife.server.network.messages.NewMapMessage;
import org.gameofthelife.server.network.messages.ParticlPositionMessage;
import org.gameofthelife.server.network.messages.SetSettingsMessage;

public class Game implements Runnable{
	
	private static final int DEFAULT_MIN_MAP_X = 0;
	private static final int DEFAULT_MIN_MAP_Y = 0;
	private static final int DEFAULT_MAP_X = 1000;
	private static final int DEFAULT_MAP_Y = 1000;
	private static final int DEFAULT_REFRESH_TIME = 1000;
	private static final int DEFAULT_INTERVAL_PARTICL_LIFE = 3;
	private static final int DEFAULT_MIN_PARTICL_POPULATION = 1;
	
	private static final boolean RANDOM_MODE = true; 
	
	private static Game 		partagedMap = null;
	
	private Thread					_t;
	private SetSettingsMessage		settings;
	private ArrayList<TcpClient> 	clients = new ArrayList<TcpClient>();
	private boolean					finished = false;
	private Map<String, Particl>	map = new HashMap<String, Particl>();
	

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
		this._t = new Thread();
		this._t.start();
	}
	
	public void addTcpClient(TcpClient client) throws IOException {
		clients.add(client);
		client.sendMessage(new NewMapMessage(this.settings.sizeMapX, this.settings.sizeMapY));
	}
	
	public void removeTcpClient(TcpClient client) {
		if (this.clients.contains(client))
			this.clients.remove(client);
		if (this.clients.size() == 0) {
			this.finished = true;
		}
	}

	@Override
	public void run() {
		load_gameMap();
		while (finished == false) {
			sendMapInformations();
			update_game();
			try { Thread.sleep(this.settings.refreshTime); } catch (InterruptedException e) {}
		}
	}
	
	private void load_gameMap() {
		if (RANDOM_MODE) {
			load_random_map();
		}
	}
	
	private void load_random_map() {
		int numberOfParticl = (this.settings.sizeMapX * this.settings.sizeMapY) / 5;
		
		for (int i = 0; i < numberOfParticl; i++) {
		
			int x = getRandomValue(DEFAULT_MIN_MAP_X, this.settings.sizeMapX);
			int y = getRandomValue(DEFAULT_MIN_MAP_Y, this.settings.sizeMapY);
			
    		if (this.map.containsKey(x + "" + y)) {
    			i--;
    			continue ;
    		}
    		Particl p = new Particl(x, y);
    		map.put(x + "" + y, p);
		}
	}
	
	public void update_game() {
		Map<String, Particl> newMap = new HashMap<String, Particl>();
		
		for (int y = DEFAULT_MIN_MAP_Y; y < this.settings.sizeMapY; y++) {
			for (int x = DEFAULT_MIN_MAP_X; x < this.settings.sizeMapX; y++) {
				
				int oppositeParticls = 0;
				boolean hasParticl = false;
				String position = x + "" + y;
				
				if (this.map.containsKey(position)) {
					hasParticl = true;
				}
				
				oppositeParticls = Particl.checkHasOppositeParticls(this.map, x, y);
				
				if (hasParticl && oppositeParticls > this.settings.interval_life)//DEFAULT_INTERVAL_PARTICL_LIFE
					continue ;//kill particl caused by supopulation
				if (hasParticl && oppositeParticls <= DEFAULT_MIN_PARTICL_POPULATION)//DEFAULT_INTERVAL_PARTICL_LIFE
					continue ;//kill particl caused by low population
				if (oppositeParticls == 3 && hasParticl == false || hasParticl) {
					Particl p = new Particl(x, y);
					newMap.put(x + "" + y, p);
				}
			}
		}
		this.map.clear();
		this.map = newMap;
	}
	
	public Collection<Particl> getParticls() {
		return (this.map.values());
	}
	
	public void sendMapInformations() {
		
		for (TcpClient client : this.clients) {
			
			ParticlPositionMessage message = new ParticlPositionMessage(this.getParticls());
			
			try {
				client.sendMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static int getRandomValue(int i1, int i2)
	{
		if (i2 < i1)
			return 0;
		Random rand = new Random();
		return (rand.nextInt((i2 - i1) + 1)) + i1;
	}
}

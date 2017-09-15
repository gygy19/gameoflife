package org.gameofthelife.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.gameofthelife.client.network.SocketClient;
import org.gameofthelife.client.network.TcpDataHandler;
import org.gameofthelife.client.network.handler.NetworkMessageHandler;
import org.gameofthelife.client.network.messages.SetSettingsMessage;
import org.gameofthelife.graphics.objects.Graphics;
import org.gameofthelife.graphics.objects.Particl;

public class Main 
{
	public static SocketClient sockClient = null;
	public static TcpDataHandler handler = new NetworkMessageHandler();
	public static boolean connected = false;
	
    public static void main(String[] args)
    {
    	/*if (args.length != 2) {
    		return ;
    	}*/
    	start();
    }
    
    public static boolean start() {
    	try {
    	sockClient = new SocketClient("127.0.0.1", 5555);
    	} catch (UnknownHostException e) {
    		//Error host
    		return true;
    	}
    	
    	try {
    	if (sockClient.initialize())
    	{
    		System.out.println("Connected");
    		connected = true;
			handler.handleTcpData(sockClient.getSession());
    		
    	} else {
    		try {Thread.sleep(100);} catch (InterruptedException e) {}
    		start();
    	}
    	
    	} catch (IOException e)  {
    		e.printStackTrace();
    		try {
	    		if (!sockClient.getSession().isClosed()) {
					sockClient.getSession().close();
				}
    		} catch (IOException e2) {
    			e2.printStackTrace();
    		}
    		return false;
    	}
    	return true;
    }
    
   /* private static ArrayList<Particl> particls = new ArrayList<Particl>();
    private static Map<String, Particl> particlsMap = new HashMap<String, Particl>();
    
    public static void load_start_particls(Graphics graph) {
    	
    	for (int i = 0; i < 1500; i++) {
    	
    		int x = getRandomValue(1, graph.getmaxX());
    		int y = getRandomValue(1, graph.getmaxY());
    		
    		Particl p = new Particl(x, y);
    		if (particlsMap.containsKey(x + "" + y)) {
    			i--;
    			continue ;
    		}
    		particlsMap.put(x + "" + y, p);
    		particls.add(p);
    	}
    	
    	for (Particl p : particls) {
    		graph.addParticl(p);
    	}
    	graph.refresh();
    }
    
    public static void update_particls(Graphics graph) {
    	ArrayList<Particl> newparticls = new ArrayList<Particl>();
		Map<String, Particl> newparticlsMap = new HashMap<String, Particl>();	
	
		for (int y = 0; y < graph.getmaxY(); y++) {
			for (int x = 0; x < graph.getmaxX(); x++) {
				
	    		boolean hasParticl = false;
	    		int colled = 0;
	    		
	    		if (particlsMap.containsKey(x + "" + y)) {
	    			hasParticl = true;
	    		}
	    		if (particlsMap.containsKey((x - 1) + "" + (y))) {//left
	    			colled++;
	    		}
	    		if (particlsMap.containsKey((x + 1) + "" + (y))) {//right
	    			colled++;
	    		}
	    		if (particlsMap.containsKey((x) + "" + (y - 1))) {//up
	    			colled++;
	    		}
	    		if (particlsMap.containsKey((x) + "" + (y + 1))) {//down
	    			colled++;
	    		}
	    		if (particlsMap.containsKey((x + 1) + "" + (y - 1))) {//up right
	    			colled++;
	    		}
	    		if (particlsMap.containsKey((x - 1) + "" + (y - 1))) {//up left
	    			colled++;
	    		}
	    		if (particlsMap.containsKey((x + 1) + "" + (y + 1))) {//down right
	    			colled++;
	    		}
	    		if (particlsMap.containsKey((x - 1) + "" + (y + 1))) {//down left
	    			colled++;
	    		}
	    		if (colled > 3 && hasParticl) {
	    			continue ;
	    		}
	    		if (colled <= 1 && hasParticl) {
	    			continue ;
	    		}
	    		if (colled == 3  && hasParticl == false || hasParticl) {
		    		
		    		Particl p = new Particl(x, y);
		    		newparticlsMap.put(x + "" + y, p);
		    		newparticls.add(p);
	    		}
			}
		}
		particlsMap = newparticlsMap;
		graph.resetParticls();
    	for (Particl p : newparticls) {
    		graph.addParticl(p);
    	}
    	graph.refresh();
    }*/
    
    
    
    public static int getRandomValue(int i1, int i2)
	{
		if (i2 < i1)
			return 0;
		Random rand = new Random();
		return (rand.nextInt((i2 - i1) + 1)) + i1;
	}
}

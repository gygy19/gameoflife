package org.gameofthelife.client;

import java.io.IOException;
import java.net.UnknownHostException;

import org.gameofthelife.client.network.SocketClient;
import org.gameofthelife.client.network.TcpDataHandler;
import org.gameofthelife.client.network.handler.NetworkMessageHandler;
import org.gameofthelife.graphics.objects.MainMenu;

public class Main 
{
	public static final int MIN_MAP_SIZE = 10;
	public static final int MAX_REFRESH_TIME = 1000 / 60;
	public static final int MAX_POPULATION = 8;
	
	public static SocketClient sockClient = null;
	public static TcpDataHandler handler = new NetworkMessageHandler();
	public static boolean connected = false;
	public static Gameofthelife game = null;
	
	public static String	hostname;
	public static int		port;
	
	public static int mapX = 100;
	public static int mapY = 100;
	public static int refreshTime = 1000 / 10;
	public static int population_max_life = 3;
	
    public static void main(String[] args)
    {
    	mainMenu("");
    	start();
    }
    
    public static void mainMenu(String error) {
    	MainMenu menu = new MainMenu(error);
    	
    	while (menu.wait == true) {
    		try {Thread.sleep(100);} catch (InterruptedException e) {}
    	}
    }
    
    public static boolean start() {
    	try {
    	sockClient = new SocketClient(hostname, port);
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
    		mainMenu("Server disconnected.");
    		start();
    	}
    	
    	} catch (IOException e)  {
    		try {
	    		if (!sockClient.getSession().isClosed()) {
					sockClient.getSession().close();
				}
    		} catch (IOException e2) {
    			e2.printStackTrace();
    		}
    		serverDisconnected();
    		return start();
    	}
    	return true;
    }
    
    public static void serverDisconnected() {
    	if (Main.game != null) {
    		Main.game.close();
    		Main.game = null;
    	}
    	Main.connected = false;
    	Main.sockClient = null;
    	Main.mainMenu("Server connection interrupted.");
    }
}

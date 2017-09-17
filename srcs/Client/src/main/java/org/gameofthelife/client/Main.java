package org.gameofthelife.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.gameofthelife.client.network.SocketClient;
import org.gameofthelife.client.network.TcpDataHandler;
import org.gameofthelife.client.network.handler.NetworkMessageHandler;
import org.gameofthelife.graphics.objects.MainMenu;

/**
 * @author jguyet
 * Main
 */
public class Main 
{
	/**
	 * Default informations
	 */
	public static final int			MIN_MAP_SIZE = 10;
	public static final int			MAX_REFRESH_TIME = 1000 / 60;
	public static final int			MAX_POPULATION = 8;
	public static final int			MIN_ALIVE_POPULATION = 2;
	public static final String		CONFIG_NAME = "config.txt";
	
	/**
	 * Client statics class
	 */
	public static SocketClient		sockClient = null;
	public static TcpDataHandler	handler = new NetworkMessageHandler();
	public static boolean			connected = false;
	public static GameOfLife		game = null;
	
	/**
	 * Host informations
	 */
	public static String			hostname = "127.0.0.1";
	public static int				port = 5555;
	public static boolean			hostspecified = false;
	
	/**
	 * Settings
	 */
	public static int				mapX = 100;
	public static int				mapY = 100;
	public static int				refreshTime = 1000 / 10;
	public static int				population_max_life = 3;
	
    public static void main(String[] args)
    {
    	/**
    	 * load config
    	 */
    	load_config();
    	/**
    	 * load flags
    	 */
    	if (load_args(args) == false)
    		return ;
    	/**
    	 * Start menu
    	 */
    	if (hostspecified == false)
    		mainMenu("");
    	/**
    	 * start connection to the server
    	 */
    	start();
    }
    
    public static void load_config() {
    	FileReader file = null;
    	BufferedReader br = null;
    	try {
    	file = new FileReader(CONFIG_NAME);
    	
    	br = new BufferedReader(file);
    	String line;
    	String args = "";
    	while((line = br.readLine()) != null) {
    		
    		if (line.length() >= 2 && line.substring(0, 2).equalsIgnoreCase("//"))
    			continue ;
    		args += " " + line;
    	}
    	load_args(args.split(" "));
    	br.close();
    	
    	} catch (Exception e) {
    		
    	} finally {
    		if (file != null) {
    			try {
					file.close();
				} catch (IOException e) {
					
				}
    		}
    		if (br != null) {
    			try {
					br.close();
				} catch (IOException e) {
					
				}
    		}
    	}
    }
    
    /**
     * Method for load args
     * @param args
     * @return
     */
    public static boolean load_args(String[] args) {
    	if (args.length == 0)
    		return true;
    	for (int i = 0; i < args.length; i++) {
    		
    		switch (args[i]) {
    			case "-size":
    				if (i + 2 > args.length){
    					System.out.println("Syntaxe error -size x y");
    					return false;
    				}
    				int x = -1;
    				int y = -1;
    				
    				try {
    					x = Integer.parseInt(args[i + 1]);
    					y = Integer.parseInt(args[i + 2]);
    				} catch (Exception e) {
    					System.out.println("Syntaxe error -size x y (x and y is a numbers)");
    					return false;
    				}
    				
    				if (x < 10 || y < 10) {
    					System.out.println("Minimum -size is 10x10");
    					return false;
    				}
    				
    				if (x > 1500 || y > 1500) {
    					System.out.println("Maximum -size is 1500x1500 (screen size too small)");
    					return false;
    				}
    				i += 2;
    				Main.mapX = x;
    				Main.mapY = y;
    				break ;
    			case "-refresh":
    				if (i + 1 > args.length){
    					System.out.println("Syntaxe error -refresh ms");
    					return false;
    				}
    				int ms = -1;
    				
    				try {
    					ms = Integer.parseInt(args[i + 1]);
    				} catch (Exception e) {
    					System.out.println("Syntaxe error -refresh ms (ms is a number)");
    					return false;
    				}
    				
    				if (ms < MAX_REFRESH_TIME) {
    					System.out.println("Maximum -refreshtime is " + MAX_REFRESH_TIME + " (60fps)");
    					return false;
    				}
    				i += 1;
    				Main.refreshTime = ms;
    				break ;
    			case "-maxAlivePopulation":
    				if (i + 1 > args.length){
    					System.out.println("Syntaxe error -refresh ms");
    					return false;
    				}
    				int maxalive = -1;
    				
    				try {
    					maxalive = Integer.parseInt(args[i + 1]);
    				} catch (Exception e) {
    					System.out.println("Syntaxe error -refresh ms (ms is a number)");
    					return false;
    				}
    				
    				if (maxalive < MIN_ALIVE_POPULATION) {
    					System.out.println("Minimum maxAlivePopulation is " + MIN_ALIVE_POPULATION);
    					return false;
    				}
    				
    				if (maxalive > MAX_POPULATION) {
    					System.out.println("Maximum maxAlivePopulation is " + MAX_POPULATION);
    					return false;
    				}
    				i += 1;
    				Main.population_max_life = maxalive;
    				break ;
    			case "-host":
    				if (i + 1 > args.length){
    					System.out.println("Syntaxe error -host ip:port");
    					return false;
    				}
    				
    				if (!args[i + 1].contains(":") || args[i + 1].split(":").length != 2) {
    					System.out.println("Syntaxe error -host ip:port");
    					return false;
    				}
    				String ip = args[i + 1].split(":")[0];
    				String port = args[i + 1].split(":")[1];
    				
    				if (!load_information(ip, port))
    					return false;
    				
    				Main.hostspecified = true;
    				break ;
    			case "--help":
    				print_help();
    				return false;
    			default:
    				break ;
    		}
    	}
    	return true;
    }
    
    public static boolean load_information(String ip, String sport) {
		
		try {
			InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			System.out.println("Server host ip not avalable.");
			return false;
		}
		int port = 0;
		
		try {
			port = Integer.parseInt(sport);
		} catch (NumberFormatException e) {
			
			System.out.println("Port is a number.");
			return false;
		}
		if (port < 0) {
			System.out.println("Port must be > 0.");
			return false;
		}
		
		if (port > 65535) {
			System.out.println("Port must be <= 65535.");
			return false;
		}
		Main.hostname = ip;
		Main.port = port;
		return true;
}
    
    public static void print_help() {
    	System.out.println("------------------------------------------------------------------------------------------------");
    	System.out.println("Client GameOfLife by jguyet v1.0");
    	System.out.println("Example                 : java -jar client.jar -size 100 100 -maxAlivePopulation 3 -refresh 500");
    	System.out.println("Commands                :");
    	System.out.println("--help                  : informations");
    	System.out.println("-size                   : -size sizeX sizeY (min size 10x10), (max size 1500x1500)");
    	System.out.println("-maxAlivePopulation     : -maxAlivePopulation 3 (min 2), (max 8)");
    	System.out.println("-refresh                : -maxAlivePopulation 500 (in millisecondes) (min 60fps(1000/60))");
    	System.out.println("------------------------------------------------------------------------------------------------");
    }
    
    /**
     * Create new Menu and wait keys entry
     * @param error
     */
    public static void mainMenu(String error) {
    	MainMenu menu = new MainMenu(error);
    	
    	while (menu.wait == true) {
    		try {Thread.sleep(100);} catch (InterruptedException e) {}
    	}
    }
    
    /**
     * Start connection to the server
     * @return
     */
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
    
    /**
     * disconnect from the server restart menu
     */
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

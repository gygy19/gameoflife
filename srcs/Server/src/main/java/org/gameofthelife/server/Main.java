package org.gameofthelife.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.gameofthelife.server.network.SocketServer;

/**
 * @author jguyet
 * Main class
 */
public class Main 
{
	public static SocketServer server;
	
	public static String	host = "localhost";
	public static int		port = 5555;
	
    public static void main(String[] args)
    {
    	if (load_args(args) == false)
    		return ;
    	start();
    }
    
    /**
     * load flags on args
     * @param args
     * @return
     */
    public static boolean load_args(String[] args) {
    	
    	if (args.length == 0)
    		return print_error_args();
    	if (args[0].equalsIgnoreCase("--default")) {
    		return true;
    	}
    	if (args[0].equalsIgnoreCase("--help")) {
    		print_help();
    		return false;
    	}
    	if (args[0].contains(":")) {
    		
    		String[] split = args[0].split(":");
    		
    		if (split.length <= 1)
    			return print_error_args();
    		if (!load_information(split[0], split[1]))
    			return false;
    		return true;
    	}
    	if (args.length < 2)
    		return print_error_args();
    	if (!load_information(args[0], args[1]))
			return print_error_args();
    	return true;
    }
    
    /**
     * Start server connection
     */
    public static void start() {
    	Main.server = new SocketServer();
    	
    	try {
			Main.server.start(host, port);
		} catch (IOException e) {
			System.out.println("Address \"" + host + "\" is not avalable.");
		}
    }
    
    /**
     * Default error args output
     * @return
     */
    public static boolean print_error_args(){
    	System.out.println("java -jar server.jar --help");
    	return false;
    }
    
    /**
     * help output
     */
    public static void print_help() {
    	System.out.println("--------------------------------------------");
    	System.out.println("Server GameOfLife by jguyet v1.0");
    	System.out.println("Example   : java -jar server.jar hostip:port");
    	System.out.println("Commands  :");
    	System.out.println("--help    : informations");
    	System.out.println("--default : start server on localhost:5555");
    	System.out.println("--------------------------------------------");
    }
    
    /**
     * Load host and port
     * @param ip
     * @param sport
     * @return
     */
    public static boolean load_information(String ip, String sport) {
    		
    		try {
    			InetAddress.getByName(ip);
    		} catch (UnknownHostException e) {
    			System.out.println("Server host ip not resolvable.");
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
    		Main.host = ip;
    		Main.port = port;
    		return true;
    }
}

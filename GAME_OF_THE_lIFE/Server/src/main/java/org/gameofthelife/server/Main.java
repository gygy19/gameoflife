package org.gameofthelife.server;

import java.io.IOException;

import org.gameofthelife.server.network.SocketServer;

public class Main 
{
	public static SocketServer server;
	
    public static void main(String[] args)
    {
    	start();
    }
    
    public static void start() {
    	Main.server = new SocketServer();
    	
    	try {
			Main.server.start("127.0.0.1", 5555);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    }
}

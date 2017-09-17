package org.gameofthelife.server.network;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.gameofthelife.server.network.client.TcpClient;
import org.gameofthelife.server.network.handler.NetworkMessageHandler;

/**
 * @author jguyet
 *
 */
public class SocketServer implements Runnable {

	private ServerSocket 						serverSocket;
	private Thread								_t;
	
	private static ArrayList<TcpClient>			clients = new ArrayList<TcpClient>();
	
	public SocketServer() {
		this._t = new Thread(this);
	}
	
	/**
	 * Start socketServer
	 * @param ipAddress
	 * @param portNumber
	 * @throws IOException
	 */
	public void start(String ipAddress, int portNumber) throws IOException {
        serverSocket = new ServerSocket(portNumber, Integer.MAX_VALUE, InetAddress.getByName(ipAddress));
        System.out.println("Server connection started on [" + ipAddress + ":" + portNumber + "]");
        this._t.start();
    }

	public void run() {
		accept();
	}
	
	/**
	 * wait all clients
	 */
	private void accept() {
		while (true)
		{
			try {
            	System.out.println("Wainting connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("new client");
                final TcpClient newClient = new TcpClient(clientSocket);
                final TcpClientDataHandler dataHandler =  new NetworkMessageHandler();
                
                clients.add(newClient);
                dataHandler.onClientConnected(newClient);
                new Thread(() -> {
                    try {
                        dataHandler.handleTcpData(newClient);
                    } catch (IOException e)
                    {
                    	if (newClient.getGame() != null) {
                    		newClient.getGame().removeTcpClient(newClient);
                    	}
                    	System.out.println("Client Disconnected");
                    }
                }).start();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
		}
	}
}

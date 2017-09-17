package org.gameofthelife.server.network;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.gameofthelife.server.network.client.TcpClient;

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
            	System.out.println("Waiting connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("new client");
                final TcpClient newClient = new TcpClient(clientSocket);
                
                clients.add(newClient);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
		}
	}
}

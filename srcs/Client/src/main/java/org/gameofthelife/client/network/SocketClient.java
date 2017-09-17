package org.gameofthelife.client.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.gameofthelife.client.Main;

/**
 * @author jguyet
 *
 */
public class SocketClient {
	
	private Socket		session;
	private InetAddress inetaddr;
	private int			port;
	
	public SocketClient(final String ip, final int port) throws UnknownHostException {
		
		this.inetaddr = InetAddress.getByName(ip);
		this.port = port;
	}
	
	public boolean initialize() {
		try {
			this.session = new Socket(inetaddr, port);
		} catch (IOException e) {
			Main.connected = false;
			return (false);
		}
		return (true);
	}
	
	public Socket getSession() {
		return (this.session);
	}
	
	/**
	 * Send NetworkMessage<br>
	 * serialize message and header and write on tcp fd
	 * @param message
	 */
	public void sendMessage(NetworkMessage message) {
		try {
			if (Main.connected) {
				message.serialize();
				message.serializeHeader();
		        byte[] messageContent = message.getData();
		        
		        System.out.println("Send [" + message.getTypeId() + "] " + message.getName() + " Length [" + messageContent.length + "]");
		        this.session.getOutputStream().write(messageContent, 0, messageContent.length);
			}
		} catch (IOException e) {
			Main.connected = false;
		}
	}
}

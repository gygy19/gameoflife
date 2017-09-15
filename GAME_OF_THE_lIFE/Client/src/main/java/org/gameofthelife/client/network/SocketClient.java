package org.gameofthelife.client.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

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
			//e.printStackTrace();
			return (false);
		}
		return (true);
	}
	
	public Socket getSession() {
		return (this.session);
	}
	
	public void sendMessage(NetworkMessage message) throws IOException {
		message.serialize();
		message.serializeHeader();
        byte[] messageContent = message.getData();
        
        System.out.println("message " + message.getTypeId() + " sended size: " + messageContent.length);
        this.session.getOutputStream().write(messageContent, 0, messageContent.length);
	}
}

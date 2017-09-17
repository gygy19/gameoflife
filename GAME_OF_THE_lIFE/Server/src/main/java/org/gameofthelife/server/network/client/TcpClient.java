package org.gameofthelife.server.network.client;

import java.io.IOException;
import java.net.Socket;

import org.gameofthelife.GameOfLife;
import org.gameofthelife.server.network.NetworkMessage;

public class TcpClient {

	private Socket	session;
	private GameOfLife	game = null;
	private boolean	online = true;
	
	public TcpClient(Socket session) {
		this.session = session;
	}
	
	public Socket getSession() {
		return (this.session);
	}
	
	public GameOfLife getGame() {
		return (this.game);
	}
	
	public void setGame(GameOfLife game) {
		this.game = game;
	}
	
	public void sendMessage(NetworkMessage message) {
		try {
		if (this.session.isClosed() && this.online == true)
        	return ;
		message.serialize();
		message.serializeHeader();
        byte[] messageContent = message.getData();
        
        System.out.println("Send [" + message.getTypeId() + "] " + message.getName() + " Length [" + messageContent.length + "]");
        this.session.getOutputStream().write(messageContent, 0, messageContent.length);
		} catch (IOException e) {
			TcpClient.kick(this);
		}
	}
	
	public static void kick(TcpClient client) {
		if (client.game != null) {
			client.game.removeTcpClient(client);
		}
		client.online = false;
	}
}

package org.gameofthelife.server.network.client;

import java.io.IOException;
import java.net.Socket;

import org.gameofthelife.server.GameOfLife;
import org.gameofthelife.server.network.NetworkMessage;

/**
 * @author jguyet
 * Class client
 */
public class TcpClient {

	private Socket	session;
	private GameOfLife	game = null;
	private boolean	online = true;
	
	/**
	 * Constructor
	 * @param session
	 */
	public TcpClient(Socket session) {
		this.session = session;
	}
	
	/**
	 * getter of socket session
	 * @return
	 */
	public Socket getSession() {
		return (this.session);
	}
	
	/**
	 * getter of client GameOfLife
	 * @return
	 */
	public GameOfLife getGame() {
		return (this.game);
	}
	
	/**
	 * Setter of Gameoflife
	 * @param game
	 */
	public void setGame(GameOfLife game) {
		this.game = game;
	}
	
	/**
	 * Send message to client tcp fd<br>
	 * kick if error of send
	 * @param message
	 */
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
	
	/**
	 * Kick method
	 * @param client
	 */
	public static void kick(TcpClient client) {
		if (client.game != null) {
			client.game.removeTcpClient(client);
		}
		client.online = false;
	}
}

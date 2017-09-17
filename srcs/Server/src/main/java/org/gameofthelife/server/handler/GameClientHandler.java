package org.gameofthelife.server.handler;

import org.gameofthelife.server.GameOfLife;
import org.gameofthelife.server.network.client.TcpClient;
import org.gameofthelife.server.network.handler.reflexion.ClassHandlerController;
import org.gameofthelife.server.network.handler.reflexion.MessageHandlerController;
import org.gameofthelife.server.network.messages.AddOneParticlMessage;
import org.gameofthelife.server.network.messages.JoinCommunityMapMessage;
import org.gameofthelife.server.network.messages.PauseMessage;

/**
 * @author jguyet
 *
 */
@ClassHandlerController("GameClientHandler")
public class GameClientHandler {
	
	/**
	 * Handle PauseMessage
	 * @param client
	 * @param message
	 * @return
	 */
	@MessageHandlerController(PauseMessage.MESSAGE_ID)
	public static boolean handlePauseMessage(TcpClient client, PauseMessage message) {
		
		if (client.getGame() == null)
			return true;
		client.getGame().setPause(message);
		return true;
	}
	
	/**
	 * Handle AddOneParticlMessage
	 * @param client
	 * @param message
	 * @return
	 */
	@MessageHandlerController(AddOneParticlMessage.MESSAGE_ID)
	public static boolean handleAddOneParticlMessage(TcpClient client, AddOneParticlMessage message) {
		
		if (client.getGame() == null)
			return true;
		client.getGame().addOneParticl(message.part);
		return true;
	}
	
	/**
	 * Handle JoinCommunityMapMessage
	 * @param client
	 * @param message
	 * @return
	 */
	@MessageHandlerController(JoinCommunityMapMessage.MESSAGE_ID)
	public static boolean handleJoinCommunityMapMessage(TcpClient client, JoinCommunityMapMessage message) {
		
		if (client.getGame() != null) {
			client.getGame().removeTcpClient(client);
		}
		if (GameOfLife.getpartagedMap() == null) {
			new GameOfLife();
		}
		try {
			GameOfLife.getpartagedMap().addTcpClient(client);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}

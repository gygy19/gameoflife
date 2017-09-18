package org.gameofthelife.server.handler;

import java.io.IOException;

import org.gameofthelife.server.GameOfLife;
import org.gameofthelife.server.network.client.TcpClient;
import org.gameofthelife.server.network.handler.reflexion.ClassHandlerController;
import org.gameofthelife.server.network.handler.reflexion.MessageHandlerController;
import org.gameofthelife.server.network.messages.SetSettingsMessage;

/**
 * @author jguyet
 *
 */
@ClassHandlerController("ConnectionClientHandler")
public class ConnectionClientHandler {
	/**
	 * Handle SetSettingsMessage
	 * @param client
	 * @param message
	 * @return
	 */
	@MessageHandlerController(SetSettingsMessage.MESSAGE_ID)
	public static boolean handleSettingsMessage(TcpClient client, SetSettingsMessage message) {
		
		if (client.getGame() != null) {
			client.getGame().removeTcpClient(client);
		}
		
		if (message.interval_life < 0 || message.interval_life > 8) {
			message.interval_life = 5;
		}
		if (message.min_interval_life < 0 || message.min_interval_life > 8) {
			message.interval_life = 1;
		}
		GameOfLife gameoflife = new GameOfLife(message);
		try {
		gameoflife.addTcpClient(client);
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}

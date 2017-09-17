package org.gameofthelife.server.handler;

import java.io.IOException;

import org.gameofthelife.GameOfLife;
import org.gameofthelife.server.network.client.TcpClient;
import org.gameofthelife.server.network.handler.reflexion.ClassHandlerController;
import org.gameofthelife.server.network.handler.reflexion.MessageHandlerController;
import org.gameofthelife.server.network.messages.SetSettingsMessage;

@ClassHandlerController("ConnectionClientHandler")
public class ConnectionClientHandler {

	@MessageHandlerController(SetSettingsMessage.MESSAGE_ID)
	public static boolean handleSettingsMessage(TcpClient client, SetSettingsMessage message) {
		
		if (client.getGame() != null) {
			client.getGame().removeTcpClient(client);
		}
		
		if (message.interval_life < 1 || message.interval_life > 5) {
			//error interval_life
			message.interval_life = 3;
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

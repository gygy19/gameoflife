package org.gameofthelife.server.handler;

import java.io.IOException;

import org.gameofthelife.Game;
import org.gameofthelife.server.network.client.TcpClient;
import org.gameofthelife.server.network.handler.reflexion.ClassHandlerController;
import org.gameofthelife.server.network.handler.reflexion.MessageHandlerController;
import org.gameofthelife.server.network.messages.SetSettingsMessage;

@ClassHandlerController("ConnectionServerHandler")
public class ConnectionServerHandler {

	@MessageHandlerController(SetSettingsMessage.MESSAGE_ID)
	public static boolean handleSettingsMessage(TcpClient client, SetSettingsMessage message) {
		
		if (message.interval_life < 1 || message.interval_life > 5) {
			//error interval_life
		}
		Game gameoflife = new Game(message);
		try {
		gameoflife.addTcpClient(client);
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}

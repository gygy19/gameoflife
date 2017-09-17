package org.gameofthelife.server.handler;

import org.gameofthelife.server.network.client.TcpClient;
import org.gameofthelife.server.network.handler.reflexion.ClassHandlerController;
import org.gameofthelife.server.network.handler.reflexion.MessageHandlerController;
import org.gameofthelife.server.network.messages.AddOneParticlMessage;
import org.gameofthelife.server.network.messages.PauseMessage;

@ClassHandlerController("GameClientHandler")
public class GameClientHandler {
	
	@MessageHandlerController(PauseMessage.MESSAGE_ID)
	public static boolean handlePauseMessage(TcpClient client, PauseMessage message) {
		
		if (client.getGame() == null)
			return true;
		client.getGame().setPause(message);
		return true;
	}
	
	@MessageHandlerController(AddOneParticlMessage.MESSAGE_ID)
	public static boolean handleAddOneParticlMessage(TcpClient client, AddOneParticlMessage message) {
		
		if (client.getGame() == null)
			return true;
		client.getGame().addOneParticl(message.part);
		return true;
	}
}

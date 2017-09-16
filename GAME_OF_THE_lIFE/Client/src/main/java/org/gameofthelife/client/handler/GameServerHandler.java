package org.gameofthelife.client.handler;

import org.gameofthelife.client.Gameofthelife;
import org.gameofthelife.client.Main;
import org.gameofthelife.client.network.handler.reflexion.ClassHandlerController;
import org.gameofthelife.client.network.handler.reflexion.MessageHandlerController;
import org.gameofthelife.client.network.messages.AddOneParticlMessage;
import org.gameofthelife.client.network.messages.NewMapMessage;
import org.gameofthelife.client.network.messages.ParticlPositionMessage;

@ClassHandlerController("GameServerHandler")
public class GameServerHandler {

	@MessageHandlerController(NewMapMessage.MESSAGE_ID)
	public static boolean handleNewMapMessage(NewMapMessage message) {
		
		if (Main.game != null) {
			Main.game.reset(message.sizeX, message.sizeY);
			return true;
		}
		
		Main.game = new Gameofthelife(message.sizeX, message.sizeY);
		return true;
	}
	
	@MessageHandlerController(ParticlPositionMessage.MESSAGE_ID)
	public static boolean handleParticlPositionMessage(ParticlPositionMessage message) {
		
		if (Main.game == null)
			return true;
		
		Main.game.updateGame(message.particls);
		
		return true;
	}
	
	@MessageHandlerController(AddOneParticlMessage.MESSAGE_ID)
	public static boolean handleAddOneParticlMessage(AddOneParticlMessage message) {
		
		if (Main.game == null)
			return true;
		
		Main.game.addOneParticl(message.part);
		return true;
	}
}

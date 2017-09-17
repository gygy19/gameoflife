package org.gameofthelife.client.handler;

import org.gameofthelife.client.GameOfLife;
import org.gameofthelife.client.Main;
import org.gameofthelife.client.network.handler.reflexion.ClassHandlerController;
import org.gameofthelife.client.network.handler.reflexion.MessageHandlerController;
import org.gameofthelife.client.network.messages.AddOneParticlMessage;
import org.gameofthelife.client.network.messages.NewMapMessage;
import org.gameofthelife.client.network.messages.ParticlPositionMessage;

/**
 * @author jguyet
 *
 */
@ClassHandlerController("GameServerHandler")
public class GameServerHandler {

	/**
	 * handle newmapmessage
	 * @param message NewMapMessage
	 * @return
	 */
	@MessageHandlerController(NewMapMessage.MESSAGE_ID)
	public static boolean handleNewMapMessage(NewMapMessage message) {
		
		if (Main.game != null) {
			Main.game.reset(message.sizeX, message.sizeY);
			return true;
		}
		
		Main.game = new GameOfLife(message.sizeX, message.sizeY);
		return true;
	}
	
	/**
	 * handle ParticlPositionMessage
	 * @param message ParticlPositionMessage
	 * @return
	 */
	@MessageHandlerController(ParticlPositionMessage.MESSAGE_ID)
	public static boolean handleParticlPositionMessage(ParticlPositionMessage message) {
		
		if (Main.game == null)
			return true;
		
		Main.game.updateGeneration(message.particls);
		
		return true;
	}
	
	/**
	 * handle AddOneParticlMessage
	 * @param message AddOneParticlMessage
	 * @return
	 */
	@MessageHandlerController(AddOneParticlMessage.MESSAGE_ID)
	public static boolean handleAddOneParticlMessage(AddOneParticlMessage message) {
		
		if (Main.game == null)
			return true;
		
		Main.game.addOneParticl(message.part);
		return true;
	}
}

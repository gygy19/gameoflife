package org.gameofthelife.client.handler;

import org.gameofthelife.client.network.handler.reflexion.ClassHandlerController;
import org.gameofthelife.client.network.handler.reflexion.MessageHandlerController;
import org.gameofthelife.client.network.messages.HelloMessage;
import org.gameofthelife.client.network.messages.NewMapMessage;

@ClassHandlerController("GameServerHandler")
public class GameServerHandler {

	@MessageHandlerController(NewMapMessage.MESSAGE_ID)
	public static boolean handleNewMapMessage(NewMapMessage message) {
		
		System.out.println("Receive hello message");
		return true;
	}
}

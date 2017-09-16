package org.gameofthelife.client.handler;

import java.io.IOException;

import org.gameofthelife.client.Main;
import org.gameofthelife.client.network.handler.reflexion.ClassHandlerController;
import org.gameofthelife.client.network.handler.reflexion.MessageHandlerController;
import org.gameofthelife.client.network.messages.HelloMessage;
import org.gameofthelife.client.network.messages.SetSettingsMessage;

@ClassHandlerController("ConnectionServerHandler")
public class ConnectionServerHandler {

	@MessageHandlerController(HelloMessage.MESSAGE_ID)
	public static boolean handleSettingsMessage(HelloMessage message) {
		
		System.out.println("Receive hello message");
		Main.sockClient.sendMessage(new SetSettingsMessage(Main.mapX, Main.mapY, Main.refreshTime, Main.population_max_life));
		return true;
	}
}

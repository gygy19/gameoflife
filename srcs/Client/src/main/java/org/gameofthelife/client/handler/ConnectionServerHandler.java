package org.gameofthelife.client.handler;

import org.gameofthelife.client.Main;
import org.gameofthelife.client.network.handler.reflexion.ClassHandlerController;
import org.gameofthelife.client.network.handler.reflexion.MessageHandlerController;
import org.gameofthelife.client.network.messages.HelloMessage;
import org.gameofthelife.client.network.messages.SetSettingsMessage;

/**
 * @author jguyet
 *
 */
@ClassHandlerController("ConnectionServerHandler")
public class ConnectionServerHandler {

	/**
	 * handle helloMessage
	 * @param message
	 * @return
	 */
	@MessageHandlerController(HelloMessage.MESSAGE_ID)
	public static boolean handleSettingsMessage(HelloMessage message) {
		//send settings
		Main.sockClient.sendMessage(new SetSettingsMessage(Main.mapX, Main.mapY, Main.refreshTime, Main.min_interval_life, Main.max_interval_life, Main.number_of_random_particls));
		return true;
	}
}

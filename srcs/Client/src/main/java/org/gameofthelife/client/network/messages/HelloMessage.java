package org.gameofthelife.client.network.messages;

import org.gameofthelife.client.network.NetworkMessage;
import org.gameofthelife.client.network.handler.reflexion.MessageHandlerController;

@MessageHandlerController(1)
public class HelloMessage extends NetworkMessage {

	public static final int MESSAGE_ID = 1;
	
	@Override
	public int getTypeId() {
		return MESSAGE_ID;
	}

	@Override
	public String getName() {
		return "HelloMessage";
	}
	
	public HelloMessage(byte[] data) {
		super();
	}
	
	public HelloMessage() {
		super();
	}

	@Override
	public void deserialize() {
		
	}

	@Override
	public void serialize() {
		
	}
	
	

}

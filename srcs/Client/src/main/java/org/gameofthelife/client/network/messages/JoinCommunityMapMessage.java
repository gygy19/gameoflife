package org.gameofthelife.client.network.messages;

import org.gameofthelife.client.network.NetworkMessage;
import org.gameofthelife.client.network.handler.reflexion.MessageHandlerController;

@MessageHandlerController(11)
public class JoinCommunityMapMessage extends NetworkMessage {

	public static final int MESSAGE_ID = 11;
	
	@Override
	public int getTypeId() {
		return MESSAGE_ID;
	}

	@Override
	public String getName() {
		return "JoinCommunityMapMessage";
	}
	
	public JoinCommunityMapMessage(byte[] data) {
		super();
		this.data = data;
	}
	
	public JoinCommunityMapMessage() {
		super();
	}

	@Override
	public void deserialize() {
		// TODO Auto-generated method stub
	}

	@Override
	public void serialize() {
		// TODO Auto-generated method stub
	}

}

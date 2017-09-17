package org.gameofthelife.server.network.messages;

import org.gameofthelife.server.network.NetworkMessage;
import org.gameofthelife.server.network.handler.reflexion.MessageHandlerController;

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

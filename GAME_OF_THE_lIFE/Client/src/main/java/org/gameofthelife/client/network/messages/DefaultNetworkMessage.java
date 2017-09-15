package org.gameofthelife.client.network.messages;

import org.gameofthelife.client.network.NetworkMessage;

public class DefaultNetworkMessage extends NetworkMessage{
	
	public int packetId;
	public int packetLen;
	
	public DefaultNetworkMessage(int headerId, int len) {
		super();
		packetId = headerId;
		packetLen = len;
	}

	@Override
	public int getTypeId() {
		return this.packetId;
	}

	@Override
	public String getName() {
		return null;
	}
	
	public int getPacketLen() {
		return (this.packetLen);
	}

	public void deserialize() {
		// TODO Auto-generated method stub
		
	}

	public void serialize() {
		// TODO Auto-generated method stub
		
	}

}

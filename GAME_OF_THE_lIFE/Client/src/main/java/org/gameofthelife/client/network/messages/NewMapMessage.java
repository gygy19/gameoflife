package org.gameofthelife.client.network.messages;

import java.nio.ByteBuffer;

import org.gameofthelife.client.network.NetworkMessage;
import org.gameofthelife.client.network.handler.reflexion.MessageHandlerController;

@MessageHandlerController(4)
public class NewMapMessage extends NetworkMessage {

	public static final int MESSAGE_ID = 4;
	
	public int sizeX;
	public int sizeY;
	
	@Override
	public int getTypeId() {
		return MESSAGE_ID;
	}

	@Override
	public String getName() {
		return "NewMapMessage";
	}
	
	public NewMapMessage(byte[] data) {
		super();
		this.data = data;
	}
	
	public NewMapMessage(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}

	@Override
	public void deserialize() {
		ByteBuffer buffer = ByteBuffer.wrap(this.data);
		
		this.sizeX = buffer.getInt();
		this.sizeY = buffer.getInt();
	}

	@Override
	public void serialize() {
		ByteBuffer buffer = ByteBuffer.allocate(16);
		
		buffer.putInt(this.sizeX);
		buffer.putInt(this.sizeY);
		
		this.data = buffer.array();
	}

}

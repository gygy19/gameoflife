package org.gameofthelife.server.network.messages;

import java.nio.ByteBuffer;

import org.gameofthelife.server.network.NetworkMessage;
import org.gameofthelife.server.network.handler.reflexion.MessageHandlerController;

@MessageHandlerController(10)
public class PauseMessage extends NetworkMessage {

	public static final int MESSAGE_ID = 10;
	
	public boolean onPause;
	
	@Override
	public int getTypeId() {
		return MESSAGE_ID;
	}

	@Override
	public String getName() {
		return "PauseMessage";
	}
	
	public PauseMessage(byte[] data) {
		super();
		this.data = data;
	}
	
	public PauseMessage(boolean onPause) {
		this.onPause = onPause;
	}

	@Override
	public void deserialize() {
		ByteBuffer buffer = ByteBuffer.wrap(this.data);
		
		this.onPause = buffer.getInt() == 1;
	}

	@Override
	public void serialize() {
		ByteBuffer buffer = ByteBuffer.allocate(8);
		
		buffer.putInt(this.onPause ? 1 : 0);
		this.data = buffer.array();
	}

	
}

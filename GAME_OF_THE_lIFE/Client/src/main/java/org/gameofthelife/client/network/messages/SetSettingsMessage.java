package org.gameofthelife.client.network.messages;

import java.nio.ByteBuffer;

import org.gameofthelife.client.network.NetworkMessage;
import org.gameofthelife.client.network.handler.reflexion.MessageHandlerController;

@MessageHandlerController(2)
public class SetSettingsMessage extends NetworkMessage {

	public static final int MESSAGE_ID = 2;
	
	public int sizeMapX;
	public int sizeMapY;
	public int refreshTime;
	public int interval_life;
	
	@Override
	public int getTypeId() {
		return MESSAGE_ID;
	}

	@Override
	public String getName() {
		return "SetSettingsMessage";
	}
	
	public SetSettingsMessage(byte[] data) {
		super();
		this.data = data;
	}
	
	public SetSettingsMessage(int sizeMapX, int sizeMapY, int refreshTime, int interval_life) {
		super();
		this.sizeMapX = sizeMapX;
		this.sizeMapY = sizeMapY;
		this.refreshTime = refreshTime;
		this.interval_life = interval_life;
	}

	@Override
	public void deserialize() {
		ByteBuffer buffer = ByteBuffer.wrap(this.data);
		
		this.sizeMapX = buffer.getInt();
		this.sizeMapY = buffer.getInt();
		this.refreshTime = buffer.getInt();
		this.interval_life = buffer.getInt();
	}

	@Override
	public void serialize() {
		ByteBuffer buffer = ByteBuffer.allocate(16);
		
		buffer.putInt(this.sizeMapX);
		buffer.putInt(this.sizeMapY);
		buffer.putInt(this.refreshTime);
		buffer.putInt(this.interval_life);
		this.data = buffer.array();
	}

}

package org.gameofthelife.server.network.messages;

import java.nio.ByteBuffer;

import org.gameofthelife.server.entity.Particl;
import org.gameofthelife.server.network.NetworkMessage;
import org.gameofthelife.server.network.handler.reflexion.MessageHandlerController;

@MessageHandlerController(6)
public class AddOneParticlMessage extends NetworkMessage{

	public static final int MESSAGE_ID = 6;
	
	public Particl part;
	
	@Override
	public int getTypeId() {
		// TODO Auto-generated method stub
		return MESSAGE_ID;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "AddOneParticlMessage";
	}
	
	public AddOneParticlMessage(byte[] data) {
		super();
		this.data = data;
	}
	
	public AddOneParticlMessage(Particl p) {
		this.part = p;
	}

	@Override
	public void deserialize() {
		ByteBuffer buffer = ByteBuffer.wrap(this.data);
		
		int x = buffer.getInt();
		int y = buffer.getInt();
		
		this.part = new Particl(x, y);
	}

	@Override
	public void serialize() {
		ByteBuffer buffer = ByteBuffer.allocate(8);
		
		buffer.putInt(this.part.x());
		buffer.putInt(this.part.y());
		
		this.data = buffer.array();
	}

}

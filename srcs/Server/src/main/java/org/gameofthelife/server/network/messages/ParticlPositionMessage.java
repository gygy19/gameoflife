package org.gameofthelife.server.network.messages;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;

import org.gameofthelife.entity.Particl;
import org.gameofthelife.server.network.NetworkMessage;
import org.gameofthelife.server.network.handler.reflexion.MessageHandlerController;

@MessageHandlerController(5)
public class ParticlPositionMessage extends NetworkMessage {

	public static final int MESSAGE_ID = 5;
	
	public Collection<Particl> particls;
	
	@Override
	public int getTypeId() {
		return MESSAGE_ID;
	}

	@Override
	public String getName() {
		return "StartPositionParticlMessage";
	}
	
	public ParticlPositionMessage(byte[] data) {
		super();
		this.data = data;
	}
	
	public ParticlPositionMessage(Collection<Particl> particls) {
		this.particls = particls;
	}

	@Override
	public void deserialize() {
		ByteBuffer buffer = ByteBuffer.wrap(this.data);
		
		particls = new ArrayList<Particl>();
		int arraySize = buffer.getInt();
		
		for (int i = 0; i < arraySize; i++) {
			
			int x = buffer.getShort();
			int y = buffer.getShort();
			
			Particl p = new Particl(x, y);
			particls.add(p);
		}
	}

	@Override
	public void serialize() {
		ByteBuffer buffer = ByteBuffer.allocate(8 + ((particls.size() * 2) * 2));
		
		buffer.putInt(particls.size());
		
		for (Particl p : particls) {
			
			buffer.putShort((short)p.x());
			buffer.putShort((short)p.y());
		}
		this.data = buffer.array();
	}

}

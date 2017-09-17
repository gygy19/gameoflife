package org.gameofthelife.server.network;

/**
 * @author jguyet
 * abstract newtorkMessage
 */
public abstract class NetworkMessage implements NetworkType{
	
	protected byte[] 	data = new byte[0];
	protected int		offset = 0;
	
	public NetworkMessage() {
		
	}
	
	/**
	 * Serialize header
	 * @return
	 */
	public byte[] serializeHeader() {
		int packetlen = this.data.length;
		byte[] header = NetworkProtocolMessage.writeHeader(this.getTypeId(), packetlen);
		byte[] packet = new byte[packetlen + header.length];
		
		System.arraycopy(header, 0, packet, 0, header.length);
        System.arraycopy(data, 0, packet, header.length, data.length);
        this.data = packet;
		return packet;
	}
	
	/**
	 * get Data byte Array
	 * @return
	 */
	public byte[] getData() {
		return (this.data);
	}
	
	/**
	 * set Data byte array
	 * @param data
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

	public abstract int getTypeId();
	
	public abstract String getName();
	
	public abstract void deserialize();
	
	public abstract void serialize();
	
}

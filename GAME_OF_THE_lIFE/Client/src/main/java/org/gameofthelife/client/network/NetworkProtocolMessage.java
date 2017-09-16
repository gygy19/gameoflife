package org.gameofthelife.client.network;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.gameofthelife.client.network.messages.DefaultNetworkMessage;

public class NetworkProtocolMessage {

	public static int STATIC_HEADER_LEN = 4;
	
	public static int readMessageLen(byte[] len) {
		ByteBuffer buffer = ByteBuffer.wrap(len);
		
		return (buffer.getInt());
    }
	
	public static DefaultNetworkMessage readHeader(InputStream socketIn) throws IOException {
		
		byte[] headerbyte = new byte[NetworkProtocolMessage.STATIC_HEADER_LEN];
		ByteBuffer buffer;
    	int header = 0;
    	int datalen = 0;
    	byte[] messageLenBuffer = new byte[0];
    	int currentPointHeader = 0;
    	
    	if (socketIn.available() < NetworkProtocolMessage.STATIC_HEADER_LEN)
    	{
	    	while (currentPointHeader < NetworkProtocolMessage.STATIC_HEADER_LEN)
	    	{
	    		byte[] b = new byte[1];
	    		int ret = socketIn.read(b);
	    		if (ret == -1)
	    			throw new IOException();
	    		if (ret > 0)
	    			headerbyte[currentPointHeader++] = b[0];
	    		else
	    		{
	    			try {Thread.sleep(50);} catch (InterruptedException e) {}
	    		}
	    	}
    	}
    	else
    	{
    		socketIn.read(headerbyte);
    	}
    	
    	buffer = ByteBuffer.wrap(headerbyte);
    	header = buffer.getInt();
    	
    	while (socketIn.available() < 4)
    		try {Thread.sleep(10);} catch (InterruptedException e) {}
    	messageLenBuffer = new byte[4];
        socketIn.read(messageLenBuffer);
		datalen = readMessageLen(messageLenBuffer);
		return (new DefaultNetworkMessage(header, datalen));
	}
	
	public static byte[] writeHeader(int packetId, int len) {
		ByteBuffer buffer = ByteBuffer.allocate(8);
		
		buffer.putInt(packetId);
		buffer.putInt(len);
        return (buffer.array());
	}
}

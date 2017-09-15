package org.gameofthelife.server.network;

import java.io.IOException;
import java.io.InputStream;

import org.gameofthelife.server.network.messages.DefaultNetworkMessage;

public class NetworkProtocolMessage {

	public static int STATIC_HEADER_LEN = 2;
	
	public static int readMessageLen(byte[] len) {
		return (((len[0] & 0xFF) << 8) | (len[1] & 0xFF));
    }
	
	public static DefaultNetworkMessage readHeader(InputStream socketIn) throws IOException {
		
		byte[] headerbyte = new byte[NetworkProtocolMessage.STATIC_HEADER_LEN];
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
    	
    	header = (headerbyte[0] & 0xFF) << 8 | (headerbyte[1] & 0xFF);
    	
    	while (socketIn.available() < 2)
    		try {Thread.sleep(10);} catch (InterruptedException e) {}
    	messageLenBuffer = new byte[2];
        socketIn.read(messageLenBuffer);
		datalen = readMessageLen(messageLenBuffer);
		return (new DefaultNetworkMessage(header, datalen));
	}
	
	public static byte[] writeHeader(int packetId, int len) {
		byte[] header = new byte[4];
		
		header[0] = (byte)(packetId >> 8);
        header[1] = (byte)(packetId);
        header[2] = (byte)(len >> 8);
        header[3] = (byte)(len);
        return (header);
	}
}

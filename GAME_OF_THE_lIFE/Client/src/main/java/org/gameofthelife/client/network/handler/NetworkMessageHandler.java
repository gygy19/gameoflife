package org.gameofthelife.client.network.handler;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import org.gameofthelife.client.handler.factory.ClientMessageHandler;
import org.gameofthelife.client.network.NetworkMessage;
import org.gameofthelife.client.network.NetworkProtocolMessage;
import org.gameofthelife.client.network.TcpDataHandler;
import org.gameofthelife.client.network.messages.DefaultNetworkMessage;

public class NetworkMessageHandler implements TcpDataHandler {

	private MessageFactoryImplementation messageFactory = new MessageFactoryImplementation();
	private ClientMessageHandler clientMessageHandler = new ClientMessageHandler();
	private DefaultNetworkMessage _message = null;
	private boolean _splittedPacket;
	
	
	public void handleTcpData(Socket sock) throws IOException {
		
		while (!sock.isClosed())
        {
            readMessage(sock.getInputStream());
            
            if (this._splittedPacket)
            	continue ;
            if (this._message == null)
            	continue ;
            NetworkMessage message = this.messageFactory.createMessage(this._message);
            
            if (message == null) {
            	System.out.println("Message [" + this._message.getTypeId() + "] doesn't exist");
            	continue ;
            }
            System.out.println("new Message [" + message.getTypeId() + "]");
            message.deserialize();
            
            if (!clientMessageHandler.handleMessage(message)) {
            	//error
            }
        }
	}
	
	private void readMessage(InputStream socketIn) throws IOException {
		if (!this._splittedPacket) {
			this._message = NetworkProtocolMessage.readHeader(socketIn);//wait new message
			
			
			System.out.println("MessageID : " + this._message.getTypeId());
			int packetLen = this._message.getPacketLen();
			System.out.println("Messagelen : " + packetLen);
			if (packetLen <= 0)
				return ;
			if (socketIn.available() < packetLen) {
				packetLen = socketIn.available();
			}
			
			byte[] messagePartBuffer = new byte[packetLen];
	        int readed = socketIn.read(messagePartBuffer);
	        
	        if (readed < this._message.getPacketLen())
	        	this._splittedPacket = true;
	        if (readed == 0)
	        	return ;
	        this._message.setData(messagePartBuffer);
		} else {
			if (socketIn.available() <= 0)
				return ;
			int packetLen = this._message.getPacketLen();
			int partLen = packetLen - this._message.getData().length;
			
			byte[] messagePartBuffer = new byte[partLen];
			byte[] messageBuffer = new byte[packetLen];
	        int readed = socketIn.read(messagePartBuffer);
	        
	        System.arraycopy(this._message.getData(), 0, messageBuffer, 0, this._message.getData().length);
	        System.arraycopy(messagePartBuffer, 0, messageBuffer, this._message.getData().length, readed);
	        this._message.setData(messageBuffer);
			
			if (readed >= partLen) {
		        this._splittedPacket = false;
			}
		}
	}

}

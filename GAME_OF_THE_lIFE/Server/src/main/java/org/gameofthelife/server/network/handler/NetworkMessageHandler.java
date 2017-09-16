package org.gameofthelife.server.network.handler;

import java.io.IOException;
import java.io.InputStream;

import org.gameofthelife.server.handler.factory.ServerMessageHandler;
import org.gameofthelife.server.network.NetworkMessage;
import org.gameofthelife.server.network.NetworkProtocolMessage;
import org.gameofthelife.server.network.TcpClientDataHandler;
import org.gameofthelife.server.network.client.TcpClient;
import org.gameofthelife.server.network.messages.DefaultNetworkMessage;
import org.gameofthelife.server.network.messages.HelloMessage;

public class NetworkMessageHandler implements TcpClientDataHandler {

	private MessageFactoryImplementation messageFactory = new MessageFactoryImplementation();
	private ServerMessageHandler serverMessageHandler = new ServerMessageHandler();
	private DefaultNetworkMessage _message = null;
	private boolean _splittedPacket;
	
	public void onClientConnected(TcpClient client) throws IOException {
		
		HelloMessage helloMessage = new HelloMessage();
		
		client.sendMessage(helloMessage);
	}
	
	public void handleTcpData(TcpClient client) throws IOException {
		
		while (!client.getSession().isClosed())
        {	
            readMessage(client.getSession().getInputStream());
            
            if (this._splittedPacket)
            	continue ;
            
            System.out.println("Message Readed");
            NetworkMessage message = this.messageFactory.createMessage(this._message);
            
            if (message == null) {
            	System.out.println("Message [" + this._message.getTypeId() + "] doesn't exist");
            	continue ;
            }
            System.out.println("new Message [" + message.getTypeId() + "]");
            message.deserialize();
            
            if (!serverMessageHandler.handleMessage(client, message)) {
            	//error
            }
        }
	}
	
	private void readMessage(InputStream socketIn) throws IOException {
		if (!this._splittedPacket) {
			this._message = NetworkProtocolMessage.readHeader(socketIn);//wait new message
			
			int packetLen = this._message.getPacketLen();
			if (packetLen <= 0)
				return ;
			if (socketIn.available() < packetLen) {
				packetLen = socketIn.available();
			}
			
			byte[] messagePartBuffer = new byte[packetLen];
	        int readed = socketIn.read(messagePartBuffer);
	        
	        byte[] messageBuffer = new byte[readed];
	        
	        System.arraycopy(messagePartBuffer, 0, messageBuffer, 0, readed);
	        
	        if (readed < this._message.getPacketLen()) {
	        	this._splittedPacket = true;
	        }
	        if (readed == 0) {
	        	this._message.setData(new byte[0]);
	        	return ;
	        }
	        this._message.setData(messageBuffer);
		} else {
			if (socketIn.available() <= 0)
				return ;
			int packetLen = this._message.getPacketLen();
			int partLen = packetLen - this._message.getData().length;
			
			byte[] messagePartBuffer = new byte[partLen];
	        int readed = socketIn.read(messagePartBuffer);
	        
	        byte[] messageBuffer = new byte[readed + this._message.getData().length];
	        
	        System.arraycopy(this._message.getData(), 0, messageBuffer, 0, this._message.getData().length);
	        System.arraycopy(messagePartBuffer, 0, messageBuffer, this._message.getData().length, readed);
	        this._message.setData(messageBuffer);
			if (messageBuffer.length >= packetLen) {
		        this._splittedPacket = false;
			}
		}
	}

}

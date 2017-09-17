package org.gameofthelife.client.network.handler;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import org.gameofthelife.client.Main;
import org.gameofthelife.client.handler.factory.ClientMessageHandler;
import org.gameofthelife.client.network.NetworkMessage;
import org.gameofthelife.client.network.NetworkProtocolMessage;
import org.gameofthelife.client.network.TcpDataHandler;
import org.gameofthelife.client.network.handler.factory.MessageFactoryImplementation;
import org.gameofthelife.client.network.messages.DefaultNetworkMessage;

/**
 * @author jguyet
 *
 */
public class NetworkMessageHandler implements TcpDataHandler {

	private MessageFactoryImplementation messageFactory = new MessageFactoryImplementation();
	private ClientMessageHandler clientMessageHandler = new ClientMessageHandler();
	private DefaultNetworkMessage _message = null;
	private boolean _splittedPacket;
	
	
	/**
	 * handler tcp message loop temp sock is opened and Main.connected == true
	 * step1 : read message
	 * step2 : factorys message
	 * step3 : deserialize message informations
	 * setp4 : handle message to method handler
	 */
	public void handleTcpData(Socket sock) throws IOException {
		
		while (!sock.isClosed() && Main.connected)
        {
            readMessage(sock.getInputStream());
            
            if (this._splittedPacket)
            	continue ;
            if (this._message == null)
            	continue ;
            NetworkMessage message = this.messageFactory.createMessage(this._message);
            
            if (message == null) {
            	continue ;
            }
            message.deserialize();
            
            if (!clientMessageHandler.handleMessage(message)) {
            	//error
            }
        }
	}
	
	/**
	 * wait new message on method NetworkProtocolMessage.readHeader and read 8bytes (header protocol size)
	 * if available bytes is < of message length this._splittedPacket = true and loop to end message.
	 * @param socketIn
	 * @throws IOException
	 */
	private void readMessage(InputStream socketIn) throws IOException {
		
		if (!this._splittedPacket) {
			//read header message
			this._message = NetworkProtocolMessage.readHeader(socketIn);//wait new message
			
			//assigne length packet a read
			int packetLen = this._message.getPacketLen();
			
			if (packetLen <= 0)
				return ;
			
			if (socketIn.available() < packetLen) {
				packetLen = socketIn.available();
			}
			
			//prepare part buffer and read
			byte[] messagePartBuffer = new byte[packetLen];
	        int readed = socketIn.read(messagePartBuffer);
	        
	        //prepare buffer of size readed size
	        byte[] messageBuffer = new byte[readed];
	        
	        //copie length readed to final buffer
	        System.arraycopy(messagePartBuffer, 0, messageBuffer, 0, readed);
	        
	        //set splitted packet if length is inf
	        if (readed < this._message.getPacketLen()) {
	        	this._splittedPacket = true;
	        }
	        
	        //set buffer to 0 if readed is equal to zero
	        if (readed == 0) {
	        	this._message.setData(new byte[0]);
	        	return ;
	        }
	        //set final buffer to defaultmessage
	        this._message.setData(messageBuffer);
	        
		} else {
			//loop if available is < or equals to zero
			if (socketIn.available() <= 0)
				return ;
			//assig current length readed and packet total length
			int packetLen = this._message.getPacketLen();
			int partLen = packetLen - this._message.getData().length;
			
			//prepare part buffer and read
			byte[] messagePartBuffer = new byte[partLen];
	        int readed = socketIn.read(messagePartBuffer);
	        
	        //prepare buffer of size readed size with after readed
	        byte[] messageBuffer = new byte[readed + this._message.getData().length];
	        
	        //copie length readed to final buffer
	        System.arraycopy(this._message.getData(), 0, messageBuffer, 0, this._message.getData().length);
	        System.arraycopy(messagePartBuffer, 0, messageBuffer, this._message.getData().length, readed);
	        
	        //set final buffer to defaultmessage
	        this._message.setData(messageBuffer);
	        
	        //set  this._splittedPacket to false if message totality readed
			if (messageBuffer.length >= packetLen) {
		        this._splittedPacket = false;
			}
		}
	}

}

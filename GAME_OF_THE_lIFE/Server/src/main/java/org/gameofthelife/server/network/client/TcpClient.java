package org.gameofthelife.server.network.client;

import java.io.IOException;
import java.net.Socket;

import org.gameofthelife.server.network.NetworkMessage;
import org.gameofthelife.server.network.messages.SetSettingsMessage;

public class TcpClient {

	private Socket session;
	
	public TcpClient(Socket session) {
		this.session = session;
	}
	
	public Socket getSession() {
		return (this.session);
	}
	
	public void sendMessage(NetworkMessage message) throws IOException {
		message.serialize();
		message.serializeHeader();
        byte[] messageContent = message.getData();
        
        System.out.println("message " + message.getTypeId() + " sended size: " + messageContent.length);
        this.session.getOutputStream().write(messageContent, 0, messageContent.length);
	}
}

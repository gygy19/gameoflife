package org.gameofthelife.server.network;

import java.io.IOException;

import org.gameofthelife.server.network.client.TcpClient;

/**
 * @author jguyet
 *
 */
public interface TcpClientDataHandler {
	void onClientConnected(TcpClient client) throws IOException;
	void handleTcpData(TcpClient client) throws IOException;
}

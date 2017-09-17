package org.gameofthelife.client.network;

import java.io.IOException;
import java.net.Socket;

/**
 * @author jguyet
 *
 */
public interface TcpDataHandler {
	void handleTcpData(Socket sock) throws IOException;
}

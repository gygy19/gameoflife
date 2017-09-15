package org.gameofthelife.client.network;

import java.io.IOException;
import java.net.Socket;

public interface TcpDataHandler {
	void handleTcpData(Socket sock) throws IOException;
}

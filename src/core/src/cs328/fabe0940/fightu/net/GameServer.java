package cs328.fabe0940.fightu.net;

import java.io.IOException;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class GameServer {
	private boolean running;
	public Server server;

	public GameServer() throws IOException {
		Log.set(Log.LEVEL_DEBUG);

		server = new Server();

		Network.register(server);

		server.bind(Network.port);
		server.start();

		running = true;
	}

	public void listen(Listener l) {
		server.addListener(l);
	}
}

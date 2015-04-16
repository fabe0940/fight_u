package cs328.fabe0940.fightu;

import java.io.IOException;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

class GameServer implements Runnable {
	private boolean running;
	private Server server;

	public GameServer() throws IOException {
		Log.set(Log.LEVEL_DEBUG);

		server = new Server() {
			protected Connection newConnection() {
				return new ClientConnection();
			}
		};

		Network.register(server);

		server.addListener(new Listener() {
			public void recieved(Connection c, Object o) {
				String str;
				Network.StringMessage msg;
				ClientConnection connection;

				connection = (ClientConnection) c;

				if (o instanceof Network.StringMessage) {
					str = ((Network.StringMessage) o).text;

					msg = new Network.StringMessage();
					msg.text = str;

					server.sendToAllExceptTCP(
						connection.getID(), msg);
				}
			}
		});

		server.bind(Network.port);
		server.start();

		running = true;
	}

	public void run() {
		while(running) {
			/* do nothing */
		}

		server.stop();
	}

	public synchronized void stop() {
		running = false;
	}

	public static class ClientConnection extends Connection {
		public int ID;
	}
}

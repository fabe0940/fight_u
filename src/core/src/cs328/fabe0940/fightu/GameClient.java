package cs328.fabe0940.fightu;

import java.io.IOException;
import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

public class GameClient {
	public static final int TIMEOUT = 5000;

	public Client client;

	public GameClient() {
		Log.set(Log.LEVEL_DEBUG);

		client = new Client();
		client.start();

		Network.register(client);

		client.addListener(new Listener() {
			public void connected(Connection c) {
				Network.StringMessage msg;

				Gdx.app.log("", "Connection successful!");

				msg = new Network.StringMessage();
				msg.text = "CONNECT";

				client.sendTCP(msg);
			}

			public void received(Connection c, Object o) {
				String msg;

				if (o instanceof Network.StringMessage) {
					msg = ((Network.StringMessage) o).text;
					Gdx.app.log(">", msg);
				}
			}

			public void disconnected(Connection c) {
				Gdx.app.log("", "Connection closed!");
			}
		});
	}

	public boolean connect(String host, int port) {
		try {
			client.connect(TIMEOUT, host, port);
		} catch (IOException e) {
			Gdx.app.error("GameClient:connect",
				"Unable to connect to host");
			return false;
		}

		return true;
	}
}

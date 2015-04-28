package cs328.fabe0940.fightu.net;

import java.io.IOException;
import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

public class GameClient {
	public static final int TIMEOUT = 3000;
	public static final int STATE_NEW = 1;
	public static final int STATE_READY = 2;
	public static final int STATE_CONNECTED = 3;
	public static final int STATE_DISCONNECTED = 4;

	private int state;
	public Client client;

	public GameClient() {
		Log.set(Log.LEVEL_DEBUG);

		state = STATE_NEW;

		client = new Client();
		client.start();

		Network.register(client);
	}

	public void listen(Listener l) {
		client.addListener(l);
		
		if (state == STATE_NEW) {
			state = STATE_READY;
		}
	}

	public boolean connect(String host, int port) {
		if (state != STATE_READY) {
			return false;
		}

		try {
			client.connect(TIMEOUT, host, port);
			state = STATE_CONNECTED;
		} catch (IOException e) {
			Gdx.app.error("GameClient:connect",
				"Unable to connect to host");
			return false;
		}

		return true;
	}
}

package cs328.fabe0940.fightu;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {
	static public final int port = 13370;

	static public void register(EndPoint endPoint) {
		Kryo kryo;

		kryo = endPoint.getKryo();
		kryo.register(StringMessage.class);
	}

	static public class StringMessage {
		public String text;
	}
}

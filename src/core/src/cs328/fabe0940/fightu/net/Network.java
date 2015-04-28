package cs328.fabe0940.fightu.net;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {
	static public final int port = 13370;

	static public void register(EndPoint endPoint) {
		Kryo kryo;

		kryo = endPoint.getKryo();
		kryo.register(StringMessage.class);
		kryo.register(EntityMessage.class);
	}

	static public class StringMessage {
		public String text;
	}

	static public class EntityMessage {
		public Array<Entity> entities;
	}
}

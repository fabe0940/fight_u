package cs328.fabe0940.fightu.net;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import cs328.fabe0940.fightu.components.StateComponent;
import cs328.fabe0940.fightu.components.TransformComponent;

public class Network {
	static public final int port = 13370;

	static public void register(EndPoint endPoint) {
		Kryo kryo;

		kryo = endPoint.getKryo();
		kryo.register(EntityMessage.class);
		kryo.register(EntityClearMessage.class);
		kryo.register(GameOverMessage.class);
		kryo.register(KeyDownMessage.class);
		kryo.register(KeyUpMessage.class);
		kryo.register(StringMessage.class);
		kryo.register(StateComponent.class);
		kryo.register(TimeMessage.class);
		kryo.register(TransformComponent.class);
		kryo.register(Vector2.class);
		kryo.register(Vector3.class);
	}

	static public class EntityMessage {
		public StateComponent state;
		public TransformComponent pos;
	}

	static public class EntityClearMessage {
	}

	static public class GameOverMessage {
		public int winner;
	}

	static public class KeyDownMessage {
		public int keycode;
	}

	static public class KeyUpMessage {
		public int keycode;
	}

	static public class StringMessage {
		public String text;
	}

	static public class TimeMessage {
		public int time;
	}
}
